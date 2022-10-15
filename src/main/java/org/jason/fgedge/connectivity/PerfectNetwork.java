package org.jason.fgedge.connectivity;

import org.jason.fgcontrol.aircraft.FlightGearAircraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfectNetwork implements ServiceCallTimeoutManagement {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerfectNetwork.class);
	
	public PerfectNetwork() {}

	@Override
	public boolean shouldTimeoutServiceCall(FlightGearAircraft aircraft) {
        boolean retval = false;
        LOGGER.debug("shouldTimeoutServiceCall returning {}", retval);
        
        return retval;
	}

	@Override
	public boolean shouldDisconnect(FlightGearAircraft aircraft) {
		//never disconnect
		return false;
	}

	@Override
	public boolean shouldConnect(FlightGearAircraft aircraft) {
		//never reconnect because no disconnects
		return false;
	}
}
