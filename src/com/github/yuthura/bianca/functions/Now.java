package com.github.yuthura.bianca.functions;

import java.sql.*;

import com.github.yuthura.bianca.*;

public class Now implements Selectable, Aliasable {
	public Now() {
		super();
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		sb.append("NOW()");
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		return 0;
	}

	@Override
	public Alias as(String name) {
		return new Alias.Simple(this, name);
	}

	@Override
	public String getSelectionName() {
		return "NOW()";
	}
}
