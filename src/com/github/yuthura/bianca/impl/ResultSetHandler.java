package com.github.yuthura.bianca.impl;

import java.sql.*;

public interface ResultSetHandler {
	public void handle(ResultSet resultSet) throws SQLException;
}
