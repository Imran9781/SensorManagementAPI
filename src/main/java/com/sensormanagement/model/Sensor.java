/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensormanagement.model;

/**
 *
 * @author imran
 */
public class Sensor {

    public enum SensorStatus {
        ACTIVE, INACTIVE, MAINTENANCE
    }

    private int sensorID;
    private String sensorName;
    private String sensorType;
    private int roomID;
    private SensorStatus status;
    private double currentValue;
    private String unit;

    public Sensor() {}

    public Sensor(int sensorID, String sensorName, String sensorType, int roomID, String unit) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.sensorType = sensorType;
        this.roomID = roomID;
        this.unit = unit;
        this.status = SensorStatus.ACTIVE;
        this.currentValue = 0.0;
    }

    public int getSensorID() { return sensorID; }
    public void setSensorID(int sensorID) { this.sensorID = sensorID; }

    public String getSensorName() { return sensorName; }
    public void setSensorName(String sensorName) { this.sensorName = sensorName; }

    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }

    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }

    public SensorStatus getStatus() { return status; }
    public void setStatus(SensorStatus status) { this.status = status; }

    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
}
