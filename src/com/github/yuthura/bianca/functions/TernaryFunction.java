package com.github.yuthura.bianca.functions;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class TernaryFunction implements Selectable, Aliasable {
	private final String operator;

	private final Partial first;

	private final Partial second;

	private final Partial third;

	public TernaryFunction(String operator, Object first, Object second, Object third) {
		super();

		this.operator = Query.requireValidName(operator);
		this.first = Partial.wrap(first);
		this.second = Partial.wrap(second);
		this.third = Partial.wrap(third);
	}

	protected Partial getFirst() {
		return first;
	}

	protected Partial getSecond() {
		return second;
	}

	protected Partial getThird() {
		return third;
	}

	@Override
	public String getSelectionName() {
		StringBuilder sb = new StringBuilder();
		buildStatement(sb);
		return sb.toString();
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		sb.append(operator);
		sb.append("(");
		getFirst().buildStatement(sb);
		sb.append(", ");
		getSecond().buildStatement(sb);
		sb.append(", ");
		getThird().buildStatement(sb);
		sb.append(")");
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		int i = 0;

		i += getFirst().prepareStatement(statement, index + i);
		i += getSecond().prepareStatement(statement, index + i);
		i += getThird().prepareStatement(statement, index + i);

		return i;
	}

	@Override
	public Alias as(String name) {
		return new Alias.Simple(this, name);
	}
}
