package org.jason.fgedge.f15c.things;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import org.jason.fgcontrol.aircraft.f15c.F15C;
import org.jason.fgcontrol.aircraft.f15c.F15CFields;
import org.jason.fgcontrol.exceptions.AircraftStartupException;
import org.jason.fgcontrol.exceptions.FlightGearSetupException;
import org.jason.fgcontrol.flight.position.PositionUtilities;
import org.jason.fgcontrol.flight.position.TrackPosition;
import org.jason.fgcontrol.flight.position.WaypointManager;
import org.jason.fgcontrol.flight.position.WaypointPosition;
import org.jason.fgcontrol.flight.util.FlightLog;
import org.jason.fgcontrol.flight.util.FlightUtilities;
import org.jason.fgedge.connectivity.PerfectNetwork;
import org.jason.fgedge.connectivity.ServiceCallTimeoutManagement;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.ConnectionException;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.metadata.PropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceParameter;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.relationships.RelationshipTypes.ThingworxEntityTypes;
import com.thingworx.types.InfoTable;
import com.thingworx.types.collections.AspectCollection;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.types.constants.Aspects;
import com.thingworx.types.constants.CommonPropertyNames;
import com.thingworx.types.events.collections.PendingEvents;
import com.thingworx.types.primitives.BooleanPrimitive;
import com.thingworx.types.primitives.InfoTablePrimitive;
import com.thingworx.types.properties.collections.PendingPropertyUpdatesByProperty;

public class F15CThing extends VirtualThing {

    private static final Logger LOGGER = LoggerFactory.getLogger(F15CThing.class);
    
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
    
    //////////
    //RunwayTest state
    private final static int POST_STARTUP_SLEEP = 5000;
    
    //////////
    //WaypointFlight state
    
    private final static double MAX_HEADING_CHANGE = 12.0;
    
    //adjust in smaller increments than MAX_HEADING_CHANGE, since course changes can be radical
    private final static double COURSE_ADJUSTMENT_INCREMENT = 3.5;
    
    //if the sim is steering the plane by forcing positional constraints,
    //then the plane is essentially a missile so we need a wide margin of error
    private final static double WAYPOINT_ARRIVAL_THRESHOLD = 10.0 * 5280.0;
    
    //beyond this distance, increase throttle to crusing level (MAX)
    private final static double WAYPOINT_ADJUST_MIN_DIST = 30.0 * 5280.0; 
    
    private final static String LAUNCH_TIME_GMT = "2021-07-01T20:00:00";
    
    private final static double THROTTLE_WAYPOINT_APPROACH = 0.75;
    private final static double THROTTLE_COURSE_CHANGE = 0.6;
    
    private final static double FUEL_LEVEL_REFILL_THRESHOLD_PERCENT = 0.9;
    
    
    private FlightLog flightLog;
    
    private WaypointManager waypointManager;
    
    private ServiceCallTimeoutManagement connectivityManager;
    
    //////////
    
    private final static int SUBSCRIBED_PROPERTIES_UPDATE_TIMEOUT = 250;
    private final static int SUBSCRIBED_EVENTS_UPDATE_TIMEOUT = 250;
    private final static int TOO_SMALL_DOOMED_TIMEOUT = 100;
        
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
    private final static String VELOCITIES_SHAPE_NAME = "VelocitiesShape";
    
    ////////
    //sleep intervals
    private final static int FLIGHT_EXECUTION_SLEEP = 250;
        
    private boolean isFlightRunning;
    
    private F15C plane;

    //default plan is the runwaytest
    private int flightPlan;
    
    private Thread flightThread;

	

    public F15CThing(String name, String description, String identifer, ConnectedThingClient client) throws Exception {
        super(name, description, identifer, client);
        
        ///////////////////////        
        //init plane object
        plane = new F15C();
                
        // Populate the thing shape with any properties, services, and events that are annotated in
        // this code
        super.initializeFromAnnotations();
        this.init();
        
        flightPlan = FLIGHTPLAN_RUNWAY;
        
        flightLog = new FlightLog();
        waypointManager = new WaypointManager();
        
        //perfect network by default
        this.connectivityManager = new PerfectNetwork();
        
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
                } catch (FlightGearSetupException e) {
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
    
    public void setConnectivityManager(ServiceCallTimeoutManagement manager) {
    	this.connectivityManager = manager;
    }
        
    public void setFlightPlan(int plan) {
        flightPlan = plan;
    }
    
    public void setRoute( ArrayList<WaypointPosition> route) {
    	waypointManager.setWaypoints(route);
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
        
        //Sim Model
        defineDataShapeDefinition(SIM_MODEL_SHAPE_NAME, DataShapeInitializer.buildSimModelShape());
            
        //Velocities
        defineDataShapeDefinition(VELOCITIES_SHAPE_NAME, DataShapeInitializer.buildVelocitiesShape());

        
        LOGGER.trace("init returning");
    }
    
    //separate function, so plane can be started after bind
    public void startupPlane() throws Exception {
        LOGGER.debug("startup invoked");
        
        //start plane. function should return or it'll block everything else
        plane.startupPlane();
        
        //set the parking brake so the plane doesn't start rolling
        //enabled by default, but the default f15c autostart disables it
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
        
        F15CDeviceScanner.DeviceScanner(this, plane);

        this.updateSubscribedProperties(SUBSCRIBED_PROPERTIES_UPDATE_TIMEOUT);
        this.updateSubscribedEvents(SUBSCRIBED_EVENTS_UPDATE_TIMEOUT);
    }
    
    /////////////////////////
    //plane services
    /////////////////////////
    
    //waypoints
    //add waypoint
    //remove waypoint

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
        description = "Set the fuel level for the primary tank"
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
        
        LOGGER.trace("SetFuelTankWaterContamination returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "GetConsumablesTelemetry", 
        description = "Get consumables telemetry fields for the F15C"
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
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_0_CAPACITY_FIELD), plane.getFuelTank0Capacity());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_0_LEVEL_FIELD), plane.getFuelTank0Level());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_1_CAPACITY_FIELD), plane.getFuelTank1Capacity());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_1_LEVEL_FIELD), plane.getFuelTank1Level());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_2_CAPACITY_FIELD), plane.getFuelTank2Capacity());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_2_LEVEL_FIELD), plane.getFuelTank2Level());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_3_CAPACITY_FIELD), plane.getFuelTank3Capacity());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_3_LEVEL_FIELD), plane.getFuelTank3Level());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_4_CAPACITY_FIELD), plane.getFuelTank4Capacity());
        entry.SetNumberValue(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_4_LEVEL_FIELD), plane.getFuelTank4Level());
 
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
        
        if(throttleAmount < F15CFields.THROTTLE_MAX && throttleAmount > F15CFields.THROTTLE_MIN) {
            LOGGER.debug("Setting throttle amount to: {}", throttleAmount);
            
            plane.setEngine1Throttle(throttleAmount);
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
        if(orientation < F15CFields.AILERON_MIN) {
            orientation = F15CFields.AILERON_MIN;
        } else if (orientation > F15CFields.AILERON_MAX) {
            orientation = F15CFields.AILERON_MAX;
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
        if(orientation < F15CFields.ELEVATOR_MIN) {
            orientation = F15CFields.ELEVATOR_MIN;
        } else if (orientation > F15CFields.ELEVATOR_MAX) {
            orientation = F15CFields.ELEVATOR_MAX;
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
        if(orientation < F15CFields.FLAPS_MIN) {
            orientation = F15CFields.FLAPS_MIN;
        } else if (orientation > F15CFields.FLAPS_MAX) {
            orientation = F15CFields.FLAPS_MAX;
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
        if(orientation < F15CFields.ELEVATOR_MIN) {
            orientation = F15CFields.ELEVATOR_MIN;
        } else if (orientation > F15CFields.ELEVATOR_MAX) {
            orientation = F15CFields.ELEVATOR_MAX;
        }

        LOGGER.debug("Setting elevator orientation to: {}", orientation);

        plane.setAileron(orientation);

        LOGGER.trace("SetRudder returning");
    }
    
    @ThingworxServiceDefinition
    (
        name = "SetParkingBrake", 
        description = "Set the F15C Parking Brake"
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
        
        entry.SetIntegerValue(EdgeUtilities.toThingworxPropertyName(F15CFields.PARKING_BRAKE_FIELD), brakeState);

        InfoTable table = new InfoTable(getDataShapeDefinition(SIM_MODEL_SHAPE_NAME));
        table.addRow(entry);
        
        LOGGER.trace("SetParkingBrake returning");
        
        return table;
    }
    
    @ThingworxServiceDefinition
    (
    	name = "GetControlTelemetry", 
        description = "Get control telemetry fields for the F15C"
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
        LOGGER.trace("GetControlTelemetry invoked");

        ValueCollection entry = new ValueCollection();
        entry.clear();
        
        entry.SetIntegerValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.BATTERY_SWITCH_FIELD), plane.getBatterySwitch());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_MIXTURE_FIELD), plane.getEngine0Mixture());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_MIXTURE_FIELD), plane.getEngine1Mixture());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_THROTTLE_FIELD), plane.getEngine0Throttle());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_THROTTLE_FIELD), plane.getEngine1Throttle());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AILERON_FIELD), plane.getAileron());
        entry.SetIntegerValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AUTO_COORDINATION_FIELD), plane.getAutoCoordination());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AUTO_COORDINATION_FACTOR_FIELD), plane.getAutoCoordinationFactor());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ELEVATOR_FIELD), plane.getElevator());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FLAPS_FIELD), plane.getFlaps());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.RUDDER_FIELD), plane.getRudder());
        entry.SetNumberValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.SPEED_BRAKE_FIELD), plane.getSpeedbrake());
        entry.SetIntegerValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.PARKING_BRAKE_FIELD), plane.getParkingBrake());
        entry.SetIntegerValue(
            EdgeUtilities.toThingworxPropertyName(F15CFields.GEAR_DOWN_FIELD), plane.getGearDown());
        
        InfoTable table = new InfoTable(getDataShapeDefinition(CONTROL_SHAPE_NAME));
        table.addRow(entry);
        
        LOGGER.trace("GetControlTelemetry returning");
        
        return table;
    }
    
    /////////////////////////
    //orientation
    /////////////////////////
    
    /////////////////////////
    //position
    /////////////////////////
    
    
    //Dedicated service for a telemetry subset 
    public InfoTable GetOrientation() {
        
        //read from 
        
        return null;
    }

    @ThingworxServiceDefinition
    (
            name = "Shutdown", 
            description = "Shut down the Edge F15C"
    )
    public void Shutdown() throws Exception {
        
        LOGGER.debug("F15CThing shut down invoked");
        
        //signal the flight thread to shutdown
        isFlightRunning = false;
        
        this.getClient().shutdown();
    }
    
    
    ////////////////////////////////////////
    //flightplans. runs as background thread
    
    /**
     * Function for managing the flight. Runway burnout/throttle test.
     * Set the parking brake on. Start the engines. Move the throttle around while fuel is consumed.
     * Executed in the flightplan thread.
     * 
     * @throws Exception 
     *
     */
    private void runRunwayFlightPlan() throws Exception {
        
        LOGGER.info("F15CThing runRunwayFlightPlan thread starting");
               
        //initial config
        
        //high throttle but not so high it overwhelms the parking brake
        double highThrottle = 0.80;
        double lowThrottle = 0.25;
        double testFuelLevel = 30.0;
        double speedUpLevel = 8.0;
        
        long throttleInterval = 5 * 1000;
        
        
        //setup. pause so these updates don't overwrite each other
        plane.setPause(true);
        plane.setDamageEnabled(false);
        plane.setGMT(LAUNCH_TIME_GMT);         
        plane.setPause(false);
        
        plane.setParkingBrake(true);
        
        //will block/wait for the engine to begin running
        plane.startupPlane();
        
        //sleep some more for the engines to get up to speed for the initial mixture/throttle setting
        try {
            Thread.sleep(POST_STARTUP_SLEEP);
        } catch (InterruptedException e) {
            LOGGER.warn("FLIGHT_EXECUTION_SLEEP interrupted", e);
        }

        //post-startup setup. pause so these updates don't overwrite each other
        plane.setPause(true);
        
        //explicitly set after startup too. sometimes startup unsets the brake
        plane.setParkingBrake(true);
        
        //i'm in a hurry
        plane.setSimSpeedUp(speedUpLevel);
        
        //lighten the tank to shorten the duration of the test
        plane.refillFuel(testFuelLevel);
        
        //set after sleep 
        plane.setEngineThrottles(highThrottle);
        
        plane.setPause(false);
        
        double currentThrottle;
        
        while(plane.isEngineRunning()) {
            
        	//throttles set collectively
            currentThrottle = plane.getEngine0Throttle();
            
            //if it's high throttle, flip to low. vice-versa
            if(currentThrottle <= lowThrottle) {
                plane.setEngineThrottles(highThrottle);
            } else {
                plane.setEngineThrottles(lowThrottle);
            }
            
            try {
                Thread.sleep(throttleInterval);
            } catch (InterruptedException e) {
                LOGGER.warn("Throttle interval interrupted", e);
            }
        }
        
        LOGGER.info("Flight operation ended. Shutting down FlightGear connection.");
        
        //shut down the plane
        plane.shutdown();
        
        //signal the flight thread to shutdown
        Shutdown();
        
        LOGGER.info("FlightPlan thread runRunwayFlightPlan returning");
    }
    
    /**
     * Function for managing the flight. Fly through the waypoint plan. Allow services to add waypoint
     * Executed in the flightplan thread.
     * 
     * @throws IOException 
     * @throws AircraftStartupException 
     * @throws FlightGearSetupException 
     *
     */
    private void runFlyAroundFlightPlan() throws IOException, AircraftStartupException, FlightGearSetupException {
        
        LOGGER.info("F15CThing runFlyAroundFlightPlan thread starting");
               
        //initial config

        double targetAltitude = 11500.0;
        
        plane.setDamageEnabled(false);
        plane.setGMT(LAUNCH_TIME_GMT);
        
        plane.refillFuel();
        

        
        isFlightRunning = true;
        
        while(isFlightRunning) {
            
            LOGGER.debug("F15CThing thread running");
            
            //fly the plane
            
            WaypointPosition startingWaypoint = waypointManager.getNextWaypoint();
            
            //figure out the heading of our first waypoint based upon our current position
            TrackPosition startPosition = plane.getPosition();
            double initialBearing = PositionUtilities.calcBearingToGPSCoordinates(startPosition, startingWaypoint);            
            
            //point the plane at our first waypoint
            LOGGER.info("First waypoint is {} and initial target bearing is {}", startingWaypoint.toString(), initialBearing);
            
            //make sure the shell script is launched with the initial heading instead
            plane.setHeading(initialBearing);
            
            plane.resetControlSurfaces();
            
            plane.setPause(false);
            
            //chase view
            plane.setCurrentView(2);

            //full throttle or the engines will have divergent thrust outputs
            plane.setEngineThrottles(F15CFields.THROTTLE_MAX);            
            
            //trouble doing waypoint flight at faster speeds with high speedup under the current threading model
            //TODO: separate threads for telemetry readouts and flight control
            //plane.setSimSpeedUp(2.0);
        
            //not much of a min, but all tanks largely filled means even weight distribution and more stable flight
            double minFuelTank0 = plane.getFuelTank0Capacity() * FUEL_LEVEL_REFILL_THRESHOLD_PERCENT,
                    minFuelTank1 = plane.getFuelTank1Capacity() * FUEL_LEVEL_REFILL_THRESHOLD_PERCENT,
                    minFuelTank2 = plane.getFuelTank2Capacity() * FUEL_LEVEL_REFILL_THRESHOLD_PERCENT,
                    minFuelTank3 = plane.getFuelTank3Capacity() * FUEL_LEVEL_REFILL_THRESHOLD_PERCENT,
                    minFuelTank4 = plane.getFuelTank4Capacity() * FUEL_LEVEL_REFILL_THRESHOLD_PERCENT;
            
            //needs to be tuned depending on aircraft speed, sim speedup, and waypoint closeness
            //int bearingRecalcCycleInterval = 5;    
            
            WaypointPosition nextWaypoint;
            TrackPosition currentPosition;
            double nextWaypointBearing = initialBearing;
            double distanceToNextWaypoint;
            int waypointFlightCycles;
            long cycleSleep = 5;
            while(waypointManager.getWaypointCount() > 0) {
                
                nextWaypoint = waypointManager.getAndRemoveNextWaypoint();
                
                //possibly slow the simulator down if the next waypoint is close.
                //it's possible that hard and frequent course adjustments are needed
                
                LOGGER.info("Headed to next waypoint: {}", nextWaypoint.toString());
                
                nextWaypointBearing = PositionUtilities.calcBearingToGPSCoordinates(plane.getPosition(), nextWaypoint);
                
                //normalize to 0-360
                if(nextWaypointBearing < 0.0) {
                    nextWaypointBearing += FlightUtilities.DEGREES_CIRCLE;
                }
                
                LOGGER.info("Bearing to next waypoint: {}", nextWaypointBearing);
                
                ///////////////////////////////
                //transition to a stable path to next waypoint.
                
                //turning to face next waypoint. throttle down
                plane.setEngineThrottles(THROTTLE_COURSE_CHANGE);
                
                double currentHeading;
                int headingComparisonResult;
                while(!FlightUtilities.withinHeadingThreshold(plane, MAX_HEADING_CHANGE, nextWaypointBearing)) {
                    
                    currentHeading = plane.getHeading();
                    
                    flightLog.addTrackPosition(plane.getPosition());
                    
                    headingComparisonResult = FlightUtilities.headingCompareTo(plane, nextWaypointBearing);
                    
                    LOGGER.info("Easing hard turn from current heading {} to target {}", currentHeading, nextWaypointBearing);
                    
                    //adjust clockwise or counter? 
                    //this may actually change in the middle of the transition itself
                    double intermediateHeading = currentHeading;
                    if(headingComparisonResult == FlightUtilities.HEADING_NO_ADJUST) {
                        LOGGER.warn("Found no adjustment needed");
                        //shouldn't happen since we'd be with the heading threshold
                        break;
                    } else if(headingComparisonResult == FlightUtilities.HEADING_CW_ADJUST) {
                        //1: adjust clockwise
                        intermediateHeading = (intermediateHeading + COURSE_ADJUSTMENT_INCREMENT ) % FlightUtilities.DEGREES_CIRCLE;
                    } else {
                        //-1: adjust counterclockwise
                        intermediateHeading -= COURSE_ADJUSTMENT_INCREMENT;
                        
                        //normalize 0-360
                        if(intermediateHeading < 0) intermediateHeading += FlightUtilities.DEGREES_CIRCLE;
                    }
                    
                    LOGGER.info("++++Stabilizing to intermediate heading {} from current {} with target {}", intermediateHeading, currentHeading, nextWaypointBearing);
                    
                    //low count here. if we're not on track by the end, the heading check should fail and get us back here
                    //seeing close waypoints get overshot
                    int stablizeCount = 0;
                    while(stablizeCount < 10) {
                        
                        FlightUtilities.altitudeCheck(plane, 500, targetAltitude);
                        stabilizeCheck(plane, intermediateHeading);
                        
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        
                        stablizeCount++;
                    }
                    
                    //recalculate bearing since we've moved
                    nextWaypointBearing = PositionUtilities.calcBearingToGPSCoordinates(plane.getPosition(), nextWaypoint);
                    
                    //normalize to 0-360
                    if(nextWaypointBearing < 0) {
                        nextWaypointBearing += FlightUtilities.DEGREES_CIRCLE;
                    }
                    
                    //refill all tanks for balance
                    if (
                        plane.getFuelTank0Level() < minFuelTank0 || 
                        plane.getFuelTank1Level() < minFuelTank1 ||
                        plane.getFuelTank2Level() < minFuelTank2 ||
                        plane.getFuelTank3Level() < minFuelTank3 ||
                        plane.getFuelTank4Level() < minFuelTank4 

                    ) {
                        plane.refillFuel();
                    }
                }
                
                LOGGER.info("Heading change within tolerance");
                
                //on our way. throttle up
                plane.setEngineThrottles(F15CFields.THROTTLE_MAX);
                
                ///////////////////////////////
                //main flight path to way point
                waypointFlightCycles = 0;
                
                //add our next waypoint to the log
                flightLog.addWaypoint(nextWaypoint);
                
                while( !PositionUtilities.hasArrivedAtWaypoint(plane.getPosition(), nextWaypoint, WAYPOINT_ARRIVAL_THRESHOLD) ) {
                
                    LOGGER.info("======================\nCycle {} start.", waypointFlightCycles);

                    currentPosition = plane.getPosition();
                    
                    distanceToNextWaypoint = PositionUtilities.distanceBetweenPositions(plane.getPosition(), nextWaypoint);
                    
                    flightLog.addTrackPosition(currentPosition);
                    
                    if(    
                        distanceToNextWaypoint > WAYPOINT_ADJUST_MIN_DIST //&&
                        //waypointFlightCycles % bearingRecalcCycleInterval == 0
                    ) 
                    {
                        //reset bearing incase we've drifted, not not if we're too close
                        nextWaypointBearing = PositionUtilities.calcBearingToGPSCoordinates(plane.getPosition(), nextWaypoint);
                        
                        //normalize to 0-360
                        if(nextWaypointBearing < 0) {
                            nextWaypointBearing += FlightUtilities.DEGREES_CIRCLE;
                        }
                        
                        LOGGER.info("Recalculating bearing to waypoint: {}", nextWaypointBearing);
                    } else if ( distanceToNextWaypoint < WAYPOINT_ARRIVAL_THRESHOLD * 3 ) {
                        //throttle down for waypoint approach to accommodate any late corrections
                        
                        plane.setEngineThrottles(THROTTLE_WAYPOINT_APPROACH);
                    } else if (plane.getEngine0Throttle() != F15CFields.THROTTLE_MAX) {
                        
                        //far enough away from the previous waypoint and not close enough to the next
                        //throttle up to max if we haven't already
                        plane.setEngineThrottles(F15CFields.THROTTLE_MAX);
                    }
                    
                    // check altitude first, if we're in a nose dive that needs to be corrected first
                    FlightUtilities.altitudeCheck(plane, 500, targetAltitude);

                    // TODO: ground elevation check. it's a problem if your target alt is 5000ft and
                    // you're facing a 5000ft mountain

                    stabilizeCheck(plane, nextWaypointBearing);                    
                    
                    if(!plane.isEngineRunning()) {
                        LOGGER.error("Engine found not running. Attempting to restart.");
//                        plane.startupPlane();
//                        
//                        //increase throttle
//                        plane.setPause(true);
//                        plane.resetControlSurfaces();
//                        plane.setPause(false);
//                        
//                        plane.setEngineThrottles(F15CFields.THROTTLE_MAX);
                        
                        plane.setPause(true);
                        
                        throw new IOException("Engine not running");
                    }
                    
                    //refill all tanks for balance
                    if (
                        plane.getFuelTank0Level() < minFuelTank0 || 
                        plane.getFuelTank1Level() < minFuelTank1 ||
                        plane.getFuelTank2Level() < minFuelTank2 ||
                        plane.getFuelTank3Level() < minFuelTank3 ||
                        plane.getFuelTank4Level() < minFuelTank4 

                    ) {
                        plane.refillFuel();
                    }
                    
                    try {
                        Thread.sleep(cycleSleep);
                    } catch (InterruptedException e) {
                        LOGGER.warn("Runtime sleep interrupted", e);
                    }
                    
                    waypointFlightCycles++;
                }
                
                LOGGER.info("Arrived at waypoint {}!", nextWaypoint.toString());
            }
            
            LOGGER.info("No more waypoints. Trip is finished!");
            
            isFlightRunning = false;
            
            //pause so the plane doesn't list from its heading and crash
            plane.setPause(true);
            
            /////////////////////
            try {
                Thread.sleep(FLIGHT_EXECUTION_SLEEP);
            } catch (InterruptedException e) {
                LOGGER.warn("FLIGHT_EXECUTION_SLEEP interrupted", e);
            }
        }
        
        LOGGER.info("Flight operation ended. Shutting down FlightGear connection.");
        
        plane.shutdown();
        
        LOGGER.info("FlightPlan thread runFlyAroundFlightPlan returning");
        
        try {
			Shutdown();
		} catch (Exception e) {
            LOGGER.warn("Exception during virtual thing shutdown", e);
		}
    }
    
    private static void stabilizeCheck(F15C plane, double bearing) throws IOException {
        if( 
        	!FlightUtilities.withinRollThreshold(plane, 1.5, 0.0) ||
            !FlightUtilities.withinPitchThreshold(plane, 3.0, 1.5) ||
            !FlightUtilities.withinHeadingThreshold(plane, COURSE_ADJUSTMENT_INCREMENT, bearing)
        ) 
        {
            plane.forceStabilize(bearing, 0.0, 2.0);
        }
    }
    
    ////////////////////////////////
    //timeout overrides
    
    /**
     * Depending on plane state, invoke regular updateSubscribedPropertyValues 
     * service or supplied updateSubscribedPropertiesAndTimeout service
     * 
     * @param timeout
     */
    @Override
    public void updateSubscribedProperties(int timeout) throws TimeoutException, ConnectionException {        
        if(!connectivityManager.shouldTimeoutServiceCall(plane)) {
            super.updateSubscribedProperties(timeout);
        } else {
            LOGGER.debug("Executing doomed-to-timeout service call for updateSubscribedProperties");
            
            if (getPendingPropertyUpdates().size() > 0) {
                ValueCollection parameters = new ValueCollection();

                PendingPropertyUpdatesByProperty propertyUpdates = getPendingPropertyUpdates().drainPendingPropertyUpdates();

                try {
                    parameters.put(CommonPropertyNames.PROP_VALUES,
                            new InfoTablePrimitive(propertyUpdates.toInfoTable()));

                    if (LOGGER.isTraceEnabled()) {
                        String valuesDump = parameters.toJSON().toString();

                        LOGGER.trace("PUSHING PROPERTIES = {}", valuesDump);
                    }

                    getClient().invokeService(
                    	ThingworxEntityTypes.Things, getBindingName(),
                        "UpdateSubscribedPropertyValuesAndTimeout", parameters, TOO_SMALL_DOOMED_TIMEOUT
                    );
                } catch (TimeoutException e) {
                	LOGGER.error("Update failed, timed out waiting for response for " + getName() + ": ", e);
                    // Re-add pending property updates
                    getPendingPropertyUpdates().addPendingPropertyUpdates(propertyUpdates);
                    throw e;
                } catch (ConnectionException e) {
                	LOGGER.error("Update failed, not connected to endpoint for " + getName() + ": ", e);
                    // Re-add pending property updates
                    getPendingPropertyUpdates().addPendingPropertyUpdates(propertyUpdates);
                    throw e;
                } catch (Exception e) {
                	LOGGER.error("Unable To Update Subscribed Properties For " + getName() + ": ", e);
                    // Re-add pending property updates
                    getPendingPropertyUpdates().addPendingPropertyUpdates(propertyUpdates);
                }
            }
        }
    }
    
    /**
     * Depending on plane state, invoke regular updateSubscribedEvents 
     * service or supplied updateSubscribedEventsAndTimeout service
     *
     * @param timeout
     */
    @Override
    public void updateSubscribedEvents(int timeout) {
        
        if(!connectivityManager.shouldTimeoutServiceCall(plane)) {
            super.updateSubscribedEvents(timeout);
        } else {
            LOGGER.debug("Executing doomed-to-timeout service call for updateSubscribedEvents");
            
            if (getPendingEvents().size() > 0) {
                ValueCollection parameters = new ValueCollection();

                PendingEvents pendingEvents = getPendingEvents().drainPendingEvents();

                try {
                    parameters.put(CommonPropertyNames.PROP_VALUES, new InfoTablePrimitive(pendingEvents.toInfoTable()));

                    getClient().invokeService(
                    	ThingworxEntityTypes.Things, getBindingName(), 
                    	"ProcessRemoteEventsAndTimeout", parameters, TOO_SMALL_DOOMED_TIMEOUT
                    );
                } catch (Exception e) {
                	LOGGER.error("Unable To Update Subscribed Events For " + getName() + " : " + e.getMessage());

                    // Re-add pending events
                    getPendingEvents().replaceEvents(pendingEvents);
                }
            }

        }
    }
}
