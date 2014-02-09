package com.github.yuthura.bianca.functions;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class Sum implements Selectable, Aliasable {
	private final Partial argument;

	public Sum(Object argument) {
		super();

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
		sb.append("SUM(");
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
