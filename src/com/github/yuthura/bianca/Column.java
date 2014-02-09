package com.github.yuthura.bianca;

import java.sql.*;

public interface Column<T> extends Selectable {
	public Table getTable();

	public String getName();
	
	public Type<T> getType();

	public Column<T> as(String alias);
	

	default
	public void buildStatement(StringBuilder sb) {
		quote(sb, getTable().getName());
		sb.append(".");
		quote(sb, getName());
	}
	
	default
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		return 0;
	}
	
	default
	public String getSelectionName() {
		return Query.requireValidName(getTable().getName()) + "." + Query.requireValidName(getName());
	}


	default
	public void quote(StringBuilder sb, String name) {
		sb.append("`");
		sb.append(Query.requireValidName(name));
		sb.append("`");
	}
}
