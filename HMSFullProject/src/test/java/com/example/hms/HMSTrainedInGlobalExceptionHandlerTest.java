package com.example.hms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hms.exception.ErrorResponse;
import com.example.hms.exception.NoProceduresFoundException;
import com.example.hms.exception.TrainedInWithThisPhysicianIdNotFoundException;
import com.example.hms.exception.PhysicianIdNotFoundException;
import com.example.hms.exception.TrainedInGlobalExceptionHandler;


public class HMSTrainedInGlobalExceptionHandlerTest {

	    private final TrainedInGlobalExceptionHandler handler = new TrainedInGlobalExceptionHandler();

	    @Test
	    public void testHandleTrainedInWithThisPhysicianIdNotFoundException() {
	        String message = "Trained In with this Physician ID not found";
	        TrainedInWithThisPhysicianIdNotFoundException exception = new TrainedInWithThisPhysicianIdNotFoundException(message);

	        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertEquals(message, response.getBody().getMessage());
	        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
	        assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
	    }

	    @Test
	    public void testHandlePhysicianIdNotFoundException() {
	        String message = "Physician ID not found";
	        PhysicianIdNotFoundException exception = new PhysicianIdNotFoundException(message);

	        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertEquals(message, response.getBody().getMessage());
	        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
	        assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
	    }

	    @Test
	    public void testHandleNoProceduresFoundException() {
	        String message = "No procedures found";
	        NoProceduresFoundException exception = new NoProceduresFoundException(message);

	        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertEquals(message, response.getBody().getMessage());
	        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
	        assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
	    }

	    /*@Test
	    public void testHandleNoTrainedInsFoundException() {
	        String message = "No trained ins found";
	        NoTrainedInsFoundException exception = new NoTrainedInsFoundException(message);

	        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertEquals(message, response.getBody().getMessage());
	        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
	        assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
	    }*/
	}

