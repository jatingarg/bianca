package com.github.yuthura.bianca.operations;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class BinaryOperation implements Partial {
	private final String operator;

	private final Partial left;

	private final Partial right;

	public BinaryOperation(Object left, String operator, Object right) {
		super();

		this.operator = Query.requireValidName(operator);
		this.left = Partial.wrap(left);
		this.right = Partial.wrap(right);
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		buildStatement(sb, left, right);
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		return prepareStatement(statement, index, left, right);
	}


	protected void buildStatement(StringBuilder sb, Partial left, Partial right) {
		left.buildStatement(sb);
		sb.append(" ");
		sb.append(operator);
		sb.append(" ");
		right.buildStatement(sb);
	}

	protected int prepareStatement(PreparedStatement statement, int index, Partial left, Partial right) throws SQLException {
		int i = 0;

		if(left != null) {
			i += left.prepareStatement(statement, index + i);
		}

		if(right != null) {
			i += right.prepareStatement(statement, index + i);
		}

		return i;
	}
}
