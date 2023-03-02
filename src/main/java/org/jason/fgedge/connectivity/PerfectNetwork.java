package org.jason.fgedge.connectivity;

import org.jason.fgcontrol.flight.position.TrackPosition;

public class PerfectNetwork implements ServiceCallTimeoutManagement {

    //private static final Logger LOGGER = LoggerFactory.getLogger(PerfectNetwork.class);
	
	public PerfectNetwork() {}

//	@Override
//	public boolean shouldTimeoutServiceCall(FlightGearAircraft aircraft) {
//        boolean retval = false;
//        LOGGER.debug("shouldTimeoutServiceCall returning {}", retval);
//        
//        return retval;
//	}

	@Override
	public boolean shouldDisconnect(TrackPosition currentPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldConnect(TrackPosition currentPosition) {
		// TODO Auto-generated method stub
		return false;
	}
}
