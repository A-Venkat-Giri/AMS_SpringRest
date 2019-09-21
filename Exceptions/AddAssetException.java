package com.dev.exceptions;

public class AddAssetException extends RuntimeException {
	// public AddAssetException(String msg)
	// {
	// System.err.println(msg);
	//
	// }
	public String getMessage() {
		return "asset is already present";
	}
}
