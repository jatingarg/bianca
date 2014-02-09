package com.github.yuthura.bianca.conditions;

import com.github.yuthura.bianca.*;

public class NotEqualTo extends BinaryCondition {
	public final static String OPERATOR = "!=";

	public NotEqualTo(Object left, Object right) {
		super(OPERATOR, left, right);
	}

	@Override
	public void buildStatement(StringBuilder sb, Partial left, Partial right) {
		boolean lNull = Partial.NULL.equals(left);
		boolean rNull = Partial.NULL.equals(right);

		if(!lNull && !rNull) {
			left.buildStatement(sb);
			sb.append(" != ");
			right.buildStatement(sb);

		} else if(lNull && rNull) {
			sb.append("NULL IS NOT NULL");

		} else if(!lNull) {
			left.buildStatement(sb);
			sb.append(" IS NOT NULL");

		} else if(!rNull) {
			right.buildStatement(sb);
			sb.append(" IS NOT NULL");

		} else {
			throw new IllegalStateException();

		}
	}
}
