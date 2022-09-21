package org.jason.fgedge.c172p.client;

import java.util.ArrayList;

import org.jason.fgcontrol.flight.position.KnownRoutes;
import org.jason.fgcontrol.flight.position.WaypointPosition;
import org.jason.fgedge.c172p.things.C172PThing;
import org.jason.fgedge.callback.AppKeyCallback;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;

public class C172PFlightPlanClient extends ConnectedThingClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(C172PFlightPlanClient.class);
    
    private static final String C172P_THING_NAME = "C172PThing"; 
        
    private static final int SCAN_RATE = 250;
    
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int BIND_TIMEOUT = 5 * 1000;
    
    private final static String WS_PROTOCOL_STR = "wss://";
    private final static String PLATFORM_URI_COMPONENT_STR = "/Thingworx/WS";

    
    public C172PFlightPlanClient(ClientConfigurator config) throws Exception {
        super(config);
    }
    
    /**
     * Main program for a C172P runway test. Start the plane and move the throttle up and down.
     * Refuel a few times, then allow the engine to run until out of fuel and stopped.
     * 
     * Run shell script c172p_runway.sh to launch simulator.
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
        
        if(!C172PThing.SUPPORTED_FLIGHTPLANS.contains(flightPlan)) {
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

        C172PFlightPlanClient c172pClient = new C172PFlightPlanClient(config);
                
		C172PThing c172pThing = new C172PThing(C172P_THING_NAME, "Cessna 172P Thing", "", c172pClient);
        
        c172pClient.bindThing(c172pThing);
        
        try {
            // Start the client
            c172pClient.start();
            
            if(!c172pClient.waitForConnection(CONNECT_TIMEOUT)) {
                throw new Exception("Could not connect");
            }
            
            //wait for bind
            if(!EdgeUtilities.waitForBind(c172pThing, BIND_TIMEOUT)) {
                throw new Exception("Could not bind virtual thing");
            }
            
            enterRunLoop = true;
            
        } catch (Exception eStart) {
            LOGGER.warn("Initial Start Failed : " + eStart.getMessage(), eStart);
        }
        
        if(enterRunLoop) {
            //if we're connected, enter the edge runtime loop
            
        	
        	
            LOGGER.info("Entering edge run loop");
            
            //we are connected and the virtual thing is bound, start the plane and launch it
            
            //literal launch handled by the fgfs script
            //this starts the flight thread
            
            if(flightPlan == C172PThing.FLIGHTPLAN_RUNWAY) {
            	c172pThing.setFlightPlan(C172PThing.FLIGHTPLAN_RUNWAY);
            } else if(flightPlan == C172PThing.FLIGHTPLAN_FLYAROUND) {
            	c172pThing.setFlightPlan(C172PThing.FLIGHTPLAN_FLYAROUND);
            	
//                CellTowerCoverageNetwork networkConnectivityManager = new CellTowerCoverageNetwork();
//                
//                networkConnectivityManager.addTower(KnownPositions.LONSDALE_QUAY, 10.0 * 5280.0);
//                networkConnectivityManager.addTower(KnownPositions.ABBOTSFORD, 5.0 * 5280.0);
//                
//                c172pThing.setConnectivityManager(networkConnectivityManager);
                
                ArrayList<WaypointPosition> route = KnownRoutes.VANCOUVER_TOUR;
                
                c172pThing.setRoute(route);
                
            } else {
            	//checked earlier for this
            	throw new Exception("Unexpected flight plan");
            }
            
            c172pThing.executeFlightPlan();
            
            //edge main execution - blocks and signals shutdown of thing/client when the flightplan is done
            edgeOperation(c172pClient);
            
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
                    client.getThing(C172P_THING_NAME).processScanRequest();
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
