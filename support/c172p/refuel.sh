#!/bin/bash

HOST="localhost"
PORT=5003

TANK_FULL="21.5"

#capacity for c172p
#"/consumables/fuel/tank/capacity-gal_us": 21.500000,
#"/consumables/fuel/tank[1]/capacity-gal_us": 21.500000

#        <!-- two fuel tanks -->
#         <chunk>
#            <node>/consumables/fuel/tank/level-gal_us</node>
#            <name>level-gal_us</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/consumables/fuel/tank/water-contamination</node>
#            <name>water-contamination</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/consumables/fuel/tank[1]/level-gal_us</node>
#            <name>level-gal_us</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/consumables/fuel/tank[1]/water-contamination</node>
#            <name>water-contamination</name>
#            <type>float</type>
#         </chunk>

echo -n "Refueling..."
echo "$TANK_FULL,0,$TANK_FULL,0" | /usr/bin/nc -u -w1 $HOST $PORT
echo "Completed"
