package com.github.yuthura.bianca;

public interface SelectChain extends SelectQuery {
	default
	public SelectChain join(Table table, Condition... conditions) {
		return innerJoin(table, conditions);
	}

	default
	public SelectChain innerJoin(Table table, Condition... conditions) {
		addJoin(new Join.Inner(table, conditions));
		return this;
	}

	default
	public SelectChain leftJoin(Table table, Condition... conditions) {
		addJoin(new Join.Left(table, conditions));
		return this;
	}

	default
	public SelectChain rightJoin(Table table, Condition... conditions) {
		addJoin(new Join.Right(table, conditions));
		return this;
	}


	default
	public SelectChain select(Selectable... selection) {
		setSelection(selection);
		return this;
	}


	default
	public SelectChain from(Table table) {
		setFrom(table);
		return this;
	}


	default
	public SelectChain where(Condition... conditions) {
		getWhere(true).addConditions(conditions);
		return this;
	}

	default
	public SelectChain orderBy(Partial partial, OrderBy.Direction direction) {
		getOrderBy(true).addOrdering(partial, direction);
		return this;
	}

	default
	public SelectChain orderBy(Partial partial) {
		return orderBy(partial, null);
	}

	default
	public SelectChain ascendBy(Partial partial) {
		return orderBy(partial, OrderBy.Direction.ASCENDING);
	}

	default
	public SelectChain descendBy(Partial partial) {
		return orderBy(partial, OrderBy.Direction.DESCENDING);
	}




	default
	public SelectChain limit(int limit) {
		getLimit(true).setLimit(limit);
		return this;
	}

	default
	public SelectChain offset(int offset) {
		getLimit(true).setOffset(offset);
		return this;
	}

	default
	public SelectChain limit(int offset, int limit) {
		Limit l = getLimit(true);
		l.setOffset(offset);
		l.setLimit(limit);
		return this;
	}
}
