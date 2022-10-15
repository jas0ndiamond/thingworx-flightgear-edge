package org.jason.fgedge.f15c.things;

import java.util.ArrayList;

import org.jason.fgcontrol.aircraft.f15c.F15CFields;
import org.jason.fgcontrol.aircraft.fields.FlightGearFields;
import org.jason.fgedge.util.EdgeUtilities;

import com.thingworx.metadata.PropertyDefinition;
import com.thingworx.types.BaseTypes;

//TODO: maybe migrate this to xml or template processing
public abstract class PropertyInitializer {
    public static ArrayList<PropertyDefinition> buildProperties() {
        ArrayList<PropertyDefinition> definitions = new ArrayList<>();
        
        //Consumables
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_0_CAPACITY_FIELD), 
                F15CFields.FUEL_TANK_0_CAPACITY_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_0_LEVEL_FIELD), 
                F15CFields.FUEL_TANK_0_LEVEL_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_1_CAPACITY_FIELD), 
                F15CFields.FUEL_TANK_1_CAPACITY_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_1_LEVEL_FIELD), 
                F15CFields.FUEL_TANK_1_LEVEL_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
		definitions.add(
			new PropertyDefinition(
				EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_2_CAPACITY_FIELD),
				F15CFields.FUEL_TANK_2_CAPACITY_FIELD_DESC, 
				BaseTypes.NUMBER
		));
		definitions.add(
			new PropertyDefinition(
				EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_2_LEVEL_FIELD),
				F15CFields.FUEL_TANK_2_LEVEL_FIELD_DESC, 
				BaseTypes.NUMBER
		));
		definitions.add(
			new PropertyDefinition(
				EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_3_CAPACITY_FIELD),
				F15CFields.FUEL_TANK_3_CAPACITY_FIELD_DESC, 
				BaseTypes.NUMBER
		));
		definitions.add(
			new PropertyDefinition(
				EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_3_LEVEL_FIELD),
				F15CFields.FUEL_TANK_3_LEVEL_FIELD_DESC, BaseTypes.NUMBER
		));
		definitions.add(
			new PropertyDefinition(
				EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_4_CAPACITY_FIELD),
				F15CFields.FUEL_TANK_4_CAPACITY_FIELD_DESC, 
				BaseTypes.NUMBER
		));
		definitions.add(
			new PropertyDefinition(
				EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_4_LEVEL_FIELD),
				F15CFields.FUEL_TANK_4_LEVEL_FIELD_DESC, BaseTypes.NUMBER
		));        
        
        //Control
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.BATTERY_SWITCH_FIELD),
                F15CFields.BATTERY_SWITCH_FIELD_DESC, 
                BaseTypes.INTEGER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_CUTOFF_FIELD),
                F15CFields.ENGINE_0_CUTOFF_FIELD_DESC, 
                BaseTypes.INTEGER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_CUTOFF_FIELD),
                F15CFields.ENGINE_1_CUTOFF_FIELD_DESC, 
                BaseTypes.INTEGER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_MIXTURE_FIELD), 
                F15CFields.ENGINE_0_MIXTURE_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_MIXTURE_FIELD), 
                F15CFields.ENGINE_1_MIXTURE_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_THROTTLE_FIELD), 
                F15CFields.ENGINE_0_THROTTLE_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_THROTTLE_FIELD), 
                F15CFields.ENGINE_1_THROTTLE_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.AILERON_FIELD), 
                F15CFields.AILERON_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.AILERON_TRIM_FIELD), 
                F15CFields.AILERON_TRIM_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.AUTO_COORDINATION_FIELD), 
                F15CFields.AUTO_COORDINATION_FIELD_DESC, 
                BaseTypes.INTEGER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.AUTO_COORDINATION_FACTOR_FIELD), 
                F15CFields.AUTO_COORDINATION_FACTOR_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ELEVATOR_FIELD), 
                F15CFields.ELEVATOR_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ELEVATOR_TRIM_FIELD), 
                F15CFields.ELEVATOR_TRIM_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.FLAPS_FIELD), 
                F15CFields.FLAPS_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.RUDDER_FIELD), 
                F15CFields.RUDDER_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.RUDDER_TRIM_FIELD), 
                F15CFields.RUDDER_TRIM_FIELD_DESC, 
                BaseTypes.NUMBER
        ));     
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.SPEED_BRAKE_FIELD), 
                F15CFields.SPEED_BRAKE_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.PARKING_BRAKE_FIELD), 
                F15CFields.PARKING_BRAKE_FIELD_DESC, 
                BaseTypes.INTEGER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.GEAR_DOWN_FIELD),
                F15CFields.GEAR_DOWN_FIELD_DESC, 
                BaseTypes.INTEGER
        ));
        
        //Engine
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_EXHAUST_GAS_TEMPERATURE_FIELD), 
                F15CFields.ENGINE_0_EXHAUST_GAS_TEMPERATURE_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_EXHAUST_GAS_TEMPERATURE_FIELD), 
                F15CFields.ENGINE_1_EXHAUST_GAS_TEMPERATURE_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
                F15CFields.ENGINE_0_EXHAUST_GAS_TEMPERATURE_NORM_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
                F15CFields.ENGINE_1_EXHAUST_GAS_TEMPERATURE_NORM_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_FUEL_FLOW_FIELD), 
                F15CFields.ENGINE_0_FUEL_FLOW_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_FUEL_FLOW_FIELD), 
                F15CFields.ENGINE_1_FUEL_FLOW_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_OIL_PRESSURE_FIELD), 
                F15CFields.ENGINE_0_OIL_PRESSURE_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_OIL_PRESSURE_FIELD), 
                F15CFields.ENGINE_1_OIL_PRESSURE_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_RUNNING_FIELD), 
                F15CFields.ENGINE_0_RUNNING_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_RUNNING_FIELD), 
                F15CFields.ENGINE_1_RUNNING_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_THRUST_FIELD), 
                F15CFields.ENGINE_0_THRUST_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_THRUST_FIELD), 
                F15CFields.ENGINE_1_THRUST_DESC, 
                BaseTypes.NUMBER
        ));    

        //Environment
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.DEWPOINT_FIELD), 
                FlightGearFields.DEWPOINT_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.EFFECTIVE_VISIBILITY_FIELD), 
                FlightGearFields.EFFECTIVE_VISIBILITY_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.PRESSURE_FIELD),
                FlightGearFields.PRESSURE_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.RELATIVE_HUMIDITY_FIELD), 
                FlightGearFields.RELATIVE_HUMIDITY_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.TEMPERATURE_FIELD),
                FlightGearFields.TEMPERATURE_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.VISIBILITY_FIELD), 
                FlightGearFields.VISIBILITY_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_DOWN_FIELD), 
                FlightGearFields.WIND_FROM_DOWN_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_EAST_FIELD), 
                FlightGearFields.WIND_FROM_EAST_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_NORTH_FIELD), 
                FlightGearFields.WIND_FROM_NORTH_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.WINDSPEED_FIELD),
                FlightGearFields.WINDSPEED_FIELD_DESC, 
                BaseTypes.NUMBER
        ));

        //FDM
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_REPAIRING_FIELD), 
                FlightGearFields.FDM_DAMAGE_REPAIRING_DESC, 
                BaseTypes.INTEGER
        ));
        
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_AERO_FIELD),
                FlightGearFields.FDM_FBX_AERO_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_EXTERNAL_FIELD), 
                FlightGearFields.FDM_FBX_EXTERNAL_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_GEAR_FIELD),
                FlightGearFields.FDM_FBX_GEAR_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_PROP_FIELD), 
                FlightGearFields.FDM_FBX_PROP_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_TOTAL_FIELD), 
                FlightGearFields.FDM_FBX_TOTAL_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_WEIGHT_FIELD), 
                FlightGearFields.FDM_FBX_WEIGHT_DESC, 
                BaseTypes.NUMBER
        ));
        
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_AERO_FIELD), 
                FlightGearFields.FDM_FBY_AERO_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_EXTERNAL_FIELD), 
                FlightGearFields.FDM_FBY_EXTERNAL_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_GEAR_FIELD),
                FlightGearFields.FDM_FBY_GEAR_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_PROP_FIELD), 
                FlightGearFields.FDM_FBY_PROP_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_TOTAL_FIELD), 
                FlightGearFields.FDM_FBY_TOTAL_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_WEIGHT_FIELD), 
                FlightGearFields.FDM_FBY_WEIGHT_DESC, 
                BaseTypes.NUMBER
        ));
        
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_AERO_FIELD), 
                FlightGearFields.FDM_FBZ_AERO_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_EXTERNAL_FIELD), 
                FlightGearFields.FDM_FBZ_EXTERNAL_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_GEAR_FIELD),
                FlightGearFields.FDM_FBZ_GEAR_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_PROP_FIELD), 
                FlightGearFields.FDM_FBZ_PROP_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_TOTAL_FIELD), 
                FlightGearFields.FDM_FBZ_TOTAL_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_WEIGHT_FIELD), 
                FlightGearFields.FDM_FBZ_WEIGHT_DESC, 
                BaseTypes.NUMBER
        ));
        
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSX_AERO_FIELD), 
                FlightGearFields.FDM_FSX_AERO_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSY_AERO_FIELD), 
                FlightGearFields.FDM_FSY_AERO_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSZ_AERO_FIELD), 
                FlightGearFields.FDM_FWZ_AERO_DESC, 
                BaseTypes.NUMBER));

        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWY_AERO_FIELD), 
                FlightGearFields.FDM_FWY_AERO_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWZ_AERO_FIELD), 
                FlightGearFields.FDM_FSZ_AERO_DESC, 
                BaseTypes.NUMBER
        ));

        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOAD_FACTOR_FIELD), 
                FlightGearFields.FDM_LOAD_FACTOR_DESC, 
                BaseTypes.NUMBER
        ));

        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOD_NORM_FIELD), 
                FlightGearFields.FDM_LOD_NORM_DESC, 
                BaseTypes.NUMBER
        ));

        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_WEIGHT), 
                FlightGearFields.FDM_WEIGHT_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_FIELD), 
                FlightGearFields.FDM_DAMAGE_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LEFT_WING_DAMAGE_FIELD), 
                FlightGearFields.FDM_LEFT_WING_DAMAGE_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_RIGHT_WING_DAMAGE_FIELD), 
                FlightGearFields.FDM_RIGHT_WING_DAMAGE_DESC, 
                BaseTypes.NUMBER
        ));
        
        //Orientation
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALPHA_FIELD),
                FlightGearFields.ALPHA_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.BETA_FIELD), 
                FlightGearFields.BETA_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_FIELD), 
                FlightGearFields.HEADING_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_MAG_FIELD), 
                FlightGearFields.HEADING_MAG_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.PITCH_FIELD), 
                FlightGearFields.PITCH_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.ROLL_FIELD), 
                FlightGearFields.ROLL_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.TRACK_MAG_FIELD), 
                FlightGearFields.TRACK_MAG_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_FIELD), 
                FlightGearFields.YAW_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_RATE_FIELD), 
                FlightGearFields.YAW_RATE_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
         
        //Position
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALTITUDE_FIELD), 
                FlightGearFields.ALTITUDE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUND_ELEVATION_FIELD), 
                FlightGearFields.GROUND_ELEVATION_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.LATITUDE_FIELD), 
                FlightGearFields.LATITUDE_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.LONGITUDE_FIELD), 
                FlightGearFields.LONGITUDE_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        
        //Sim
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_CLOCK_FIELD), 
                FlightGearFields.SIM_FREEZE_CLOCK_FIELD_DESC, 
                BaseTypes.INTEGER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_MASTER_FIELD), 
                FlightGearFields.SIM_FREEZE_MASTER_FIELD_DESC, 
                BaseTypes.INTEGER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_SPEEDUP_FIELD), 
                FlightGearFields.SIM_SPEED_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_ELAPSED_FIELD), 
                FlightGearFields.SIM_TIME_ELAPSED_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_GMT_FIELD), 
                FlightGearFields.SIM_TIME_GMT_DESC, 
                BaseTypes.STRING
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_LOCAL_DAY_SECONDS_FIELD), 
                FlightGearFields.SIM_LOCAL_DAY_SECONDS_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_MP_CLOCK_FIELD), 
                FlightGearFields.SIM_MP_CLOCK_DESC, 
                BaseTypes.NUMBER
        ));

        //Sim Model
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ARMAMENT_AGM_COUNT),
                F15CFields.ARMAMENT_AGM_COUNT_DESC, 
                BaseTypes.INTEGER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(F15CFields.ARMAMENT_SYSTEM_RUNNING),
                F15CFields.ARMAMENT_SYSTEM_RUNNING_DESC, 
                BaseTypes.INTEGER
        ));
        
        //Velocities
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.AIRSPEED_FIELD),
                FlightGearFields.AIRSPEED_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUNDSPEED_FIELD), 
                FlightGearFields.GROUNDSPEED_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.VERTICALSPEED_FIELD), 
                FlightGearFields.VERTICALSPEED_FIELD_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.U_BODY_FIELD), 
                FlightGearFields.U_BODY_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.V_BODY_FIELD), 
                FlightGearFields.V_BODY_DESC, 
                BaseTypes.NUMBER
        ));
        definitions.add(
            new PropertyDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.W_BODY_FIELD), 
                FlightGearFields.W_BODY_DESC, 
                BaseTypes.NUMBER
        ));

        return definitions;
    }
}
