package org.jason.fgedge.c172p.client;

import org.jason.fgedge.c172p.things.C172PThing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;

public class C172PClient extends ConnectedThingClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(C172PClient.class);
	
	private static final int SCAN_RATE = 250;
	
	public C172PClient(ClientConfigurator config) throws Exception {
		super(config);
	}
	
    /**
     * The main client loop. Keep running processScanRequest to refresh telemetry until shutdown
     * 
     * @param client
     */
    protected static void edgeOperation(ConnectedThingClient client, C172PThing c172pThing) {
                 	
    	LOGGER.info("Beginning client edge operation loop");
    	
        while ( !client.isShutdown()) {
            // Only process the Virtual Things if the client is connected
            if (client.isConnected()) {
                
            	if(LOGGER.isTraceEnabled()) {
            		LOGGER.trace("runtime cycle started");
            	}
                
                try {
                    //twx-edge execution. 
                	
                	c172pThing.processScanRequest();
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
