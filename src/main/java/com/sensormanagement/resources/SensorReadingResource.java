/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensormanagement.resources;

import com.sensormanagement.exceptions.SensorUnavailableException;
import com.sensormanagement.model.DataStorage;
import com.sensormanagement.model.Sensor;
import com.sensormanagement.model.SensorReading;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author imran
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private int sensorID;

    public SensorReadingResource(int sensorID) {
        this.sensorID = sensorID;
    }

    @GET
    public Response getReadings() {
        List<SensorReading> readingList = DataStorage.getReadings().get(sensorID);
        if (readingList == null) {
            readingList = new ArrayList<>();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("sensorID", sensorID);
        response.put("count", readingList.size());
        response.put("readings", readingList);
        return Response.ok(response).build();
    }

    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = DataStorage.getSensors().get(sensorID);

        if (sensor.getStatus() == Sensor.SensorStatus.MAINTENANCE) {
            throw new SensorUnavailableException("Sensor " + sensor.getSensorName() + " is currently under MAINTENANCE and cannot accept new readings.");
        }

        if (reading == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("errorMessage", "Reading body is required.");
            error.put("errorCode", 400);
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }

        List<SensorReading> readingList = DataStorage.getReadings().get(sensorID);
        int newID = readingList.size() + 1;
        reading.setReadingID(newID);
        reading.setSensorID(sensorID);
        readingList.add(reading);

        sensor.setCurrentValue(reading.getVal());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reading recorded successfully.");
        response.put("reading", reading);
        response.put("sensorCurrentValue", sensor.getCurrentValue());
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
    
}