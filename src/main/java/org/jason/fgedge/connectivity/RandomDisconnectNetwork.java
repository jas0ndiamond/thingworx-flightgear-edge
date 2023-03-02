package org.jason.fgedge.connectivity;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.jason.fgcontrol.flight.position.TrackPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomDisconnectNetwork implements ServiceCallTimeoutManagement {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomDisconnectNetwork.class);
	
    private static final double DEFAULT_FAIL_PERCENT = 0.0001;
	
    private double failurePercent;
    
    private SecureRandom randomGenerator;
    
	public RandomDisconnectNetwork() throws NoSuchAlgorithmException {
		this(DEFAULT_FAIL_PERCENT);
	}
	
	public RandomDisconnectNetwork(double percent) throws NoSuchAlgorithmException {
		failurePercent = percent;
		
		randomGenerator = SecureRandom.getInstance("NativePRNG");
	}
	
//	@Override
//	public boolean shouldTimeoutServiceCall(FlightGearAircraft aircraft) {
//        boolean retval = true;
//        
//        retval = failurePercent >= randomGenerator.nextDouble() * 100;
//        
//        LOGGER.debug("shouldTimeoutServiceCall returning {}", retval);
//        
//        return retval;
//	}

	@Override
	public boolean shouldDisconnect(TrackPosition currentPosition) {
		boolean retval = true;
		
		retval = failurePercent >= randomGenerator.nextDouble() * 100.0;
		
		LOGGER.debug("shouldDisconnect returning {}", retval);
		
		return retval;
	}

	@Override
	public boolean shouldConnect(TrackPosition currentPosition) {
		boolean retval = true;
		
		retval = (1.0 - failurePercent) >= randomGenerator.nextDouble() * 100.0;
		
		LOGGER.debug("shouldConnect returning {}", retval);
		
		return retval;
	}
}
