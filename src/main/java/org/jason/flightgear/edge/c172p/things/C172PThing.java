package org.jason.flightgear.edge.c172p.things;

import java.io.IOException;

import org.jason.flightgear.planes.c172p.C172P;
import org.jason.flightgear.planes.c172p.C172PFields;
import org.jason.flightgear.planes.c172p.C172PFlightUtilities;
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
    
    private final static int TARGET_ALTITUDE = 5000;
    private final static int TARGET_HEADING = 0;
    
    private final static int SUBSCRIBED_PROPERTIES_UPDATE_TIMEOUT = 2000;
    
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
        //yaw correction event
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
        
        //Velocities
        defineDataShapeDefinition(VELOCITIES_SHAPE_NAME, DataShapeInitializer.buildVelocitiesShape());

        
        LOGGER.trace("init returning");
    }
    
    //separate function, so plane can be started after bind
    public void startupPlane() throws Exception {
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
        
        //assume plane autostart nasal script was invoked
        //sets mixture 95, throttle 20
        
        //TODO: pack this into a C172P utilities-type visitor so we can have options to launch
        
        //assume start unpaused;
        
        plane.setPause(true);
        
        //head north
        plane.setHeading(TARGET_HEADING);
        
        plane.setParkingBrake(false);
        
        //start moving the plane down the runway
        //bump throttle
        plane.setThrottle(0.80);
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        
        plane.setAirSpeed(100);
        
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
        //plane.setAirSpeed(100);
        
        //initial drop. allow to level off
        try {
            Thread.sleep(POST_LAUNCH_SLEEP);
        } catch (InterruptedException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        
        //////
        //initial check that we've leveled off. want some pitch upward and no roll
        C172PFlightUtilities.altitudeCheck(plane, 500, TARGET_ALTITUDE);
        C172PFlightUtilities.pitchCheck(plane, 4, 3.0);
        C172PFlightUtilities.rollCheck(plane, 4, 0.0);
        
        //increase throttle
//        plane.setPause(true);
//        plane.setThrottle(0.80);
//        plane.setPause(false);
        
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
        
        C172PDeviceScanner.DeviceScanner(this, plane);

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
        

        entry.SetIntegerValue(C172PFields.SIM_PARKING_BRAKE_FIELD.substring(1).replaceAll("/\\//", "_"), brakeState);

        
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
            
            C172PFlightUtilities.altitudeCheck(plane, 500, TARGET_ALTITUDE);
            
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
