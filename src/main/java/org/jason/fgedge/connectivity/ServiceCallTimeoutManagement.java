package org.jason.fgedge.connectivity;

import org.jason.fgcontrol.aircraft.FlightGearAircraft;

public interface ServiceCallTimeoutManagement {
	/**
	 * Determine whether or not to deliberately execute a doomed-to-timeout 
	 * service call in place of a service call that's typically expected to 
	 * succeed. 
	 * 
	 * @param aircraft	aircraft and simulator state to base decision
	 * 
	 * @return	true if there should be a timeout, false otherwise
	 */
	public abstract boolean shouldTimeoutServiceCall(FlightGearAircraft aircraft);
}
