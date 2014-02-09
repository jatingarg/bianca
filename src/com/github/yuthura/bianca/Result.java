package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

public class Result {
	private final ResultSet resultSet;

	public Result(ResultSet resultSet) {
		this.resultSet = Objects.requireNonNull(resultSet);
	}


	public <T> T get(String column, Type<T> type) {
		try {
			return type.get(resultSet, column);
		} catch(SQLException x) {
			throw new QueryException(x);
		}
	}

	public <T> T get(Column<T> column) {
		return get(column.getSelectionName(), column.getType());
	}

	public <T> T get(Selectable selectable, Type<T> type) {
		return get(selectable.getSelectionName(), type);
	}
}
