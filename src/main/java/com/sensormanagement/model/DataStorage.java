/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensormanagement.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author imran
 */
public class DataStorage {

    private static final Map<Integer, Room> rooms = new HashMap<>();
    private static final Map<Integer, Sensor> sensors = new HashMap<>();
    private static final Map<Integer, List<SensorReading>> readings = new HashMap<>();

    static {
        // Sample rooms
        rooms.put(1, new Room(1, "LG.106", "Copland", 1, "Computer science lab"));
        rooms.put(2, new Room(2, "G.03", "Little Titchfield", 0, "Large lecture theatre"));

        // Sample sensors
        sensors.put(1, new Sensor(1, "CO2 Sensor", "CO2", 1, "ppm"));
        sensors.put(2, new Sensor(2, "Temperature Sensor", "TEMPERATURE", 1, "C"));
        sensors.put(3, new Sensor(3, "NumOfOccupants Counter", "OCCUPANCY", 2, "people"));
        sensors.get(3).setStatus(Sensor.SensorStatus.MAINTENANCE);

        // Empty reading lists for each sensor
        readings.put(1, new ArrayList<>());
        readings.put(2, new ArrayList<>());
        readings.put(3, new ArrayList<>());
    }

    public static Map<Integer, Room> getRooms() { return rooms; }
    public static Map<Integer, Sensor> getSensors() { return sensors; }
    public static Map<Integer, List<SensorReading>> getReadings() { return readings; }
    
}

