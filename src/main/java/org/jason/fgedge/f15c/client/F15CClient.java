package org.jason.fgedge.f15c.client;

import java.util.ArrayList;

import org.jason.fgcontrol.flight.position.KnownRoutes;
import org.jason.fgcontrol.flight.position.WaypointPosition;
import org.jason.fgedge.callback.AppKeyCallback;
import org.jason.fgedge.f15c.things.F15CThing;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;

public class F15CClient extends ConnectedThingClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(F15CClient.class);
    
    private static final String F15CP_THING_NAME = "F15CThing"; 
        
    private static final int SCAN_RATE = 250;
    
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int BIND_TIMEOUT = 5 * 1000;
    
    private final static String WS_PROTOCOL_STR = "wss://";
    private final static String PLATFORM_URI_COMPONENT_STR = "/Thingworx/WS";

    
    public F15CClient(ClientConfigurator config) throws Exception {
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
        //host
        //port
        //appkey
        
        boolean enterRunLoop = false;
        
        //////////
        //input
        
        //TODO: add guard rails for these
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String appKey = args[2];
        int flightPlan = Integer.parseInt(args[3]);
        
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

        F15CClient f15cClient = new F15CClient(config);
                
        F15CThing f15cThing = new F15CThing(F15CP_THING_NAME, "McD F15C Thing", "", f15cClient);
        
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
            edgeOperation(f15cClient);
            
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
    private static void edgeOperation(ConnectedThingClient client) {
                 	
        while ( !client.isShutdown()) {
            // Only process the Virtual Things if the client is connected
            if (client.isConnected()) {
                
                LOGGER.trace("runtime cycle started");
                
                try {
                    //twx-edge execution. 
                    client.getThing(F15CP_THING_NAME).processScanRequest();
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
