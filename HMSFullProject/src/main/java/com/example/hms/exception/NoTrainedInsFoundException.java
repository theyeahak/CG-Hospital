package com.example.hms.exception;

public class NoTrainedInsFoundException extends RuntimeException{
	public NoTrainedInsFoundException(String msg)
	{
		super(msg);
	}

	public NoTrainedInsFoundException() {
		
	}

}
