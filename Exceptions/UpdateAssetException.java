package com.dev.exceptions;

public class UpdateAssetException extends RuntimeException {
	public String getMessage()
	{
		return "enter valid asset id";
	}
}
