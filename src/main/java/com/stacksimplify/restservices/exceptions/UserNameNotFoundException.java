package com.stacksimplify.restservices.exceptions;

public class UserNameNotFoundException extends Exception {

	private static final long serialVersionUID = -6529594361645486449L;
	
	public UserNameNotFoundException(String message) {
		super(message);
	}
	
}
