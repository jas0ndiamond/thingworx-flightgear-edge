package org.jason.fgedge.c172p.client;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jason.fgcontrol.aircraft.c172p.C172PConfig;
import org.jason.fgcontrol.aircraft.fields.FlightGearFields;
import org.jason.fgcontrol.flight.position.KnownPositions;
import org.jason.fgcontrol.flight.position.KnownRoutes;
import org.jason.fgcontrol.flight.position.PositionUtilities;
import org.jason.fgcontrol.flight.position.TrackPosition;
import org.jason.fgedge.c172p.things.C172PThing;
import org.jason.fgedge.config.EdgeConfig;
import org.jason.fgedge.config.EdgeConfigVisitor;
import org.jason.fgedge.connectivity.CaltropsClient;
import org.jason.fgedge.connectivity.CellTowerCoverageNetwork;
import org.jason.fgedge.exception.ConfigException;
import org.jason.fgedge.util.EdgeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO: ICaltrops
public class C172PWaypointFlightVariableConnectivityClient extends C172PClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(C172PWaypointFlightVariableConnectivityClient.class);
            
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int BIND_TIMEOUT = 5 * 1000;
    
    private CaltropsClient caltropsClient;
    
    private int proxyConnectPort;
    
    public C172PWaypointFlightVariableConnectivityClient(EdgeConfig config) throws Exception {
        super(config);
        
        if(!config.isCaltropsEnabled()) {
        	throw new ConfigException("Caltrops not enabled. Invalid config?");
        }
        caltropsClient = new CaltropsClient(config.getCaltropsHost(), config.getCaltropsPort());
        
        proxyConnectPort = config.getCaltropsProxyPort();
    }
    
    public boolean signalCaltropsDisconnect() {
    	return caltropsClient.dropInbound(proxyConnectPort);
    }
    
    public boolean signalCaltropsConnect() {
    	return caltropsClient.acceptInbound(proxyConnectPort);
    }
    
    public int getProxyConnectPort() {
    	return proxyConnectPort;
    }
    
/////////////////////////////////////////////
    
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
    	else if(args.length == 1 && args[0].equals("-h")) {
    		System.err.println("Usage: App twx_edge.properties sim.properties");
    		System.exit(1);
    	}
        
    	LOGGER.info("Using twx config file {} and sim config file {}", twxConfigFile, simConfigFile);
    	
    	Properties twxConfigProperties = new Properties();
    	twxConfigProperties.load(new FileInputStream(twxConfigFile) );
    	
    	Properties simConfigProperties = new Properties();
    	simConfigProperties.load(new FileInputStream(simConfigFile) );
    	
    	//TODO: validate expected config directives are defined
    	
        boolean enterRunLoop = false;
        
        //////////
        //input
        
        String thingName;
        
        EdgeConfig twxClientConfig = new EdgeConfig(); 
        EdgeConfigVisitor.buildEdgeConfig(twxClientConfig, twxConfigProperties);
        
        C172PConfig c172PConfig = new C172PConfig(simConfigProperties);
        //////////
                
        thingName = twxClientConfig.getThingName();
                
        C172PWaypointFlightVariableConnectivityClient c172pClient = new C172PWaypointFlightVariableConnectivityClient(twxClientConfig);
		
        C172PThing c172pThing = new C172PThing(
        	thingName, 
        	"Cessna 172P Thing - " + c172PConfig.getAircraftName(), 
        	"", 
        	c172pClient, 
        	c172PConfig
        );
		
		//set our route
		c172pThing.setRoute( KnownRoutes.VANCOUVER_TOUR );
        
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
            c172pThing.shutdown();
            c172pClient.shutdown();
        }    
    }
    
    /**
     * The main client loop. Keep running processScanRequest to refresh telemetry until shutdown
     * 
     * @param client
     */
    protected static void edgeOperation(C172PWaypointFlightVariableConnectivityClient client, C172PThing c172pThing) {
    	
    	LOGGER.info("Beginning client edge operation loop");
    	
    	Map<String, String> telemetry = new HashMap<String, String>(100);
    	
    	//cell network - disconnect when too far from any tower
    	CellTowerCoverageNetwork cellNetwork = new CellTowerCoverageNetwork();
    	
    	//https://www.mapdevelopers.com/draw-circle-tool.php?circles=%5B%5B8046.7%2C49.1929646%2C-123.1799938%2C%22%2317AA00%22%2C%22%23000000%22%2C0.4%5D%2C%5B3218.68%2C49.3098847%2C-123.0829901%2C%22%2317AA00%22%2C%22%23000000%22%2C0.4%5D%2C%5B4828.02%2C49.3697404%2C-123.0974744%2C%22%2317AA00%22%2C%22%23000000%22%2C0.4%5D%5D
    	cellNetwork.addTower(KnownPositions.VAN_INTER_AIRPORT_YVR, 5.0 * PositionUtilities.FEET_IN_MILE);
    	cellNetwork.addTower(KnownPositions.LONSDALE_QUAY, 2.0 * PositionUtilities.FEET_IN_MILE);
    	cellNetwork.addTower(KnownPositions.GROUSE_MOUNTAIN, 3.0 * PositionUtilities.FEET_IN_MILE);
    	
    	double lat, lon;
    	TrackPosition currentPosition = new TrackPosition();
    	
        while ( !client.isShutdown()) {

			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("runtime cycle started");
			}

			try {
				// model scan request

				//TODO: throws an exception that pre-empts the caltrops cycle after discon
				c172pThing.processScanRequest();
			} catch (Exception e) {
				LOGGER.warn("Exception occurred during processScanRequest", e);
			}
			////////////////////

			// load our local copy of the aircraft telemetry
			telemetry.putAll(c172pThing.getAircraftTelemetry());

			lat = Double.parseDouble(telemetry.get(FlightGearFields.LATITUDE_FIELD));
			lon = Double.parseDouble(telemetry.get(FlightGearFields.LONGITUDE_FIELD));

			currentPosition.setLatitude(lat);
			currentPosition.setLongitude(lon);

			if (client.isConnected() && cellNetwork.shouldDisconnect(currentPosition)) {
				// signal drop connection inbound

				LOGGER.info("C172P has ventured outside of coverage zone. Signaling inbound connection drop");

				if (!client.signalCaltropsDisconnect()) {
					LOGGER.error("Caltrops disconnect failed");
				}

			} else if (!client.isConnected() && cellNetwork.shouldConnect(currentPosition)) {
				// signal accept connection inbound

				LOGGER.info("C172P has ventured inside of coverage zone. Signaling inbound connection accept");

				if (!client.signalCaltropsConnect()) {
					LOGGER.error("Caltrops connect failed");
				}
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("edgeOperation - no connectivity changes");
				}
			}

			// clear the telemetry
			telemetry.clear();


			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("runtime cycle completed");
			}

            //////////////////////
			//scan cycle completed
            // Suspend processing at the scan rate interval
            try {
                Thread.sleep(SCAN_RATE);
            } catch (InterruptedException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
    }
}
