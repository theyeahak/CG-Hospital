package com.example.hms.exception;

public class DepartmentsNotFoundException extends RuntimeException{
	public DepartmentsNotFoundException(String msg)
	{
		super(msg);
	}

}
