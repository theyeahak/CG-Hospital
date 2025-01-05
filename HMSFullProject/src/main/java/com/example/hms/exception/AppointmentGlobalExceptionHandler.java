package com.example.hms.exception;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class AppointmentGlobalExceptionHandler {


	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(Exception e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
    
    //appointment
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(PatientNotFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	//appoint
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(ExaminationRoomNotFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}

	//appoint
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(RoomNotFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	//Ranjan

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException e) {
		// create error object
		ErrorResponse err = new ErrorResponse();
		err.setMessage(e.getMessage());
		err.setStatus(HttpStatus.BAD_REQUEST);
		err.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoAppointmentFoundForPhysicianId.class)
	public ResponseEntity<ErrorResponse> handleException(NoAppointmentFoundForPhysicianId e) {
		ErrorResponse err = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoAppointmentFoundForId.class)
	public ResponseEntity<ErrorResponse> handleException(NoAppointmentFoundForId e) {
		ErrorResponse err = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException e) {
		// create error object
		ErrorResponse err = new ErrorResponse();
		err.setMessage(e.getMessage());
		err.setStatus(HttpStatus.BAD_REQUEST);
		err.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PatientIdNotFoundException.class)

	public ResponseEntity<ErrorResponse> handleException(PatientIdNotFoundException e)

	{

		ErrorResponse err = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());

		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(NoNurseFoundByPatientIdException.class)

	public ResponseEntity<ErrorResponse> handleException(NoNurseFoundByPatientIdException e)
	
	{

		ErrorResponse err = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());

		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(NurseNotFoundException e) {
		ErrorResponse err = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(FieldsNotFoundException e) {
		ErrorResponse err = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	
}
