package org.jason.fgedge.connectivity;

import org.jason.fgcontrol.aircraft.FlightGearAircraft;

public interface ServiceCallTimeoutManagement {
	/**
	 * Determine whether or not to deliberately execute a doomed-to-timeout 
	 * service call in place of a service call that's typically expected to 
	 * succeed. 
	 * 
	 * Examples:
     *     Beyond a maxDistance from a set of positions (cell tower coverage).
     *     Above or below a set altitude (cell tower coverage or edge antenna range).
     *  Above or below a velocity
     *  Battery level low (low power environment)
     *  Randomly (general network failure)
	 * 
	 * @param aircraft	aircraft and simulator state to base decision
	 * 
	 * @return	true if there should be a timeout, false otherwise
	 */
	public abstract boolean shouldTimeoutServiceCall(FlightGearAircraft aircraft);
	
	
	
	public abstract boolean shouldDisconnect(FlightGearAircraft aircraft);
	
	public abstract boolean shouldConnect(FlightGearAircraft aircraft);
}
