package com.stacksimplify.restservices.exceptions;

public class InvalidCredentialsException extends Exception {

	private static final long serialVersionUID = -5828069360634131012L;
	
	public InvalidCredentialsException(String message) {
		super(message);
	}

}
