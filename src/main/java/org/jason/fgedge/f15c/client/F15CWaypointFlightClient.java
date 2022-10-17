package org.jason.fgedge.f15c.client;

import java.io.FileInputStream;
import java.util.Properties;

import org.jason.fgcontrol.aircraft.f15c.F15CConfig;
import org.jason.fgcontrol.flight.position.KnownRoutes;
import org.jason.fgedge.callback.AppKeyCallback;
import org.jason.fgedge.config.TWXConfigDirectives;
import org.jason.fgedge.f15c.things.F15CThing;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;

public class F15CWaypointFlightClient extends F15CClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(F15CWaypointFlightClient.class);
    
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int BIND_TIMEOUT = 5 * 1000;
    
    private final static String WS_PROTOCOL_STR = "wss://";
    private final static String PLATFORM_URI_COMPONENT_STR = "/Thingworx/WS";

    
    public F15CWaypointFlightClient(ClientConfigurator config) throws Exception {
        super(config);
    }
    
    /**
     * Main program for a F15C runway test. Start the plane and move the throttle up and down.
     * Refuel a few times, then allow the engine to run until out of fuel and stopped.
     * 
     * Run shell script f15c_runway.sh to launch simulator.
     * 
     * @param args	host port appkey
     * 
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
    	//read config
    	//App twx_edge.properties sim.properties
    	
    	String twxConfigFile = "./conf/twx_edge.properties";
    	String simConfigFile = "./conf/f15c.properties";
    	
    	if(args.length == 2) {
    		twxConfigFile = args[0];
    		simConfigFile = args[1];
    	}
    	else if(args.length == 1 && args[0].equals("-h")) {
    		System.err.println("Usage: App twx_edge.properties sim.properties");
    		System.exit(1);
    	}
    	
    	LOGGER.info("Using twx config file {} and sim config file {}", twxConfigFile, simConfigFile);
        
    	Properties twxConfig = new Properties();
    	twxConfig.load(new FileInputStream(twxConfigFile) );
    	
    	Properties simConfig = new Properties();
    	simConfig.load(new FileInputStream(simConfigFile) );
    	
    	//TODO: validate expected config directives are defined
        
        boolean enterRunLoop = false;
        
        //////////
        //input
        
        //TODO: add guard rails for these
        String host = twxConfig.getProperty(TWXConfigDirectives.PLATFORM_HOST_DIRECTIVE);
        int port = Integer.parseInt(twxConfig.getProperty(TWXConfigDirectives.PLATFORM_PORT_DIRECTIVE));
        String appKey = twxConfig.getProperty(TWXConfigDirectives.APPKEY_DIRECTIVE);
               
        String uri = WS_PROTOCOL_STR + host + ":" + port + PLATFORM_URI_COMPONENT_STR;
        
        LOGGER.info("Launching with target uri: " + uri);
        
        ClientConfigurator config = new ClientConfigurator();
        config.setUri(uri);
        config.setSecurityClaims( new AppKeyCallback(appKey) );
        config.ignoreSSLErrors(true);
        
        String thingName = F15CThing.F15C_DEFAULT_THING_NAME;
        
        if(simConfig.containsKey(TWXConfigDirectives.THINGNAME_DIRECTIVE)) {
        	thingName = simConfig.getProperty(TWXConfigDirectives.THINGNAME_DIRECTIVE);
        }
        
        F15CConfig f15cConfig = new F15CConfig(simConfig);

        F15CWaypointFlightClient f15cClient = new F15CWaypointFlightClient(config);
                
        F15CThing f15cThing = new F15CThing(thingName, "McD F15C Thing - " + f15cConfig.getAircraftName(), "", f15cClient, f15cConfig);
        
        f15cThing.setRoute( KnownRoutes.VAN_ISLAND_TOUR_SOUTH );

        
        f15cClient.bindThing(f15cThing);
        
        try {
            // Start the client
            f15cClient.start();
            
            if(!f15cClient.waitForConnection(CONNECT_TIMEOUT)) {
                throw new Exception("Could not connect");
            }
            
            //wait for bind
            if(!EdgeUtilities.waitForBind(f15cThing, BIND_TIMEOUT)) {
                throw new Exception("Could not bind virtual thing");
            }
            
            enterRunLoop = true;
            
        } catch (Exception eStart) {
            LOGGER.warn("Initial Start Failed : " + eStart.getMessage(), eStart);
        }
        
        //TODO: build out services for the client and interact with those to control/config the modeled plane
        if(enterRunLoop) {
            //if we're connected, enter the edge runtime loop
            
            LOGGER.info("Entering edge run loop");
            
            //we are connected and the virtual thing is bound, start the plane and launch it
            
            //literal launch handled by the fgfs script
            //this starts the flight thread
            
            //TODO: move inside the CTC
            f15cThing.executeFlightPlan();
            
            //edge main execution - blocks and signals shutdown of thing/client when the flightplan is done
            edgeOperation(f15cClient, f15cThing);
            
            LOGGER.info("Exiting edge run loop");
        }
        else
        {
            LOGGER.warn("Edge startup failure. Exiting.");
        }    
    }
    
    /**
     * The main client loop. Keep running processScanRequest to refresh telemetry until shutdown
     * 
     * @param client
     */
//    private static void edgeOperation(ConnectedThingClient client, String thingName) {
//                 	
//        while ( !client.isShutdown()) {
//            // Only process the Virtual Things if the client is connected
//            if (client.isConnected()) {
//                
//            	if(LOGGER.isTraceEnabled()) {
//            		LOGGER.trace("runtime cycle started");
//            	}
//                
//                try {
//                    //twx-edge execution. 
//                    client.getThing(thingName).processScanRequest();
//                } catch (Exception e) {
//                    LOGGER.warn("Exception occurred during processScanRequest", e);
//                }
//                
//                if(LOGGER.isTraceEnabled()) {
//                	LOGGER.trace("runtime cycle completed");
//                }
//            }
//            else {
//                LOGGER.warn("Client disconnected");
//            }
//            
//            // Suspend processing at the scan rate interval
//            try {
//                Thread.sleep(SCAN_RATE);
//            } catch (InterruptedException e) {
//                LOGGER.warn(e.getMessage(), e);
//            }
//        }
//    }
}
