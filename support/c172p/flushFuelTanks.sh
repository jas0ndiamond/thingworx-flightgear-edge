#!/bin/bash

HOST="localhost"
PORT=5003

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

echo -n "Flushing Fuel tanks..."
echo "0,0,0,0" | /usr/bin/nc -u -w1 $HOST $PORT
echo "Completed"
