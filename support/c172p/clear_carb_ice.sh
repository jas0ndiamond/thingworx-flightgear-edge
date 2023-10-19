#!/bin/bash

HOST="localhost"
PORT=5005

#         <!-- engines -->
#         <chunk>
#            <node>/engines/active-engine/carb_ice</node>
#            <name>carb_ice</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/engines/active-engine/complex-engine-procedures</node>
#            <name>complex-engine-procedures</name>
#            <type>bool</type>
#         </chunk>
#         <chunk>
#            <node>/engines/active-engine/winter-kit-installed</node>
#            <name>winter-kit-installed</name>
#            <type>bool</type>
#         </chunk>

#need to keep complex-engine-procedures enabled -> set field 2 to 1

echo -n "Clearing carburetor ice..."
echo "0.0,1,1" | /usr/bin/nc -u -w1 $HOST $PORT
echo "Completed"
