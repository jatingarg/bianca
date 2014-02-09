package com.github.yuthura.bianca;

import java.sql.*;
import java.time.*;
import java.time.format.*;

public interface Type<T> {
	public final static Type<String>          STRING = new StringType();
	public final static Type<Boolean>        BOOLEAN = new BooleanType();
	public final static Type<Integer>        INTEGER = new IntegerType();
	public final static Type<Long>              LONG = new LongType();
	public final static Type<Float>            FLOAT = new FloatType();
	public final static Type<Double>          DOUBLE = new DoubleType();
	public final static Type<LocalDate>         DATE = new DateType();
	public final static Type<LocalTime>         TIME = new TimeType();
	public final static Type<LocalDateTime> DATETIME = new DateTimeType();


	public int getRawSQLType();

	public T coerce(Object object) throws CoercionException;

	public void set(Object value, PreparedStatement statement, int index) throws SQLException;

	public T get(ResultSet rs, String column) throws SQLException;

	public Partial asPartial(T object);




	public static abstract class AbstractType<T> implements Type<T> {
		@Override
		public T coerce(Object object) throws CoercionException {
			if(object == null) {
				return null;
			}

			return coerceNonNull(object);
		}

		@Override
		public int hashCode() {
			return getRawSQLType();
		}

		@Override
		public boolean equals(Object object) {
			return object != null && getClass().equals(object.getClass());
		}

		@Override
		public void set(Object value, PreparedStatement statement, int index) throws SQLException {
			if(value == null) {
				statement.setNull(index, getRawSQLType());
			} else {
				setNonNull(coerce(value), statement, index);
			}
		}

		@Override
		public T get(ResultSet result, String column) throws SQLException {
			T value = getMaybeNull(result, column);
			if(result.wasNull()) {
				return null;
			}

			return value;
		}

		@Override
		public Partial asPartial(final T object) {
			if(object == null) {
				return Partial.NULL;
			}

			return new Partial() {
				@Override
				public void buildStatement(StringBuilder sb) {
					sb.append("?");
				}

				@Override
				public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
					setNonNull(object, statement, index);
					return 1;
				}

			};
		}

		protected abstract T coerceNonNull(Object value);

		protected abstract void setNonNull(T value, PreparedStatement statement, int index) throws SQLException;

		protected abstract T getMaybeNull(ResultSet result, String column) throws SQLException;
	}



	public static class IntegerType extends AbstractType<Integer> {
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

	public static class LongType extends AbstractType<Long> {
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

	public static class FloatType extends AbstractType<Float> {
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

	public static class DoubleType extends AbstractType<Double> {
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



	public static class StringType extends AbstractType<String> {
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


	public static class BooleanType extends AbstractType<Boolean> {
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






	public static class DateType extends AbstractType<LocalDate> {
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


	public static class TimeType extends AbstractType<LocalTime> {
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




	public static class DateTimeType extends AbstractType<LocalDateTime> {
		private final static DateTimeFormatter LONG_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

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

}
