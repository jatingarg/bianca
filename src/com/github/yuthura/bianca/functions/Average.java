package com.github.yuthura.bianca.functions;

public class Average extends DistinctableUnaryFunction {
	public static final String OPERATOR = "AVG";

	public Average(Object argument) {
		super(OPERATOR, argument);
	}
}
