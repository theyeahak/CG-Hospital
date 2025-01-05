package com.example.hms.exception;

import java.time.LocalDateTime;

public class RoomNotFoundException extends RuntimeException  {
	public RoomNotFoundException (String msg)
	{
		super(msg);
	}

	
}
