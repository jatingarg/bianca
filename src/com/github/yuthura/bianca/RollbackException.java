package com.github.yuthura.bianca;

public class RollbackException extends QueryException {
	public RollbackException() {
	}

	public RollbackException(String message) {
		super(message);
	}

	public RollbackException(Throwable cause) {
		super(cause);
	}

	public RollbackException(String message, Throwable cause) {
		super(message, cause);
	}

	public RollbackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
