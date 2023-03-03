# thingworx-flightgear-edge Setup #

## Dependencies ##
1. Linux x86_64 running with a windowing system.
1. Git
1. JDK 8
1. [flightgear-control](https://github.com/jas0ndiamond/flightgear-control) project setup completed with the flightgear simulator installed.
1. ThingWorx Platform 9.3 or similar.
1. ThingWorx Edge Java SDK 7.1.0a.

---
### ThingWorx Platform Configuration ###
1. Start up the ThingWorx Platform.
1. From the ThingWorx Platform Composer:
    1. Import all of the DataShapes entities (DataShapes*) from the `thingworx-flightgear-edge/entities` directory. 
    1. Import all of the Things entities (Things*) from the `thingworx-flightgear-edge/entities` directory. 
    1. Import the Application Key entity ApplicationKeys_EdgeKey.xml. This is expected by the ThingWorx Edge SDK applications in this project.

---
### Building ###
1. Switch to the project root of the `thingworx-flightgear-edge` project, and ensure the latest release or development branch is checked out.
1. Copy your ThingWorx Java SDK 7.1.0a jar to the `thingworx-flightgear-edge/lib` directory.
1. Run the build tasks `jar` and `sourcesjar` with the gradle wrapper, specifying JDK 8:
    `JAVA_HOME=/path/to/jdk8 ./gradlew jar sourcesjar`
1. Check that the jars `thingworx-flightgear-edge-[version].jar` and `thingworx-flightgear-edge-[version]-src.jar` appear in `build/lib`.
1. Use this jar in other projects.

---
#### Run flights ####

Documented [here](OPERATION.md).
