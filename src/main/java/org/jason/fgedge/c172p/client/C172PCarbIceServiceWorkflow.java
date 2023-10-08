package org.jason.fgedge.c172p.client;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import org.jason.fgcontrol.aircraft.c172p.C172PConfig;
import org.jason.fgcontrol.aircraft.c172p.C172PFields;
import org.jason.fgedge.c172p.things.C172PThing;
import org.jason.fgedge.config.EdgeConfig;
import org.jason.fgedge.config.EdgeConfigVisitor;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;

/**
 * Start the engine for an aircraft on a runway and increase the carb_ice level.
 *
 */
public class C172PCarbIceServiceWorkflow extends C172PClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(C172PCarbIceServiceWorkflow.class);
        
    private static final int SCAN_RATE = 250;
    
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int BIND_TIMEOUT = 5 * 1000;

    
    public C172PCarbIceServiceWorkflow(ClientConfigurator config) throws Exception {
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
        
        C172PCarbIceServiceWorkflow c172pClient = new C172PCarbIceServiceWorkflow(twxClientConfig);
        C172PThing c172pThing = new C172PThing(
        	thingName, 
        	"Cessna 172P Thing - " + c172PConfig.getAircraftName(), 
        	"", 
        	c172pClient, 
        	c172PConfig
        );
        
        c172pClient.bindThing(c172pThing);
        
        try {
            // Start the client
            c172pClient.start();
            
            if(!c172pClient.waitForConnection(CONNECT_TIMEOUT)) {
                throw new Exception("Could not connect");
            }
            
            LOGGER.info("C172P Client is connected!");
            
            //wait for bind
            if(!EdgeUtilities.waitForBind(c172pThing, BIND_TIMEOUT)) {
                throw new Exception("Could not bind virtual thing");
            }

            LOGGER.info("C172P virtual thing is bound!");
            
            enterRunLoop = true;
            
        } catch (Exception eStart) {
            LOGGER.warn("Initial Start Failed : " + eStart.getMessage(), eStart);
        }
        
        if(enterRunLoop) {
            //if we're connected, enter the edge runtime loop
            
            LOGGER.info("Entering edge run loop");
            
            //we are connected and the virtual thing is bound, start the plane and launch it
                        
            //c172pThing.setFlightPlan(C172PThing.FLIGHTPLAN_RUNWAY);
            //c172pThing.executeFlightPlan();
            
            c172pThing.SetEnableComplexEngineProcedures(true);
            
            // start engine to begin workflow
            c172pThing.startupPlane();
    		
            new Thread() {
            	@Override
            	public void run() {
            		
					LOGGER.info("Increasing engine throttle to test value");

					try {
						c172pThing.SetThrottle(0.35);
						Thread.sleep(2000L);
						c172pThing.SetThrottle(0.55);
						Thread.sleep(2000L);
						c172pThing.SetThrottle(0.75);
	
						LOGGER.info("Engine throttle set to test value");
	
						LOGGER.info("Starting affectation of carburetor ice in engine");
	
						Thread.sleep(20000L);
	
						c172pThing.SetCarbIce(0.1);
	
						Thread.sleep(10000L);
	
						c172pThing.SetCarbIce(0.2);
	
						Thread.sleep(10000L);
	
						c172pThing.SetCarbIce(0.3);
	
						LOGGER.info("Ending affectation of carburetor ice in engine. Entering service interval.");
	
						Thread.sleep(60000L);
	
						LOGGER.info("Ending service interval.");
					}
					catch (InterruptedException e) {
						LOGGER.error("InterruptedException in carb ice workflow", e);
					}
					catch (Exception e) {
						LOGGER.error("Exception in carb ice workflow", e);
					}
            	}
            }.start();
            
            //edge main execution - blocks and signals shutdown of thing/client when the flightplan is done
            edgeOperation(c172pClient, thingName, c172pThing);
            
            LOGGER.info("Exiting edge run loop");
        }
        else
        {
            LOGGER.warn("Edge application startup failure. Exiting.");
        }    
    }
    
    /**
     * The main client loop. Keep running processScanRequest to refresh telemetry until shutdown
     * 
     * @param client
     */
    protected static void edgeOperation(ConnectedThingClient client, String thingName, C172PThing planeThing) {
    	
        while ( !client.isShutdown()) {
            // Only process the Virtual Things if the client is connected
            if (client.isConnected()) {
            	if(LOGGER.isTraceEnabled()) {
            		LOGGER.trace("runtime cycle started");
            	}
                
                try {
                    //twx-edge execution. 
                    //client.getThing(C172P_DEFAULT_THING_NAME).processScanRequest();
                	planeThing.processScanRequest();
                	
                	Map<String, String> tel = planeThing.getAircraftTelemetry();
                	
                	if(LOGGER.isDebugEnabled()) {
	                	LOGGER.debug("=======\nCarburetor Ice Amount: {}\nEngine Output: {} rpms\n",
	                			tel.get(C172PFields.ENGINES_CARB_ICE),
	                			tel.get(C172PFields.ENGINES_RPM_FIELD) 
	                			);
                	}
                	
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
