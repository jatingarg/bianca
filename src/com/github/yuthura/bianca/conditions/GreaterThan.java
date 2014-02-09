package com.github.yuthura.bianca.conditions;

public class GreaterThan extends BinaryCondition {
	public final static String OPERATOR = ">";

	public GreaterThan(Object left, Object right) {
		super(OPERATOR, left, right);
	}
}
