package com.github.yuthura.bianca.impl;

import java.sql.*;
import java.util.*;

import com.github.yuthura.bianca.*;

public class DelegatedInsert implements InsertQuery {
	private final InsertQuery target;

	public DelegatedInsert(InsertQuery target) {
		super();

		this.target = Objects.requireNonNull(target);
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		target.buildStatement(sb);
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		return target.prepareStatement(statement, index);
	}

	@Override
	public void setColumns(Column<?>... columns) {
		target.setColumns(columns);
	}

	@Override
	public void addValues(Object... values) {
		target.addValues(values);
	}

	@Override
	public void setSelect(SelectQuery select) {
		target.setSelect(select);
	}

	@Override
	public int run() {
		return target.run();
	}
}
