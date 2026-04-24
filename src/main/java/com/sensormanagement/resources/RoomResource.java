/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensormanagement.resources;

import com.sensormanagement.exceptions.RoomNotEmptyException;
import com.sensormanagement.model.DataStorage;
import com.sensormanagement.model.Room;
import com.sensormanagement.model.Sensor;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author imran
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Response getAllRooms() {
        Map<String, Object> response = new HashMap<>();
        response.put("count", DataStorage.getRooms().size());
        response.put("rooms", DataStorage.getRooms().values());
        return Response.ok(response).build();
    }

    @POST
    public Response createRoom(Room room) {
        if (room == null || room.getRoomName() == null || room.getRoomName().isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("errorMessage", "Room name is required.");
            error.put("errorCode", 400);
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }

        int newID = DataStorage.getRooms().size() + 1;
        room.setRoomID(newID);
        DataStorage.getRooms().put(newID, room);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Room created successfully.");
        response.put("room", room);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{roomID}")
    public Response getRoom(@PathParam("roomID") int roomID) {
        Room room = DataStorage.getRooms().get(roomID);
        if (room == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("errorMessage", "Room with ID " + roomID + " not found.");
            error.put("errorCode", 404);
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomID}")
    public Response deleteRoom(@PathParam("roomID") int roomID) {
        Room room = DataStorage.getRooms().get(roomID);
        if (room == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("errorMessage", "Room with ID " + roomID + " not found.");
            error.put("errorCode", 404);
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        for (Sensor sensor : DataStorage.getSensors().values()) {
            if (sensor.getRoomID() == roomID) {
                throw new RoomNotEmptyException("Room " + room.getRoomName() + " cannot be deleted because it still has sensors assigned to it.");
            }
        }

        DataStorage.getRooms().remove(roomID);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Room " + room.getRoomName() + " deleted successfully.");
        return Response.ok(response).build();
    }
    
}