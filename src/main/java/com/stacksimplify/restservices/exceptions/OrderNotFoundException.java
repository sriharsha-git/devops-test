package com.stacksimplify.restservices.exceptions;

public class OrderNotFoundException extends Exception {

	private static final long serialVersionUID = -6701662960407246086L;

	public OrderNotFoundException(String message) {
		super(message);
	}

}
