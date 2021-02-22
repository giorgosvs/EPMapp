package com.redux.Controller;

public class ApplicationNotFoundException extends RuntimeException {
	public ApplicationNotFoundException(String exception) {
		super(exception);
	}
}
