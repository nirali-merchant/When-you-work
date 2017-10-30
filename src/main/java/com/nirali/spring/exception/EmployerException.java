package com.nirali.spring.exception;

public class EmployerException extends Exception {
	
	public EmployerException(String message)
	{
		super("EmployerException-"+message);
	}
	
	public EmployerException(String message, Throwable cause)
	{
		super("EmployerException-"+message,cause);
	}
	

}
