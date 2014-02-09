package com.github.yuthura.bianca;

import java.util.*;
import java.util.function.*;

import com.github.yuthura.bianca.impl.*;

public interface SelectQuery extends Partial {
	public List<Selectable> getSelection();

	public void setSelection(Selectable... selection);


	public Table getFrom();

	public void setFrom(Table from);


	public List<Join> getJoins();

	public void setJoins(Join... joins);

	default
	public void addJoin(Join join) {
		getJoins().add(Objects.requireNonNull(join));
	}



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


	default
	public GroupBy getGroupBy(boolean ensure) {
		GroupBy groupBy = getGroupBy();
		if(groupBy == null && ensure) {
			groupBy = new GroupBy();
			setGroupBy(groupBy);
		}

		return groupBy;
	}

	public GroupBy getGroupBy();

	public void setGroupBy(GroupBy groupBy);


	default
	public OrderBy getOrderBy(boolean ensure) {
		OrderBy orderBy = getOrderBy();
		if(orderBy == null && ensure) {
			orderBy = new OrderBy();
			setOrderBy(orderBy);
		}

		return orderBy;
	}


	public OrderBy getOrderBy();

	public void setOrderBy(OrderBy orderBy);


	default
	public Limit getLimit(boolean ensure) {
		Limit limit = getLimit();
		if(limit == null && ensure) {
			limit = new Limit(-1, -1);
			setLimit(limit);
		}

		return limit;
	}

	public Limit getLimit();

	public void setLimit(Limit limit);


	public void forEach(Consumer<Result> consumer);

	public <T> Collection<T> mapEach(Collection<T> collection, Function<Result, T> function);

	public void forFirst(Consumer<Result> consumer);

	public <T> T mapFirst(Function<Result, T> function);


	default
	public void forEach(ConnectionFactory connectionFactory, Consumer<Result> consumer) {
		with(connectionFactory).forEach(consumer);
	}

	default
	public <T> Collection<T> mapEach(ConnectionFactory connectionFactory, Collection<T> collection, Function<Result, T> function) {
		return with(connectionFactory).mapEach(collection, function);
	}

	default
	public void forFirst(ConnectionFactory connectionFactory, Consumer<Result> consumer) {
		with(connectionFactory).forFirst(consumer);
	}

	default
	public <T> T mapFirst(ConnectionFactory connectionFactory, Function<Result, T> function) {
		return with(connectionFactory).mapFirst(function);
	}


	default
	public SelectQuery with(ConnectionFactory connectionFactory) {
		return new ExecutableSelect(this, connectionFactory);
	}


	public static void log(Object msg) {
		System.out.println("INFO : " + String.valueOf(msg));
	}
}
