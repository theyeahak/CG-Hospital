package com.example.hms.exception;

public class PatientIdNotFoundException  extends RuntimeException{
	
	public PatientIdNotFoundException(String msg) {
		super(msg);
	}

}
