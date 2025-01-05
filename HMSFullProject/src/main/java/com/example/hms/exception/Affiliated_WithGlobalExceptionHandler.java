package com.example.hms.exception;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
 
@ControllerAdvice
public class Affiliated_WithGlobalExceptionHandler {
	@ExceptionHandler
		public  ResponseEntity<ErrorResponse> handleException(Exception e)
		{
			ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
			return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
		}
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(AffiliatedWithExistsException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(NoAffiliationsFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
 
}


