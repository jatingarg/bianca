package com.github.yuthura.bianca;

import java.sql.*;
import java.time.*;

/**
 * <p>
 * Represents any part of a query that knows how to build and prepare itself. The {@code Partial} class is at the core of
 * the entire framework; all queries simply consist of various partials which are joined together.
 * </p>
 * <p>
 * This class provides two default partials, one to represent an SQL {@code NULL} value with {@link Partial#NULL}, and
 * one that represents the 'all' selector ({@code *}) in a select clause with {@link Partial#ALL}, as shorthands. It
 * also provides a way to easily wrap simple known objects into partial instances, so most of the API can receive the basic
 * classes directly (instead of having to wrap every integer into a partial manually).
 * </p>
 *
 * @author Yuthura
 */
public interface Partial {
	/**
	 * <p>
	 * Concatenates the representation of {@code this} partial to the given {@code StringBuilder} instance in order to prepare
	 * the query to be executed. Any partial must be fully capable to build itself, but it should never set any actual values
	 * (that should be done by {@link Partial#prepareStatement(PreparedStatement, int)}). For example, the implementation of
	 * a partial that represents an equality clause might concatenate something like {@code ? = ?}, or something like
	 * {@code ? IS NULL} (if it is able to determine a {@code null} value).
	 * </p>
	 *
	 * @param sb the stringbuilder instance to concat the SQL representation of {@code this} partial to
	 */
	public void buildStatement(StringBuilder sb);

	/**
	 * <p>
	 * Sets any parameters on the prepared statement as needed by {@code this} partial. The given {@code index} is the last
	 * index used when constructing the query by other partials. For the first partial in the query, this value will most
	 * likely by {@code 0}. Any parameters set by {@code this} partial must be set on an index of at least {@code index + 1}.
	 * </p>
	 * <p>
	 * The implementation must return the amount of parameters set and any parameters set must be set with an index {@code i},
	 * where {@code i > index && i <= index + (returned value)}. For example, the implementation of a partial that represents an
	 * equality clause ({@code ? = ?}) might set two parameters, where the first parameter will be set on {@code index + 1} and
	 * the second parameter will be set on {@code index + 2}. The returned value should then be {@code 2}, since two parameters
	 * have been set.
	 * </p>
	 * <p>
	 * Note that the framework does no checks on the validity of these parameter indices, but the driver used may throw some
	 * exception regarding unset parameters, or parameters set by other partials may be overwritten by {@code this} partial if
	 * an index out of the specified range is used.
	 * </p>
	 *
	 * @param statement the prepared statement to set any values for {@code this} partial on
	 * @param index the last index set whilst preparing the statement, will be {@code 0} for the first {@code partial} called
	 *
	 * @return the amount of parameters set by {@code this} partial during the call
	 *
	 * @throws SQLException if any call to {@code statement} throws a {@code SQLException}
	 */
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException;



	/**
	 * <p>
	 * Represents an SQL {@code NULL} clause.
	 * </p>
	 */
	public final Partial NULL = new Partial() {
		@Override
		public void buildStatement(StringBuilder sb) {
			sb.append("NULL");
		}

		@Override
		public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
			return 0;
		}
	};

	/**
	 * <p>
	 * Represents the SQL 'all' selection clause ({@code *}).
	 * </p>
	 */
	public final Partial ALL = new Partial() {
		@Override
		public void buildStatement(StringBuilder sb) {
			sb.append("*");
		}

		@Override
		public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
			return 0;
		}
	};



	/**
	 * <p>
	 * Wraps the given object into a {@code Partial} and returns it, or throws a {@link CoercionException} if it does not know
	 * how to wrap the object.
	 * </p>
	 * <p>
	 * This method knows how to wrap the following types (all are tested using {@code instanceof}, except for {@code null} obviously):
	 * </p>
	 * <ul>
	 *   <li>{@code null} (will return {@link Partial#NULL})</li>
	 *   <li>{@code java.lang.Integer}</li>
	 *   <li>{@code java.lang.Long}</li>
	 *   <li>{@code java.lang.Float}</li>
	 *   <li>{@code java.lang.Double}</li>
	 *   <li>{@code java.lang.Boolean}</li>
	 *   <li>{@code java.lang.String}</li>
	 *   <li>{@code java.time.LocalDate}</li>
	 *   <li>{@code java.time.LocalTime}</li>
	 *   <li>{@code java.time.LocalDateTime}</li>
	 *   <li>{@link Partial} (will simply cast {@code object} and return it)</li>
	 *   <li>{@link Partiable} (will simply call {@link Partiable#toPartial()} and return the received value)</li>
	 * </ul>
	 * <p>
	 * If {@code object} is of any other type, a {@link CoercionException} is thrown.
	 * </p>
	 *
	 * @param object the object to be wrapped in a {@code Partial} instance.
	 *
	 * @throws CoercionException if the given {@code object} is not of a wrappable type
	 *
	 * @return a {@code Partial} implementation that knows how to represent the {@code object}'s type.
	 */
	public static Partial wrap(Object object) {
		if(object == null) {
			return Partial.NULL;

		} else if(object instanceof Partial) {
			return (Partial)object;

		} else if(object instanceof Partiable) {
			return ((Partiable)object).toPartial();

		} else if(object instanceof Integer) {
			return Type.INTEGER.asPartial((Integer)object);

		} else if(object instanceof Long) {
			return Type.LONG.asPartial((Long)object);

		} else if(object instanceof Float) {
			return Type.FLOAT.asPartial((Float)object);

		} else if(object instanceof Double) {
			return Type.DOUBLE.asPartial((Double)object);

		} else if(object instanceof Boolean) {
			return Type.BOOLEAN.asPartial((Boolean)object);

		} else if(object instanceof String) {
			return Type.STRING.asPartial((String)object);

		} else if(object instanceof LocalDate) {
			return Type.DATE.asPartial((LocalDate)object);

		} else if(object instanceof LocalTime) {
			return Type.TIME.asPartial((LocalTime)object);

		} else if(object instanceof LocalDateTime) {
			return Type.DATETIME.asPartial((LocalDateTime)object);

		} else {
			throw new CoercionException(object.getClass().getName());
		}
	}
}
