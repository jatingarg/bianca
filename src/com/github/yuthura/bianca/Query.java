package com.github.yuthura.bianca;

import java.sql.*;

public interface Query {
	public void buildStatement(StringBuilder sb);

	public int prepareStatement(PreparedStatement statement, int index) throws SQLException;


	public static String requireValidName(String value) {
		if(value == null) {
			throw new NullPointerException();
		}

		if(value.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}

		if(value.indexOf('`') >= 0) {
			throw new IllegalArgumentException();
		}

		return value;
	}

	public static void log(Object msg) {
		System.out.println("INFO : " + String.valueOf(msg));
	}
}
