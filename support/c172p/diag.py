#!/usr/bin/python3

import json
import subprocess
import re
import sys

def main(args):

    host = "127.0.0.1"
    port = 5000
    buffSizeMax = 5000  #actually around 4774 for c172p
    
    # read from the telemetry stream
    syscall = ("/usr/bin/socat -t 0 - udp-listen:%d,readbytes=%d" % (port, buffSizeMax)).split()
    
    # returns output as byte string
    syscall_output = subprocess.check_output(syscall)
    
    #print( "%s" % syscall_output.decode("utf-8"))
    
    if ( syscall_output is not None ):
        #substring from beginning to first instance of a newline not preceded by a comma
    
        parsed_output = ""
        
        for line in syscall_output.decode("utf-8").split("\n"):
            if(line.endswith(",") is False):
                #last line in stream (probably)
                
                #print( "last line: %s" % line)
                
                parsed_output += line + "\n"
                break
            else:
                parsed_output += line + "\n"
    
        parsed_output_json = "{%s}" % parsed_output
        
        #print ("%s" % parsed_output_json)
    else:
        print("Error reading telemetry stream")
        exit(1)
    
    # the telemetry stream size is variable due to floating precision on fields
    # trim the stream output since we'll catch more than one update
    
    telem = json.loads(parsed_output_json)
    
    if (telem is None):
        print ("Error parsing telemetry data")
        exit(1)

    # just print out the telemetry read
    if(len(args) == 2 and args[1] == "-r"):
        
        print( "%s" % json.dumps(telem, indent=2, sort_keys=True) )
    
        exit(1)

    ################
    # test values
    
    #telem["/consumables/fuel/tank/water-contamination"] = 0.75
    #telem["/fdm/jsbsim/wing-damage/left-wing"] = 0.5
    #telem["/fdm/jsbsim/wing-damage/right-wing"] = 0.5
    
    ################
        
    ################
    # read from telemetry stream and generate actionable report
    
    actions = []
    report = []
    
    #####
    # check fuel levels > 0
    
    fuel_tank_0_level = float(telem["/consumables/fuel/tank/level-gal_us"])
    
    if(fuel_tank_0_level <= 0.0):
        report.append("Fuel Tank 0 Level:\t\t\tFAIL")
        actions.append("Refill fuel tank 0")
    else:
        report.append("Fuel Tank 0 Level:\t\t\tOK") 
    
    fuel_tank_1_level = float(telem["/consumables/fuel/tank[1]/level-gal_us"])
    
    if(fuel_tank_1_level <= 0.0):
        report.append("Fuel Tank 1 Level:\t\t\tFAIL")
        actions.append("Refill fuel tank 1")
    else:
        report.append("Fuel Tank 1 Level:\t\t\tOK") 
    
    #####
    # check water contamination = 0
    
    fuel_tank_0_water_contam = float(telem["/consumables/fuel/tank/water-contamination"])
    
    if(fuel_tank_0_water_contam > 0.0):
        report.append("Fuel Tank 0 Water contamination:\tFAIL")
        actions.append("Flush fuel tank 0")
    else:
        report.append("Fuel Tank 0 Water contamination:\tOK") 
    
    fuel_tank_1_water_contam = float(telem["/consumables/fuel/tank[1]/water-contamination"])
    
    if(fuel_tank_1_water_contam > 0.0):
        report.append("Fuel Tank 1 Water contamination:\tFAIL")
        actions.append("Flush fuel tank 1")
    else:
        report.append("Fuel Tank 1 Water contamination:\tOK") 
    
    ##### carb_ice < 0.2
    
    carb_ice_amt = float(telem["/engines/active-engine/carb_ice"])
    
    if(carb_ice_amt >= 0.2):
        report.append("Engine Carburetor ice amount:\tFAIL")
        actions.append("Clear carburetor of ice")
    else:
        report.append("Engine Carburetor ice amount:\t\tOK") 
    
    #####
    # check battery level > 0
    
    sys_battery_level = float(telem["/systems/electrical/battery-charge-percent"])
    
    if(sys_battery_level < 0.1):
        report.append("Battery charge level:\t\t\tFAIL")
        actions.append("Recharge battery")
    else:
        report.append("Battery charge level:\t\t\tOK") 
    
    #####
    # check wings damaged = 0
    
    wing_left_damage_amt = float(telem["/fdm/jsbsim/wing-damage/left-wing"])
    
    if(wing_left_damage_amt > 0.1):
        report.append("Left Wing status:\t\t\tFAIL")
        actions.append("Repair Left Wing")
    else:
        report.append("Left Wing status:\t\t\tOK") 
    
    wing_right_damage_amt = float(telem["/fdm/jsbsim/wing-damage/right-wing"])
    
    if(wing_right_damage_amt > 0.1):
        report.append("Right Wing status:\t\t\tFAIL")
        actions.append("Repair Right Wing")
    else:
        report.append("Right Wing status:\t\t\tOK") 
    
    #####
    #check mixture and throttle not too deviated
    
    mixture_setting = float(telem["/controls/engines/current-engine/mixture"])
    throttle_setting = float(telem["/controls/engines/current-engine/throttle"])
    
    if( abs(throttle_setting - mixture_setting) > 0.5):
        report.append("Throttle-mixture deviation:\t\tFAIL")
        actions.append("Reset throttle and mixture levels")
    else:
        report.append("Throttle-mixture deviation:\t\tOK")
    
    #####
    # check engine temp < 2000
    
    exhaust_gas_temperature = float(telem["/engines/active-engine/egt-degf"])
    
    if(exhaust_gas_temperature > 2000.0 ):
        report.append("Exhaust Gas Temperature check:\t\tFAIL")
        actions.append("Lower engine temperature")
    else:
        report.append("Exhaust Gas Temperature check:\t\tOK") 
    
    #####
    # check oil temp < 250
    
    # about 250 for 75% throttle 
    
    oil_temp = float(telem["/engines/active-engine/oil-temperature-degf"])
    
    if(oil_temp > 250.0 ):
        report.append("Oil Temperature check:\t\t\tFAIL")
        actions.append("Lower engine oil temperature")
    else:
        report.append("Oil Temperature check:\t\t\tOK") 
    
    #####
    # check oil pressure < 100
    
    oil_pressure = float(telem["/engines/active-engine/oil-pressure-psi"])
    
    if(oil_pressure > 100.0 ):
        report.append("Oil Pressure check:\t\t\tFAIL")
        actions.append("Lower engine oil pressure")
    else:
        report.append("Oil Pressure check:\t\t\tOK") 
    
    ###################
    
    # Print report
    print("===================")
    print("Diagnostic Report:")
    for item in report:
        print ("\t%s" % item)
    
    
    # Print actions
    print("===================")
    print("Service Actions:")
    if( len(actions) == 0):
        print("\tNone")
    else:
        for item in actions:
            print("\t%s" % item)    

if __name__ == "__main__":
    main(sys.argv)