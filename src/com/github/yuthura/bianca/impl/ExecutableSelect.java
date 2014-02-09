package com.github.yuthura.bianca.impl;

import java.sql.*;
import java.util.*;
import java.util.function.*;

import com.github.yuthura.bianca.*;

public class ExecutableSelect extends DelegatedSelect {
	private final ConnectionFactory connectionFactory;

	public ExecutableSelect(SelectQuery target, ConnectionFactory connectionFactory) {
		super(target);

		this.connectionFactory = Objects.requireNonNull(connectionFactory);
	}


	@Override
	public void forEach(Consumer<Result> consumer) {
		runQuery(results -> {
			while(results.next()) {
				consumer.accept(new Result(results));
			}
		});
	}

	@Override
	public <T> Collection<T> mapEach(Collection<T> collection, Function<Result, T> function) {
		forEach(result -> {
			collection.add(function.apply(result));
		});

		return collection;
	}

	// TODO: force limit
	@Override
	public void forFirst(Consumer<Result> consumer) {
		setLimit(new Limit(1));
		runQuery(results -> {
			if(results.next()) {
				consumer.accept(new Result(results));
			}
		});
	}

	// TODO: force limit
	@Override
	public <T> T mapFirst(Function<Result, T> function) {
		List<T> list = new ArrayList<>(1);
		forFirst(result -> {
			list.add(function.apply(result));
		});

		return list.isEmpty() ? null : list.get(0);
	}



	protected void runQuery(ResultSetHandler consumer) {
		StringBuilder sql = new StringBuilder();
		buildStatement(sql);

		SelectQuery.log(sql);

		try(Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {
			prepareStatement(statement, 1);
			try(ResultSet results = statement.executeQuery()) {
				consumer.handle(results);
			}
		} catch(SQLException x) {
			throw new QueryException(x);
		}
	}
}