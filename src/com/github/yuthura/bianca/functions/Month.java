package com.github.yuthura.bianca.functions;

public class Month extends UnaryFunction {
	public static final String OPERATOR = "MONTH";

	public Month(Object argument) {
		super(OPERATOR, argument);
	}
}
