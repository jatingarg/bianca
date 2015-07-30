package com.github.yuthura.bianca.conditions;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class BetweenSupport implements Condition {
	private final String operator;

	private final Partial left;

	private final Partial right;

	public BetweenSupport(String operator, Object left, Object right) {
		super();

		this.operator = Query.requireValidName(operator);

		this.left = Partial.wrap(left);
		this.right = Partial.wrap(right);
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		sb.append(operator);
		sb.append(" ");
		left.buildStatement(sb);
		sb.append(" AND ");
		right.buildStatement(sb);
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		int i = 0;

		i += left.prepareStatement(statement, index + i);
		i += right.prepareStatement(statement, index + i);

		return i;
	}

}
