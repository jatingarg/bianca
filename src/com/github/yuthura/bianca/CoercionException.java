package com.github.yuthura.bianca;

public class CoercionException extends RuntimeException {
	public CoercionException() {
		super();
	}

	public CoercionException(String message) {
		super(message);
	}

	public CoercionException(Throwable cause) {
		super(cause);
	}

	public CoercionException(String message, Throwable cause) {
		super(message, cause);
	}

	public CoercionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
