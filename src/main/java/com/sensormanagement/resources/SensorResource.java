/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensormanagement.resources;

import com.sensormanagement.exceptions.LinkedResourceNotFoundException;
import com.sensormanagement.model.DataStorage;
import com.sensormanagement.model.Sensor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author imran
 */
@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public Response getAllSensors(@QueryParam("type") String type) {
        List<Sensor> sensorList = new ArrayList<>(DataStorage.getSensors().values());

        if (type != null && !type.isEmpty()) {
            List<Sensor> filtered = new ArrayList<>();
            for (Sensor sensor : sensorList) {
                if (sensor.getSensorType().equalsIgnoreCase(type)) {
                    filtered.add(sensor);
                }
            }
            sensorList = filtered;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("count", sensorList.size());
        response.put("sensors", sensorList);
        if (type != null) {
            response.put("filteredByType", type);
        }
        return Response.ok(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        if (sensor == null || sensor.getSensorName() == null || sensor.getSensorName().isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("errorMessage", "Sensor name is required.");
            error.put("errorCode", 400);
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }

        if (!DataStorage.getRooms().containsKey(sensor.getRoomID())) {
            throw new LinkedResourceNotFoundException("Room with ID " + sensor.getRoomID() + " does not exist. Please provide a valid roomID.");
        }

        int newID = DataStorage.getSensors().size() + 1;
        sensor.setSensorID(newID);
        sensor.setStatus(Sensor.SensorStatus.ACTIVE);
        DataStorage.getSensors().put(newID, sensor);
        DataStorage.getReadings().put(newID, new ArrayList<>());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Sensor registered successfully.");
        response.put("sensor", sensor);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{sensorID}")
    public Response getSensor(@PathParam("sensorID") int sensorID) {
        Sensor sensor = DataStorage.getSensors().get(sensorID);
        if (sensor == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("errorMessage", "Sensor with ID " + sensorID + " not found.");
            error.put("errorCode", 404);
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        return Response.ok(sensor).build();
    }

    @Path("/{sensorID}/readings")
    public SensorReadingResource getReadingResource(@PathParam("sensorID") int sensorID) {
        Sensor sensor = DataStorage.getSensors().get(sensorID);
        if (sensor == null) {
            throw new LinkedResourceNotFoundException("Sensor with ID " + sensorID + " not found.");
        }
        return new SensorReadingResource(sensorID);
    }
    
}