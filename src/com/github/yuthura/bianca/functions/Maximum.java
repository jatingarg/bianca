package com.github.yuthura.bianca.functions;

public class Maximum extends DistinctableUnaryFunction {
	public static final String OPERATOR = "MAX";

	public Maximum(Object argument) {
		super(OPERATOR, argument);
	}
}
