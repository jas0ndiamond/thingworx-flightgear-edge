package org.jason.flightgear.edge.c172p.fields;

public abstract class PlaneFields {

	//TODO: maybe migrate these to flightgear-control fields? 
	
	//TODO: Enums or Hashes for these
	
    ////////
    //property field names and stuff

    //Position
//    "/position/altitude-ft",
//    "/position/ground-elev-ft",
//    "/position/latitude-deg",
//    "/position/longitude-deg"
    
    public final static String ALTITUDE_FIELD = "/position/altitude-ft";
    public final static String ALTITUDE_FIELD_DESC = "The altitude of the plane in feet";

    public final static String GROUND_ELEVATION_FIELD = "/position/ground-elev-ft";
    public final static String GROUND_ELEVATION_FIELD_DESC = "The ground elevation in feet";
    
    public final static String LATITUDE_FIELD = "/position/latitude-deg";
    public final static String LATITUDE_FIELD_DESC = "The latitude of the plane in degrees";
    
    public final static String LONGITUDE_FIELD = "/position/longitude-deg";
    public final static String LONGITUDE_FIELD_DESC = "The longitude of the plane in degrees";
    
    //Orientation
//    "/orientation/alpha-deg",
//    "/orientation/beta-deg",
//    "/orientation/heading-deg",
//    "/orientation/heading-magnetic-deg",
//    "/orientation/pitch-deg",
//    "/orientation/roll-deg",
//    "/orientation/track-magnetic-deg",
//    "/orientation/yaw-deg"
    
    public final static String ALPHA_FIELD = "/orientation/alpha-deg";
    public final static String ALPHA_FIELD_DESC = "The alpha orientation of the plane in degrees";
    
    public final static String BETA_FIELD = "/orientation/beta-deg";
    public final static String BETA_FIELD_DESC = "The beta orientation of the plane in degrees";
    
    public final static String HEADING_FIELD = "/orientation/heading-deg";
    public final static String HEADING_FIELD_DESC = "The heading of the plane in degrees";
    
    public final static String HEADING_MAG_FIELD = "/orientation/heading-magnetic-deg";
    public final static String HEADING_MAG_FIELD_DESC = "The magnetc heading of the plane in degrees";
    
    public final static String PITCH_FIELD = "/orientation/pitch-deg";
    public final static String PITCH_FIELD_DESC = "The pitch of the plane in degrees";
    
    public final static String ROLL_FIELD = "/orientation/roll-deg";
    public final static String ROLL_FIELD_DESC = "The roll of the plane in degrees";
    
    public final static String TRACK_MAG_FIELD = "/orientation/track-magnetic-deg";
    public final static String TRACK_MAG_FIELD_DESC = "The magnetic track of the plane in degrees";
    
    public final static String YAW_FIELD = "/orientation/yaw-deg";
    public final static String YAW_FIELD_DESC = "The yaw of the plane in degrees";
    
    public final static String YAW_RATE_FIELD = "/orientation/yaw-rate-degps";
    public final static String YAW_RATE_FIELD_DESC = "The yaw rate of the plane in degrees per second";
    
    //Control
//    "/controls/electric/battery-switch",
//    "/controls/engines/current-engine/mixture",
//    "/controls/engines/current-engine/throttle",
//    "/controls/flight/aileron",
//    "/controls/flight/auto-coordination",
//    "/controls/flight/auto-coordination-factor",
//    "/controls/flight/elevator",
//    "/controls/flight/flaps",
//    "/controls/flight/rudder",
//    "/controls/flight/speedbrake",
//    "/controls/gear/brake-parking",
//    "/controls/gear/gear-down"
    
    public final static String BATTERY_SWITCH_FIELD = "/controls/electric/battery-switch";
    public final static String BATTERY_SWITCH_FIELD_DESC = "The state of the battery switch";
    
    public final static String MIXTURE_FIELD = "/controls/engines/current-engine/mixture";
    public final static String MIXTURE_FIELD_DESC = "The engine mixture percentage";
    
    public final static String THROTTLE_FIELD = "/controls/engines/current-engine/throttle";
    public final static String THROTTLE_FIELD_DESC = "The engine throttle percentage";
    
    public final static String AILERON_FIELD = "/controls/flight/aileron";
    public final static String AILERON_FIELD_DESC = "The aileron orientation";
    
    public final static String AUTO_COORDINATION_FIELD = "/controls/flight/auto-coordination";
    public final static String AUTO_COORDINATION_FIELD_DESC = "The auto-coordination setting for controls";
    
    public final static String AUTO_COORDINATION_FACTOR_FIELD = "/controls/flight/auto-coordination-factor";
    public final static String AUTO_COORDINATION_FACTOR_FIELD_DESC = "The auto-coordination factor controls";
    
    public final static String ELEVATOR_FIELD = "/controls/flight/elevator";
    public final static String ELEVATOR_FIELD_DESC = "The elevator orientation";
    
    public final static String FLAPS_FIELD = "/controls/flight/flaps";
    public final static String FLAPS_FIELD_DESC = "The flaps orientation";
    
    public final static String RUDDER_FIELD = "/controls/flight/rudder";
    public final static String RUDDER_FIELD_DESC = "The rudder orientation";
    
    public final static String SPEED_BRAKE_FIELD = "/controls/flight/speedbrake";
    public final static String SPEED_BRAKE_FIELD_DESC = "The speedbrake orientation";
    
    public final static String PARKING_BRAKE_FIELD = "/controls/gear/brake-parking";
    public final static String PARKING_BRAKE_FIELD_DESC = "The parking brake setting";
    
    public final static String GEAR_DOWN_FIELD = "/controls/gear/gear-down";
    public final static String GEAR_DOWN_FIELD_DESC = "The gear down setting";
    
    //Sim
//    /sim/model/c172p/brake-parking
    public final static String SIM_PARKING_BRAKE_FIELD = "/sim/model/c172p/brake-parking";
    public final static String SIM_PARKING_BRAKE_FIELD_DESC = "The sim parking brake setting";
    
    //Velocities
//    "/velocities/airspeed-kt",
//    "/velocities/groundspeed-kt",
//    "/velocities/vertical-speed-fps"
    
    public final static String AIRSPEED_FIELD = "/velocities/airspeed-kt";
    public final static String AIRSPEED_FIELD_DESC = "The current airspeed in knots";
    
    public final static String GROUNDSPEED_FIELD = "/velocities/groundspeed-kt";
    public final static String GROUNDSPEED_FIELD_DESC = "The current groundspeed in knots";
    
    public final static String VERTICALSPEED_FIELD = "/velocities/vertical-speed-fps";
    public final static String VERTICALSPEED_FIELD_DESC = "The current vertical speed in feet per second";
}
