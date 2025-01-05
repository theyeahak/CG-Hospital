package com.example.hms.exception;

public class DepartmentExistsException extends RuntimeException{
	public DepartmentExistsException(String msg)
	{
		super(msg);
	}

}
