package org.jason.fgedge.connectivity;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.jason.fgcontrol.aircraft.FlightGearAircraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomDropsNetwork implements ServiceCallTimeoutManagement {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomDropsNetwork.class);
	
    private static final double DEFAULT_FAIL_PERCENT = 0.0001;
	
    private double failurePercent;
    
    private SecureRandom randomGenerator;
    
	public RandomDropsNetwork() throws NoSuchAlgorithmException {
		this(DEFAULT_FAIL_PERCENT);
	}
	
	public RandomDropsNetwork(double percent) throws NoSuchAlgorithmException {
		failurePercent = percent;
		
		randomGenerator = SecureRandom.getInstance("NativePRNG");
	}
	
	@Override
	public boolean shouldTimeoutServiceCall(FlightGearAircraft aircraft) {
        boolean retval = true;
        
        retval = failurePercent >= randomGenerator.nextDouble() * 100;
        
        LOGGER.debug("shouldTimeoutServiceCall returning {}", retval);
        
        return retval;
	}

	@Override
	public boolean shouldDisconnect(FlightGearAircraft aircraft) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldConnect(FlightGearAircraft aircraft) {
		// TODO Auto-generated method stub
		return false;
	}
}
