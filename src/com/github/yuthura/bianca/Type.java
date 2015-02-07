package com.github.yuthura.bianca;

import java.sql.*;
import java.time.*;

import com.github.yuthura.bianca.types.*;

public interface Type<T> {
	public int getRawSQLType();

	public T coerce(Object object) throws CoercionException;

	public void set(Object value, PreparedStatement statement, int index) throws SQLException;

	public T get(ResultSet rs, String column) throws SQLException;

	public Partial asPartial(T object);

	public Class<T> getTypeClass();


	public final static Type<String>          STRING = new StringType();
	public final static Type<Boolean>        BOOLEAN = new BooleanType();
	public final static Type<Integer>        INTEGER = new IntegerType();
	public final static Type<Long>              LONG = new LongType();
	public final static Type<Float>            FLOAT = new FloatType();
	public final static Type<Double>          DOUBLE = new DoubleType();
	public final static Type<LocalDate>         DATE = new DateType();
	public final static Type<LocalTime>         TIME = new TimeType();
	public final static Type<LocalDateTime> DATETIME = new DateTimeType();
}
