package com.example.hms.exception;

public class NoAffiliationsFoundException extends RuntimeException
{
	public NoAffiliationsFoundException(String msg)
	{
		super(msg);
	}
}
