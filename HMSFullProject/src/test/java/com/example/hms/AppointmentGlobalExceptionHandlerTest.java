package com.example.hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hms.exception.AppointmentGlobalExceptionHandler;
import com.example.hms.exception.ErrorResponse;
import com.example.hms.exception.ExaminationRoomNotFoundException;
import com.example.hms.exception.PatientNotFoundException;
import com.example.hms.exception.RoomNotFoundException;


public class AppointmentGlobalExceptionHandlerTest {

    private final AppointmentGlobalExceptionHandler exceptionHandler = new AppointmentGlobalExceptionHandler();

    @Test
    void testHandleGenericException() {
        Exception genericException = new Exception("Generic error");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(genericException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Generic error", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
    }

    @Test
    void testHandlePatientNotFoundException() {
        PatientNotFoundException exception = new PatientNotFoundException("Patient not found");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Patient not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
    }

    @Test
    void testHandleExaminationRoomNotFoundException() {
        ExaminationRoomNotFoundException exception = new ExaminationRoomNotFoundException("Examination room not found");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Examination room not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
    }

    @Test
    void testHandleRoomNotFoundException() {
        RoomNotFoundException exception = new RoomNotFoundException("Room not found");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Room not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
    }
}
