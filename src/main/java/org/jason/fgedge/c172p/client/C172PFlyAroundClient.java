package org.jason.fgedge.c172p.client;

import org.jason.fgedge.c172p.things.C172PThing;
import org.jason.fgedge.callback.AppKeyCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;

public class C172PFlyAroundClient extends ConnectedThingClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(C172PFlyAroundClient.class);
    
    private static final String C172P_THING_NAME = "C172PThing"; 
    
    //run time of an edge flight
    private static final int MAX_RUN_TIME = 2 * 60 * 1000;
    
    private static final int SCAN_RATE = 250;
    
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int BIND_TIMEOUT = 5 * 1000;
    
    private static final int RUNWAY_PRELAUNCH_SLEEP = 2 * 60 * 1000;
    
    private final static String WS_PROTOCOL_STR = "wss://";
    private final static String PLATFORM_URI_COMPONENT_STR = "/Thingworx/WS";

    
    public C172PFlyAroundClient(ClientConfigurator config) throws Exception {
        super(config);
    }
    
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

        C172PFlyAroundClient c172pClient = new C172PFlyAroundClient(config);
                
        C172PThing c172pThing = new C172PThing(C172P_THING_NAME, "Cessna 172P Thing", "", c172pClient);
        
        c172pClient.bindThing(c172pThing);
        
        try {
            // Start the client
            c172pClient.start();
            
            if(!c172pClient.waitForConnection(CONNECT_TIMEOUT)) {
                throw new Exception("Could not connect");
            }
            
            //wait for bind
            if(!waitForBind(c172pThing, BIND_TIMEOUT)) {
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
            
            //edge startup
            
            //we are connected and the virtual thing is bound, start the plane and launch it
            edgeStartup( c172pThing );
            
            c172pThing.executeFlightPlan();
            
            //edge main execution
            edgeOperation(c172pClient);
            
            //edge shutdown
            edgeShutdown(c172pClient, c172pThing);
            
            LOGGER.info("Exiting edge run loop");
        }
        else
        {
            LOGGER.warn("Edge startup failure. Exiting.");
        }    
    }
    
    private static void edgeShutdown(C172PFlyAroundClient client, C172PThing thing) throws Exception {
        
        //shutdown the plane
        
        LOGGER.trace("edgeShutdown started");
        
        //shuts down the client too
        if(thing != null) {
            thing.Shutdown();
        }
        
        //trailing sleep to allow thing and client to complete shutdown
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        
        LOGGER.trace("edgeShutdown completed");
        
    }
    
    /**
     * A few telemetry cycles after startup and before launch
     * 
     * I feel bad about operating on the Client and Thing independently, 
     * but right now the services aren't built out yet
     * 
     * 
     * @param thing
     * @throws Exception 
     */
    private static void edgeStartup(C172PThing thing) throws Exception {
        
        LOGGER.trace("edgeStartup started");
        
        
        
        //assume bound client is connected and bound
        //TODO: check this
        
        thing.startupPlane();
        
        int waitTime = 0;
        while (waitTime <= RUNWAY_PRELAUNCH_SLEEP) {
            try {
                thing.processScanRequest();
            } catch (Exception e) {
                LOGGER.warn(e.getMessage(), e);
            }
            
            waitTime += SCAN_RATE;
            
            // Suspend processing at the scan rate interval
            try {
                Thread.sleep(SCAN_RATE);
            } catch (InterruptedException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
        
        LOGGER.trace("edgeStartup completed");
    }
    
    private static void edgeOperation(ConnectedThingClient client) {
              
        int runTime = 0;
        while (runTime <= MAX_RUN_TIME && !client.isShutdown()) {
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
            
            runTime += SCAN_RATE;
            
            // Suspend processing at the scan rate interval
            try {
                Thread.sleep(SCAN_RATE);
            } catch (InterruptedException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
    }
    
    private static boolean waitForBind(VirtualThing thing, int timeout) {
        boolean retval = false;
        
        int waitTime = 0;
        int sleepInterval = 500;
        while( !retval && waitTime < timeout ) {
            if(thing.isBound()) {
                retval = true;
            }
            else {
                waitTime += sleepInterval;
                try {
                    Thread.sleep(sleepInterval);
                } catch (InterruptedException e) {
                    LOGGER.warn(e.getMessage(), e);
                }
            }    
        }
        
        return retval;
    }
}
