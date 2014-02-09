package com.github.yuthura.bianca.schema;

import java.util.*;

import com.github.yuthura.bianca.*;

public class SchemaColumnAlias<T> implements Column<T> {
	private final Column<T> column;

	private final String name;

	public SchemaColumnAlias(Column<T> column, String name) {
		this.column = Objects.requireNonNull(column);
		this.name = Query.requireValidName(name);
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		quote(sb, getTable().getName());
		sb.append(".");
		quote(sb, column.getName());
	}

	@Override
	public void buildSelectionStatement(StringBuilder sb) {
		quote(sb, getTable().getName());
		sb.append(".");
		quote(sb, column.getName());
		sb.append(" AS ");
		quote(sb, getName());
	}

	@Override
	public Table getTable() {
		return column.getTable();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type<T> getType() {
		return column.getType();
	}

	@Override
	public Column<T> as(String alias) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSelectionName() {
		return getName();
	}
}