package org.jason.fgedge.c172p.things;

import org.jason.fgcontrol.aircraft.fields.FlightGearFields;
import org.jason.fgedge.util.EdgeUtilities;
import org.jason.fgcontrol.aircraft.c172p.C172P;
import org.jason.fgcontrol.aircraft.c172p.C172PFields;

import com.thingworx.types.primitives.IntegerPrimitive;
import com.thingworx.types.primitives.NumberPrimitive;
import com.thingworx.types.primitives.StringPrimitive;

public abstract class C172PDeviceScanner {
    public static void DeviceScanner(C172PThing c172pThing, C172P plane) throws Exception {
        //get telemetry from the plane, pour into twx types, and set defined properties
        
        //Consumables        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_CAPACITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getFuelTank0Capacity()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_LEVEL_FIELD),
            new NumberPrimitive( (Number)( plane.getFuelTank0Level()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_0_WATER_CONTAMINATION_FIELD), 
            new NumberPrimitive( (Number)( plane.getFuelTank0WaterContamination()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_CAPACITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getFuelTank1Capacity()) 
        ));
        c172pThing.setProperty(
        	EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_LEVEL_FIELD),
            new NumberPrimitive( (Number)( plane.getFuelTank1Level()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.FUEL_TANK_1_WATER_CONTAMINATION_FIELD), 
            new NumberPrimitive( (Number)( plane.getFuelTank1WaterContamination()) 
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
            EdgeUtilities.toThingworxPropertyName(C172PFields.AILERON_TRIM_FIELD), 
            new NumberPrimitive( (Number)( plane.getAileronTrim()) 
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
            EdgeUtilities.toThingworxPropertyName(C172PFields.ELEVATOR_TRIM_FIELD), 
            new NumberPrimitive( (Number)( plane.getElevatorTrim()) 
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
            EdgeUtilities.toThingworxPropertyName(C172PFields.RUDDER_TRIM_FIELD), 
            new NumberPrimitive( (Number)( plane.getRudderTrim()) 
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
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_COWLING_AIR_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getCowlingAirTemperature()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_EXHAUST_GAS_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getExhaustGasTemperature()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
            new NumberPrimitive( (Number)( plane.getExhaustGasTemperatureNormalization()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_FUEL_FLOW_FIELD),
            new NumberPrimitive( (Number)( plane.getFuelFlow()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_MP_OSI_FIELD), 
            new NumberPrimitive( (Number)( plane.getMpOsi()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_OIL_PRESSURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getOilPressure()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_OIL_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getOilTemperature()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_RPM_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngineRpms()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(C172PFields.ENGINES_RUNNING_FIELD), 
            new IntegerPrimitive( (Number)( plane.getEngineRunning()) 
        ));
        
        //Environment
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.DEWPOINT_FIELD),
            new NumberPrimitive( (Number)( plane.getDewpoint()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.EFFECTIVE_VISIBILITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getEffectiveVisibility()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.PRESSURE_FIELD),
            new NumberPrimitive( (Number)( plane.getPressure()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.RELATIVE_HUMIDITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getRelativeHumidity()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getTemperature()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.VISIBILITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getVisibility()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_DOWN_FIELD), 
            new NumberPrimitive( (Number)( plane.getWindFromDown()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_EAST_FIELD), 
            new NumberPrimitive( (Number)( plane.getWindFromEast()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_NORTH_FIELD), 
            new NumberPrimitive( (Number)( plane.getWindFromNorth()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WINDSPEED_FIELD),
            new NumberPrimitive( (Number)( plane.getWindspeed()) 
        ));

        //FDM
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_REPAIRING_FIELD), 
            new IntegerPrimitive( (Number)( plane.getDamageRepairing()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxExternalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_GEAR_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxGearForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_PROP_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxPropForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_TOTAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxTotalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_WEIGHT_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxWeightForce()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyExternalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_GEAR_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyGearForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_PROP_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyPropForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_TOTAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyTotalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_WEIGHT_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyWeightForce()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzExternalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_GEAR_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzGearForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_PROP_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzPropForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_TOTAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzTotalForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_WEIGHT_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzWeightForce()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSX_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFsxAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSY_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFsyAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSZ_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFszAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWY_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFwyAeroForce()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWZ_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFwzAeroForce()) 
        ));
        
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOAD_FACTOR_FIELD), 
            new NumberPrimitive( (Number)( plane.getLoadFactor()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOD_NORM_FIELD), 
            new NumberPrimitive( (Number)( plane.getLodNorm()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_WEIGHT), 
            new NumberPrimitive( (Number)( plane.getWeight()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_FIELD), 
            new IntegerPrimitive( (Number)( plane.getDamage()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LEFT_WING_DAMAGE_FIELD), 
            new NumberPrimitive( (Number)( plane.getLeftWingDamage()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_RIGHT_WING_DAMAGE_FIELD), 
            new NumberPrimitive( (Number)( plane.getRightWingDamage()) 
        ));

        //Orientation
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALPHA_FIELD), 
            new NumberPrimitive( (Number)( plane.getAlpha()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.BETA_FIELD), 
            new NumberPrimitive( (Number)( plane.getBeta()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_FIELD), 
            new NumberPrimitive( (Number)( plane.getHeading()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_MAG_FIELD),
            new NumberPrimitive( (Number)( plane.getHeadingMag()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.PITCH_FIELD), 
            new NumberPrimitive( (Number)( plane.getPitch()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ROLL_FIELD), 
            new NumberPrimitive( (Number)( plane.getRoll()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.TRACK_MAG_FIELD), 
            new NumberPrimitive( (Number)( plane.getTrack()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_FIELD),
            new NumberPrimitive( (Number)( plane.getYaw()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_RATE_FIELD), 
            new NumberPrimitive( (Number)( plane.getYawRate()) 
        ));

        //Position
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALTITUDE_FIELD), 
            new NumberPrimitive( (Number)( plane.getAltitude()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUND_ELEVATION_FIELD), 
            new NumberPrimitive( (Number)( plane.getGroundElevation()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.LATITUDE_FIELD), 
            new NumberPrimitive( (Number)( plane.getLatitude()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.LONGITUDE_FIELD), 
            new NumberPrimitive( (Number)( plane.getLongitude()) 
        ));
        
        //Sim
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_CLOCK_FIELD), 
            new IntegerPrimitive( (Number)( plane.getSimFreezeClock()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_MASTER_FIELD), 
            new IntegerPrimitive( (Number)( plane.getSimFreezeMaster()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_SPEEDUP_FIELD), 
            new IntegerPrimitive( (Number)( plane.getSimSpeedUp()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_ELAPSED_FIELD), 
            new NumberPrimitive( (Number)( plane.getTimeElapsed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_GMT_FIELD), 
            new StringPrimitive(  plane.getGMT() ) 
        );
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_LOCAL_DAY_SECONDS_FIELD), 
            new NumberPrimitive( (Number)( plane.getLocalDaySeconds()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_MP_CLOCK_FIELD),
            new NumberPrimitive( (Number)( plane.getMpClock()) 
        ));
        
        //Velocities
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.AIRSPEED_FIELD), 
            new NumberPrimitive( (Number)( plane.getAirSpeed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUNDSPEED_FIELD), 
            new NumberPrimitive( (Number)( plane.getGroundSpeed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.VERTICALSPEED_FIELD), 
            new NumberPrimitive( (Number)( plane.getVerticalSpeed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.U_BODY_FIELD), 
            new NumberPrimitive( (Number)( plane.getUBodySpeed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.V_BODY_FIELD), 
            new NumberPrimitive( (Number)( plane.getVBodySpeed()) 
        ));
        c172pThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.W_BODY_FIELD), 
            new NumberPrimitive( (Number)( plane.getWBodySpeed())
        ));
    }
}
