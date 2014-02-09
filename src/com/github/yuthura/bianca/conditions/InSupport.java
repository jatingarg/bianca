package com.github.yuthura.bianca.conditions;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class InSupport implements Condition {
	private final String operator;

	private final Partial left;

	private final Partial[] right;

	public InSupport(String operator, Object left, Object... right) {
		super();

		if(right.length < 1) {
			throw new IllegalArgumentException();
		}

		this.operator = Query.requireValidName(operator);
		this.left = Partial.wrap(left);

		this.right = new Partial[right.length];
		for(int i = 0; i < right.length; i++) {
			this.right[i] = Partial.wrap(right[i]);
		}
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		left.buildStatement(sb);
		sb.append(" ");
		sb.append(operator);
		sb.append(" (");
		for(int i = 0; i < right.length; i++) {
			right[i].buildStatement(sb);
			if(i < right.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		int i = 0;

		i += left.prepareStatement(statement, index);

		for(Partial r : right) {
			i += r.prepareStatement(statement, index + i);
		}

		return i;
	}
}
