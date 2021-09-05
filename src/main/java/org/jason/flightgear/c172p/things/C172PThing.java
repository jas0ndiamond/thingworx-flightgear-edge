package org.jason.flightgear.c172p.things;

import java.io.IOException;

import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.jason.flightgear.planes.c172p.C172P;

import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.types.InfoTable;

public class C172PThing extends VirtualThing {

	private static final long serialVersionUID = -1670069869427933890L;
	
	private final static int TARGET_ALTITUDE = 5000;
	private final static int TARGET_HEADING = 0;
	
	private C172P plane;

	public C172PThing(String name, String description, String identifer, ConnectedThingClient client) throws InvalidTelnetOptionException, IOException {
		super(name, description, identifer, client);
		
		this.init();
	}
	
	private void init() throws InvalidTelnetOptionException, IOException {
		
		//define datashape definition
		
		
		//init plane object
		plane = new C172P();
		
		plane.setDamageEnabled(false);
		
		//start plane. function should return or it'll block everything else
		plane.startupPlane();
		
		launchPlane();
	}
	
	private void launchPlane() {
		//TODO: pack this into a C172P utilities-type visitor
		
		//assume start unpaused;
		
		plane.setPause(true);
		
		//place in the air
		plane.setAltitude(TARGET_ALTITUDE);
		
		//retract landing gear if not fixed

		//head north
		plane.setHeading(TARGET_HEADING);
				
		plane.setPause(false);
		
		//set while not paused. this functions more like a boost- 
		//the plane can be acceled or deceled to the specified speed, 
		//but then the fdm takes over and stabilizes the air speed
		plane.setAirSpeed(100);
		
		//initial drop. allow to level off
		try {
			Thread.sleep(40*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//////
		//initial check that we've leveled off
		plane.altitudeCheck(500, TARGET_ALTITUDE);
		plane.pitchCheck(4, 3.0);
		plane.rollCheck(4, 0.0);
		
		//increase throttle
		plane.setPause(true);
		plane.setThrottle(0.80);
		plane.setPause(false);
	}
	
	@Override
	public void synchronizeState() {
		
		//check readytosend?
		
		super.syncProperties();
	}
	
	@Override
	public void processScanRequest() {
		
		this.scanDevice();
	}
	
	public void scanDevice() {
		
		//get telemetry from the plane, pour into twx types, and set defined properties
	}
	
	/////////////////////////
	//plane services
	/////////////////////////
	
	//waypoints
	//add waypoint
	//remove waypoint

	/////////////////////////
	//control surfaces
	/////////////////////////
	
	
	/////////////////////////
	//orientation
	/////////////////////////
	
	/////////////////////////
	//position
	/////////////////////////
	
	
	//not really necessary except for display 
	public InfoTable GetTelemetry() {
		
		//read from 
		
		return null;
	}

}
