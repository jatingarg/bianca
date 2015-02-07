package com.github.yuthura.bianca;

import java.sql.*;

public interface ConnectionFactory {
	public Connection getConnection() throws SQLException;

	default
	public boolean transaction(Transfer transfer) throws Exception {
		try(Connection connection = getConnection()) {
			try {
				connection.setAutoCommit(false);

				Transaction transaction = new Transaction(connection);

				transfer.run(transaction);

				connection.commit();

				return true;

			} catch(RollbackException x) {
				connection.rollback();
				return false;

			} catch(Exception x) {
				connection.rollback();
				throw x;
			}
		} catch(SQLException x) {
			throw new QueryException(x);
		}
	}

	public interface Transfer {
		public void run(Transaction transaction) throws Exception;
	}
}
