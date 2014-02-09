package com.github.yuthura.bianca.impl;

import java.sql.*;
import java.util.*;

import com.github.yuthura.bianca.*;

public class ExecutableInsert extends DelegatedInsert {
	private final ConnectionFactory connectionFactory;

	public ExecutableInsert(InsertQuery target, ConnectionFactory connectionFactory) {
		super(target);

		this.connectionFactory = Objects.requireNonNull(connectionFactory);
	}


	@Override
	public int run() {
		return runQuery(results -> { });
	}



	protected int runQuery(ResultSetHandler consumer) {
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
}
