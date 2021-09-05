package org.jason.flightgear.c172p.client;

import java.io.IOException;

import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.jason.flightgear.c172p.client.callback.AppKeyCallback;
import org.jason.flightgear.c172p.things.C172PThing;
import org.jason.flightgear.planes.c172p.C172P;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;

public class C172PClient extends ConnectedThingClient {
	
	private static final Logger logger = LoggerFactory.getLogger(C172PClient.class);
	
	public C172PClient(ClientConfigurator config) throws Exception {
		super(config);
	}
	
	public static void main(String args[]) throws Exception {
		//host
		//port
		//appkey
		
		String C172PThingName = "C172PThing";
		
		int maxRunTime = 5 * 60 * 1000;
		int runTime = 0;
		
		int scanRate = 500;
		
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String appKey = args[2];
		
		String uri = "wss://" + host + ":" + port + "/Thingworx/WS";
		
		logger.info("Launching with target uri: " + uri);
		
		ClientConfigurator config = new ClientConfigurator();
		config.setUri(uri);
		config.setSecurityClaims( new AppKeyCallback(appKey) );
		config.ignoreSSLErrors(true);

		C172PClient c172pClient = new C172PClient(config);
				
		c172pClient.bindThing(new C172PThing(C172PThingName, "Cessna 172P Thing", "", c172pClient));
		
        try {
            // Start the client
        	c172pClient.start();
        	
        	if(!c172pClient.waitForConnection(5000)) {
        		throw new Exception("Could not connect");
        	}
        	
        } catch (Exception eStart) {
            System.out.println("Initial Start Failed : " + eStart.getMessage());
        }
        
        while (runTime < maxRunTime && !c172pClient.isShutdown()) {
            // Only process the Virtual Things if the client is connected
            if (c172pClient.isConnected()) {
            	c172pClient.getThing(C172PThingName).processScanRequest();
            }
            else {
            	logger.warn("Client disconnected");
            }
            
            
            runTime += scanRate;
            
            // Suspend processing at the scan rate interval
            Thread.sleep(scanRate);
            
            
        }
	}
}
