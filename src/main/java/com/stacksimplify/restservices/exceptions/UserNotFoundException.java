package com.stacksimplify.restservices.exceptions;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 2420299282061796474L;
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
