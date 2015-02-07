package com.github.yuthura.bianca.types;

import java.sql.*;
import java.time.*;
import java.time.format.*;

import com.github.yuthura.bianca.*;

public class DateType extends AbstractType<LocalDate> {
	public DateType() {
		super(LocalDate.class);
	}

	@Override
	public int getRawSQLType() {
		return Types.DATE;
	}

	@Override
	public LocalDate coerceNonNull(Object object) throws CoercionException {
		if(object instanceof LocalDate) {
			return (LocalDate)object;

		} else if(object instanceof Date) {
			return ((Date)object).toLocalDate();

		} else if(object instanceof Integer) {
			return LocalDate.parse(String.valueOf(object), DateTimeFormatter.BASIC_ISO_DATE);

		} else if(object instanceof Long) {
			return LocalDate.ofEpochDay(((Long)object).longValue());

		} else if(object instanceof String) {
			return LocalDate.parse((String)object, DateTimeFormatter.ISO_DATE);
		}

		throw new CoercionException();
	}

	@Override
	public void setNonNull(LocalDate value, PreparedStatement statement, int index) throws SQLException {
		statement.setDate(index, Date.valueOf(value));
	}

	@Override
	public LocalDate getMaybeNull(ResultSet result, String column) throws SQLException {
		Date value = result.getDate(column);
		if(value == null) {
			return null;
		}

		return value.toLocalDate();
	}
}