package com.github.yuthura.bianca.operations;

public class Modulo extends BinaryOperation {
	public static final String OPERATOR = "%";

	public Modulo(Object left, Object right) {
		super(left, OPERATOR, right);
	}
}
