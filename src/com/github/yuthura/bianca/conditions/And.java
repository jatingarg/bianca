package com.github.yuthura.bianca.conditions;

public class And extends AggregateCondition {
	public final static String OPERATOR = "AND";

	public And(Object... partials) {
		super(OPERATOR, partials);
	}
}
