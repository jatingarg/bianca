package com.github.yuthura.bianca.impl;

import java.sql.*;
import java.util.*;

import com.github.yuthura.bianca.*;

public class DelegatedUpdate implements UpdateQuery {
	private final UpdateQuery target;

	public DelegatedUpdate(UpdateQuery target) {
		super();

		this.target = Objects.requireNonNull(target);
	}

	@Override
	public void addSet(Column<?> column, Object value) {
		target.addSet(column, value);
	}

	@Override
	public Where getWhere() {
		return target.getWhere();
	}

	@Override
	public void setWhere(Where where) {
		target.setWhere(where);
	}

	@Override
	public int run() {
		return target.run();
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		target.buildStatement(sb);
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		return target.prepareStatement(statement, index);
	}
}
