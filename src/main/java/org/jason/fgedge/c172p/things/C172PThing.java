package org.jason.fgedge.c172p.things;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import org.jason.fgcontrol.aircraft.c172p.C172P;
import org.jason.fgcontrol.aircraft.c172p.C172PConfig;
import org.jason.fgcontrol.aircraft.c172p.C172PFields;
import org.jason.fgcontrol.aircraft.c172p.flight.RunwayBurnoutFlightExecutor;
import org.jason.fgcontrol.aircraft.c172p.flight.WaypointFlightExecutor;
import org.jason.fgcontrol.aircraft.fields.FlightGearFields;
import org.jason.fgcontrol.exceptions.AircraftStartupException;
import org.jason.fgcontrol.flight.position.WaypointManager;
import org.jason.fgcontrol.flight.position.WaypointPosition;
import org.jason.fgedge.sshd.EdgeSSHDServer;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.metadata.PropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceParameter;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.types.InfoTable;
import com.thingworx.types.collections.AspectCollection;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.types.constants.Aspects;
import com.thingworx.types.constants.CommonPropertyNames;
import com.thingworx.types.primitives.BooleanPrimitive;

public class C172PThing extends VirtualThing {

    private static final Logger LOGGER = LoggerFactory.getLogger(C172PThing.class);
    
    private static final long serialVersionUID = -1670069869427933890L;   

    public static final int FLIGHTPLAN_RUNWAY = 0;
    public static final int FLIGHTPLAN_FLYAROUND = 1;
    
    public static final TreeSet<Integer> SUPPORTED_FLIGHTPLANS = new TreeSet<>() {
        private static final long serialVersionUID = 304016809906622587L;

        {
            add(FLIGHTPLAN_RUNWAY);
            add(FLIGHTPLAN_FLYAROUND);
        }
    };
    
    private final static boolean ENABLE_TUNNELING = true;
    
    //////////
    
    private final static int SUBSCRIBED_PROPERTIES_UPDATE_TIMEOUT = 250;
    private final static int SUBSCRIBED_EVENTS_UPDATE_TIMEOUT = 250;
    
    ////////
    //datashape names
    private final static String CONSUMABLES_SHAPE_NAME = "ConsumablesShape";
    private final static String CONTROL_SHAPE_NAME = "ControlShape";
    private final static String ENGINE_SHAPE_NAME = "EngineShape";
    private final static String ENVIRONMENT_SHAPE_NAME = "EnvironmentShape";
    private final static String FDM_SHAPE_NAME = "FDMShape";
    private final static String ORIENTATION_SHAPE_NAME = "OrientationShape";
    private final static String POSITION_SHAPE_NAME = "PositionShape";
    private final static String SIM_SHAPE_NAME = "SimShape";
    private final static String SIM_MODEL_SHAPE_NAME = "SimModelShape";
    private final static String SYSTEMS_SHAPE_NAME = "SystemsShape";
    private final static String VELOCITIES_SHAPE_NAME = "VelocitiesShape";
    
    private final static String FLIGHTPLAN_SHAPE_NAME = "FlightPlanShape";
    
    //a nice, clear, warm, bright date/time in western canada
    private final static String LAUNCH_TIME_GMT = "2021-07-01T20:00:00";
    
    private C172P plane;

    //default plan is the runwaytest
    private int flightPlan;
    
    private Thread flightThread;

	private EdgeSSHDServer sshdServer = null;
	private Thread sshdServerThread;

	public C172PThing(String name, String description, String identifer, ConnectedThingClient client) 
		throws Exception 
	{
		this(name, description, identifer, client, new C172PConfig());
	}
	
    public C172PThing(String name, String description, String identifer, ConnectedThingClient client, C172PConfig simConfig) 
    	throws Exception 
    {
        super(name, description, identifer, client);
        
        ///////////////////////        
        //init plane object
        plane = new C172P(simConfig);
                
        // Populate the thing shape with any properties, services, and events that are annotated in
        // this code
        super.initializeFromAnnotations();
        this.init();
        
        //edge embedded sshd server
        if(ENABLE_TUNNELING) {
        	
        	//TODO: grab any relevant directives from the config file
        	
	        sshdServer = new EdgeSSHDServer();
	        sshdServerThread = new Thread(sshdServer);
	        sshdServerThread.start();
	    }
        
        flightPlan = FLIGHTPLAN_FLYAROUND;
                
        ///////////////////////
        //properties
        
        AspectCollection persist = new AspectCollection();
        persist.put(Aspects.ASPECT_ISPERSISTENT, new BooleanPrimitive(true));

        //enable persist only for telemetry that should persist between flights (executions of the client application)
        //not applicable for most live telemetry
        
        //could do the property initialization with annotations, but using the api directly is easier to troubleshoot
        
        for( PropertyDefinition propertyDefinition : PropertyInitializer.buildProperties() ) {
            LOGGER.debug("Defining property name: {}, Type: {} => {}", 
                    propertyDefinition.getName(), 
                    propertyDefinition.getBaseType().name(),
                    propertyDefinition.getDescription()
            );
            
            super.defineProperty(propertyDefinition);
        }
        
        ///////////////////////
        //events
        
        //altitude correction event
        //heading correction event
        //yaw threshold event
        //pitch correction event
        //low fuel event
        //water in fuel tank alert
        
        ///////////////////////
        //configure flight thread
        
        flightThread = new Thread() {
            @Override
            public void run() {
                LOGGER.trace("flight thread started");
                
                //TODO: select a flight plan based on config
                try {
                    if( flightPlan == FLIGHTPLAN_RUNWAY ) {
                        runRunwayFlightPlan();
                    } else if ( flightPlan == FLIGHTPLAN_FLYAROUND ) {
                        runFlyAroundFlightPlan();
                    } else {
                        LOGGER.error("Unsupported flight plan. Terminating.");
                    }         
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                } catch (AircraftStartupException e) {
                    LOGGER.error(e.getMessage(), e);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
                
                LOGGER.trace("flight thread returning");
            }
        };
        
        ///////////////////////
    }
        
    public void setFlightPlan(int plan) {
        flightPlan = plan;
    }
    
    public void setRoute( ArrayList<WaypointPosition> route) {
    	plane.setWaypoints(route);
    }
    
    private void init() throws Exception {
        LOGGER.trace("init invoked");
        
        super.initialize();
        
        ///////////////////////
        //define datashape definition, mostly for service invocation result infotables and events

        //Consumables
        defineDataShapeDefinition(CONSUMABLES_SHAPE_NAME, DataShapeInitializer.buildConsumablesShape());

        //Control
        defineDataShapeDefinition(CONTROL_SHAPE_NAME, DataShapeInitializer.buildControlShape());
        
        //Engine
        defineDataShapeDefinition(ENGINE_SHAPE_NAME, DataShapeInitializer.buildEngineShape());
        
        //Environment
        defineDataShapeDefinition(ENVIRONMENT_SHAPE_NAME, DataShapeInitializer.buildEnvironmentShape());

        //FDM
        defineDataShapeDefinition(FDM_SHAPE_NAME, DataShapeInitializer.buildFDMShape());
        
        //Orientation
        defineDataShapeDefinition(ORIENTATION_SHAPE_NAME, DataShapeInitializer.buildOrientationShape());
        
        //Position
        defineDataShapeDefinition(POSITION_SHAPE_NAME, DataShapeInitializer.buildPositionShape());

        //Sim
        defineDataShapeDefinition(SIM_SHAPE_NAME, DataShapeInitializer.buildSimShape());
        
        //Sim model
        defineDataShapeDefinition(SIM_MODEL_SHAPE_NAME, DataShapeInitializer.buildSimModelShape());

        //Systems
        defineDataShapeDefinition(SYSTEMS_SHAPE_NAME, DataShapeInitializer.buildSystemsShape());
        
        //Velocities
        defineDataShapeDefinition(VELOCITIES_SHAPE_NAME, DataShapeInitializer.buildVelocitiesShape());

        //waypoint readout
        defineDataShapeDefinition(FLIGHTPLAN_SHAPE_NAME, DataShapeInitializer.buildFlightPlanShape());
        
        LOGGER.trace("init returning");
    }
    
    //separate function, so plane can be started after bind
    public void startupPlane() throws Exception {
        LOGGER.debug("startup invoked");
        
        //start plane. function should return or it'll block everything else
        plane.startupPlane();
        
        //set the parking brake so the plane doesn't start rolling
        //enabled by default, but the default c172p autostart disables it
        plane.setParkingBrake(true);
        
        LOGGER.debug("startup returning");
    }
    
    public void executeFlightPlan() {
        LOGGER.trace("executeFlightPlan invoked");
        
        //start the flight thread
        flightThread.start();
        
        LOGGER.trace("executeFlightPlan returning");
    }
    
    @Override
    public void synchronizeState() {
        
        //check readytosend?
        
        super.syncProperties();
    }
    
    /**
     * VirtualThing-level edge execution
     * 
     * @throws Exception 
     *
     */
    @Override
    public void processScanRequest() throws Exception {
        LOGGER.trace("processScanRequest invoked");
        
        super.processScanRequest();
        
        this.scanDevice();
        
        LOGGER.trace("processScanRequest returning");

    }
    
    /**
     * Thing-specific edge execution
     * 
     * @throws Exception 
     * 
     */
    private void scanDevice() throws Exception {
    	
    	//connectivityCallback(?)
    	//defined by client?
    	//connectivity not really part of thing model
    	
    	//how to manage boundary transitions?
    	//this.getClient().isConnected()
    	
    	C172PDeviceScanner.DeviceScanner(this, plane.getTelemetry());

        this.updateSubscribedProperties(SUBSCRIBED_PROPERTIES_UPDATE_TIMEOUT);
        this.updateSubscribedEvents(SUBSCRIBED_EVENTS_UPDATE_TIMEOUT);
    }
    
    /////////////////////////
    //plane services
    /////////////////////////
    
    //waypoints
    @ThingworxServiceDefinition
    (
       name = "AddWaypoint", 
       description = "Append a new waypoint to the end of the flight plan"
    )
    public synchronized void AddWaypoint(
        @ThingworxServiceParameter
        ( 
        	name="latitude", 
        	description="latitude in degrees", 
        	baseType="NUMBER" 
        ) Double latitude,
    	@ThingworxServiceParameter
    	( 
    		name="longitude", 
    		description="longitude in degrees", 
    		baseType="NUMBER" 
    	) Double longitude
    ) throws Exception
    {
    	plane.addWaypoint(latitude, longitude);
    }
    
    @ThingworxServiceDefinition
    (
       name = "AddNextWaypoint", 
       description = "Append a new waypoint to the beginning of the flight plan"
    )
    public synchronized void AddNextWaypoint(
        @ThingworxServiceParameter
        ( 
        	name="latitude", 
        	description="latitude in degrees", 
        	baseType="NUMBER" 
        ) Double latitude,
    	@ThingworxServiceParameter
    	( 
    		name="longitude", 
    		description="longitude in degrees", 
    		baseType="NUMBER" 
    	) Double longitude
    )
    {
    	plane.addNextWaypoint(latitude, longitude);
    }

    //abandon all waypoints
    @ThingworxServiceDefinition
    (
       name = "AbandonCurrentWaypoint", 
       description = "Abandon current waypoint in flight plan, and proceed to the next one."
    )
    public synchronized void AbandonCurrentWaypoint() {
    	plane.abandonCurrentWaypoint();
    }
    
    //remove all waypoints
    @ThingworxServiceDefinition
    (
       name = "ClearWaypoints", 
       description = "Clear the flightplan"
    )
    public synchronized void ClearWaypoints() {
    	plane.clearWaypoints();
    }
    
    //remove waypoint
    @ThingworxServiceDefinition
    (
       name = "RemoveWaypoints", 
       description = "Remove waypoints from the flight plan matching provided lat/lon"
    )
    public synchronized void RemoveWaypoints(
        @ThingworxServiceParameter
        ( 
        	name="latitude", 
        	description="latitude in degrees", 
        	baseType="NUMBER" 
        ) Double latitude,
    	@ThingworxServiceParameter
    	( 
    		name="longitude", 
    		description="longitude in degrees", 
    		baseType="NUMBER" 
    	) Double longitude
    )
    {
    	plane.removeWaypoints(latitude, longitude);
    }
    
    //Get the human-readable flightplan
    @ThingworxServiceDefinition
    (
       name = "GetFlightPlan", 
       description = "Retrieve the flightplan"
    )
	@ThingworxServiceResult
	(
			name = CommonPropertyNames.PROP_RESULT, 
			description = "InfoTable of waypoint data", 
			baseType = "INFOTABLE",
			aspects = { "dataShape:" + FLIGHTPLAN_SHAPE_NAME }
	)
    public synchronized InfoTable GetFlightPlan() throws Exception {
    	
    	LOGGER.debug("Retrieving C172P flight plan");
    	
    	InfoTable table = new InfoTable(getDataShapeDefinition(FLIGHTPLAN_SHAPE_NAME));
    	    	
    	for( WaypointPosition wp : plane.getWaypoints()) {
			
    		//need to create a new vc for the row in the loop body
    		ValueCollection entry = new ValueCollection();
    		
			entry.SetNumberValue(
				EdgeUtilities.toThingworxPropertyName(FlightGearFields.LATITUDE_FIELD), wp.getLatitude());
			entry.SetNumberValue(
				EdgeUtilities.toThingworxPropertyName(FlightGearFields.LONGITUDE_FIELD), wp.getLongitude());
			entry.SetNumberValue(
				EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALTITUDE_FIELD), wp.getAltitude());
			entry.SetStringValue(WaypointManager.WAYPOINT_NAME_FIELD, wp.getName());
			
	    	LOGGER.debug("Logging waypoint: {}", entry.toString());
			
			table.addRow(entry);
    	}

    	LOGGER.debug("Retrieved C172P flight plan");
    	
    	return table;
    }

    /////////////////////////
    //consumables
    /////////////////////////
    
    @ThingworxServiceDefinition
    (
       name = "SetFuelLevel", 
       description = "Set the fuel level for the primary tank"
    )
    public synchronized void SetFuelLevel(
    	@ThingworxServiceParameter
    	( 
    		name="fuelLevel", 
    		description="Fuel level in gallons", 
    		baseType="NUMBER" 
    	) Double fuelLevel
    ) throws Exception 
    {
        LOGGER.trace("SetFuelLevel invoked");
        
        Double newFuelAmount = plane.getFuelTank0Capacity();
        Double tankCapacity = newFuelAmount;
        
        //guard rails
        if(fuelLevel < 0) {
            newFuelAmount = Double.valueOf(0);
        }
        else if (fuelLevel < tankCapacity) {
            newFuelAmount = fuelLevel;
        }
        else {
            //by default refuel to tank capacity
        }
        
        LOGGER.debug("Setting fuel level to: {}", newFuelAmount);
        
        plane.setFuelTank0Level(newFuelAmount);
        plane.setFuelTank1Level(newFuelAmount);
        
        LOGGER.trace("SetFuelLevel returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "SetFuelLevel", 
        description = "Set the water contamination for the primary tank"
    )
    public synchronized void SetFuelTankWaterContamination(
        @ThingworxServiceParameter
        ( 
        	name="waterAmount", 
            description="Water amount in gallons", 
            baseType="NUMBER" 
        ) Double waterAmount
    ) throws Exception 
    {
        LOGGER.trace("SetFuelTankWaterContamination invoked");
        
        double newWaterAmount = 0;
        
        //guard rails
        if(waterAmount < 0) {
            newWaterAmount = Double.valueOf(0);
        }
        
        LOGGER.debug("Setting fuel tank water amount to: {}", newWaterAmount);
        
        plane.setFuelTank0WaterContamination(newWaterAmount);
        plane.setFuelTank1WaterContamination(newWaterAmount);
        
        LOGGER.trace("SetFuelTankWaterContamination returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "GetConsumablesTelemetry", 
        description = "Get consumables telemetry fields for the C172"
    )
    @ThingworxServiceResult
    (
        name = CommonPropertyNames.PROP_RESULT, 
        description = "InfoTable of consumables values", 
        baseType = "INFOTABLE",
        aspects = { "dataShape:" + CONSUMABLES_SHAPE_NAME }
    )
    public synchronized InfoTable GetConsumablesTelemetry() throws Exception 
    {
        LOGGER.trace("GetConsumablesTelemetry invoked");

        ValueCollection entry = new ValueCollection();
        entry.clear();
        
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_CAPACITY_FIELD), plane.getFuelTank0Capacity());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_LEVEL_FIELD), plane.getFuelTank0Level());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_WATER_CONTAMINATION_FIELD), plane.getFuelTank0WaterContamination());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_CAPACITY_FIELD), plane.getFuelTank1Capacity());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_LEVEL_FIELD), plane.getFuelTank1Level());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_WATER_CONTAMINATION_FIELD), plane.getFuelTank1WaterContamination());
 
        InfoTable table = new InfoTable(getDataShapeDefinition(CONSUMABLES_SHAPE_NAME));
        table.addRow(entry);
        
        LOGGER.trace("GetConsumablesTelemetry returning");
        
        return table;
    }
    
    /////////////////////////
    //controls
    /////////////////////////
    
    @ThingworxServiceDefinition
    (
        name = "SetBatterySwitch", 
        description = "Set the battery switch"
    )
    public synchronized void SetBatterySwitch(
        @ThingworxServiceParameter
        ( 
        	name="isEnabled", 
            description="State of the battery switch", 
            baseType="BOOLEAN" 
        ) Boolean isEnabled
    ) throws Exception 
    {
        LOGGER.trace("SetBatterySwitch invoked");
        
        LOGGER.debug("Setting battery switch to: {}", isEnabled);
        
        plane.setBatterySwitch(isEnabled);
        
        LOGGER.trace("SetBatterySwitch returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "SetMixture", 
        description = "Set the fuel mixture level"
    )
    public synchronized void SetMixture(
        @ThingworxServiceParameter
        ( 
        	name="mixtureValue", 
            description="Fuel mixture setting", 
            baseType="NUMBER" 
        ) Double mixtureAmount
    ) throws Exception 
    {
        LOGGER.trace("SetMixture invoked");
        
        if(mixtureAmount < 1.0 && mixtureAmount > 0.0) {
            LOGGER.debug("Setting mixture amount to: {}", mixtureAmount);
            
            plane.setMixture(mixtureAmount);
        } else {
            LOGGER.warn("Ignoring mixture change to invalid amount");
        }
                
        LOGGER.trace("SetMixture returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "SetThrottle", 
        description = "Set the throttle level"
    )
    public synchronized void SetThrottle(
        @ThingworxServiceParameter
        ( 
        	name="throttleAmount", 
            description="Throttle setting", 
            baseType="NUMBER" 
        ) Double throttleAmount
    ) throws Exception 
    {
        LOGGER.trace("SetThrottle invoked");
        
        if(throttleAmount < C172PFields.THROTTLE_MAX && throttleAmount > C172PFields.THROTTLE_MIN) {
            LOGGER.debug("Setting throttle amount to: {}", throttleAmount);
            
            plane.setThrottle(throttleAmount);
        } else {
            LOGGER.warn("Ignoring throttle change to invalid amount");
        }
                
        LOGGER.trace("SetThrottle returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "SetAileron", 
        description = "Set the aileron orientation"
    )
    public synchronized void SetAileron(
        @ThingworxServiceParameter
        ( 
        	name="aileronOrientation", 
            description="Orientation of the aileron", 
            baseType="NUMBER" 
        ) Double orientation
    ) throws Exception 
    {
        LOGGER.trace("SetAileron invoked");
        
        //guardrails -1.0 <-> 1.0
        if(orientation < C172PFields.AILERON_MIN) {
            orientation = C172PFields.AILERON_MIN;
        } else if (orientation > C172PFields.AILERON_MAX) {
            orientation = C172PFields.AILERON_MAX;
        }

        LOGGER.debug("Setting aileron orientation to: {}", orientation);

        plane.setAileron(orientation);

        LOGGER.trace("SetAileron returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "SetAutoCoordination", 
        description = "Set auto coordination of control surfaces"
    )
    public synchronized void SetAutoCoordination(
        @ThingworxServiceParameter
        ( 
        	name="isEnabled", 
            description="State of control surface auto coordination", 
            baseType="BOOLEAN" 
        ) Boolean isEnabled
    ) throws Exception 
    {
        LOGGER.trace("SetAutoCoordination invoked");
        
        LOGGER.debug("Setting AutoCoordination to: {}", isEnabled);
        
        plane.setAutoCoordination(isEnabled);
        
        LOGGER.trace("SetAutoCoordination returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "SetElevator", 
        description = "Set the elevator orientation"
    )
    public synchronized void SetElevator(
        @ThingworxServiceParameter
        ( 
        	name="flapsOrientation", 
            description="Orientation of the elevator", 
            baseType="NUMBER" 
        ) Double orientation
    ) throws Exception 
    {
        LOGGER.trace("SetElevator invoked");
        
        //guardrails -1 <-> 1
        if(orientation < C172PFields.ELEVATOR_MIN) {
            orientation = C172PFields.ELEVATOR_MIN;
        } else if (orientation > C172PFields.ELEVATOR_MAX) {
            orientation = C172PFields.ELEVATOR_MAX;
        }

        LOGGER.debug("Setting elevator orientation to: {}", orientation);

        plane.setAileron(orientation);

        LOGGER.trace("SetElevator returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "SetFlaps", 
        description = "Set the flaps orientation"
    )
    public synchronized void SetFlaps(
        @ThingworxServiceParameter
        ( 
        	name="flapsOrientation", 
            description="Orientation of the flaps", 
            baseType="NUMBER" 
        ) Double orientation
    ) throws Exception 
    {
        LOGGER.trace("SetFlaps invoked");
        
        //guardrails 0 <-> 1
        if(orientation < C172PFields.FLAPS_MIN) {
            orientation = C172PFields.FLAPS_MIN;
        } else if (orientation > C172PFields.FLAPS_MAX) {
            orientation = C172PFields.FLAPS_MAX;
        }

        LOGGER.debug("Setting flaps orientation to: {}", orientation);

        plane.setAileron(orientation);

        LOGGER.trace("SetFlaps returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "SetRudder", 
        description = "Set the rudder orientation"
    )
    public synchronized void SetRudder(
        @ThingworxServiceParameter
        ( 
        	name="rudderOrientation", 
            description="Orientation of the rudder", 
            baseType="NUMBER" 
        ) Double orientation
    ) throws Exception 
    {
        LOGGER.trace("SetRudder invoked");
        
        //guardrails 0 <-> 1
        if(orientation < C172PFields.ELEVATOR_MIN) {
            orientation = C172PFields.ELEVATOR_MIN;
        } else if (orientation > C172PFields.ELEVATOR_MAX) {
            orientation = C172PFields.ELEVATOR_MAX;
        }

        LOGGER.debug("Setting elevator orientation to: {}", orientation);

        plane.setAileron(orientation);

        LOGGER.trace("SetRudder returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "SetParkingBrake", 
        description = "Set the C172 Parking Brake"
    )
    @ThingworxServiceResult
    (
        name = CommonPropertyNames.PROP_RESULT, 
        description = "InfoTable of control settings", 
        baseType = "INFOTABLE",
        aspects = { "dataShape:" + SIM_MODEL_SHAPE_NAME }
    )
    public synchronized InfoTable SetParkingBrake(
        @ThingworxServiceParameter
        ( 
        	name="enabled", 
            description="Parking brake enabled", 
            baseType="BOOLEAN" 
        ) Boolean enabled
    ) throws Exception 
    {
        LOGGER.trace("SetParkingBrake invoked");

        plane.setParkingBrake(enabled);
        int brakeState = plane.getParkingBrakeEnabled();
        
        LOGGER.debug("Got parking brake state {}", brakeState);
        
        ValueCollection entry = new ValueCollection();
        entry.clear();
        
        entry.SetIntegerValue(EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_PARKING_BRAKE_FIELD), brakeState);

        InfoTable table = new InfoTable(getDataShapeDefinition(SIM_MODEL_SHAPE_NAME));
        table.addRow(entry);
        
        LOGGER.trace("SetParkingBrake returning");
        
        return table;
    }
    
    @ThingworxServiceDefinition
    (
    	name = "GetControlTelemetry", 
        description = "Get control telemetry fields for the C172"
    )
    @ThingworxServiceResult
    (
        name = CommonPropertyNames.PROP_RESULT, 
        description = "InfoTable of control values", 
        baseType = "INFOTABLE",
        aspects = { "dataShape:" + CONTROL_SHAPE_NAME }
    )
    public synchronized InfoTable GetControlTelemetry() throws Exception 
    {
    	if(LOGGER.isTraceEnabled()) {
    		LOGGER.trace("GetControlTelemetry invoked");
    	}

        ValueCollection entry = new ValueCollection();
        entry.clear();
        
        entry.SetIntegerValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.BATTERY_SWITCH_FIELD), plane.getBatterySwitch());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.MIXTURE_FIELD), plane.getMixture());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.THROTTLE_FIELD), plane.getThrottle());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AILERON_FIELD), plane.getAileron());
        entry.SetIntegerValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AUTO_COORDINATION_FIELD), plane.getAutoCoordination());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AUTO_COORDINATION_FACTOR_FIELD), plane.getAutoCoordinationFactor());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ELEVATOR_FIELD), plane.getElevator());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FLAPS_FIELD), plane.getFlaps());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.RUDDER_FIELD), plane.getRudder());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.SPEED_BRAKE_FIELD), plane.getSpeedbrake());
        entry.SetIntegerValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.PARKING_BRAKE_FIELD), plane.getParkingBrake());
        entry.SetIntegerValue(
            EdgeUtilities.toThingworxPropertyName(C172PFields.GEAR_DOWN_FIELD), plane.getGearDown());
        
        InfoTable table = new InfoTable(getDataShapeDefinition(CONTROL_SHAPE_NAME));
        table.addRow(entry);
        
        if(LOGGER.isTraceEnabled()) {
        	LOGGER.trace("GetControlTelemetry returning");
        }
        
        return table;
    }
    
    /////////////////////////
    //orientation
    /////////////////////////
    
    //Dedicated service for a telemetry subset 
    public InfoTable GetOrientation() {
        
        //read from 
        
        return null;
    }
    
    /////////////////////////
    //position
    /////////////////////////
    
    
    
    /////////////////////////
    @ThingworxServiceDefinition
    (
            name = "Shutdown", 
            description = "Shut down the Edge C172P"
    )
    public void Shutdown() throws Exception {
        
        LOGGER.debug("C172PThing shut down invoked");
     
        //TODO: terminate flight thread
        
        this.getClient().shutdown();
        
        if(this.sshdServer != null) {
        	this.sshdServer.shutdown();
        }
        
        LOGGER.debug("C172PThing shut down completed");
    }
    
    
    ////////////////////////////////////////
    //flightplans. runs as background thread
    
    /**
     * Function for managing the flight. Runway burnout/throttle test.
     * Set the parking brake on. Start the engines. Move the throttle around while fuel is consumed.
     * Executed in the flightplan thread.
     * 
     * @throws IOException 
     * @throws AircraftStartupException 
     * 
     * @throws Exception 
     *
     */
    private void runRunwayFlightPlan() throws IOException, AircraftStartupException {
        
    	double testFuelAmount = 4.0;
    	
        LOGGER.info("C172PThing runRunwayFlightPlan thread starting");
          
        
        
        //refill in case a previous run emptied it
        plane.refillFuel();

        //probably not going to happen but do it anyway
        plane.setDamageEnabled(false);
        
        //a full fuel tank will take a while
        plane.setFuelTank0Level(testFuelAmount);
        plane.setFuelTank1Level(testFuelAmount);  
        
        try {
			RunwayBurnoutFlightExecutor.runFlight(plane);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
     
        LOGGER.debug("Exiting runtime loop");
        
        //at higher speedups the simulator window is unusable, so return it to something usable
        plane.setSimSpeedUp(1);


        
        LOGGER.info("Flight operation ended. Shutting down FlightGear connection.");
        
        //shut down the plane
        plane.shutdown();
        
        //signal the flight thread to shutdown
        try {
			Shutdown();
		} catch (Exception e) {
            LOGGER.warn("Exception during virtual thing shutdown", e);
		}
        
        LOGGER.info("FlightPlan thread runRunwayFlightPlan returning");
    }
    
    /**
     * Function for managing the flight. Fly through the waypoint plan. Allow services to add waypoint
     * Executed in the flightplan thread.
     * 
     * @throws IOException 
     * @throws AircraftStartupException 
     *
     */
    private void runFlyAroundFlightPlan() throws IOException, AircraftStartupException {
        
        LOGGER.info("C172PThing runFlyAroundFlightPlan thread starting");
               
        //plane route should have been set before this function is invoked

		// set chase view
		plane.setCurrentView(2);

		plane.setDamageEnabled(false);
		plane.setComplexEngineProcedures(false);
		plane.setWinterKitInstalled(true);
		plane.setGMT(LAUNCH_TIME_GMT);

		// in case we get a previously lightly-used environment
		plane.refillFuel();
		plane.setBatteryCharge(C172PFields.BATTERY_CHARGE_MAX);
		
		// kick off our flight in the main thread
		WaypointFlightExecutor.runFlight(plane);

		// pause so the plane doesn't list from its heading and crash
		plane.setPause(true);

        LOGGER.info("Flight operation ended. Shutting down FlightGear connection.");
        
        plane.shutdown();
        
        LOGGER.info("FlightPlan thread runFlyAroundFlightPlan returning");
        
        try {
			Shutdown();
		} catch (Exception e) {
            LOGGER.warn("Exception during virtual thing shutdown", e);
		}
    }
}
