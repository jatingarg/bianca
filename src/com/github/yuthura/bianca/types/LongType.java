package com.github.yuthura.bianca.types;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class LongType extends AbstractType<Long> {
	public LongType() {
		super(Long.class);
	}

	@Override
	public int getRawSQLType() {
		return Types.BIGINT;
	}

	@Override
	public Long coerceNonNull(Object object) throws CoercionException {
		if(object instanceof Long) {
			return (Long)object;

		} else if(object instanceof Number) {
			return Long.valueOf(((Number)object).longValue());

		} else if(object instanceof String) {
			try {
				return Long.valueOf((String)object);
			} catch(Exception x) {
				throw new CoercionException(x);
			}
		}

		throw new CoercionException();
	}

	@Override
	public void setNonNull(Long value, PreparedStatement statement, int index) throws SQLException {
		statement.setLong(index, value.intValue());
	}

	@Override
	public Long getMaybeNull(ResultSet result, String column) throws SQLException {
		return Long.valueOf(result.getLong(column));
	}
}