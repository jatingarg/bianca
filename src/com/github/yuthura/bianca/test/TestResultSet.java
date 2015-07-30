package com.github.yuthura.bianca.test;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;

public class TestResultSet implements ResultSet {
	public final Map<String, Object> params;

	public boolean wasNull = false;

	public TestResultSet() {
		params = new HashMap<>();
	}


	@Override
	public boolean next() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void close() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean wasNull() throws SQLException {
		return wasNull;
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		String x = (String)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		Boolean x = (Boolean)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		Byte x = (Byte)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		Short x = (Short)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		Integer x = (Integer)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		Long x = (Long)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		Float x = (Float)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		Double x = (Double)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		Date x = (Date)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		Time x = (Time)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		Timestamp x = (Timestamp)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		URL x = (URL)params.get(columnLabel);
		wasNull = x == null;
		return x;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	// ==============================================================================================================
	// --- untestables below ---
	// ==============================================================================================================

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public String getCursorName() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean isFirst() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean isLast() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void beforeFirst() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void afterLast() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean first() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean last() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public int getRow() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean absolute(int row) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean previous() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public int getType() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public int getConcurrency() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void insertRow() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateRow() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void deleteRow() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void refreshRow() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Statement getStatement() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new UntestableOperationException();
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new UntestableOperationException();
	}

}
