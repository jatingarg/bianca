package com.github.yuthura.bianca.types;

import java.sql.*;

import com.github.yuthura.bianca.*;

public abstract class AbstractType<T> implements Type<T> {
	private final Class<T> typeClass;

	public AbstractType(Class<T> typeClass) {
		this.typeClass = typeClass;
	}


	@Override
	public Class<T> getTypeClass() {
		return typeClass;
	}

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

