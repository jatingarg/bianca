package com.github.yuthura.bianca.conditions;

public class Or extends AggregateCondition {
	public final static String OPERATOR = "OR";

	public Or(Object... partials) {
		super(OPERATOR, partials);
	}
}
