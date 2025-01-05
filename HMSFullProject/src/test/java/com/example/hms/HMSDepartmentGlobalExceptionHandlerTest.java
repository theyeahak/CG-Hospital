package com.example.hms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hms.exception.DepartmentExistsException;
import com.example.hms.exception.DepartmentGlobalExceptionHandler;
import com.example.hms.exception.DepartmentIdNotFoundException;
import com.example.hms.exception.DepartmentsNotFoundException;
import com.example.hms.exception.ErrorResponse;
import com.example.hms.exception.NoTrainedInsFoundException;
import com.example.hms.exception.PhysiciansNotFoundException;

public class HMSDepartmentGlobalExceptionHandlerTest {

    private final DepartmentGlobalExceptionHandler handler = new DepartmentGlobalExceptionHandler();

    @Test
    public void testHandleDepartmentExistsException() {
        String message = "Department already exists";
        DepartmentExistsException exception = new DepartmentExistsException(message);

        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
    }

    @Test
    public void testHandleDepartmentIdNotFoundException() {
        String message = "Department ID not found";
        DepartmentIdNotFoundException exception = new DepartmentIdNotFoundException(message);

        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
    }

    @Test
    public void testHandleDepartmentsNotFoundException() {
        String message = "Departments not found";
        DepartmentsNotFoundException exception = new DepartmentsNotFoundException(message);

        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
    }

    @Test
    public void testHandleNoTrainedInsFoundException() {
        String message = "No trained ins found";
        NoTrainedInsFoundException exception = new NoTrainedInsFoundException(message);

        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
    }

    @Test
    public void testHandlePhysiciansNotFoundException() {
        String message = "Physicians not found";
        PhysiciansNotFoundException exception = new PhysiciansNotFoundException(message);

        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
    }
    @Test
    public void testHandleGenericException() {
        // Arrange
        String message = "An error occurred";
        Exception exception = new Exception(message);

        // Act
        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
    }
}