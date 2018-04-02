package com.accenture.aris.core;

public class BussinessFailureException extends RuntimeException {

	private static final long serialVersionUID = -3179384074875691695L;

	public BussinessFailureException() {
	
	}

	public BussinessFailureException(String message) {
		super(message);
	}

	public BussinessFailureException(Throwable cause) {
		super(cause);
	}

	public BussinessFailureException(String message, Throwable cause) {
		super(message, cause);
	}
}
