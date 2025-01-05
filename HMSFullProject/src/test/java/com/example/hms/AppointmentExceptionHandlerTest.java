package com.example.hms;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.hms.exception.AppointmentGlobalExceptionHandler;
import com.example.hms.exception.ErrorResponse;
import com.example.hms.exception.ExaminationRoomNotFoundException;
import com.example.hms.exception.FieldsNotFoundException;
import com.example.hms.exception.NoAppointmentFoundForId;
import com.example.hms.exception.NoAppointmentFoundForPhysicianId;
import com.example.hms.exception.NoNurseFoundByPatientIdException;
import com.example.hms.exception.NurseNotFoundException;
import com.example.hms.exception.PatientIdNotFoundException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentExceptionHandlerTest {

    private final AppointmentGlobalExceptionHandler exceptionHandler = new AppointmentGlobalExceptionHandler();

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Generic error");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Generic error", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); 
    }

    @Test
    void testHandleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException exception = Mockito.mock(MethodArgumentTypeMismatchException.class);
        Mockito.when(exception.getMessage()).thenReturn("Type mismatch error");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Type mismatch error", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); 
    }

    @Test
    void testHandleNoAppointmentFoundForPhysicianId() {
        NoAppointmentFoundForPhysicianId exception = new NoAppointmentFoundForPhysicianId("No appointment found for physician ID");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No appointment found for physician ID", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); 
    }

    @Test
    void testHandleNoAppointmentFoundForId() {
        NoAppointmentFoundForId exception = new NoAppointmentFoundForId("No appointment found for ID");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No appointment found for ID", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); 
    }

    @Test
    void testHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException exception = Mockito.mock(HttpMessageNotReadableException.class);
        Mockito.when(exception.getMessage()).thenReturn("Message not readable");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Message not readable", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); 
    }

    @Test
    void testHandlePatientIdNotFoundException() {
        PatientIdNotFoundException exception = new PatientIdNotFoundException("Patient ID not found");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Patient ID not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); 
    }

    @Test
    void testHandleNoNurseFoundByPatientIdException() {
        NoNurseFoundByPatientIdException exception = new NoNurseFoundByPatientIdException("No nurse found for patient ID");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No nurse found for patient ID", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); 
    }

    @Test
    void testHandleNurseNotFoundException() {
        NurseNotFoundException exception = new NurseNotFoundException("Nurse not found");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Nurse not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); 
    }

    @Test
    void testHandleExaminationRoomNotFoundException() {
        ExaminationRoomNotFoundException exception = new ExaminationRoomNotFoundException("Examination room not found");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Examination room not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); 
    }

    @Test
    void testHandleFieldsNotFoundException() {
        FieldsNotFoundException exception = new FieldsNotFoundException("Required fields not found");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Required fields not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getMinute(), response.getBody().getTimestamp().getMinute()); 
    }
}