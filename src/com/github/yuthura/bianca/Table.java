package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

public interface Table extends Partial {
	public String getName();

	public Table as(String alias);

	public Set<? extends Column<?>> getColumns();


	public Column<?> column(String name);


	@Override
	default
	public void buildStatement(StringBuilder sb) {
		sb.append("`");
		sb.append(getName());
		sb.append("`");
	}

	@Override
	default
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		return 0;
	}

	default
	public void buildSelectionStatement(StringBuilder sb) {
		buildStatement(sb);
	}

	default
	public int prepareSelectionStatement(PreparedStatement statement, int index) throws SQLException {
		return prepareStatement(statement, index);
	}


	default
	public void quote(StringBuilder sb, String name) {
		sb.append("`");
		sb.append(Query.requireValidName(name));
		sb.append("`");
	}
}
