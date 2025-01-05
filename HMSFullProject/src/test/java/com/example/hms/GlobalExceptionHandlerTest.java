package com.example.hms;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.hms.exception.ErrorResponse;
import com.example.hms.exception.GlobalExceptionHandler;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

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
    void testHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException exception = Mockito.mock(HttpMessageNotReadableException.class);
        Mockito.when(exception.getMessage()).thenReturn("Message not readable");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Message not readable", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); // Check minute for timestamp
    }
}