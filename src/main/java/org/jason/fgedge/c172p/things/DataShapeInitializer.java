package org.jason.fgedge.c172p.things;

import org.jason.fgcontrol.aircraft.c172p.C172PFields;
import org.jason.fgcontrol.aircraft.fields.FlightGearFields;
import org.jason.fgcontrol.flight.position.WaypointManager;
import org.jason.fgedge.util.EdgeUtilities;

import com.thingworx.metadata.FieldDefinition;
import com.thingworx.metadata.collections.FieldDefinitionCollection;
import com.thingworx.types.BaseTypes;

//TODO: maybe migrate this to xml or template processing
public abstract class DataShapeInitializer {
    
    public static FieldDefinitionCollection buildConsumablesShape() {
        FieldDefinitionCollection consumablesFields = new FieldDefinitionCollection();
        consumablesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_CAPACITY_FIELD), 
                C172PFields.FUEL_TANK_0_CAPACITY_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        consumablesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_LEVEL_FIELD), 
                C172PFields.FUEL_TANK_0_LEVEL_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        consumablesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_WATER_CONTAMINATION_FIELD), 
                C172PFields.FUEL_TANK_0_WATER_CONTAMINATION_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        consumablesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_CAPACITY_FIELD), 
                C172PFields.FUEL_TANK_1_CAPACITY_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        consumablesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_LEVEL_FIELD), 
                C172PFields.FUEL_TANK_1_LEVEL_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        consumablesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_WATER_CONTAMINATION_FIELD), 
                C172PFields.FUEL_TANK_1_WATER_CONTAMINATION_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        
        return consumablesFields;
    }

    public static FieldDefinitionCollection buildControlShape() {
        FieldDefinitionCollection controlFields = new FieldDefinitionCollection();
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.BATTERY_SWITCH_FIELD), 
                C172PFields.BATTERY_SWITCH_FIELD_DESC,
                BaseTypes.INTEGER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.MIXTURE_FIELD), 
                C172PFields.MIXTURE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.THROTTLE_FIELD), 
                C172PFields.THROTTLE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.AILERON_FIELD), 
                C172PFields.AILERON_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.AILERON_TRIM_FIELD), 
                C172PFields.AILERON_TRIM_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.AUTO_COORDINATION_FIELD), 
                C172PFields.AUTO_COORDINATION_FIELD_DESC,
                BaseTypes.INTEGER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.AUTO_COORDINATION_FACTOR_FIELD),
                C172PFields.AUTO_COORDINATION_FACTOR_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ELEVATOR_FIELD), 
                C172PFields.ELEVATOR_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ELEVATOR_TRIM_FIELD), 
                C172PFields.ELEVATOR_TRIM_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FLAPS_FIELD), 
                C172PFields.FLAPS_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.RUDDER_FIELD),
                C172PFields.RUDDER_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.RUDDER_TRIM_FIELD),
                C172PFields.RUDDER_TRIM_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.SPEED_BRAKE_FIELD),
                C172PFields.SPEED_BRAKE_FIELD_DESC,
                BaseTypes.INTEGER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.PARKING_BRAKE_FIELD), 
                C172PFields.PARKING_BRAKE_FIELD_DESC,
                BaseTypes.INTEGER
        ));
        controlFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.GEAR_DOWN_FIELD), 
                C172PFields.GEAR_DOWN_FIELD_DESC,
                BaseTypes.INTEGER
        ));
        
        return controlFields;
    }

    public static FieldDefinitionCollection buildEngineShape() {
        FieldDefinitionCollection engineFields = new FieldDefinitionCollection();
        engineFields.addFieldDefinition(
        	new FieldDefinition(
        		EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_CARB_ICE), 
                C172PFields.ENGINES_CARB_ICE_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
            	EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_COMPLEX_ENGINE_PROCEDURES), 
                C172PFields.ENGINES_COMPLEX_ENGINE_PROCEDURES_DESC,
                BaseTypes.INTEGER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_COWLING_AIR_TEMPERATURE_FIELD), 
                C172PFields.ENGINES_COWLING_AIR_TEMPERATURE_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_EXHAUST_GAS_TEMPERATURE_FIELD), 
                C172PFields.ENGINES_EXHAUST_GAS_TEMPERATURE_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
                C172PFields.ENGINES_EXHAUST_GAS_TEMPERATURE_NORM_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_FUEL_FLOW_FIELD), 
                C172PFields.ENGINES_FUEL_FLOW_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_MP_OSI_FIELD), 
                C172PFields.ENGINES_MP_OSI_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_OIL_PRESSURE_FIELD), 
                C172PFields.ENGINES_OIL_PRESSURE_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_OIL_TEMPERATURE_FIELD), 
                C172PFields.ENGINES_OIL_TEMPERATURE_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_RPM_FIELD), 
                C172PFields.ENGINES_RPM_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_RUNNING_FIELD), 
                C172PFields.ENGINES_RUNNING_DESC,
                BaseTypes.INTEGER
        ));
        
        return engineFields;
    }
    
    public static FieldDefinitionCollection buildEnvironmentShape() {
        FieldDefinitionCollection environmentFields = new FieldDefinitionCollection();
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.DEWPOINT_FIELD), 
                FlightGearFields.DEWPOINT_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.EFFECTIVE_VISIBILITY_FIELD), 
                FlightGearFields.EFFECTIVE_VISIBILITY_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.PRESSURE_FIELD), 
                FlightGearFields.PRESSURE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.RELATIVE_HUMIDITY_FIELD), 
                FlightGearFields.RELATIVE_HUMIDITY_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.TEMPERATURE_FIELD), 
                FlightGearFields.TEMPERATURE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.VISIBILITY_FIELD), 
                FlightGearFields.VISIBILITY_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_DOWN_FIELD), 
                FlightGearFields.WIND_FROM_DOWN_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_EAST_FIELD), 
                FlightGearFields.WIND_FROM_EAST_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_NORTH_FIELD),
                FlightGearFields.WIND_FROM_NORTH_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.WINDSPEED_FIELD), 
                FlightGearFields.WINDSPEED_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        
        return environmentFields;
    }

    public static FieldDefinitionCollection buildFDMShape() {
        FieldDefinitionCollection fdmFields = new FieldDefinitionCollection();
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_REPAIRING_FIELD), 
                FlightGearFields.FDM_DAMAGE_REPAIRING_DESC,
                BaseTypes.INTEGER
        ));
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_AERO_FIELD), 
                FlightGearFields.FDM_FBX_AERO_DESC,
                BaseTypes.NUMBER
        ));        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_EXTERNAL_FIELD), 
                FlightGearFields.FDM_FBX_EXTERNAL_DESC,
                BaseTypes.NUMBER
        ));   
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_GEAR_FIELD), 
                FlightGearFields.FDM_FBX_GEAR_DESC,
                BaseTypes.NUMBER
        ));     
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_PROP_FIELD), 
                FlightGearFields.FDM_FBX_PROP_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_TOTAL_FIELD), 
                FlightGearFields.FDM_FBX_TOTAL_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_WEIGHT_FIELD), 
                FlightGearFields.FDM_FBX_WEIGHT_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_AERO_FIELD), 
                FlightGearFields.FDM_FBY_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_EXTERNAL_FIELD), 
                FlightGearFields.FDM_FBY_EXTERNAL_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_GEAR_FIELD), 
                FlightGearFields.FDM_FBY_GEAR_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_PROP_FIELD), 
                FlightGearFields.FDM_FBY_PROP_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_TOTAL_FIELD), 
                FlightGearFields.FDM_FBY_TOTAL_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_WEIGHT_FIELD), 
                FlightGearFields.FDM_FBY_WEIGHT_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_AERO_FIELD), 
                FlightGearFields.FDM_FBZ_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_EXTERNAL_FIELD), 
                FlightGearFields.FDM_FBZ_EXTERNAL_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_GEAR_FIELD),
                FlightGearFields.FDM_FBZ_GEAR_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_PROP_FIELD), 
                FlightGearFields.FDM_FBZ_PROP_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_TOTAL_FIELD), 
                FlightGearFields.FDM_FBZ_TOTAL_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_WEIGHT_FIELD), 
                FlightGearFields.FDM_FBZ_WEIGHT_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSX_AERO_FIELD), 
                FlightGearFields.FDM_FBZ_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSY_AERO_FIELD), 
                FlightGearFields.FDM_FSY_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSZ_AERO_FIELD), 
                FlightGearFields.FDM_FSZ_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWY_AERO_FIELD),
                FlightGearFields.FDM_FWY_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWZ_AERO_FIELD), 
                FlightGearFields.FDM_FWZ_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOAD_FACTOR_FIELD), 
                FlightGearFields.FDM_LOAD_FACTOR_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOD_NORM_FIELD), 
                FlightGearFields.FDM_LOD_NORM_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
             new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_WEIGHT), 
                FlightGearFields.FDM_WEIGHT_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_FIELD), 
                FlightGearFields.FDM_DAMAGE_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LEFT_WING_DAMAGE_FIELD), 
                FlightGearFields.FDM_LEFT_WING_DAMAGE_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_RIGHT_WING_DAMAGE_FIELD), 
                FlightGearFields.FDM_RIGHT_WING_DAMAGE_DESC,
                BaseTypes.NUMBER
        ));
        
        return fdmFields;
    }

    public static FieldDefinitionCollection buildOrientationShape() {
        FieldDefinitionCollection orientationFields = new FieldDefinitionCollection();
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALPHA_FIELD), 
                FlightGearFields.ALPHA_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.BETA_FIELD),
                FlightGearFields.BETA_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_FIELD), 
                FlightGearFields.HEADING_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_MAG_FIELD), 
                FlightGearFields.HEADING_MAG_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.PITCH_FIELD), 
                FlightGearFields.PITCH_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.ROLL_FIELD), 
                FlightGearFields.ROLL_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.TRACK_FIELD), 
                FlightGearFields.TRACK_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.TRACK_MAG_FIELD), 
                FlightGearFields.TRACK_MAG_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_FIELD), 
                FlightGearFields.YAW_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_RATE_FIELD), 
                FlightGearFields.YAW_RATE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        
        return orientationFields;
    }

    public static FieldDefinitionCollection buildPositionShape() {
        FieldDefinitionCollection positionFields = new FieldDefinitionCollection();
        positionFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALTITUDE_FIELD), 
                FlightGearFields.ALTITUDE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        positionFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUND_ELEVATION_FIELD), 
                FlightGearFields.GROUND_ELEVATION_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        positionFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.LATITUDE_FIELD), 
                FlightGearFields.LATITUDE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        positionFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.LONGITUDE_FIELD), 
                FlightGearFields.LONGITUDE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        
        return positionFields;
    } 

    public static FieldDefinitionCollection buildSimShape() {
        FieldDefinitionCollection simFields = new FieldDefinitionCollection();
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_PARKING_BRAKE_FIELD),
                C172PFields.SIM_PARKING_BRAKE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_CLOCK_FIELD), 
                FlightGearFields.SIM_FREEZE_CLOCK_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_MASTER_FIELD), 
                FlightGearFields.SIM_FREEZE_MASTER_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_SPEEDUP_FIELD), 
                FlightGearFields.SIM_SPEED_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_ELAPSED_FIELD), 
                FlightGearFields.SIM_TIME_ELAPSED_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_GMT_FIELD), 
                FlightGearFields.SIM_TIME_GMT_DESC,
                BaseTypes.STRING
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_LOCAL_DAY_SECONDS_FIELD), 
                FlightGearFields.SIM_LOCAL_DAY_SECONDS_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_MP_CLOCK_FIELD), 
                FlightGearFields.SIM_MP_CLOCK_DESC,
                BaseTypes.NUMBER
        ));
        
        return simFields;
    }
    
    public static FieldDefinitionCollection buildSimModelShape() {
        FieldDefinitionCollection simFields = new FieldDefinitionCollection();
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_PARKING_BRAKE_FIELD),
                C172PFields.SIM_PARKING_BRAKE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        
        return simFields;
    }

    public static FieldDefinitionCollection buildSystemsShape() {
        FieldDefinitionCollection systemsFields = new FieldDefinitionCollection();
        
        systemsFields.addFieldDefinition(
        	new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.BATTERY_CHARGE_FIELD), 
                C172PFields.BATTERY_CHARGE_FIELD_DESC,
                BaseTypes.NUMBER
        ));

    	return systemsFields;
    }
    
    public static FieldDefinitionCollection buildVelocitiesShape() {
        FieldDefinitionCollection velocitiesFields = new FieldDefinitionCollection();
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.AIRSPEED_FIELD), 
                FlightGearFields.AIRSPEED_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUNDSPEED_FIELD), 
                FlightGearFields.GROUNDSPEED_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.VERTICALSPEED_FIELD), 
                FlightGearFields.VERTICALSPEED_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.U_BODY_FIELD),
                FlightGearFields.U_BODY_DESC,
                BaseTypes.NUMBER
        ));
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.V_BODY_FIELD), 
                FlightGearFields.V_BODY_DESC,
                BaseTypes.NUMBER
        ));
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(FlightGearFields.W_BODY_FIELD), 
                FlightGearFields.W_BODY_DESC,
                BaseTypes.NUMBER
        ));
        
        return velocitiesFields;
    }
    
    public static FieldDefinitionCollection buildFlightPlanShape() {
    	FieldDefinitionCollection flightplanFields = new FieldDefinitionCollection();
    	
    	flightplanFields.addFieldDefinition(
    		new FieldDefinition(
    			EdgeUtilities.toThingworxPropertyName(FlightGearFields.LATITUDE_FIELD), 
                FlightGearFields.LATITUDE_FIELD_DESC,
                BaseTypes.NUMBER
    	));
    	flightplanFields.addFieldDefinition(
        	new FieldDefinition(
        		EdgeUtilities.toThingworxPropertyName(FlightGearFields.LONGITUDE_FIELD), 
                FlightGearFields.LONGITUDE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
    	flightplanFields.addFieldDefinition(
            new FieldDefinition(
            	EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALTITUDE_FIELD), 
                FlightGearFields.ALTITUDE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
    	//not a twx property but we can re-use the format utility
    	flightplanFields.addFieldDefinition(
            new FieldDefinition(
            	EdgeUtilities.toThingworxPropertyName(WaypointManager.WAYPOINT_NAME_FIELD), 
               	WaypointManager.WAYPOINT_NAME_FIELD_DESC,
                BaseTypes.STRING
        ));
    	
    	
    	return flightplanFields;
    }
}
