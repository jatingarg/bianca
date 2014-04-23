package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

public class Delete implements DeleteChain {
	private final Table table;

	private Where where;

	public Delete(Table table) {
		super();

		this.table = Objects.requireNonNull(table);
	}

	@Override
	public Where getWhere() {
		return where;
	}

	@Override
	public void setWhere(Where where) {
		this.where = where;
	}

	@Override
	public int run() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		sb.append("DELETE FROM ");
		table.buildStatement(sb);

		if(where != null) {
			where.buildStatement(sb);
		}
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		int i = 0;

		if(where != null) {
			i += where.prepareStatement(statement, index + i);
		}

		return i;
	}
}
