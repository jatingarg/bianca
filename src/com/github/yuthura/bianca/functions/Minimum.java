package com.github.yuthura.bianca.functions;

public class Minimum extends DistinctableUnaryFunction {
	public static final String OPERATOR = "MIN";

	public Minimum(Object argument) {
		super(OPERATOR, argument);
	}
}
