package com.github.yuthura.bianca;

import java.sql.*;

public class JDBCConnectionFactory implements ConnectionFactory {
	private final String url;

	private final String username;

	private final String password;


	public JDBCConnectionFactory(String url, String username, String password) {
		super();

		this.url = url;
		this.username = username;
		this.password = password;
	}


	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}



	public static void mysql() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			throw new UnsupportedDriverException(e);
		}
	}
}
