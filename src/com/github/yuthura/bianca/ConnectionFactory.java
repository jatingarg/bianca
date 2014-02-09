package com.github.yuthura.bianca;

import java.sql.*;

public interface ConnectionFactory {
	public Connection getConnection() throws SQLException;
}
