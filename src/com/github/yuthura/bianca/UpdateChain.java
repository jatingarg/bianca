package com.github.yuthura.bianca;

public interface UpdateChain extends UpdateQuery {
	default
	public UpdateChain set(Column<?> column, Object value) {
		addSet(column, value);
		return this;
	}

	default
	public UpdateChain where(Condition... conditions) {
		getWhere(true).setConditions(conditions);
		return this;
	}
}
