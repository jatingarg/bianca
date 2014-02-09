package com.github.yuthura.bianca.conditions;

public class LessThanOrEqualTo extends BinaryCondition {
	public final static String OPERATOR = "<=";

	public LessThanOrEqualTo(Object left, Object right) {
		super(OPERATOR, left, right);
	}
}
