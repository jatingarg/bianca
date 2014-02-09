package com.github.yuthura.bianca.functions;

public class Year extends UnaryFunction {
	public static final String OPERATOR = "YEAR";

	public Year(Object argument) {
		super(OPERATOR, argument);
	}
}
