package com.github.yuthura.bianca;

import java.sql.*;

public interface Selectable extends Partial {
	default
	public void buildSelectionStatement(StringBuilder sb) {
		buildStatement(sb);
	}
	
	default
	public int prepareSelectionStatement(PreparedStatement statement, int index) throws SQLException {
		return prepareStatement(statement, index);
	}


	public String getSelectionName();
}
