package com.stacksimplify.restservices.entities;

public class AuthenticationRequest {

	private String userName;
	private String passCode;
	
	public AuthenticationRequest() {
		
	}

	public AuthenticationRequest(String userName, String passCode) {
		this.userName = userName;
		this.passCode = passCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassCode() {
		return passCode;
	}

	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}

}
