package com.dev.exceptions;

public class LoginException extends RuntimeException {
	public String getMessage()
	{
		return "invalid login credentials";
	}
}
