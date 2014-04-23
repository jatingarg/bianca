package com.github.yuthura.bianca;

import com.github.yuthura.bianca.impl.*;

/**
 * DELETE table WHERE conditions ORDER BY column LIMIT 1
 */
public interface DeleteQuery extends Query {
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
	public DeleteQuery with(ConnectionFactory connectionFactory) {
		return new ExecutableDelete(this, connectionFactory);
	}

	default
	public int run(ConnectionFactory connectionFactory) {
		return with(connectionFactory).run();
	}
}
