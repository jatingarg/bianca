package com.github.yuthura.bianca.conditions;

public class LessThan extends BinaryCondition {
	public final static String OPERATOR = "<";

	public LessThan(Object left, Object right) {
		super(OPERATOR, left, right);
	}
}
