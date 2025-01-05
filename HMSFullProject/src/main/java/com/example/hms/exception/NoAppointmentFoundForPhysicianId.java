package com.example.hms.exception;

public class NoAppointmentFoundForPhysicianId extends RuntimeException{
	public NoAppointmentFoundForPhysicianId(String msg) {
		super(msg);
	}
}
