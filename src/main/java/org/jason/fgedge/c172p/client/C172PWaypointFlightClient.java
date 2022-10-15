package org.jason.fgedge.c172p.client;

import java.io.FileInputStream;
import java.util.Properties;

import org.jason.fgcontrol.aircraft.c172p.C172PConfig;
import org.jason.fgcontrol.flight.position.KnownRoutes;
import org.jason.fgedge.c172p.things.C172PThing;
import org.jason.fgedge.callback.AppKeyCallback;
import org.jason.fgedge.config.TWXConfigDirectives;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;

public class C172PWaypointFlightClient extends ConnectedThingClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(C172PWaypointFlightClient.class);
            
    private static final int SCAN_RATE = 250;
    
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int BIND_TIMEOUT = 5 * 1000;
    
    private final static String WS_PROTOCOL_STR = "wss://";
    private final static String PLATFORM_URI_COMPONENT_STR = "/Thingworx/WS";

    
    public C172PWaypointFlightClient(ClientConfigurator config) throws Exception {
        super(config);
    }
    
    /**
     * Main program for a C172P fleet test. Start the plane and fly a set of waypoints.
     * 
     * Run shell script c172p_flight_fleet.sh to launch simulator.
     * 
     * @param args	twx_edge.properties sim.properties
     * 
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {

    	//read config
    	//App twx_edge.properties sim.properties
    	
    	String twxConfigFile = "./conf/twx_edge.properties";
    	String simConfigFile = "./conf/c172p.properties";
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
                
        //////////
        
        String uri = WS_PROTOCOL_STR + host + ":" + port + PLATFORM_URI_COMPONENT_STR;
        
        LOGGER.info("Launching with target uri: " + uri);
        
        ClientConfigurator config = new ClientConfigurator();
        config.setUri(uri);
        config.setSecurityClaims( new AppKeyCallback(appKey) );
        config.ignoreSSLErrors(true);

        String thingName = simConfig.getProperty(TWXConfigDirectives.THINGNAME_DIRECTIVE);
        
        C172PWaypointFlightClient c172pClient = new C172PWaypointFlightClient(config);
        
        C172PConfig c172PConfig = new C172PConfig(simConfig);
                
		C172PThing c172pThing = new C172PThing(thingName, "Cessna 172P Thing", "", c172pClient, c172PConfig);
		
		//set our route
		c172pThing.setRoute( KnownRoutes.VANCOUVER_TOUR );
		
		
        
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
            
            //TODO: move inside the CTC
            c172pThing.executeFlightPlan();
            
            //edge main execution - blocks and signals shutdown of thing/client when the flightplan is done
            edgeOperation(c172pClient, thingName);
            
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
                
            	if(LOGGER.isTraceEnabled()) {
            		LOGGER.trace("runtime cycle started");
            	}
                
                try {
                    //twx-edge execution. 
                    client.getThing(thingName).processScanRequest();
                } catch (Exception e) {
                    LOGGER.warn("Exception occurred during processScanRequest", e);
                }
                
                if(LOGGER.isTraceEnabled()) {	
                	LOGGER.trace("runtime cycle completed");
                }
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
