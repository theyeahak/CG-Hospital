package com.example.hms.exception;


public class NoAppointmentFoundForId extends RuntimeException{
	public NoAppointmentFoundForId(String msg) {
		super(msg);
	}
}
