package org.jason.flightgear.edge.c172p.things;

import org.jason.flightgear.planes.c172p.C172P;
import org.jason.flightgear.planes.c172p.C172PFields;
import org.jason.util.EdgeUtilities;

import com.thingworx.types.primitives.IntegerPrimitive;
import com.thingworx.types.primitives.NumberPrimitive;

public abstract class C172PDeviceScanner {
    public static void DeviceScanner(C172PThing c172pThing, C172P plane) throws Exception {
        //get telemetry from the plane, pour into twx types, and set defined properties
        
        //Consumables        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_CAPACITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getCapacity_gal_us()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_LEVEL_FIELD),
            new NumberPrimitive( (Number)( plane.getLevel_gal_us()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.WATER_CONTAMINATION_FIELD), 
            new NumberPrimitive( (Number)( plane.getWaterContamination()) 
        ));

        //Control
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.BATTERY_SWITCH_FIELD),
            new IntegerPrimitive( (Number)( plane.getBatterySwitch()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.MIXTURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getMixture()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.THROTTLE_FIELD), 
            new NumberPrimitive( (Number)( plane.getThrottle()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AILERON_FIELD), 
            new NumberPrimitive( (Number)( plane.getAileron()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AUTO_COORDINATION_FIELD), 
            new IntegerPrimitive( (Number)( plane.getAutoCoordination()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AUTO_COORDINATION_FACTOR_FIELD), 
            new NumberPrimitive( (Number)( plane.getAutoCoordinationFactor()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ELEVATOR_FIELD), 
            new NumberPrimitive( (Number)( plane.getElevator()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FLAPS_FIELD), 
            new NumberPrimitive( (Number)( plane.getFlaps()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.RUDDER_FIELD), 
            new NumberPrimitive( (Number)( plane.getRudder()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.SPEED_BRAKE_FIELD), 
            new NumberPrimitive( (Number)( plane.getSpeedbrake()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.PARKING_BRAKE_FIELD), 
            new IntegerPrimitive( (Number)( plane.getParkingBrakeEnabled()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.GEAR_DOWN_FIELD),
            new IntegerPrimitive( (Number)( plane.getGearDown()) 
        ));
        
        //Engine
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_COWLING_AIR_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getCowlingAirTemperature()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_EXHAUST_GAS_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getExhaustGasTemperature()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
            new NumberPrimitive( (Number)( plane.getExhaustGasTemperatureNormalization()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_FUEL_FLOW_FIELD),
            new NumberPrimitive( (Number)( plane.getFuelFlow()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_MP_OSI_FIELD), 
            new NumberPrimitive( (Number)( plane.getMpOsi()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_OIL_PRESSURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getOilPressure()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_OIL_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getOilTemperature()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_RPM_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngineRpms()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINE_RUNNING_FIELD), 
            new IntegerPrimitive( (Number)( plane.getEngineRunning()) 
        ));
        
        //Environment
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.DEWPOINT_FIELD),
            new NumberPrimitive( (Number)( plane.getDewpoint()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.EFFECTIVE_VISIBILITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getEffectiveVisibility()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.PRESSURE_FIELD),
            new NumberPrimitive( (Number)( plane.getPressure()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.RELATIVE_HUMIDITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getRelativeHumidity()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getTemperature()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.VISIBILITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getVisibility()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.WIND_FROM_DOWN_FIELD), 
            new NumberPrimitive( (Number)( plane.getWindFromDown()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.WIND_FROM_EAST_FIELD), 
            new NumberPrimitive( (Number)( plane.getWindFromEast()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.WIND_FROM_NORTH_FIELD), 
            new NumberPrimitive( (Number)( plane.getWindFromNorth()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.WINDSPEED_FIELD),
            new NumberPrimitive( (Number)( plane.getWindspeed()) 
        ));

        //FDM
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_DAMAGE_REPAIRING_FIELD), 
            new IntegerPrimitive( (Number)( plane.getDamageRepairing()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxExternalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_GEAR_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxGearForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_PROP_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxPropForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_TOTAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxTotalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBX_WEIGHT_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxWeightForce()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyExternalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_GEAR_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyGearForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_PROP_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyPropForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_TOTAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyTotalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBY_WEIGHT_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyWeightForce()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzExternalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_GEAR_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzGearForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_PROP_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzPropForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_TOTAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzTotalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FBZ_WEIGHT_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzWeightForce()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FSX_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFsxAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FSY_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFsyAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FSZ_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFszAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FWY_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFwyAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_FWZ_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFwzAeroForce()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_LOAD_FACTOR_FIELD), 
            new NumberPrimitive( (Number)( plane.getLoadFactor()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_LOD_NORM_FIELD), 
            new NumberPrimitive( (Number)( plane.getLodNorm()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_DAMAGE_FIELD), 
            new IntegerPrimitive( (Number)( plane.getDamage()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_LEFT_WING_DAMAGE_FIELD), 
            new NumberPrimitive( (Number)( plane.getLeftWingDamage()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FDM_RIGHT_WING_DAMAGE_FIELD), 
            new NumberPrimitive( (Number)( plane.getRightWingDamage()) 
        ));

        //Orientation
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ALPHA_FIELD), 
            new NumberPrimitive( (Number)( plane.getAlpha()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.BETA_FIELD), 
            new NumberPrimitive( (Number)( plane.getBeta()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.HEADING_FIELD), 
            new NumberPrimitive( (Number)( plane.getHeading()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.HEADING_MAG_FIELD),
            new NumberPrimitive( (Number)( plane.getHeadingMag()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.PITCH_FIELD), 
            new NumberPrimitive( (Number)( plane.getPitch()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ROLL_FIELD), 
            new NumberPrimitive( (Number)( plane.getRoll()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.TRACK_MAG_FIELD), 
            new NumberPrimitive( (Number)( plane.getTrack()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.YAW_FIELD),
            new NumberPrimitive( (Number)( plane.getYaw()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.YAW_RATE_FIELD), 
            new NumberPrimitive( (Number)( plane.getYawRate()) 
        ));

        //Position
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ALTITUDE_FIELD), 
            new NumberPrimitive( (Number)( plane.getAltitude()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.GROUND_ELEVATION_FIELD), 
            new NumberPrimitive( (Number)( plane.getGroundElevation()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.LATITUDE_FIELD), 
            new NumberPrimitive( (Number)( plane.getLatitude()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.LONGITUDE_FIELD), 
            new NumberPrimitive( (Number)( plane.getLongitude()) 
        ));
        
        //Sim
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_FREEZE_CLOCK_FIELD), 
            new IntegerPrimitive( (Number)( plane.getSimFreezeClock()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_FREEZE_MASTER_FIELD), 
            new IntegerPrimitive( (Number)( plane.getSimFreezeMaster()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_SPEEDUP_FIELD), 
            new IntegerPrimitive( (Number)( plane.getSimSpeedUp()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_TIME_ELAPSED_FIELD), 
            new NumberPrimitive( (Number)( plane.getTimeElapsed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_LOCAL_DAY_SECONDS_FIELD), 
            new NumberPrimitive( (Number)( plane.getLocalDaySeconds()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.SIM_MP_CLOCK_FIELD),
            new NumberPrimitive( (Number)( plane.getMpClock()) 
        ));
        
        //Velocities
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.AIRSPEED_FIELD), 
            new NumberPrimitive( (Number)( plane.getAirSpeed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.GROUNDSPEED_FIELD), 
            new NumberPrimitive( (Number)( plane.getGroundSpeed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.VERTICALSPEED_FIELD), 
            new NumberPrimitive( (Number)( plane.getVerticalSpeed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.U_BODY_FIELD), 
            new NumberPrimitive( (Number)( plane.getUBodySpeed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.V_BODY_FIELD), 
            new NumberPrimitive( (Number)( plane.getVBodySpeed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.W_BODY_FIELD), 
            new NumberPrimitive( (Number)( plane.getWBodySpeed())
        ));
    }
}
