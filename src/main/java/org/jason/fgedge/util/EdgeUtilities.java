package org.jason.fgedge.util;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.things.VirtualThing;

public abstract class EdgeUtilities {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EdgeUtilities.class);
	
	private static HashMap<String, String> twxPropertyMap = new HashMap<String, String> () {
		private static final long serialVersionUID = 6332858047106595994L;
	};
	
    /**
     * Convert flight sim property names to their twx-safe equivalent. Cache the results 
     * so the string replacement doesn't have to be redone.
     * 
     * This will likely be called once on twx property definition, where the string replacement
     * is made, and the result is referred to during device scans and property updates.  
     * 
     * @param propertyName The flightsim property name
     * 
     * @return The twx-safe property name
     */
    public static String toThingworxPropertyName(String propertyName) {
    	
    	/** 
    	 * "/controls/anti-ice/wing-heat" 
    	 * 		=> _controls_anti-ice_wing-heat
    	 * 
    	 * "/consumables/fuel/tank[1]/capacity-gal_us"
    	 * 		=> _consumables_fuel_tank1_capacity-gal_us
    	 * 
    	 */
    	
    	String retval = null;
    	
    	if(twxPropertyMap.containsKey(propertyName)) {
    		retval = twxPropertyMap.get(propertyName);
    	}
    	else {
    		if(propertyName.charAt(0) == '/') {
	    		retval = propertyName.substring(1)
	    			.replace(" ", "_")
	    	       	.replace('/', '_')
	    	       	.replace("[", "")
	    	       	.replace("]", "");
    		}
    		else {
	    		retval = propertyName
		    			.replace(" ", "_")
		    	       	.replace('/', '_')
		    	       	.replace("[", "")
		    	       	.replace("]", "");
    		}
    	       
    		//cache the result
    		twxPropertyMap.put(propertyName, retval);
    	}
    	
        return retval;
    }
    
    public static boolean waitForBind(VirtualThing thing, long timeout) {
        boolean retval = false;
        
        long waitTime = 0L;
        long sleepInterval = 500L;
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
