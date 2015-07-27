package com.github.yuthura.bianca.operations;

public class Multiply extends BinaryOperation {
	public static final String OPERATOR = "*";

	public Multiply(Object left, Object right) {
		super(left, OPERATOR, right);
	}

}
