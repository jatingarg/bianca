package com.github.yuthura.bianca.types;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class IntegerType extends AbstractType<Integer> {
	public IntegerType() {
		super(Integer.class);
	}

	@Override
	public int getRawSQLType() {
		return Types.INTEGER;
	}

	@Override
	public Integer coerceNonNull(Object object) throws CoercionException {
		if(object instanceof Integer) {
			return (Integer)object;

		} else if(object instanceof Number) {
			return Integer.valueOf(((Number)object).intValue());

		} else if(object instanceof String) {
			try {
				return Integer.valueOf((String)object);
			} catch(Exception x) {
				throw new CoercionException(x);
			}
		}

		throw new CoercionException();
	}

	@Override
	public void setNonNull(Integer value, PreparedStatement statement, int index) throws SQLException {
		statement.setInt(index, value.intValue());
	}

	@Override
	public Integer getMaybeNull(ResultSet result, String column) throws SQLException {
		return Integer.valueOf(result.getInt(column));
	}
}