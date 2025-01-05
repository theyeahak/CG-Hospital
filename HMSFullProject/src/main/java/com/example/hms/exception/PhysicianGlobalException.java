package com.example.hms.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class PhysicianGlobalException {
	@ExceptionHandler(PhysicianIdNotFoundException.class)

    public  ResponseEntity<ErrorResponse> handleException(PhysicianIdNotFoundException e)
 
	{
 
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
 
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PhysicianIdNotFound.class)
	public  ResponseEntity<ErrorResponse> handleException(PhysicianIdNotFound e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(PhysiciansNotFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(ProcedureNameNotFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	

	@ExceptionHandler(PositionNotFoundForPosition.class)
	public  ResponseEntity<ErrorResponse> handleException(PositionNotFoundForPosition e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(PositionNotFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
}
