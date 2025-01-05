package com.example.hms.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class ProcedureExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		ErrorResponse err = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException e) {
		// create error object
		ErrorResponse err = new ErrorResponse();
		err.setMessage(e.getMessage());
		err.setStatus(HttpStatus.BAD_REQUEST);
		err.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(AlreadyFoundException e) {
		ErrorResponse err = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(ProcedureIdNotFoundException e) {
		ErrorResponse err = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler
	public  ResponseEntity<ErrorResponse> handleException(NoProceduresFoundException e)
	{
		ErrorResponse err=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}

}
