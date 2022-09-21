package org.jason.fgedge.f15c.client;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.jason.fgcontrol.aircraft.f15c.F15CConfig;
import org.jason.fgcontrol.flight.position.KnownRoutes;
import org.jason.fgcontrol.flight.position.WaypointPosition;
import org.jason.fgedge.callback.AppKeyCallback;
import org.jason.fgedge.config.TWXConfigDirectives;
import org.jason.fgedge.f15c.things.F15CThing;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;

public class F15CFlightFleetClient extends ConnectedThingClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(F15CFlightFleetClient.class);
          
    private static final int SCAN_RATE = 250;
    
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int BIND_TIMEOUT = 5 * 1000;
    
    private final static String WS_PROTOCOL_STR = "wss://";
    private final static String PLATFORM_URI_COMPONENT_STR = "/Thingworx/WS";

    
    public F15CFlightFleetClient(ClientConfigurator config) throws Exception {
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
    	else {
    		System.out.println("Usage: App twx_edge.properties sim.properties");
    		System.exit(1);
    	}
        
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
        
        int flightPlan = F15CThing.FLIGHTPLAN_FLYAROUND;
        
        if(!F15CThing.SUPPORTED_FLIGHTPLANS.contains(flightPlan)) {
        	LOGGER.error("Unsupported flight plan");
        	System.exit(-1);
        }
                
        //////////
        
        String uri = WS_PROTOCOL_STR + host + ":" + port + PLATFORM_URI_COMPONENT_STR;
        
        LOGGER.info("Launching with target uri: " + uri);
        
        ClientConfigurator config = new ClientConfigurator();
        config.setUri(uri);
        config.setSecurityClaims( new AppKeyCallback(appKey) );
        config.ignoreSSLErrors(true);
        
        String thingName = simConfig.getProperty(TWXConfigDirectives.THINGNAME_DIRECTIVE);
        
        F15CConfig f15cConfig = new F15CConfig(simConfig);

        F15CFlightFleetClient f15cClient = new F15CFlightFleetClient(config);
                
        F15CThing f15cThing = new F15CThing(thingName, "McD F15C Thing", "", f15cClient, f15cConfig);
        
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
            
            if(flightPlan == F15CThing.FLIGHTPLAN_RUNWAY) {
            	f15cThing.setFlightPlan(F15CThing.FLIGHTPLAN_RUNWAY);
            } else if(flightPlan == F15CThing.FLIGHTPLAN_FLYAROUND) {
            	f15cThing.setFlightPlan(F15CThing.FLIGHTPLAN_FLYAROUND);
            	
//                CellTowerCoverageNetwork networkConnectivityManager = new CellTowerCoverageNetwork();
//                
//                networkConnectivityManager.addTower(KnownPositions.LONSDALE_QUAY, 10.0 * 5280.0);
//                networkConnectivityManager.addTower(KnownPositions.ABBOTSFORD, 5.0 * 5280.0);
//                
//                f15cThing.setConnectivityManager(networkConnectivityManager);
            	
                ArrayList<WaypointPosition> route = KnownRoutes.BC_SOUTH_TOUR;
                
                //for fun, reverse waypoint order
                //Collections.reverse(route);
                
                f15cThing.setRoute(route);
            } else {
            	//checked earlier for this
            	throw new Exception("Unexpected flight plan");
            }
            
            f15cThing.executeFlightPlan();
            
            //edge main execution - blocks and signals shutdown of thing/client when the flightplan is done
            edgeOperation(f15cClient, thingName);
            
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
    private static void edgeOperation(ConnectedThingClient client, String thingName) {
                 	
        while ( !client.isShutdown()) {
            // Only process the Virtual Things if the client is connected
            if (client.isConnected()) {
                
                LOGGER.trace("runtime cycle started");
                
                try {
                    //twx-edge execution. 
                    client.getThing(thingName).processScanRequest();
                } catch (Exception e) {
                    LOGGER.warn("Exception occurred during processScanRequest", e);
                }
                
                LOGGER.trace("runtime cycle completed");
            }
            else {
                LOGGER.warn("Client disconnected");
            }
            
            // Suspend processing at the scan rate interval
            try {
                Thread.sleep(SCAN_RATE);
            } catch (InterruptedException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
    }
}
