package com.redux.Controller;

public class UserNotFoundException extends RuntimeException {
	
	public UserNotFoundException(String exception) {
		super(exception);
	}
}
