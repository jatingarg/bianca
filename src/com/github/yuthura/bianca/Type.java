package com.github.yuthura.bianca;

import java.sql.*;

public interface Type<T> {
	public final static Type<Integer> INTEGER = new IntegerType();
	public final static Type<Double>   DOUBLE = new DoubleType();
	public final static Type<String>   STRING = new StringType();


	public int getRawSQLType();
	
	public T coerce(Object object) throws CoercionException;
	
	public void set(Object value, PreparedStatement statement, int index) throws SQLException;
	
	public T get(ResultSet rs, String column) throws SQLException;

	public Partial asPartial(T object);

	
	
	
	public static abstract class AbstractType<T> implements Type<T> {
		public T coerce(Object object) throws CoercionException {
			if(object == null) {
				return null;
			}

			return coerceNonNull(object);
		}

		public int hashCode() {
			return getRawSQLType();
		}

		public boolean equals(Object object) {
			return object != null && getClass().equals(object.getClass());
		}

		public void set(Object value, PreparedStatement statement, int index) throws SQLException {
			if(value == null) {
				statement.setNull(index, getRawSQLType());
			} else {
				setNonNull(coerce(value), statement, index);
			}
		}

		public T get(ResultSet result, String column) throws SQLException {
			T value = getMaybeNull(result, column);
			if(result.wasNull()) {
				return null;
			}
			
			return value;
		}
		
		public Partial asPartial(final T object) {
			if(object == null) {
				throw new NullPointerException();
			}

			return new Partial() {
				public void buildStatement(StringBuilder sb) {
					sb.append("?");
				}

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
		public int getRawSQLType() {
			return Types.INTEGER;
		}

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

		public void setNonNull(Integer value, PreparedStatement statement, int index) throws SQLException {
			statement.setInt(index, value.intValue());
		}

		public Integer getMaybeNull(ResultSet result, String column) throws SQLException {
			return Integer.valueOf(result.getInt(column));
		}
	}


	public static class DoubleType extends AbstractType<Double> {
		public int getRawSQLType() {
			return Types.DOUBLE;
		}

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

		public void setNonNull(Double value, PreparedStatement statement, int index) throws SQLException {
			statement.setDouble(index, value.doubleValue());
		}

		public Double getMaybeNull(ResultSet result, String column) throws SQLException {
			return Double.valueOf(result.getDouble(column));
		}
	}
	
	

	public static class StringType extends AbstractType<String> {
		public int getRawSQLType() {
			return Types.VARCHAR;
		}

		public String coerceNonNull(Object object) throws CoercionException {
			if(object instanceof String) {
				return (String)object;
			}
			
			throw new CoercionException();
		}

		public void setNonNull(String value, PreparedStatement statement, int index) throws SQLException {
			statement.setString(index, value);
		}

		public String getMaybeNull(ResultSet result, String column) throws SQLException {
			return result.getString(column);
		}
	}

}
