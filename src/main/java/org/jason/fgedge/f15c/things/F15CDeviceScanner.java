package org.jason.fgedge.f15c.things;

import org.jason.fgcontrol.aircraft.fields.FlightGearFields;
import org.jason.fgedge.util.EdgeUtilities;
import org.jason.fgcontrol.aircraft.f15c.F15C;
import org.jason.fgcontrol.aircraft.f15c.F15CFields;

import com.thingworx.types.primitives.IntegerPrimitive;
import com.thingworx.types.primitives.NumberPrimitive;
import com.thingworx.types.primitives.StringPrimitive;

public abstract class F15CDeviceScanner {
    public static void DeviceScanner(F15CThing f15cThing, F15C plane) throws Exception {
        //get telemetry from the plane, pour into twx types, and set defined properties
        
        //Consumables        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_0_CAPACITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getFuelTank0Capacity()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_0_LEVEL_FIELD),
            new NumberPrimitive( (Number)( plane.getFuelTank0Level()) 
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_1_CAPACITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getFuelTank1Capacity()) 
        ));
        f15cThing.setProperty(
        	EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_1_LEVEL_FIELD),
            new NumberPrimitive( (Number)( plane.getFuelTank1Level()) 
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_2_CAPACITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getFuelTank2Capacity()) 
        ));
        f15cThing.setProperty(
        	EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_2_LEVEL_FIELD),
            new NumberPrimitive( (Number)( plane.getFuelTank2Level()) 
        ));
            
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_3_CAPACITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getFuelTank3Capacity()) 
        ));
        f15cThing.setProperty(
           	EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_3_LEVEL_FIELD),
            new NumberPrimitive( (Number)( plane.getFuelTank3Level()) 
        ));
                
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_4_CAPACITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getFuelTank4Capacity()) 
        ));
        f15cThing.setProperty(
           	EdgeUtilities.toThingworxPropertyName(F15CFields.FUEL_TANK_4_LEVEL_FIELD),
            new NumberPrimitive( (Number)( plane.getFuelTank4Level()) 
        ));                         

        //Control
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.BATTERY_SWITCH_FIELD),
            new IntegerPrimitive( (Number)( plane.getBatterySwitch()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_CUTOFF_FIELD),
            new IntegerPrimitive( (Number)( plane.getEngine0Cutoff()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_CUTOFF_FIELD),
            new IntegerPrimitive( (Number)( plane.getEngine1Cutoff()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_MIXTURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine0Mixture()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_MIXTURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine1Mixture()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_THROTTLE_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine0Throttle()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_THROTTLE_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine0Throttle()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AILERON_FIELD), 
            new NumberPrimitive( (Number)( plane.getAileron()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AILERON_TRIM_FIELD), 
            new NumberPrimitive( (Number)( plane.getAileronTrim()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AUTO_COORDINATION_FIELD), 
            new IntegerPrimitive( (Number)( plane.getAutoCoordination()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.AUTO_COORDINATION_FACTOR_FIELD), 
            new NumberPrimitive( (Number)( plane.getAutoCoordinationFactor()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ELEVATOR_FIELD), 
            new NumberPrimitive( (Number)( plane.getElevator()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ELEVATOR_TRIM_FIELD), 
            new NumberPrimitive( (Number)( plane.getElevatorTrim()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.FLAPS_FIELD), 
            new NumberPrimitive( (Number)( plane.getFlaps()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.RUDDER_FIELD), 
            new NumberPrimitive( (Number)( plane.getRudder()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.RUDDER_TRIM_FIELD), 
            new NumberPrimitive( (Number)( plane.getRudderTrim()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.SPEED_BRAKE_FIELD), 
            new NumberPrimitive( (Number)( plane.getSpeedbrake()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.PARKING_BRAKE_FIELD), 
            new IntegerPrimitive( (Number)( plane.getParkingBrakeEnabled()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.GEAR_DOWN_FIELD),
            new IntegerPrimitive( (Number)( plane.getGearDown()) 
        ));
        
        //Engine
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_EXHAUST_GAS_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine0ExhaustGasTemperature()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_EXHAUST_GAS_TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine1ExhaustGasTemperature()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine0ExhaustGasTemperatureNormalization()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_EXHAUST_GAS_TEMPERATURE_NORM_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine1ExhaustGasTemperatureNormalization()) 
        ));      
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_FUEL_FLOW_FIELD),
            new NumberPrimitive( (Number)( plane.getEngine0FuelFlow()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_FUEL_FLOW_FIELD),
            new NumberPrimitive( (Number)( plane.getEngine1FuelFlow()) 
        ));    
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_OIL_PRESSURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine0OilPressure()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_OIL_PRESSURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine1OilPressure()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_RUNNING_FIELD), 
            new IntegerPrimitive( (Number)( plane.getEngine0Running()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_RUNNING_FIELD), 
            new IntegerPrimitive( (Number)( plane.getEngine1Running()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_0_THRUST_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine0Thrust()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ENGINE_1_THRUST_FIELD), 
            new NumberPrimitive( (Number)( plane.getEngine1Thrust()) 
        ));
        
        //Environment
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.DEWPOINT_FIELD),
            new NumberPrimitive( (Number)( plane.getDewpoint()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.EFFECTIVE_VISIBILITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getEffectiveVisibility()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.PRESSURE_FIELD),
            new NumberPrimitive( (Number)( plane.getPressure()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.RELATIVE_HUMIDITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getRelativeHumidity()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.TEMPERATURE_FIELD), 
            new NumberPrimitive( (Number)( plane.getTemperature()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.VISIBILITY_FIELD), 
            new NumberPrimitive( (Number)( plane.getVisibility()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_DOWN_FIELD), 
            new NumberPrimitive( (Number)( plane.getWindFromDown()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_EAST_FIELD), 
            new NumberPrimitive( (Number)( plane.getWindFromEast()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WIND_FROM_NORTH_FIELD), 
            new NumberPrimitive( (Number)( plane.getWindFromNorth()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.WINDSPEED_FIELD),
            new NumberPrimitive( (Number)( plane.getWindspeed()) 
        ));

        //FDM
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_REPAIRING_FIELD), 
            new IntegerPrimitive( (Number)( plane.getDamageRepairing()) 
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxAeroForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxExternalForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_GEAR_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxGearForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_PROP_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxPropForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_TOTAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxTotalForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBX_WEIGHT_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbxWeightForce()) 
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyAeroForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyExternalForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_GEAR_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyGearForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_PROP_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyPropForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_TOTAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyTotalForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBY_WEIGHT_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbyWeightForce()) 
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzAeroForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_EXTERNAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzExternalForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_GEAR_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzGearForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_PROP_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzPropForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_TOTAL_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzTotalForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FBZ_WEIGHT_FIELD), 
            new NumberPrimitive( (Number)( plane.getFbzWeightForce()) 
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSX_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFsxAeroForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSY_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFsyAeroForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FSZ_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFszAeroForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWY_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFwyAeroForce()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_FWZ_AERO_FIELD), 
            new NumberPrimitive( (Number)( plane.getFwzAeroForce()) 
        ));
        
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOAD_FACTOR_FIELD), 
            new NumberPrimitive( (Number)( plane.getLoadFactor()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LOD_NORM_FIELD), 
            new NumberPrimitive( (Number)( plane.getLodNorm()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_WEIGHT), 
            new NumberPrimitive( (Number)( plane.getWeight()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_DAMAGE_FIELD), 
            new IntegerPrimitive( (Number)( plane.getDamage()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_LEFT_WING_DAMAGE_FIELD), 
            new NumberPrimitive( (Number)( plane.getLeftWingDamage()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.FDM_RIGHT_WING_DAMAGE_FIELD), 
            new NumberPrimitive( (Number)( plane.getRightWingDamage()) 
        ));

        //Orientation
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALPHA_FIELD), 
            new NumberPrimitive( (Number)( plane.getAlpha()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.BETA_FIELD), 
            new NumberPrimitive( (Number)( plane.getBeta()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_FIELD), 
            new NumberPrimitive( (Number)( plane.getHeading()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.HEADING_MAG_FIELD),
            new NumberPrimitive( (Number)( plane.getHeadingMag()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.PITCH_FIELD), 
            new NumberPrimitive( (Number)( plane.getPitch()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ROLL_FIELD), 
            new NumberPrimitive( (Number)( plane.getRoll()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.TRACK_MAG_FIELD), 
            new NumberPrimitive( (Number)( plane.getTrack()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_FIELD),
            new NumberPrimitive( (Number)( plane.getYaw()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.YAW_RATE_FIELD), 
            new NumberPrimitive( (Number)( plane.getYawRate()) 
        ));

        //Position
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.ALTITUDE_FIELD), 
            new NumberPrimitive( (Number)( plane.getAltitude()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUND_ELEVATION_FIELD), 
            new NumberPrimitive( (Number)( plane.getGroundElevation()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.LATITUDE_FIELD), 
            new NumberPrimitive( (Number)( plane.getLatitude()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.LONGITUDE_FIELD), 
            new NumberPrimitive( (Number)( plane.getLongitude()) 
        ));
        
        //Sim
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_CLOCK_FIELD), 
            new IntegerPrimitive( (Number)( plane.getSimFreezeClock()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_FREEZE_MASTER_FIELD), 
            new IntegerPrimitive( (Number)( plane.getSimFreezeMaster()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_SPEEDUP_FIELD), 
            new IntegerPrimitive( (Number)( plane.getSimSpeedUp()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_ELAPSED_FIELD), 
            new NumberPrimitive( (Number)( plane.getTimeElapsed()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_TIME_GMT_FIELD), 
            new StringPrimitive(  plane.getGMT() ) 
        );
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_LOCAL_DAY_SECONDS_FIELD), 
            new NumberPrimitive( (Number)( plane.getLocalDaySeconds()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.SIM_MP_CLOCK_FIELD),
            new NumberPrimitive( (Number)( plane.getMpClock()) 
        ));
        
        //sim model
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ARMAMENT_AGM_COUNT), 
            new IntegerPrimitive( (Number)( plane.getArmamentAGMCount()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(F15CFields.ARMAMENT_SYSTEM_RUNNING), 
            new IntegerPrimitive( (Number)( plane.getArmamentSystemRunning()) 
        ));
        
        //Velocities
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.AIRSPEED_FIELD), 
            new NumberPrimitive( (Number)( plane.getAirSpeed()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.GROUNDSPEED_FIELD), 
            new NumberPrimitive( (Number)( plane.getGroundSpeed()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.VERTICALSPEED_FIELD), 
            new NumberPrimitive( (Number)( plane.getVerticalSpeed()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.U_BODY_FIELD), 
            new NumberPrimitive( (Number)( plane.getUBodySpeed()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.V_BODY_FIELD), 
            new NumberPrimitive( (Number)( plane.getVBodySpeed()) 
        ));
        f15cThing.setProperty(
            EdgeUtilities.toThingworxPropertyName(FlightGearFields.W_BODY_FIELD), 
            new NumberPrimitive( (Number)( plane.getWBodySpeed())
        ));
    }
}
