package com.github.yuthura.bianca.functions;

public class Sum extends UnaryFunction {
	public static final String OPERATOR = "SUM";

	public Sum(Object argument) {
		super(OPERATOR, argument);
	}
}
