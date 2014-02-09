package com.github.yuthura.bianca.conditions;

public class In extends InSupport {
	public final static String OPERATOR = "IN";

	public In(Object left, Object... right) {
		super(OPERATOR, left, right);
	}
}
