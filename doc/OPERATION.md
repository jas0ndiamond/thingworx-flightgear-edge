# thingworx-flightgear-edge Operation

Operate virtual connected IoT aircraft with the ThingWorx Platform

Ensure the simulator environment and project setup is completed. Process documented [here](SETUP.md).

-----
### Run a basic connected flight ###

Each program takes two arguments:
1. ThingWorx config file
    * Sets the ThingWorx Platform host and port.
    * Sets the Application key from the entity defined in file `thingworx-flightgear-edge/entities/ApplicationKeys_EdgeKey.xml`
    * Sets the RemoteThing name the Edge application will use to connect to the ThingWorx Platform.
1. Simulator config file

### Applications ###
* org.jason.fgedge.c172p.client.C172PClient
* org.jason.fgedge.c172p.client.C172PRunwayBurnoutClient
* org.jason.fgedge.c172p.client.C172WaypointFlightClient
    `path/to/java -cp build/libs/thingworx-flightgear-edge-[version].jar org.jason.fgedge.c172p.client.C172PWaypointFlightClient ~/thingworx-flightgear-edge/conf/c172p/twx_c172p_alpha_flight_caltrops.properties ~/flightgear-control/scripts/conf/c172p/c172p_alpha_flight.properties`
* org.jason.fgedge.f15c.client.F15CClient
* org.jason.fgedge.f15c.client.F15CWaypointFlightClient
    `path/to/java -cp build/libs/thingworx-flightgear-edge-[version].jar org.jason.fgedge.f15c.client.F15CWaypointFlightClient ~/thingworx-flightgear-edge/conf/f15c/twx_f15c_beta_flight.properties ~/flightgear-control/scripts/conf/f15c/f15c_beta_flight.properties`

-----
### Controlled network failures ###

* org.jason.fgedge.c172p.client.C172WaypointFlightVariableConnectivityClient
* org.jason.fgedge.f15c.client.F15CWaypointFlightVariableConnectivityClient