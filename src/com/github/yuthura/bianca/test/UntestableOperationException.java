package com.github.yuthura.bianca.test;

public class UntestableOperationException extends RuntimeException {

	public UntestableOperationException() {
	}

	public UntestableOperationException(String message) {
		super(message);
	}

	public UntestableOperationException(Throwable cause) {
		super(cause);
	}

	public UntestableOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public UntestableOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
