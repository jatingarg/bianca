package com.github.yuthura.bianca.types;

import java.sql.*;
import java.time.*;
import java.time.format.*;

import com.github.yuthura.bianca.*;

public class TimeType extends AbstractType<LocalTime> {
	public TimeType() {
		super(LocalTime.class);
	}

	@Override
	public int getRawSQLType() {
		return Types.TIME;
	}

	@Override
	public LocalTime coerceNonNull(Object object) throws CoercionException {
		if(object instanceof LocalTime) {
			return (LocalTime)object;

		} else if(object instanceof Time) {
			return ((Time)object).toLocalTime();

		} else if(object instanceof Long) {
			return LocalTime.ofSecondOfDay(((Long)object).longValue());

		} else if(object instanceof String) {
			return LocalTime.parse((String)object, DateTimeFormatter.ISO_TIME);
		}

		throw new CoercionException();
	}

	@Override
	public void setNonNull(LocalTime value, PreparedStatement statement, int index) throws SQLException {
		statement.setTime(index, Time.valueOf(value));
	}

	@Override
	public LocalTime getMaybeNull(ResultSet result, String column) throws SQLException {
		Time value = result.getTime(column);
		if(value == null) {
			return null;
		}

		return value.toLocalTime();
	}
}
