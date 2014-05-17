package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

/**
 * DELETE table WHERE conditions ORDER BY column LIMIT 1
 */
public class Delete implements Query {
	private final Table table;

	private Where where;

	public Delete(Table table) {
		super();

		this.table = Objects.requireNonNull(table);
	}



	public Delete where(Condition... conditions) {
		if(where == null) {
			where = new Where();
		}

		where.setConditions(conditions);
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
