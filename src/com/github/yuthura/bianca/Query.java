package com.github.yuthura.bianca;

public class Query {
	public static String requireValidName(String value) {
		if(value == null) {
			throw new NullPointerException();
		}

		if(value.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}

		if(value.indexOf('`') >= 0) {
			throw new IllegalArgumentException();
		}

		return value;
	}
}
