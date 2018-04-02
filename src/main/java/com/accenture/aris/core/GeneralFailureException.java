package com.accenture.aris.core;

public class GeneralFailureException extends RuntimeException {

	private static final long serialVersionUID = -3179384074875691695L;

	public GeneralFailureException() {
	}

	public GeneralFailureException(String message) {
		super(message);
	}

	public GeneralFailureException(Throwable cause) {
		super(cause);
	}

	public GeneralFailureException(String message, Throwable cause) {
		super(message, cause);
	}
}
