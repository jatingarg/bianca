package com.github.yuthura.bianca.functions;

public class Count extends DistinctableUnaryFunction {
	public static final String OPERATOR = "COUNT";

	public Count(Object argument) {
		super(OPERATOR, argument);
	}
}
