package com.github.yuthura.bianca.conditions;

public class NotBetween extends BetweenSupport {
	public static final String OPERATOR = "NOT BETWEEN";

	public NotBetween(Object left, Object right) {
		super(OPERATOR, left, right);
	}

}
