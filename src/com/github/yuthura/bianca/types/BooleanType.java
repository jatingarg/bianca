package com.github.yuthura.bianca.types;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class BooleanType extends AbstractType<Boolean> {
	@Override
	public int getRawSQLType() {
		return Types.VARCHAR;
	}

	@Override
	public Boolean coerceNonNull(Object object) throws CoercionException {
		if(object instanceof Boolean) {
			return (Boolean)object;

		} else if(object instanceof Number) {
			return ((Number)object).intValue() > 0 ? Boolean.TRUE : Boolean.FALSE;

		} else if(object instanceof String) {
			switch(((String)object).toLowerCase()) {
				case "true":
				case "yes":
				case "on":
				case "1":
					return Boolean.TRUE;
				case "false":
				case "no":
				case "off":
				case "0":
					return Boolean.FALSE;
			}
		}

		throw new CoercionException();
	}

	@Override
	public void setNonNull(Boolean value, PreparedStatement statement, int index) throws SQLException {
		statement.setBoolean(index, value.booleanValue());
	}

	@Override
	public Boolean getMaybeNull(ResultSet result, String column) throws SQLException {
		return Boolean.valueOf(result.getBoolean(column));
	}
}