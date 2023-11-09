package org.jason.fgedge;

import java.util.Map;
import java.util.TreeSet;

public interface IAircraftThing {

    public static final int FLIGHTPLAN_RUNWAY = 0;
    public static final int FLIGHTPLAN_FLYAROUND = 1;
    
    public static final TreeSet<Integer> SUPPORTED_FLIGHTPLANS = new TreeSet<Integer>() {
        private static final long serialVersionUID = 304016809906622587L;

        {
            add(FLIGHTPLAN_RUNWAY);
            add(FLIGHTPLAN_FLYAROUND);
        }
    };
	
	public abstract Map<String, String> getAircraftTelemetry();

	public abstract void executeFlightPlan();

}
