package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

public class Update implements UpdateChain {
	private final Table table;

	private final Map<Column<?>, Partial> sets;

	private Where where;

	public Update(Table table) {
		super();

		this.table = Objects.requireNonNull(table);
		sets = new LinkedHashMap<>();
	}

	@Override
	public void addSet(Column<?> column, Object value) {
		sets.put(column, Partial.wrap(value));
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
		if(sets.isEmpty()) {
			throw new IllegalStateException();
		}

		sb.append("UPDATE ");
		table.buildStatement(sb);

		sb.append(" SET ");
		for(Iterator<Map.Entry<Column<?>, Partial>> i = sets.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry<Column<?>, Partial> e = i.next();
			e.getKey().buildStatement(sb);
			sb.append(" = ");
			e.getValue().buildStatement(sb);

			if(i.hasNext()) {
				sb.append(", ");
			}
		}

		if(where != null) {
			where.buildStatement(sb);
		}
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		int i = 0;

		for(Map.Entry<Column<?>, Partial> e : sets.entrySet()) {
			i += e.getKey().prepareStatement(statement, index + i);
			i += e.getValue().prepareStatement(statement, index + i);
		}

		if(where != null) {
			i += where.prepareStatement(statement, index + i);
		}

		return i;
	}
}
