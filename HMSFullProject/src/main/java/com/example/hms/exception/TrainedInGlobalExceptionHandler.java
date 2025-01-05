package com.example.hms.exception;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
@ControllerAdvice
public class TrainedInGlobalExceptionHandler {
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(TrainedInWithThisPhysicianIdNotFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(PhysicianIdNotFoundException.class)

    public  ResponseEntity<ErrorResponse> handleException(PhysicianIdNotFoundException e)
 
	{
 
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
 
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
 
	}
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(NoProceduresFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
}
