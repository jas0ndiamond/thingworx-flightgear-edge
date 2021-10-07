package org.jason.flightgear.edge.c172p.things;

import org.jason.flightgear.planes.c172p.C172PFields;
import org.jason.util.EdgeUtilities;

import com.thingworx.metadata.FieldDefinition;
import com.thingworx.metadata.collections.FieldDefinitionCollection;
import com.thingworx.types.BaseTypes;

//TODO: maybe migrate this to xml or template processing
public abstract class DataShapeInitializer {
    
    public static FieldDefinitionCollection buildConsumablesShape() {
        FieldDefinitionCollection consumablesFields = new FieldDefinitionCollection();
        consumablesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_CAPACITY_FIELD), 
                C172PFields.FUEL_TANK_CAPACITY_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        consumablesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_LEVEL_FIELD), 
                C172PFields.FUEL_TANK_LEVEL_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        consumablesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.WATER_CONTAMINATION_FIELD), 
                C172PFields.WATER_CONTAMINATION_FIELD_DESC,
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
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_COWLING_AIR_TEMPERATURE_FIELD), 
                C172PFields.ENGINE_COWLING_AIR_TEMPERATURE_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_EXHAUST_GAS_TEMPERATURE_FIELD), 
                C172PFields.ENGINE_EXHAUST_GAS_TEMPERATURE_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
                C172PFields.ENGINE_EXHAUST_GAS_TEMPERATURE_NORM_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_FUEL_FLOW_FIELD), 
                C172PFields.ENGINE_FUEL_FLOW_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_MP_OSI_FIELD), 
                C172PFields.ENGINE_MP_OSI_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_OIL_PRESSURE_FIELD), 
                C172PFields.ENGINE_OIL_PRESSURE_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_OIL_TEMPERATURE_FIELD), 
                C172PFields.ENGINE_OIL_TEMPERATURE_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_RPM_FIELD), 
                C172PFields.ENGINE_RPM_DESC,
                BaseTypes.NUMBER
        ));
        engineFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_RUNNING_FIELD), 
                C172PFields.ENGINE_RUNNING_DESC,
                BaseTypes.INTEGER
        ));
        
        return engineFields;
    }
    
    public static FieldDefinitionCollection buildEnvironmentShape() {
        FieldDefinitionCollection environmentFields = new FieldDefinitionCollection();
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.DEWPOINT_FIELD), 
                C172PFields.DEWPOINT_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.EFFECTIVE_VISIBILITY_FIELD), 
                C172PFields.EFFECTIVE_VISIBILITY_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.PRESSURE_FIELD), 
                C172PFields.PRESSURE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.RELATIVE_HUMIDITY_FIELD), 
                C172PFields.RELATIVE_HUMIDITY_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.TEMPERATURE_FIELD), 
                C172PFields.TEMPERATURE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.VISIBILITY_FIELD), 
                C172PFields.VISIBILITY_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.WIND_FROM_DOWN_FIELD), 
                C172PFields.WIND_FROM_DOWN_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.WIND_FROM_EAST_FIELD), 
                C172PFields.WIND_FROM_EAST_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.WIND_FROM_NORTH_FIELD),
                C172PFields.WIND_FROM_NORTH_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        environmentFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.WINDSPEED_FIELD), 
                C172PFields.WINDSPEED_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        
        return environmentFields;
    }

    public static FieldDefinitionCollection buildFDMShape() {
        FieldDefinitionCollection fdmFields = new FieldDefinitionCollection();
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_DAMAGE_REPAIRING_FIELD), 
                C172PFields.FDM_DAMAGE_REPAIRING_DESC,
                BaseTypes.INTEGER
        ));
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_AERO_FIELD), 
                C172PFields.FDM_FBX_AERO_DESC,
                BaseTypes.NUMBER
        ));        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_EXTERNAL_FIELD), 
                   C172PFields.FDM_FBX_EXTERNAL_DESC,
                BaseTypes.NUMBER
        ));   
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                  EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_GEAR_FIELD), 
                  C172PFields.FDM_FBX_GEAR_DESC,
                BaseTypes.NUMBER
        ));     
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                  EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_PROP_FIELD), 
                  C172PFields.FDM_FBX_PROP_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_TOTAL_FIELD), 
                   C172PFields.FDM_FBX_TOTAL_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_WEIGHT_FIELD), 
                   C172PFields.FDM_FBX_WEIGHT_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_AERO_FIELD), 
                   C172PFields.FDM_FBY_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_EXTERNAL_FIELD), 
                   C172PFields.FDM_FBY_EXTERNAL_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_GEAR_FIELD), 
                   C172PFields.FDM_FBY_GEAR_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_PROP_FIELD), 
                   C172PFields.FDM_FBY_PROP_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                  EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_TOTAL_FIELD), 
                  C172PFields.FDM_FBY_TOTAL_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_WEIGHT_FIELD), 
                   C172PFields.FDM_FBY_WEIGHT_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_AERO_FIELD), 
                   C172PFields.FDM_FBZ_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_EXTERNAL_FIELD), 
                   C172PFields.FDM_FBZ_EXTERNAL_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_GEAR_FIELD),
                   C172PFields.FDM_FBZ_GEAR_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_PROP_FIELD), 
                   C172PFields.FDM_FBZ_PROP_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_TOTAL_FIELD), 
                   C172PFields.FDM_FBZ_TOTAL_DESC,
                BaseTypes.NUMBER
        ));  
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_WEIGHT_FIELD), 
                   C172PFields.FDM_FBZ_WEIGHT_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FSX_AERO_FIELD), 
                   C172PFields.FDM_FBZ_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FSY_AERO_FIELD), 
                   C172PFields.FDM_FSY_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FSZ_AERO_FIELD), 
                   C172PFields.FDM_FSZ_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FWY_AERO_FIELD),
                   C172PFields.FDM_FWY_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                  EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FWZ_AERO_FIELD), 
                  C172PFields.FDM_FWZ_AERO_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_LOAD_FACTOR_FIELD), 
                   C172PFields.FDM_LOAD_FACTOR_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_LOD_NORM_FIELD), 
                   C172PFields.FDM_LOD_NORM_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                  EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_DAMAGE_FIELD), 
                  C172PFields.FDM_DAMAGE_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_LEFT_WING_DAMAGE_FIELD), 
                   C172PFields.FDM_LEFT_WING_DAMAGE_DESC,
                BaseTypes.NUMBER
        ));  
        
        fdmFields.addFieldDefinition(
            new FieldDefinition(
                   EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_RIGHT_WING_DAMAGE_FIELD), 
                   C172PFields.FDM_RIGHT_WING_DAMAGE_DESC,
                BaseTypes.NUMBER
        ));
        
        return fdmFields;
    }

    public static FieldDefinitionCollection buildOrientationShape() {
        FieldDefinitionCollection orientationFields = new FieldDefinitionCollection();
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ALPHA_FIELD), 
                C172PFields.ALPHA_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.BETA_FIELD),
                C172PFields.BETA_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.HEADING_FIELD), 
                C172PFields.HEADING_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.HEADING_MAG_FIELD), 
                C172PFields.HEADING_MAG_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.PITCH_FIELD), 
                C172PFields.PITCH_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ROLL_FIELD), 
                C172PFields.ROLL_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.TRACK_MAG_FIELD), 
                C172PFields.TRACK_MAG_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.YAW_FIELD), 
                C172PFields.YAW_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        orientationFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.YAW_RATE_FIELD), 
                C172PFields.YAW_RATE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        
        return orientationFields;
    }

    public static FieldDefinitionCollection buildPositionShape() {
        FieldDefinitionCollection positionFields = new FieldDefinitionCollection();
        positionFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.ALTITUDE_FIELD), 
                C172PFields.ALTITUDE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        positionFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.GROUND_ELEVATION_FIELD), 
                C172PFields.GROUND_ELEVATION_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        positionFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.LATITUDE_FIELD), 
                C172PFields.LATITUDE_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        positionFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.LONGITUDE_FIELD), 
                C172PFields.LONGITUDE_FIELD_DESC,
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
                EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_FREEZE_CLOCK_FIELD), 
                C172PFields.SIM_FREEZE_CLOCK_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_FREEZE_MASTER_FIELD), 
                C172PFields.SIM_FREEZE_MASTER_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_SPEEDUP_FIELD), 
                C172PFields.SIM_SPEED_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_TIME_ELAPSED_FIELD), 
                C172PFields.SIM_TIME_ELAPSED_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_LOCAL_DAY_SECONDS_FIELD), 
                C172PFields.SIM_LOCAL_DAY_SECONDS_DESC,
                BaseTypes.NUMBER
        ));
        simFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_MP_CLOCK_FIELD), 
                C172PFields.SIM_MP_CLOCK_DESC,
                BaseTypes.NUMBER
        ));
        
        return simFields;
    }

    public static FieldDefinitionCollection buildVelocitiesShape() {
        FieldDefinitionCollection velocitiesFields = new FieldDefinitionCollection();
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.AIRSPEED_FIELD), 
                C172PFields.AIRSPEED_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.GROUNDSPEED_FIELD), 
                C172PFields.GROUNDSPEED_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.VERTICALSPEED_FIELD), 
                C172PFields.VERTICALSPEED_FIELD_DESC,
                BaseTypes.NUMBER
        ));
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.U_BODY_FIELD),
                C172PFields.U_BODY_DESC,
                BaseTypes.NUMBER
        ));
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.V_BODY_FIELD), 
                C172PFields.V_BODY_DESC,
                BaseTypes.NUMBER
        ));
        velocitiesFields.addFieldDefinition(
            new FieldDefinition(
                EdgeUtilities.toThingworxPropertyName(C172PFields.W_BODY_FIELD), 
                C172PFields.W_BODY_DESC,
                BaseTypes.NUMBER
        ));
        
        return velocitiesFields;
    }
}
