package com.github.yuthura.bianca.types;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class FloatType extends AbstractType<Float> {
	@Override
	public int getRawSQLType() {
		return Types.FLOAT;
	}

	@Override
	public Float coerceNonNull(Object object) throws CoercionException {
		if(object instanceof Float) {
			return (Float)object;

		} else if(object instanceof Number) {
			return Float.valueOf(((Number)object).floatValue());

		} else if(object instanceof String) {
			try {
				return Float.valueOf((String)object);
			} catch(Exception x) {
				throw new CoercionException(x);
			}
		}

		throw new CoercionException();
	}

	@Override
	public void setNonNull(Float value, PreparedStatement statement, int index) throws SQLException {
		statement.setFloat(index, value.floatValue());
	}

	@Override
	public Float getMaybeNull(ResultSet result, String column) throws SQLException {
		return Float.valueOf(result.getFloat(column));
	}
}