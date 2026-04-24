/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensormanagement.resources;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author imran
 */
@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response discover() {
        Map<String, Object> response = new HashMap<>();

        // API info
        response.put("api", "Sensor Management API");
        response.put("version", "1.0");

        // Admin contact
        Map<String, String> contact = new HashMap<>();
        contact.put("name", "John Doe");
        contact.put("email", "j.doe@smartcampus.ac.uk");
        contact.put("department", "Campus Technical Support");
        response.put("contact", contact);

        // Resource map
        Map<String, String> resources = new HashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");
        response.put("resources", resources);

        return Response.ok(response).build();
    }
    
}
