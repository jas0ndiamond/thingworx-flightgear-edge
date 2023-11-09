package org.jason.fgedge.connectivity;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jason.fgcontrol.flight.position.LatLonPosition;
import org.jason.fgcontrol.flight.position.PositionUtilities;
import org.jason.fgcontrol.flight.position.TrackPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellTowerCoverageNetwork implements ServiceCallTimeoutManagement {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellTowerCoverageNetwork.class);
	
	private ArrayList< Pair<LatLonPosition, Double> > network;
	
	public CellTowerCoverageNetwork() {
		network = new ArrayList< Pair<LatLonPosition, Double> >();	
	}
	
	/**
	 * Add a cell tower with a range. 
	 * 
	 * @param pos		Location of tower
	 * @param radius	Radius of service from the tower origin, in feet
	 */
	public void addTower(LatLonPosition pos, double radius) {
		network.add( new ImmutablePair<>(pos, radius));
	}

	private boolean isWithinTowerRange(TrackPosition currentPosition) {
		
		boolean retval = false;
				
        for( Pair<LatLonPosition, Double> site : network) {
            if(PositionUtilities.distanceBetweenPositions(currentPosition, site.getKey()) < site.getValue()) {
                
                LOGGER.debug("In range of connectivity site {}", site.toString());
                
                //found an in-range tower to talk to, end search
                retval = true;
                break;
            }
        }
        
        return retval;
	}

	@Override
	public boolean shouldDisconnect(TrackPosition currentPosition) {
		return isWithinTowerRange(currentPosition) == false;
	}

	@Override
	public boolean shouldConnect(TrackPosition currentPosition) {
		return isWithinTowerRange(currentPosition) == true;
	}
}
