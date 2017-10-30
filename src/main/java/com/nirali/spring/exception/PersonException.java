package com.nirali.spring.exception;

public class PersonException extends Exception {

	public PersonException(String message)
	{
		super("PersonException-"+message);
	}
	
	public PersonException(String message, Throwable cause)
	{
		super("PersonException-"+message,cause);
	}
	
}
