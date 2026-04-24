/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensormanagement.mappers;

import com.sensormanagement.exceptions.LinkedResourceNotFoundException;
import com.sensormanagement.exceptions.RoomNotEmptyException;
import com.sensormanagement.exceptions.SensorUnavailableException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
/**
 *
 * @author imran
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable exception) {
        
        // Let specific mappers handle their own exceptions
        if (exception instanceof RoomNotEmptyException) {
            return new RoomNotEmptyExceptionMapper().toResponse((RoomNotEmptyException) exception);
        }
        if (exception instanceof LinkedResourceNotFoundException) {
            return new LinkedResourceNotFoundExceptionMapper().toResponse((LinkedResourceNotFoundException) exception);
        }
        if (exception instanceof SensorUnavailableException) {
            return new SensorUnavailableExceptionMapper().toResponse((SensorUnavailableException) exception);
        }

        LOGGER.log(Level.SEVERE, "Unexpected error occurred", exception);

        Map<String, Object> error = new HashMap<>();
        error.put("errorMessage", "An unexpected error occurred. Please contact the system administrator.");
        error.put("errorCode", 500);
        error.put("documentation", "Please contact Campus Technical Support.");

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}