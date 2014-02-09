package com.github.yuthura.bianca.functions;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class UnaryFunction implements Selectable, Aliasable {
	private final String operator;

	private final Partial argument;

	public UnaryFunction(String operator, Object argument) {
		super();

		this.operator = Query.requireValidName(operator);
		this.argument = Partial.wrap(argument);
	}

	protected Partial getArgument() {
		return argument;
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
		getArgument().buildStatement(sb);
		sb.append(")");
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		return getArgument().prepareStatement(statement, index);
	}

	@Override
	public Alias as(String name) {
		return new Alias.Simple(this, name);
	}
}
