package org.jason.fgedge.f15c.things;

import java.util.Map;

import org.jason.fgcontrol.aircraft.f15c.F15CFields;
import org.jason.fgcontrol.aircraft.fields.FlightGearFields;
import org.jason.fgedge.util.EdgeUtilities;

import com.thingworx.types.primitives.IntegerPrimitive;
import com.thingworx.types.primitives.NumberPrimitive;
import com.thingworx.types.primitives.StringPrimitive;

public abstract class F15CDeviceScanner {
	
	/**
	 * Run a pass of the state of the f15c and set the twx properties for transit to platform
	 * 
	 * Pass a telemetry map because a lot of calls to plane.getXField will lock on each read, and slow things down.
	 * The drawback of this is managing the casting locally, and telemetry might be fractionally out-of-date.
	 * 
	 * @param f15cThing		Our virtual thing that holds our remote properties
	 * @param aircraftTelemetry		A map of telemetry fields and values
	 * 
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public static void DeviceScanner(
		F15CThing f15cThing, 
		Map<String, String> aircraftTelemetry
	) throws NumberFormatException, Exception {
		//EdgeUtilities.toThingworxPropertyName caches the result of its first call with each sim property name.
		
		//could probably just iterate most of this map, but not every twx property value is a Double casted to 
		//a NumberPrimitive 
		
		//integer property values like sim master are double values represented as strings, so they have to be
		//Double.parseDoubled and then casted to int 
		
		/////////////////
        //Consumables        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_0_CAPACITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FUEL_TANK_0_CAPACITY_FIELD) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_0_LEVEL_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FUEL_TANK_0_LEVEL_FIELD) )
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_1_CAPACITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FUEL_TANK_1_CAPACITY_FIELD) )
        ));
        f15cThing.setProperty(
        	EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_1_LEVEL_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FUEL_TANK_1_LEVEL_FIELD) )
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_2_CAPACITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FUEL_TANK_2_CAPACITY_FIELD) )
        ));
        f15cThing.setProperty(
        	EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_2_LEVEL_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FUEL_TANK_2_LEVEL_FIELD) ) 
        ));
            
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_3_CAPACITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FUEL_TANK_3_CAPACITY_FIELD) ) 
        ));
        f15cThing.setProperty(
           	EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_3_LEVEL_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FUEL_TANK_3_LEVEL_FIELD) ) 
        ));
                
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_4_CAPACITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FUEL_TANK_4_CAPACITY_FIELD) ) 
        ));
        f15cThing.setProperty(
           	EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_4_LEVEL_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FUEL_TANK_4_LEVEL_FIELD) ) 
        ));      
		
        /////////////////
        //Control
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.BATTERY_SWITCH_FIELD),
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(F15CFields.BATTERY_SWITCH_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_CUTOFF_FIELD),
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_0_CUTOFF_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_CUTOFF_FIELD),
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_1_CUTOFF_FIELD ) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_MIXTURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_0_MIXTURE_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_MIXTURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_1_MIXTURE_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_THROTTLE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_0_THROTTLE_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_THROTTLE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_1_THROTTLE_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AILERON_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.AILERON_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AILERON_TRIM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.AILERON_TRIM_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AUTO_COORDINATION_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(F15CFields.AUTO_COORDINATION_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AUTO_COORDINATION_FACTOR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.AUTO_COORDINATION_FACTOR_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ELEVATOR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ELEVATOR_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ELEVATOR_TRIM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ELEVATOR_TRIM_FIELD) )  
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FLAPS_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.FLAPS_FIELD) )  
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.RUDDER_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.RUDDER_FIELD) )  
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.RUDDER_TRIM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.RUDDER_TRIM_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.SPEED_BRAKE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.SPEED_BRAKE_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.PARKING_BRAKE_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(F15CFields.PARKING_BRAKE_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.GEAR_DOWN_FIELD),
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(F15CFields.GEAR_DOWN_FIELD ) )
        ));		
        
        /////////////////
        //Engine
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_EXHAUST_GAS_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_0_EXHAUST_GAS_TEMPERATURE_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_EXHAUST_GAS_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_1_EXHAUST_GAS_TEMPERATURE_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_0_EXHAUST_GAS_TEMPERATURE_NORM_FIELD) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_1_EXHAUST_GAS_TEMPERATURE_NORM_FIELD) ) 
        ));      
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_FUEL_FLOW_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_0_FUEL_FLOW_FIELD) )  
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_FUEL_FLOW_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_1_FUEL_FLOW_FIELD) )  
        ));    
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_OIL_PRESSURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_0_OIL_PRESSURE_FIELD) )  
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_OIL_PRESSURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_1_OIL_PRESSURE_FIELD) )  
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_RUNNING_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_0_RUNNING_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_RUNNING_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_1_RUNNING_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_THRUST_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_0_THRUST_FIELD) )  
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_THRUST_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(F15CFields.ENGINE_1_THRUST_FIELD) )  
        ));        
        
        /////////////////
        //Environment
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.DEWPOINT_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.DEWPOINT_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.EFFECTIVE_VISIBILITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.EFFECTIVE_VISIBILITY_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.PRESSURE_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.PRESSURE_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.RELATIVE_HUMIDITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.RELATIVE_HUMIDITY_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.TEMPERATURE_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.VISIBILITY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.VISIBILITY_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_DOWN_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.WIND_FROM_DOWN_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_EAST_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.WIND_FROM_EAST_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_NORTH_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.WIND_FROM_NORTH_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WINDSPEED_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.WINDSPEED_FIELD ) )
        ));   
        
        /////////////////
        //FDM
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_REPAIRING_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_DAMAGE_REPAIRING_FIELD ) )
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_AERO_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_EXTERNAL_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_GEAR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_GEAR_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_PROP_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_PROP_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_TOTAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_TOTAL_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_WEIGHT_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBX_WEIGHT_FIELD ) )
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_AERO_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_EXTERNAL_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_GEAR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_GEAR_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_PROP_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_PROP_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_TOTAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_TOTAL_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_WEIGHT_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBY_WEIGHT_FIELD ) )
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_AERO_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_EXTERNAL_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_GEAR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_GEAR_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_PROP_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_PROP_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_TOTAL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_TOTAL_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_WEIGHT_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FBZ_WEIGHT_FIELD ) )
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSX_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FSX_AERO_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSY_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FSY_AERO_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSZ_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FSZ_AERO_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWY_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FWY_AERO_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWZ_AERO_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_FWZ_AERO_FIELD ) )
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOAD_FACTOR_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_LOAD_FACTOR_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOD_NORM_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_LOD_NORM_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_WEIGHT), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_WEIGHT ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_DAMAGE_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LEFT_WING_DAMAGE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_LEFT_WING_DAMAGE_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_RIGHT_WING_DAMAGE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.FDM_RIGHT_WING_DAMAGE_FIELD ) )
        ));
        
        /////////////////
        //Orientation
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALPHA_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.ALPHA_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.BETA_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.BETA_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.HEADING_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_MAG_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.HEADING_MAG_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.PITCH_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.PITCH_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ROLL_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.ROLL_FIELD ) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.TRACK_MAG_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.TRACK_MAG_FIELD ) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.YAW_FIELD ) ) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_RATE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.YAW_RATE_FIELD ) ) 
        ));        
        
        /////////////////
        //Position
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALTITUDE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.ALTITUDE_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUND_ELEVATION_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.GROUND_ELEVATION_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.LATITUDE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.LATITUDE_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.LONGITUDE_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.LONGITUDE_FIELD ) )
        ));        
        
        /////////////////
        //Sim
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_CLOCK_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_FREEZE_CLOCK_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_MASTER_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_FREEZE_MASTER_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_SPEEDUP_FIELD), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_SPEEDUP_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_ELAPSED_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_TIME_ELAPSED_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_GMT_FIELD), 
            new StringPrimitive( aircraftTelemetry.get(FlightGearFields.SIM_TIME_GMT_FIELD ) ) 
        );
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_LOCAL_DAY_SECONDS_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_LOCAL_DAY_SECONDS_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_MP_CLOCK_FIELD),
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.SIM_MP_CLOCK_FIELD ) )
        ));        
        
        /////////////////
        //sim model
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ARMAMENT_AGM_COUNT), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(F15CFields.ARMAMENT_AGM_COUNT ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ARMAMENT_SYSTEM_RUNNING), 
            new IntegerPrimitive( (Number) (int)Double.parseDouble(aircraftTelemetry.get(F15CFields.ARMAMENT_SYSTEM_RUNNING ) )
        ));
        
        /////////////////        
        //Velocities
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.AIRSPEED_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.AIRSPEED_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUNDSPEED_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.GROUNDSPEED_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.VERTICALSPEED_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.VERTICALSPEED_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.U_BODY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.U_BODY_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.V_BODY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.V_BODY_FIELD ) )
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.W_BODY_FIELD), 
            new NumberPrimitive( (Number) Double.parseDouble(aircraftTelemetry.get(FlightGearFields.W_BODY_FIELD ) )
        ));
	}
}
