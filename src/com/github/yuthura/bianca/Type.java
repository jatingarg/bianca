package com.github.yuthura.bianca;

import java.sql.*;
import java.time.*;

import com.github.yuthura.bianca.types.*;

/**
 * <p>
 * Represents a datatype supported by the framework. The most basic datatypes are provided as constants for easy usage, but
 * you can create any implementation you need.
 * </p>
 *
 * @author Yuthura
 *
 * @param <T> the Java-type of the datatype (not the SQL-type)
 */
public interface Type<T> {
	/**
	 * Convenient access to an instance representing {@code java.lang.String}.
	 */
	public final static Type<String>          STRING = new StringType();


	/**
	 * Convenient access to an instance representing {@code java.lang.Boolean}.
	 */
	public final static Type<Boolean>        BOOLEAN = new BooleanType();


	/**
	 * Convenient access to an instance representing {@code java.lang.Integer}.
	 */
	public final static Type<Integer>        INTEGER = new IntegerType();


	/**
	 * Convenient access to an instance representing {@code java.lang.Long}.
	 */
	public final static Type<Long>              LONG = new LongType();


	/**
	 * Convenient access to an instance representing {@code java.lang.Float}.
	 */
	public final static Type<Float>            FLOAT = new FloatType();


	/**
	 * Convenient access to an instance representing {@code java.lang.Double}.
	 */
	public final static Type<Double>          DOUBLE = new DoubleType();


	/**
	 * Convenient access to an instance representing {@code java.time.LocalDate} (for {@code java.sql.Date} types).
	 */
	public final static Type<LocalDate>         DATE = new DateType();


	/**
	 * Convenient access to an instance representing {@code java.time.LocalTime} (for {@code java.sql.Time} types).
	 */
	public final static Type<LocalTime>         TIME = new TimeType();


	/**
	 * Convenient access to an instance representing {@code java.time.LocalDateTime} (for {@code java.sql.Timestamp} types).
	 */
	public final static Type<LocalDateTime> DATETIME = new DateTimeType();


	/**
	 * <p>
	 * Returns the proper SQL datatype as one of the constants from {@code java.sql.Types}.
	 * </p>
	 *
	 * @return the proper constant from {@code java.sql.Types}, representing the SQL datatype
	 */
	public int getRawSQLType();


	/**
	 * <p>
	 * Coerces any given object to the actual Java-type this implementation represents. Any and all possible coercion should
	 * be performed. For example, numeric types should be able to successfully parse a given string. If coercion is impossible
	 * for any given reason, a {@link CoercionException} must be thrown. This method may <strong>not</strong> throw any other
	 * exceptions if coercion fails (for example, numeric types may not throw {@code NumberFormatException}s when parsing
	 * string arguments), not even a {@code ClassCastException}. Implementations must handle a {@code null} value gracefully.
	 * The expected behavior is to return {@code null}, but if this type has a more sensible representation when given a
	 * {@code null} value, that may be returned instead too (for example, a non-null numeric type may sensibly return {@code 0}
	 * when given a {@code null} value).
	 * </p>
	 *
	 * @param object the object of any type to attempt to coerce to this type
	 * @return the coerced value, possibly {@code null}
	 * @throws CoercionException if the given {@code object} cannot be gracefully coerced into this type
	 */
	public T coerce(Object object) throws CoercionException;


	/**
	 * <p>
	 * Sets the given {@code value} at the specified {@code index} on the given {@code statement}. The value may be of any type
	 * and this instance is responsible for coercing it to the proper value before setting it (using {@link #coerce(Object)}).
	 * </p>
	 * <p>
	 * This instance <strong>must</strong> set the value on the specified index and may not set it on any other index. It may
	 * also not set any other value, nor any value other than the one given. Because the index is maintained externally from
	 * this instance, if the value is {@code null} after coercion, it must still be set properly on the statement object.
	 * </p>
	 *
	 * @param value the value to coerce and to set on the statement
	 * @param statement the prepared statement on which to set the specified value
	 * @param index the index at which to set the specified value
	 * @throws CoercionException if {@code value} cannot be gracefully coerced
	 * @throws SQLException if any call to {@code statement} throws an {@code SQLException}
	 */
	public void set(Object value, PreparedStatement statement, int index) throws SQLException;


	/**
	 * <p>
	 * Gets the value from the specified column (as specified in the SQL {@code SELECT} clause) and returns it, properly and
	 * gracefully converted to this appropriate type. If the value received from the resultset was {@code null}, this method
	 * <strong>must</strong> also return {@code null}. Therefore, implementations should check for this with
	 * {@code ResultSet#wasNull()} before attempting coercion.
	 * </p>
	 *
	 * @param rs the resultset to get the value from under the specified label
	 * @param column the label under which to get the value from the resultset
	 * @return the gracefully converted value from the resultset, or {@code null}
	 * @throws SQLException if any call to {@code rs} throws an {@code SQLException}
	 */
	public T get(ResultSet rs, String column) throws SQLException;


	/**
	 * <p>
	 * Createse a {@link Partial} instance that knows how to properly represent the given {@code object} during query
	 * construction. If the specified {@code object} was {@code null}, this may <strong>not</strong> return {@code null}, but
	 * must return a proper {@link Partial} implementation that is able to represent a {@code null} of this type, usually by
	 * returning {@link Partial#NULL}.
	 * </p>
	 *
	 * @param object the object to wrap in a partial
	 * @return a partial representing the given object
	 */
	public Partial asPartial(T object);


	/**
	 * <p>
	 * Returns the {@code Class} object that this type represents.
	 * </p>
	 *
	 * @return the Java-type class that this type represents
	 */
	public Class<T> getTypeClass();
}
