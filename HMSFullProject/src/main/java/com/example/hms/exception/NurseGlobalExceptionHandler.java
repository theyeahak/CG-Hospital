package com.example.hms.exception;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
 
public class NurseGlobalExceptionHandler {
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(PhysiciansNotFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(NurseNotFoundException e)
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
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(AlreadyFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
 
}
