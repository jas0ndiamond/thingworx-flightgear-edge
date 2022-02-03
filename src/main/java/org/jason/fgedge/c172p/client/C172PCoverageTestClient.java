package org.jason.fgedge.c172p.client;

import java.util.ArrayList;

import org.jason.fgcontrol.flight.position.KnownPositions;
import org.jason.fgcontrol.flight.position.WaypointPosition;
import org.jason.fgedge.c172p.things.C172PThing;
import org.jason.fgedge.callback.AppKeyCallback;
import org.jason.fgedge.connectivity.CellTowerCoverageNetwork;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;

public class C172PCoverageTestClient extends ConnectedThingClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(C172PCoverageTestClient.class);
    
    private static final String C172P_THING_NAME = "C172PThing"; 
        
    private static final int SCAN_RATE = 250;
    
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int BIND_TIMEOUT = 5 * 1000;
    
    private final static String WS_PROTOCOL_STR = "wss://";
    private final static String PLATFORM_URI_COMPONENT_STR = "/Thingworx/WS";

    
    public C172PCoverageTestClient(ClientConfigurator config) throws Exception {
        super(config);
    }
    
    /**
     * Flaky connectivity tester. Timeout service calls based on plane state. Plane will still be connected
     * to the platform but property updates should timeout according to the configured plan/rules.
     * 
     * Run shell script c172p_flight.sh to launch simulator.
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
               
        //////////
        
        String uri = WS_PROTOCOL_STR + host + ":" + port + PLATFORM_URI_COMPONENT_STR;
        
        LOGGER.info("Launching with target uri: " + uri);
        
        ClientConfigurator config = new ClientConfigurator();
        config.setUri(uri);
        config.setSecurityClaims( new AppKeyCallback(appKey) );
        config.ignoreSSLErrors(true);

        C172PCoverageTestClient c172pClient = new C172PCoverageTestClient(config);
                
        C172PThing c172pThing = new C172PThing(C172P_THING_NAME, "Cessna 172P Thing", "", c172pClient);

        CellTowerCoverageNetwork networkConnectivityManager = new CellTowerCoverageNetwork();
        
        networkConnectivityManager.addTower(KnownPositions.VAN_INTER_AIRPORT_YVR, 5.0 * 5280.0);
        networkConnectivityManager.addTower(KnownPositions.UBC, 5.0 * 5280.0);
        networkConnectivityManager.addTower(KnownPositions.LONSDALE_QUAY, 5.0 * 5280.0);
        networkConnectivityManager.addTower(KnownPositions.ABBOTSFORD, 5.0 * 5280.0);
        
        c172pThing.setConnectivityManager(networkConnectivityManager);
        
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
        
        //TODO: build out services for the client and interact with those to control/config the modeled plane
        if(enterRunLoop) {
            //if we're connected, enter the edge runtime loop
            
        	
        	
            LOGGER.info("Entering edge run loop");
            
            //we are connected and the virtual thing is bound, start the plane and launch it
            
            //literal launch handled by the fgfs script
            //this starts the flight thread

            //start in range of cell towers, then move out of range 
            ArrayList<WaypointPosition> route = new ArrayList<WaypointPosition>();
            route.add(KnownPositions.UBC);
            route.add(KnownPositions.LONSDALE_QUAY);
            route.add(KnownPositions.ABBOTSFORD);
            route.add(KnownPositions.PRINCETON);
            
            c172pThing.setRoute(route);
            c172pThing.setFlightPlan(C172PThing.FLIGHTPLAN_FLYAROUND);
            
            
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
