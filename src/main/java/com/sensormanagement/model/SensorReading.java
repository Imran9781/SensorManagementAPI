/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensormanagement.model;

/**
 *
 * @author imran
 */
public class SensorReading {

    private int readingID;
    private int sensorID;
    private double val;
    private String unit;
    private String note;

    public SensorReading() {}

    public SensorReading(int readingID, int sensorID, double val, String unit, String note) {
        this.readingID = readingID;
        this.sensorID = sensorID;
        this.val = val;
        this.unit = unit;
        this.note = note;
    }

    public int getReadingID() { return readingID; }
    public void setReadingID(int readingID) { this.readingID = readingID; }

    public int getSensorID() { return sensorID; }
    public void setSensorID(int sensorID) { this.sensorID = sensorID; }

    public double getVal() { return val; }
    public void setVal(double val) { this.val = val; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    
}