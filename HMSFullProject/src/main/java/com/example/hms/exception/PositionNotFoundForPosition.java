package com.example.hms.exception;

public class PositionNotFoundForPosition extends RuntimeException {
	public PositionNotFoundForPosition(String msg) {
		super(msg);
	}
}
