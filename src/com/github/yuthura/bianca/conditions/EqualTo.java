package com.github.yuthura.bianca.conditions;

import com.github.yuthura.bianca.*;

public class EqualTo extends BinaryCondition {
	public final static String OPERATOR = "=";

	public EqualTo(Object left, Object right) {
		super(OPERATOR, left, right);
	}

	@Override
	public void buildStatement(StringBuilder sb, Partial left, Partial right) {
		boolean lNull = left == null || Partial.NULL.equals(left);
		boolean rNull = right == null || Partial.NULL.equals(right);

		if(!lNull && !rNull) {
			left.buildStatement(sb);
			sb.append(" = ");
			right.buildStatement(sb);

		} else if(lNull && rNull) {
			sb.append("NULL IS NULL");

		} else if(!lNull) {
			left.buildStatement(sb);
			sb.append(" IS NULL");

		} else if(!rNull) {
			right.buildStatement(sb);
			sb.append(" IS NULL");

		} else {
			throw new IllegalStateException();

		}
	}
}
