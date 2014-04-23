package com.github.yuthura.bianca;

import com.github.yuthura.bianca.impl.*;

/**
 * UPDATE table SET column=value, column=value WHERE conditions ORDER BY column LIMIT 1
 */
public interface UpdateQuery extends Query {
	public void addSet(Column<?> column, Object value);

	default
	public Where getWhere(boolean ensure) {
		Where where = getWhere();
		if(where == null && ensure) {
			where = new Where();
			setWhere(where);
		}

		return where;
	}

	public Where getWhere();

	public void setWhere(Where where);

	public int run();

	default
	public UpdateQuery with(ConnectionFactory connectionFactory) {
		return new ExecutableUpdate(this, connectionFactory);
	}

	default
	public int run(ConnectionFactory connectionFactory) {
		return with(connectionFactory).run();
	}
}
