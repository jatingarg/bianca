package com.github.yuthura.bianca.impl;

import java.sql.*;
import java.util.*;

import com.github.yuthura.bianca.*;

public class DelegatedDelete implements DeleteQuery {
	private final DeleteQuery target;

	public DelegatedDelete(DeleteQuery target) {
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
}
