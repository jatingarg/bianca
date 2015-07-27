package com.github.yuthura.bianca.operations;

public class Divide extends BinaryOperation {
	public static final String OPERATOR = "/";

	public Divide(Object left, Object right) {
		super(left, OPERATOR, right);
	}
}
