package com.github.yuthura.bianca;

public interface DeleteChain extends DeleteQuery {
	default
	public DeleteChain where(Condition... conditions) {
		getWhere(true).setConditions(conditions);
		return this;
	}
}
