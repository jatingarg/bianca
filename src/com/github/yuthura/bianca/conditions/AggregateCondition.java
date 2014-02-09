package com.github.yuthura.bianca.conditions;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class AggregateCondition implements Condition {
	private final String operator;

	private final Partial[] partials;

	public AggregateCondition(String operator, Object... partials) {
		super();

		this.operator = Query.requireValidName(operator);

		if(partials.length < 2) {
			throw new IllegalArgumentException();
		}

		this.partials = new Partial[partials.length];
		for(int i = 0; i < partials.length; i++) {
			this.partials[i] = Partial.wrap(partials[i]);
		}
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		sb.append("(");
		for(int i = 0; i < partials.length; i++) {
			partials[i].buildStatement(sb);
			if(i < partials.length - 1) {
				sb.append(" ");
				sb.append(operator);
				sb.append(" ");
			}
		}
		sb.append(")");
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		int i = 0;

		for(Partial partial : partials) {
			i += partial.prepareStatement(statement, index + i);
		}

		return i;
	}
}
