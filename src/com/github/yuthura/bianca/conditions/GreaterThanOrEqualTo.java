package com.github.yuthura.bianca.conditions;

public class GreaterThanOrEqualTo extends BinaryCondition {
	public final static String OPERATOR = ">=";

	public GreaterThanOrEqualTo(Object left, Object right) {
		super(OPERATOR, left, right);
	}
}
