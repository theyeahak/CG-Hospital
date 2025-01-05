package com.example.hms.exception;


public class NurseNotFoundException extends RuntimeException
{
	public NurseNotFoundException(String msg)
	{
		super(msg);
	}

}
