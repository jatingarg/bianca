package com.github.yuthura.bianca.conditions;

public class NotIn extends InSupport {
	public final static String OPERATOR = "NOT IN";

	public NotIn(Object left, Object... right) {
		super(OPERATOR, left, right);
	}
}
