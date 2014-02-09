package com.github.yuthura.bianca;

public class UnsupportedDriverException extends RuntimeException {
	public UnsupportedDriverException() {
		super();
	}

	public UnsupportedDriverException(String message) {
		super(message);
	}

	public UnsupportedDriverException(Throwable cause) {
		super(cause);
	}

	public UnsupportedDriverException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedDriverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
