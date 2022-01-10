package org.jason.fgedge.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.things.VirtualThing;

public abstract class EdgeUtilities {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EdgeUtilities.class);
	
    public static String toThingworxPropertyName(String propertyName) {
    	
    	/** 
    	 * "/controls/anti-ice/wing-heat" 
    	 * 		=> _controls_anti-ice_wing-heat
    	 * 
    	 * "/consumables/fuel/tank[1]/capacity-gal_us"
    	 * 		=> _consumables_fuel_tank1_capacity-gal_us
    	 * 
    	 */
    	
        return propertyName.substring(1)
        	.replace('/', '_')
        	.replace("[", "")
        	.replace("]", "")
        ;
    }
    
    public static boolean waitForBind(VirtualThing thing, int timeout) {
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
