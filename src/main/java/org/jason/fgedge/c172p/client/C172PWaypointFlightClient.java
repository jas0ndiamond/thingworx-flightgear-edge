package org.jason.fgedge.c172p.client;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.jason.fgcontrol.aircraft.c172p.C172PConfig;
import org.jason.fgcontrol.flight.position.KnownRoutes;
import org.jason.fgcontrol.flight.position.WaypointPosition;
import org.jason.fgedge.c172p.things.C172PThing;
import org.jason.fgedge.config.EdgeConfig;
import org.jason.fgedge.config.EdgeConfigVisitor;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class C172PWaypointFlightClient extends C172PClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(C172PWaypointFlightClient.class);
            
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int BIND_TIMEOUT = 5 * 1000;
    
    public C172PWaypointFlightClient(EdgeConfig config) throws Exception {
        super(config);
        
    }
    
    @Override
    public void shutdown() {
    	try {
			super.shutdown();
		} catch (Exception e) {
			LOGGER.warn("Exception shutting down ConnectedThingClient", e);
		}
    }
    
    /////////////////////////////////////////////
    
    /**
     * Main program for a C172P waypoint flight. Start the plane and fly a set of waypoints.
     * 
     * Run shell script c172p_flight.sh to launch simulator.
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
    	else if(args.length == 1 && args[0].equals("-h")) {
    		System.err.println("Usage: App twx_edge.properties sim.properties");
    		System.exit(1);
    	}
        
    	LOGGER.info("Using twx config file {} and sim config file {}", twxConfigFile, simConfigFile);
    	
    	Properties twxConfigProperties = new Properties();
    	twxConfigProperties.load(new FileInputStream(twxConfigFile) );
    	
    	Properties simConfigProperties = new Properties();
    	simConfigProperties.load(new FileInputStream(simConfigFile) );
    	    	
        boolean enterRunLoop = false;
        
        //////////
        //input
        
        String thingName;
        
        EdgeConfig twxClientConfig = new EdgeConfig(); 
        EdgeConfigVisitor.buildEdgeConfig(twxClientConfig, twxConfigProperties);
        
        C172PConfig c172PConfig = new C172PConfig(simConfigProperties);
        //////////
                
        thingName = twxClientConfig.getThingName();
        
        C172PWaypointFlightClient c172pClient = new C172PWaypointFlightClient(twxClientConfig);
		C172PThing c172pThing = new C172PThing(
			thingName, 
			"Cessna 172P Thing - " + c172PConfig.getAircraftName(), 
			"", 
			c172pClient, 
			c172PConfig
		);
		
        ArrayList<WaypointPosition> route = KnownRoutes.VANCOUVER_SHORT_TOUR;       
        //route.add(0, KnownPositions.LONSDALE_QUAY);
        //route.add(0, KnownPositions.GROUSE_MOUNTAIN);
        
        c172pThing.setRoute( route );
        
        
        
		
        c172pClient.bindThing(c172pThing);
        
        c172pThing.synchronizeState();
        
        try {
            // Start the client
            c172pClient.start();
            
            if(!c172pClient.waitForConnection(CONNECT_TIMEOUT)) {
                throw new Exception("Could not connect client");
            }
            
            LOGGER.info("C172P Client is connected!");
            
            //wait for bind
            if(!EdgeUtilities.waitForBind(c172pThing, BIND_TIMEOUT)) {
                throw new Exception("Could not bind virtual thing");
            }
            
            LOGGER.info("C172P virtual thing [{}] is bound: {}", thingName, c172pThing.isBound());
            
            enterRunLoop = true;
            
        } catch (Exception eStart) {
            LOGGER.warn("Initial Start Failed : " + eStart.getMessage(), eStart);
        }
        
        ////////////////
        if(enterRunLoop) {
            //if we're connected, enter the edge runtime loop
            
            LOGGER.info("Entering edge run loop");
            
            //we are connected and the virtual thing is bound, start the plane and launch it
            
            //literal launch handled by the fgfs script
            //this starts the flight thread
            
            c172pThing.setFlightPlan(C172PThing.FLIGHTPLAN_FLYAROUND);
            
            //TODO: move inside the CTC??
            c172pThing.executeFlightPlan();
            
            //edge main execution - blocks and signals shutdown of thing/client when the flightplan is done
            edgeOperation(c172pClient, c172pThing);
            
            LOGGER.info("Exiting edge run loop");
        }
        else
        {
            LOGGER.warn("Edge startup failure. Exiting.");
            c172pClient.shutdown();
            c172pThing.Shutdown();
        }    
    }
}
