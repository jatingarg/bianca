package com.github.yuthura.bianca.types;

import java.sql.*;
import java.time.*;
import java.time.format.*;

import com.github.yuthura.bianca.*;

public class DateTimeType extends AbstractType<LocalDateTime> {
	private final static DateTimeFormatter LONG_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	public DateTimeType() {
		super(LocalDateTime.class);
	}

	@Override
	public int getRawSQLType() {
		return Types.DATE;
	}

	@Override
	public LocalDateTime coerceNonNull(Object object) throws CoercionException {
		if(object instanceof LocalDateTime) {
			return (LocalDateTime)object;

		} else if(object instanceof Timestamp) {
			return ((Timestamp)object).toLocalDateTime();

		} else if(object instanceof Long) {
			return LocalDateTime.parse(String.valueOf(object), LONG_FORMAT);

		} else if(object instanceof String) {
			return LocalDateTime.parse((String)object, DateTimeFormatter.ISO_DATE_TIME);
		}

		throw new CoercionException();
	}

	@Override
	public void setNonNull(LocalDateTime value, PreparedStatement statement, int index) throws SQLException {
		statement.setTimestamp(index, Timestamp.valueOf(value));
	}

	@Override
	public LocalDateTime getMaybeNull(ResultSet result, String column) throws SQLException {
		Timestamp value = result.getTimestamp(column);
		if(value == null) {
			return null;
		}

		return value.toLocalDateTime();
	}
}