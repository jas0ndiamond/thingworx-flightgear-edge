package org.jason.fgedge.c172p.things;

import java.util.Map;

import org.jason.fgcontrol.aircraft.c172p.C172PFields;
import org.jason.fgcontrol.aircraft.fields.FlightGearFields;
import org.jason.fgedge.util.EdgeUtilities;

import com.thingworx.types.primitives.IntegerPrimitive;
import com.thingworx.types.primitives.NumberPrimitive;
import com.thingworx.types.primitives.StringPrimitive;

public abstract class C172PDeviceScanner {
	
	/**
	 * Run a pass of the state of the c172p and set the twx properties for transit to platform
	 * 
	 * Pass a telemetry map because a lot of calls to plane.getXField will lock on each read, and slow things down.
	 * The drawback of this is managing the casting locally, and telemetry might be fractionally out-of-date.
	 * 
	 * @param c172pThing	Our virtual thing that holds our remote properties
	 * @param aircraftTelemetry		A map of telemetry fields and values
	 * 
	 * @throws Exception 	On any failures processing the telemetry state- fail the whole scan
	 * @throws NumberFormatException	On any failures processing the telemetry state- fail the whole scan
	 */
	public static void DeviceScanner(
		C172PThing c172pThing, 
		Map<String, String> aircraftTelemetry
	) throws NumberFormatException, Exception {
		
		//EdgeUtilities.toThingworxPropertyName caches the result of its first call with each sim property name.
		
		//could probably just iterate most of this map, but not every twx property value is a Double casted to 
		//a NumberPrimitive 
		
		//integer property values like sim master are double values represented as strings, so they have to be
		//Double.parseDoubled and then casted to int 
		
		/////////////////
        //Consumables        
		
		//tank 0
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_CAPACITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.FUEL_TANK_0_CAPACITY_FIELD ) ) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_LEVEL_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.FUEL_TANK_0_LEVEL_FIELD ) ) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_WATER_CONTAMINATION_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.FUEL_TANK_0_WATER_CONTAMINATION_FIELD ) )
        ));
        
        //tank 1
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_CAPACITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.FUEL_TANK_1_CAPACITY_FIELD ) ) 
        ));
        c172pThing.setProperty(
        	EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_LEVEL_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.FUEL_TANK_1_LEVEL_FIELD ) )  
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_WATER_CONTAMINATION_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.FUEL_TANK_1_WATER_CONTAMINATION_FIELD ) )
        ));
        
		/////////////////
        //Control
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.BATTERY_SWITCH_FIELD),
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(C172PFields.BATTERY_SWITCH_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.MIXTURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.MIXTURE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.THROTTLE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.THROTTLE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AILERON_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.AILERON_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AILERON_TRIM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.AILERON_TRIM_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AUTO_COORDINATION_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(C172PFields.AUTO_COORDINATION_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AUTO_COORDINATION_FACTOR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.AUTO_COORDINATION_FACTOR_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ELEVATOR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.ELEVATOR_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ELEVATOR_TRIM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.ELEVATOR_TRIM_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FLAPS_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.FLAPS_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.RUDDER_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.RUDDER_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.RUDDER_TRIM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.RUDDER_TRIM_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.SPEED_BRAKE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.SPEED_BRAKE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.PARKING_BRAKE_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(C172PFields.PARKING_BRAKE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.GEAR_DOWN_FIELD),
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(C172PFields.GEAR_DOWN_FIELD ) )
        ));
        
		/////////////////
        //Engine
        c172pThing.setProperty(
        	EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_COWLING_AIR_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.ENGINES_COWLING_AIR_TEMPERATURE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_EXHAUST_GAS_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.ENGINES_EXHAUST_GAS_TEMPERATURE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.ENGINES_EXHAUST_GAS_TEMPERATURE_NORM_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_FUEL_FLOW_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.ENGINES_FUEL_FLOW_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_MP_OSI_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.ENGINES_MP_OSI_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_OIL_PRESSURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.ENGINES_OIL_PRESSURE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_OIL_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.ENGINES_OIL_TEMPERATURE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_RPM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.ENGINES_RPM_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_RUNNING_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(C172PFields.ENGINES_RUNNING_FIELD ) )
        ));        
        
        /////////////////
        //Environment
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.DEWPOINT_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.DEWPOINT_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.EFFECTIVE_VISIBILITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.EFFECTIVE_VISIBILITY_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.PRESSURE_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.PRESSURE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.RELATIVE_HUMIDITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.RELATIVE_HUMIDITY_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.TEMPERATURE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.VISIBILITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.VISIBILITY_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_DOWN_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.WIND_FROM_DOWN_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_EAST_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.WIND_FROM_EAST_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_NORTH_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.WIND_FROM_NORTH_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WINDSPEED_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.WINDSPEED_FIELD ) )
        ));        
        
        /////////////////
        //FDM
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_REPAIRING_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_DAMAGE_REPAIRING_FIELD ) )
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_AERO_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_EXTERNAL_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_GEAR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_GEAR_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_PROP_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_PROP_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_TOTAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_TOTAL_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_WEIGHT_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_WEIGHT_FIELD ) )
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_AERO_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_EXTERNAL_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_GEAR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_GEAR_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_PROP_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_PROP_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_TOTAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_TOTAL_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_WEIGHT_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_WEIGHT_FIELD ) )
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_AERO_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_EXTERNAL_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_GEAR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_GEAR_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_PROP_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_PROP_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_TOTAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_TOTAL_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_WEIGHT_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_WEIGHT_FIELD ) )
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSX_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FSX_AERO_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSY_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FSY_AERO_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSZ_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FSZ_AERO_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWY_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FWY_AERO_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWZ_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FWZ_AERO_FIELD ) )
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOAD_FACTOR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_LOAD_FACTOR_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOD_NORM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_LOD_NORM_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_WEIGHT), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_WEIGHT ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_DAMAGE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LEFT_WING_DAMAGE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_LEFT_WING_DAMAGE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_RIGHT_WING_DAMAGE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_RIGHT_WING_DAMAGE_FIELD ) )
        ));
        
        /////////////////
        //Orientation
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALPHA_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.ALPHA_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.BETA_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.BETA_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.HEADING_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_MAG_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.HEADING_MAG_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.PITCH_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.PITCH_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ROLL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.ROLL_FIELD ) ) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.TRACK_MAG_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.TRACK_MAG_FIELD ) ) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.YAW_FIELD ) ) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_RATE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.YAW_RATE_FIELD ) ) 
        ));        
        
        /////////////////
        //Position
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALTITUDE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.ALTITUDE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUND_ELEVATION_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.GROUND_ELEVATION_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.LATITUDE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.LATITUDE_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.LONGITUDE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.LONGITUDE_FIELD ) )
        ));        
        
        /////////////////
        //Sim
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_CLOCK_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_FREEZE_CLOCK_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_MASTER_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_FREEZE_MASTER_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_SPEEDUP_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_SPEEDUP_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_ELAPSED_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_TIME_ELAPSED_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_GMT_FIELD), 
            new StringPrimitive( aircraftTelemetry.get(FlightGearFields.SIM_TIME_GMT_FIELD ) ) 
        );
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_LOCAL_DAY_SECONDS_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_LOCAL_DAY_SECONDS_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_MP_CLOCK_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_MP_CLOCK_FIELD ) )
        ));
        
        /////////////////
        //Sim Model
        //parking brake state accessible read-only in controls
        
        /////////////////
        //Systems
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.BATTERY_CHARGE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(C172PFields.BATTERY_CHARGE_FIELD ) )
        ));
        
        /////////////////        
        //Velocities
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.AIRSPEED_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.AIRSPEED_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUNDSPEED_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.GROUNDSPEED_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.VERTICALSPEED_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.VERTICALSPEED_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.U_BODY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.U_BODY_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.V_BODY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.V_BODY_FIELD ) )
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.W_BODY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.W_BODY_FIELD ) )
        ));
	}
}
