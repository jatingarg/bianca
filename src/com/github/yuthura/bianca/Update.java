package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

/**
 * UPDATE table SET column=value, column=value WHERE conditions ORDER BY column LIMIT 1
 */
public class Update implements Query {
	private final Table table;

	private final Map<Column<?>, Partial> sets;

	private Where where;

	public Update(Table table) {
		super();

		this.table = Objects.requireNonNull(table);
		sets = new LinkedHashMap<>();
	}


	public Update set(Column<?> column, Object value) {
		sets.put(column, Partial.wrap(value));
		return this;
	}


	public Update where(Condition... conditions) {
		if(where == null) {
			where = new Where();
		}

		where.addConditions(conditions);
		return this;
	}


	public int run(ConnectionFactory connectionFactory) {
		return runQuery(connectionFactory, results -> { });
	}


	protected int runQuery(ConnectionFactory connectionFactory, ResultSetHandler consumer) {
		StringBuilder sql = new StringBuilder();
		buildStatement(sql);

		Query.log(sql);

		try(Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {
			prepareStatement(statement, 1);
			int count = statement.executeUpdate();
			if(count > 0) {
				try(ResultSet results = statement.getResultSet()) {
					consumer.handle(results);
				}
			}

			return count;
		} catch(SQLException x) {
			throw new QueryException(x);
		}
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
