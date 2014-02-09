package com.github.yuthura.bianca;

import com.github.yuthura.bianca.impl.*;

/**
 * INSERT INTO {table} (column) VALUES (value), (value)
 *	INSERT INTO table SET column=value, column=value
 * INSERT INTO table1 (col_name) SELECT * FROM table2
 */
public interface InsertQuery extends Query {
	public void setColumns(Column<?>... columns);

	public void addValues(Object... values);

	public void setSelect(SelectQuery select);


	public int run();


	default
	public InsertQuery with(ConnectionFactory connectionFactory) {
		return new ExecutableInsert(this, connectionFactory);
	}


	default
	public int run(ConnectionFactory connectionFactory) {
		return with(connectionFactory).run();
	}
}
