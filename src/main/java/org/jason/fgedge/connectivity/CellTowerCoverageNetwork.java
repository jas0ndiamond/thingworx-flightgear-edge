package org.jason.fgedge.connectivity;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jason.fgcontrol.aircraft.FlightGearAircraft;
import org.jason.fgcontrol.flight.position.LatLonPosition;
import org.jason.fgcontrol.flight.position.PositionUtilities;
import org.jason.fgcontrol.flight.position.TrackPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellTowerCoverageNetwork implements ServiceCallTimeoutManagement {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellTowerCoverageNetwork.class);
	
	private ArrayList< Pair<LatLonPosition, Double> > network;
	
	public CellTowerCoverageNetwork() {
		network = new ArrayList<>();	
	}
	
	public void addTower(LatLonPosition pos, double radius) {
		network.add( new ImmutablePair<>(pos, radius));
	}
	
	@Override
	public boolean shouldTimeoutServiceCall(FlightGearAircraft aircraft) {
        boolean retval = true;
        
        //TODO: consider not caching this value. aircraft can move a lot while a long network is iterated
        TrackPosition currentPosition = aircraft.getPosition();
        
        for( Pair<LatLonPosition, Double> site : network) {
            if(PositionUtilities.distanceBetweenPositions(currentPosition, site.getKey()) < site.getValue()) {
                
                LOGGER.debug("In range of connectivity site {}", site.toString());
                
                //found an in-range tower to talk to, end search
                retval = false;
                break;
            }
        }
        
        LOGGER.debug("shouldTimeoutServiceCall returning {}", retval);
        
        return retval;
	}
}
