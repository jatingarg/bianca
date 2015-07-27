package com.github.yuthura.bianca.operations;

public class Add extends BinaryOperation {
	public static final String OPERATOR = "+";

	public Add(Object left, Object right) {
		super(left, OPERATOR, right);
	}

}
