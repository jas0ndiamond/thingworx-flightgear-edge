package org.jason.flightgear.edge.c172p.things;

import org.jason.flightgear.edge.c172p.fields.PlaneFields;
import org.jason.flightgear.planes.c172p.C172P;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.metadata.FieldDefinition;
import com.thingworx.metadata.PropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceParameter;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.metadata.collections.FieldDefinitionCollection;
import com.thingworx.types.BaseTypes;
import com.thingworx.types.InfoTable;
import com.thingworx.types.collections.AspectCollection;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.types.constants.Aspects;
import com.thingworx.types.constants.CommonPropertyNames;
import com.thingworx.types.primitives.BooleanPrimitive;
import com.thingworx.types.primitives.IntegerPrimitive;
import com.thingworx.types.primitives.NumberPrimitive;

public class C172PThing extends VirtualThing {

    private static final Logger LOGGER = LoggerFactory.getLogger(C172PThing.class);
    
    private static final long serialVersionUID = -1670069869427933890L;
    
    private final static int TARGET_ALTITUDE = 5000;
    private final static int TARGET_HEADING = 0;
    
    private final static int SUBSCRIBED_PROPERTIES_UPDATE_TIMEOUT = 2000;
    
    ////////
    //datashape names
    private final static String POSITION_SHAPE_NAME = "PositionShape";
    private final static String ORIENTATION_SHAPE_NAME = "OrientationShape";
    private final static String CONTROL_SHAPE_NAME = "ControlShape";
    private final static String SIM_SHAPE_NAME = "SimShape";
    private final static String VELOCITIES_SHAPE_NAME = "VelocitiesShape";
    
    ////////
    
    private final static int FLIGHT_EXECUTION_SLEEP = 250;
	private final static int POST_LAUNCH_SLEEP = 40 * 1000;
	private final static int MAIN_THREAD_STARTUP_SLEEP = 2 * 1000;
	
	private boolean startup;
    
    private boolean isFlightRunning;
    
    private C172P plane;


	private Thread flightThread;

	

    public C172PThing(String name, String description, String identifer, ConnectedThingClient client) throws Exception {
        super(name, description, identifer, client);
        
        ///////////////////////        
        //init plane object
        plane = new C172P();
        
        //initial config
        plane.setDamageEnabled(false);
        
		// Populate the thing shape with the properties, services, and events that are annotated in
		// this code
		super.initializeFromAnnotations();
		this.init();
		
        ///////////////////////
        //properties
        //TODO: annotations for thest
        
        AspectCollection persist = new AspectCollection();
        persist.put(Aspects.ASPECT_ISPERSISTENT, new BooleanPrimitive(true));

        //enable persist only for telemetry that should persist between flights
        //not applicable for most live telemetry
        
        //Position
        super.defineProperty(new PropertyDefinition(PlaneFields.ALTITUDE_FIELD, PlaneFields.ALTITUDE_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.GROUND_ELEVATION_FIELD, PlaneFields.GROUND_ELEVATION_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.LATITUDE_FIELD, PlaneFields.LATITUDE_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.LONGITUDE_FIELD, PlaneFields.LONGITUDE_FIELD_DESC, BaseTypes.NUMBER));
        
        //Orientation
        super.defineProperty(new PropertyDefinition(PlaneFields.ALPHA_FIELD, PlaneFields.ALPHA_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.BETA_FIELD, PlaneFields.BETA_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.HEADING_FIELD, PlaneFields.HEADING_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.HEADING_MAG_FIELD, PlaneFields.HEADING_MAG_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.PITCH_FIELD, PlaneFields.PITCH_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.ROLL_FIELD, PlaneFields.ROLL_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.TRACK_MAG_FIELD, PlaneFields.TRACK_MAG_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.YAW_FIELD, PlaneFields.YAW_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.YAW_RATE_FIELD, PlaneFields.YAW_RATE_FIELD_DESC, BaseTypes.NUMBER));
        
        //Control
        super.defineProperty(new PropertyDefinition(PlaneFields.BATTERY_SWITCH_FIELD, PlaneFields.BATTERY_SWITCH_FIELD_DESC, BaseTypes.INTEGER));
        super.defineProperty(new PropertyDefinition(PlaneFields.MIXTURE_FIELD, PlaneFields.MIXTURE_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.THROTTLE_FIELD, PlaneFields.THROTTLE_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.AILERON_FIELD, PlaneFields.AILERON_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.AUTO_COORDINATION_FIELD, PlaneFields.AUTO_COORDINATION_FIELD_DESC, BaseTypes.INTEGER));
        super.defineProperty(new PropertyDefinition(PlaneFields.AUTO_COORDINATION_FACTOR_FIELD, PlaneFields.AUTO_COORDINATION_FACTOR_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.ELEVATOR_FIELD, PlaneFields.ELEVATOR_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.FLAPS_FIELD, PlaneFields.FLAPS_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.RUDDER_FIELD, PlaneFields.RUDDER_FIELD_DESC, BaseTypes.NUMBER));     
        super.defineProperty(new PropertyDefinition(PlaneFields.SPEED_BRAKE_FIELD, PlaneFields.SPEED_BRAKE_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.PARKING_BRAKE_FIELD, PlaneFields.PARKING_BRAKE_FIELD_DESC, BaseTypes.INTEGER));
        super.defineProperty(new PropertyDefinition(PlaneFields.GEAR_DOWN_FIELD, PlaneFields.GEAR_DOWN_FIELD_DESC, BaseTypes.INTEGER));
        
        //Velocities
        super.defineProperty(new PropertyDefinition(PlaneFields.AIRSPEED_FIELD, PlaneFields.AIRSPEED_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.GROUNDSPEED_FIELD, PlaneFields.GROUNDSPEED_FIELD_DESC, BaseTypes.NUMBER));
        super.defineProperty(new PropertyDefinition(PlaneFields.VERTICALSPEED_FIELD, PlaneFields.VERTICALSPEED_FIELD_DESC, BaseTypes.NUMBER));
        
        ///////////////////////
        //events
        
        //altitude correction event
        //heading correction event
        //yaw correction event
        //pitch correction event
        
        ///////////////////////
        //configure flight thread
        
        flightThread = new Thread() {
        	@Override
        	public void run() {
                LOGGER.trace("flight thread started");
                
                //TODO: select a flight plan based on config
                runFlight();
                
                LOGGER.trace("flight thread returning");
        	}
        };
        
        ///////////////////////
    }
    
    private void init() throws Exception {
        LOGGER.trace("init invoked");
        
        super.initialize();
        
        ///////////////////////
        //define datashape definition, mostly for service invocation result infotables and events

        //Position
        FieldDefinitionCollection positionFields = new FieldDefinitionCollection();
        positionFields.addFieldDefinition(new FieldDefinition(PlaneFields.ALTITUDE_FIELD, BaseTypes.NUMBER));
        positionFields.addFieldDefinition(new FieldDefinition(PlaneFields.GROUND_ELEVATION_FIELD, BaseTypes.NUMBER));
        positionFields.addFieldDefinition(new FieldDefinition(PlaneFields.LATITUDE_FIELD, BaseTypes.NUMBER));
        positionFields.addFieldDefinition(new FieldDefinition(PlaneFields.LONGITUDE_FIELD, BaseTypes.NUMBER));
        defineDataShapeDefinition(POSITION_SHAPE_NAME, positionFields);
        
        //Orientation
        FieldDefinitionCollection orientationFields = new FieldDefinitionCollection();
        orientationFields.addFieldDefinition(new FieldDefinition(PlaneFields.ALPHA_FIELD, BaseTypes.NUMBER));
        orientationFields.addFieldDefinition(new FieldDefinition(PlaneFields.BETA_FIELD, BaseTypes.NUMBER));
        orientationFields.addFieldDefinition(new FieldDefinition(PlaneFields.HEADING_FIELD, BaseTypes.NUMBER));
        orientationFields.addFieldDefinition(new FieldDefinition(PlaneFields.HEADING_MAG_FIELD, BaseTypes.NUMBER));
        orientationFields.addFieldDefinition(new FieldDefinition(PlaneFields.PITCH_FIELD, BaseTypes.NUMBER));
        orientationFields.addFieldDefinition(new FieldDefinition(PlaneFields.ROLL_FIELD, BaseTypes.NUMBER));
        orientationFields.addFieldDefinition(new FieldDefinition(PlaneFields.TRACK_MAG_FIELD, BaseTypes.NUMBER));
        orientationFields.addFieldDefinition(new FieldDefinition(PlaneFields.YAW_FIELD, BaseTypes.NUMBER));
        orientationFields.addFieldDefinition(new FieldDefinition(PlaneFields.YAW_RATE_FIELD, BaseTypes.NUMBER));
        defineDataShapeDefinition(ORIENTATION_SHAPE_NAME, orientationFields);
        
        //Control
        FieldDefinitionCollection controlFields = new FieldDefinitionCollection();
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.BATTERY_SWITCH_FIELD.substring(1).replace('/', '_'), BaseTypes.INTEGER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.MIXTURE_FIELD.substring(1).replace('/', '_'), BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.THROTTLE_FIELD.substring(1).replace('/', '_'), BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(PlaneFields.AILERON_FIELD.substring(1).replace('/', '_'), BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.AUTO_COORDINATION_FIELD.substring(1).replace('/', '_'), BaseTypes.INTEGER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.AUTO_COORDINATION_FACTOR_FIELD.substring(1).replace('/', '_'), BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.ELEVATOR_FIELD.substring(1).replace('/', '_'), BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.FLAPS_FIELD.substring(1).replace('/', '_'), BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.RUDDER_FIELD.substring(1).replace('/', '_'), BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.SPEED_BRAKE_FIELD.substring(1).replace('/', '_'), BaseTypes.INTEGER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.PARKING_BRAKE_FIELD.substring(1).replace('/', '_'), BaseTypes.INTEGER
        ));
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.GEAR_DOWN_FIELD.substring(1).replace('/', '_'), BaseTypes.INTEGER
        ));
        defineDataShapeDefinition(CONTROL_SHAPE_NAME, controlFields);

        //Sim
        FieldDefinitionCollection simFields = new FieldDefinitionCollection();
        controlFields.addFieldDefinition(
        		new FieldDefinition(
        				PlaneFields.SIM_PARKING_BRAKE_FIELD.substring(1).replace('/', '_'), BaseTypes.NUMBER
        ));
        defineDataShapeDefinition(SIM_SHAPE_NAME, simFields);
        
        //Velocities
        FieldDefinitionCollection velocitiesFields = new FieldDefinitionCollection();
        velocitiesFields.addFieldDefinition(new FieldDefinition(PlaneFields.AIRSPEED_FIELD, BaseTypes.NUMBER));
        velocitiesFields.addFieldDefinition(new FieldDefinition(PlaneFields.GROUNDSPEED_FIELD, BaseTypes.NUMBER));
        velocitiesFields.addFieldDefinition(new FieldDefinition(PlaneFields.VERTICALSPEED_FIELD, BaseTypes.NUMBER));
        defineDataShapeDefinition(VELOCITIES_SHAPE_NAME, velocitiesFields);

        
        LOGGER.trace("init returning");
    }
    
    //separate function, so plane can be started after bind
    public void startupPlane() {
        LOGGER.trace("startup invoked");
        
        //start plane. function should return or it'll block everything else
        plane.startupPlane();
        
        //set the parking brake so the plane doesn't start rolling
        //enabled by default, but the default c172p autostart disables it
        plane.setParkingBrake(true);
        
        LOGGER.trace("startup returning");
    }
    
    public void launchPlane() {
        
    	//TODO: this blocks property updates, despite telemetry arriving. fix that.
    	//maybe launch this as a background thread
    	
        //TODO: maybe start the plane rolling fast on the runway, then place it into the air.
    	//step up the throttle and mixture, preserve the heading 
        
        LOGGER.trace("launch invoked");
        
        //TODO: pack this into a C172P utilities-type visitor so we can have options to launch
        
        
        
        //assume start unpaused;
        
        plane.setPause(true);
        
        plane.setParkingBrake(false);
        
        //place in the air
        plane.setAltitude(TARGET_ALTITUDE);
        
        //retract landing gear if not fixed

        //head north
        plane.setHeading(TARGET_HEADING);
                
        plane.setPause(false);
        
        //set while not paused. this functions more like a boost- 
        //the plane can be acceled or deceled to the specified speed, 
        //but then the fdm takes over and stabilizes the air speed
        //TODO: may not be worth doing if we manage the take-off, throttle, and mixture correctly
        plane.setAirSpeed(100);
        
        //initial drop. allow to level off
        try {
            Thread.sleep(POST_LAUNCH_SLEEP);
        } catch (InterruptedException e) {
        	LOGGER.warn(e.getMessage(), e);
        }
        
        //////
        //initial check that we've leveled off. want some pitch upward and no roll
        plane.altitudeCheck(500, TARGET_ALTITUDE);
        plane.pitchCheck(4, 3.0);
        plane.rollCheck(4, 0.0);
        
        //increase throttle
        plane.setPause(true);
        plane.setThrottle(0.80);
        plane.setPause(false);
        
        //start the flight thread
        flightThread.start();
        
        LOGGER.trace("launch returning");
    }
    
    @Override
    public void synchronizeState() {
        
        //check readytosend?
        
        super.syncProperties();
    }
    
    /**
     *
     * VirtualThing-level edge execution
     * @throws Exception 
     *
     */
    @Override
    public void processScanRequest() throws Exception {
        LOGGER.debug("processScanRequest invoked");
        
        super.processScanRequest();
        
        //conditional execution of scanDevice?
        
        this.scanDevice();
        
        LOGGER.debug("processScanRequest returning");

    }
    
    
    /**
     * 
     * Thing-specific edge execution
     * @throws Exception 
     * 
     */
    private void scanDevice() throws Exception {
        
        //get telemetry from the plane, pour into twx types, and set defined properties
        
    	//TODO: add methods for these in flightgear-control
    	
    	//Position
        super.setProperty(PlaneFields.ALTITUDE_FIELD, new NumberPrimitive( (Number)( plane.getAltitude()) ));
        //super.setProperty(PlaneFields.GROUND_ELEVATION_FIELD, new NumberPrimitive( (Number)( plane.getGroundElevation()) ));
        //super.setProperty(PlaneFields.LATITUDE_FIELD, new NumberPrimitive( (Number)( plane.getLatitude()) ));
        //super.setProperty(PlaneFields.LONGITUDE_FIELD, new NumberPrimitive( (Number)( plane.getLongitude()) ));
        
        //Orientation
        //super.setProperty(PlaneFields.ALPHA_FIELD, new NumberPrimitive( (Number)( plane.getAlpha()) ));
        //super.setProperty(PlaneFields.BETA_FIELD, new NumberPrimitive( (Number)( plane.getBeta()) ));
        super.setProperty(PlaneFields.HEADING_FIELD, new NumberPrimitive( (Number)( plane.getHeading()) ));
        //super.setProperty(PlaneFields.HEADING_MAG_FIELD, new NumberPrimitive( (Number)( plane.getMagHeading()) ));
        super.setProperty(PlaneFields.PITCH_FIELD, new NumberPrimitive( (Number)( plane.getPitch()) ));
        super.setProperty(PlaneFields.ROLL_FIELD, new NumberPrimitive( (Number)( plane.getRoll()) ));
        //super.setProperty(PlaneFields.TRACK_MAG_FIELD, new NumberPrimitive( (Number)( plane.getMagTrack()) ));
        super.setProperty(PlaneFields.YAW_FIELD, new NumberPrimitive( (Number)( plane.getYaw()) ));
        super.setProperty(PlaneFields.YAW_RATE_FIELD, new NumberPrimitive( (Number)( plane.getYawRate()) ));

        //Control
        //super.setProperty(PlaneFields.BATTERY_SWITCH_FIELD, new IntegerPrimitive( (Number)( plane.getBatterySwitch()) ));
        //super.setProperty(PlaneFields.MIXTURE_FIELD, new NumberPrimitive( (Number)( plane.getMixture()) ));
        //super.setProperty(PlaneFields.THROTTLE_FIELD, new NumberPrimitive( (Number)( plane.getThrottle()) ));
        //super.setProperty(PlaneFields.AILERON_FIELD, new NumberPrimitive( (Number)( plane.getAileron()) ));
        //super.setProperty(PlaneFields.AUTO_COORDINATION_FIELD, new IntegerPrimitive( (Number)( plane.getAutoCoordination()) ));
        //super.setProperty(PlaneFields.AUTO_COORDINATION_FACTOR_FIELD, new NumberPrimitive( (Number)( plane.getAutoCoordinationFactor()) ));
        //super.setProperty(PlaneFields.ELEVATOR_FIELD, new NumberPrimitive( (Number)( plane.getElevator()) ));
        //super.setProperty(PlaneFields.FLAPS_FIELD, new NumberPrimitive( (Number)( plane.getFlaps()) ));
        //super.setProperty(PlaneFields.RUDDER_FIELD, new NumberPrimitive( (Number)( plane.getRudder()) ));
        //super.setProperty(PlaneFields.SPEEDBRAKE_FIELD, new NumberPrimitive( (Number)( plane.getSpeedBrake()) ));
        super.setProperty(PlaneFields.PARKING_BRAKE_FIELD, new IntegerPrimitive( (Number)( plane.getParkingBrakeEnabled()) ));
        //super.setProperty(PlaneFields.GEARDOWN_FIELD, new IntegerPrimitive( (Number)( plane.getGearDown()) ));
        
        //Velocities
        //super.setProperty(PlaneFields.AIRSPEED_FIELD, new NumberPrimitive( (Number)( plane.getAirSpeed()) ));
        //super.setProperty(PlaneFields.GROUNDSPEED_FIELD, new NumberPrimitive( (Number)( plane.getGroundSpeed()) ));
        //super.setProperty(PlaneFields.VERTICALSPEED_FIELD, new NumberPrimitive( (Number)( plane.getVerticalSpeed()) ));

        
        super.updateSubscribedProperties(SUBSCRIBED_PROPERTIES_UPDATE_TIMEOUT);
    }
    
    /////////////////////////
    //plane services
    /////////////////////////
    
    //waypoints
    //add waypoint
    //remove waypoint

    /////////////////////////
    //controls
    /////////////////////////
    
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
			aspects = { "dataShape:" + SIM_SHAPE_NAME }
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
		LOGGER.debug("SetParkingBrake invoked");

		plane.setParkingBrake(enabled);
		int brakeState = plane.getParkingBrakeEnabled();
		
		LOGGER.debug("Got parking brake state {}", brakeState);
		
		ValueCollection entry = new ValueCollection();
		entry.clear();
		

		entry.SetIntegerValue(PlaneFields.SIM_PARKING_BRAKE_FIELD.substring(1).replaceAll("/\\//", "_"), brakeState);

		
		InfoTable table = new InfoTable(getDataShapeDefinition(SIM_SHAPE_NAME));
		table.addRow(entry);
		
		LOGGER.debug("SetParkingBrake returning");
		
		return table;
    }
    
    /////////////////////////
    //orientation
    /////////////////////////
    
    /////////////////////////
    //position
    /////////////////////////
    
    
    //not really necessary except for display 
    public InfoTable GetTelemetry() {
        
        //read from 
        
        return null;
    }

	@ThingworxServiceDefinition
	(
			name = "Shutdown", 
			description = "Shut down the Edge C172P"
	)
    public void Shutdown() {
        
    	LOGGER.debug("C172PThing shut down invoked");
    	
    	//signal the flight thread to shutdown
        isFlightRunning = false;
        
    }
    
    /**
     *
     * Function for managing the flight.
     *
     */
    private void runFlight() {
        
    	LOGGER.trace("C172PThing flight thread starting");
    	
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
        	LOGGER.warn(e.getMessage(), e);

		}
    	
        //TODO: possibly make this class final abstract, and expect different subclasses
        //to implement distinct flight operations here
        
        isFlightRunning = true;
        
        while(isFlightRunning) {
            
            LOGGER.debug("C172PThing thread running");
        	
            //fly the plane
            
            LOGGER.debug("Current altitude: " + plane.getAltitude());
            
            plane.altitudeCheck(500, TARGET_ALTITUDE);
            
            
            
            /////////////////////
            try {
                Thread.sleep(FLIGHT_EXECUTION_SLEEP);
            } catch (InterruptedException e) {
                LOGGER.warn("FLIGHT_EXECUTION_SLEEP interrupted", e);
            }
        }
        
        LOGGER.info("Flight operation ended. Shutting down FlightGear connection.");
        
        plane.shutdown();
        
        
    }

}
