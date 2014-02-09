package com.github.yuthura.bianca.types;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class StringType extends AbstractType<String> {
	@Override
	public int getRawSQLType() {
		return Types.VARCHAR;
	}

	@Override
	public String coerceNonNull(Object object) throws CoercionException {
		if(object instanceof String) {
			return (String)object;
		}

		throw new CoercionException();
	}

	@Override
	public void setNonNull(String value, PreparedStatement statement, int index) throws SQLException {
		statement.setString(index, value);
	}

	@Override
	public String getMaybeNull(ResultSet result, String column) throws SQLException {
		return result.getString(column);
	}
}