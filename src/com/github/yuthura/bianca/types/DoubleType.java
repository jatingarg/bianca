package com.github.yuthura.bianca.types;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class DoubleType extends AbstractType<Double> {
	public DoubleType() {
		super(Double.class);
	}

	@Override
	public int getRawSQLType() {
		return Types.DOUBLE;
	}

	@Override
	public Double coerceNonNull(Object object) throws CoercionException {
		if(object instanceof Double) {
			return (Double)object;

		} else if(object instanceof Number) {
			return Double.valueOf(((Number)object).doubleValue());

		} else if(object instanceof String) {
			try {
				return Double.valueOf((String)object);
			} catch(Exception x) {
				throw new CoercionException(x);
			}
		}

		throw new CoercionException();
	}

	@Override
	public void setNonNull(Double value, PreparedStatement statement, int index) throws SQLException {
		statement.setDouble(index, value.doubleValue());
	}

	@Override
	public Double getMaybeNull(ResultSet result, String column) throws SQLException {
		return Double.valueOf(result.getDouble(column));
	}
}