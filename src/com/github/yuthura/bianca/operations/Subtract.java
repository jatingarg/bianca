package com.github.yuthura.bianca.operations;

public class Subtract extends BinaryOperation {
	public static final String OPERATOR = "-";

	public Subtract(Object left, Object right) {
		super(left, OPERATOR, right);
	}
}
