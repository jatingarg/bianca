package com.github.yuthura.bianca;

public interface InsertChain extends InsertQuery {
	default
	public InsertChain columns(Column<?>... columns) {
		setColumns(columns);
		return this;
	}

	default
	public InsertChain values(Object... values) {
		addValues(values);
		return this;
	}
}
