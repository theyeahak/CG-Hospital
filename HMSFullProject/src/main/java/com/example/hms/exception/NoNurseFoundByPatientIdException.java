package com.example.hms.exception;

public class NoNurseFoundByPatientIdException extends RuntimeException{
	
	public NoNurseFoundByPatientIdException(String msg) {
		super(msg);
	}

}
