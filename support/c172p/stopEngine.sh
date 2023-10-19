#!/bin/bash

HOST="localhost"
PORT=5004

#yank mixture to stop engine

#         <!-- control -->
#         <chunk>
#            <node>/controls/anti-ice/pitot-heat</node>
#            <name>pitot-heat</name>
#            <type>bool</type>
#         </chunk>
#         <chunk>
#            <node>/controls/anti-ice/window-heat</node>
#            <name>window-heat</name>
#            <type>bool</type>
#         </chunk>
#         <chunk>
#            <node>/controls/anti-ice/wing-heat</node>
#            <name>wing-heat</name>
#            <type>bool</type>
#         </chunk>
#         <chunk>
#            <node>/controls/electric/battery-switch</node>
#            <name>battery-switch</name>
#            <type>bool</type>
#         </chunk>
#         <chunk>
#            <node>/controls/engines/current-engine/mixture</node>
#            <name>mixture</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/engines/current-engine/throttle</node>
#            <name>throttle</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/flight/aileron</node>
#            <name>aileron</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/flight/aileron-trim</node>
#            <name>aileron-trim</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/flight/auto-coordination</node>
#            <name>auto-coordination</name>
#            <type>bool</type>
#         </chunk>
#         <chunk>
#            <node>/controls/flight/auto-coordination-factor</node>
#            <name>auto-coordination-factor</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/flight/elevator</node>
#            <name>elevator</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/flight/elevator-trim</node>
#            <name>elevator-trim</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/flight/flaps</node>
#            <name>flaps</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/flight/rudder</node>
#            <name>rudder</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/flight/rudder-trim</node>
#            <name>rudder-trim</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/flight/speedbrake</node>
#            <name>speedbrake</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/gear/brake-parking</node>
#            <name>brake-parking</name>
#            <type>float</type>
#         </chunk>
#         <chunk>
#            <node>/controls/gear/gear-down</node>
#            <name>gear-down</name>
#            <type>bool</type>
#         </chunk>

echo -n "Stopping Engine..."
echo "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0" | /usr/bin/nc -u -w1 $HOST $PORT
echo "Completed"
