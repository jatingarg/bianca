package com.github.yuthura.bianca.conditions;

public class Between extends BetweenSupport {
	public static final String OPERATOR = "BETWEEN";

	public Between(Object left, Object right) {
		super(OPERATOR, left, right);
	}

}
