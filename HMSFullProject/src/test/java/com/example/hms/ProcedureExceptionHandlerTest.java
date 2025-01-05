package com.example.hms;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.hms.exception.AlreadyFoundException;
import com.example.hms.exception.ErrorResponse;
import com.example.hms.exception.ProcedureExceptionHandler;
import com.example.hms.exception.ProcedureIdNotFoundException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcedureExceptionHandlerTest {

    private final ProcedureExceptionHandler exceptionHandler = new ProcedureExceptionHandler();

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Generic error");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Generic error", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); // Check minute for timestamp
    }

    @Test
    void testHandleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException exception = Mockito.mock(MethodArgumentTypeMismatchException.class);
        Mockito.when(exception.getMessage()).thenReturn("Type mismatch error");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Type mismatch error", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); // Check minute for timestamp
    }

    @Test
    void testHandleAlreadyFoundException() {
        AlreadyFoundException exception = new AlreadyFoundException("Already found error");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Already found error", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); // Check minute for timestamp
    }

    @Test
    void testHandleProcedureIdNotFoundException() {
        ProcedureIdNotFoundException exception = new ProcedureIdNotFoundException("Procedure ID not found");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Procedure ID not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); // Check minute for timestamp
    }
}