/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensormanagement.model;

/**
 *
 * @author imran
 */
public class Room {

    private int roomID;
    private String roomName;
    private String building;
    private int floorNum;
    private String description;

    public Room() {}

    public Room(int roomID, String roomName, String building, int floorNum, String description) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.building = building;
        this.floorNum = floorNum;
        this.description = description;
    }

    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }

    public int getFloorNum() { return floorNum; }
    public void setFloorNum(int floorNum) { this.floorNum = floorNum; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
}