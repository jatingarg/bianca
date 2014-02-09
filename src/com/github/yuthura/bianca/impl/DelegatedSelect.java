package com.github.yuthura.bianca.impl;

import java.sql.*;
import java.util.*;
import java.util.function.*;

import com.github.yuthura.bianca.*;

public class DelegatedSelect implements SelectQuery {
	private final SelectQuery target;

	public DelegatedSelect(SelectQuery target) {
		this.target = Objects.requireNonNull(target);
	}

	@Override
	public void setSelection(Selectable... selection) {
		target.setSelection(selection);
	}

	@Override
	public void setFrom(Table from) {
		target.setFrom(from);
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		target.buildStatement(sb);
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		return target.prepareStatement(statement, index);
	}

	@Override
	public void forEach(ConnectionFactory connectionFactory, Consumer<Result> consumer) {
		target.forEach(connectionFactory, consumer);
	}

	@Override
	public SelectQuery with(ConnectionFactory connectionFactory) {
		return target.with(connectionFactory);
	}

	@Override
	public void setJoins(Join... joins) {
		target.setJoins(joins);
	}

	@Override
	public void setWhere(Where where) {
		target.setWhere(where);
	}

	@Override
	public void setOrderBy(OrderBy orderBy) {
		target.setOrderBy(orderBy);
	}

	@Override
	public Limit getLimit() {
		return target.getLimit();
	}

	@Override
	public void setLimit(Limit limit) {
		target.setLimit(limit);
	}

	@Override
	public void forEach(Consumer<Result> consumer) {
		target.forEach(consumer);
	}

	@Override
	public <T> Collection<T> mapEach(Collection<T> collection, Function<Result, T> function) {
		return target.mapEach(collection, function);
	}

	@Override
	public void forFirst(Consumer<Result> consumer) {
		target.forFirst(consumer);
	}

	@Override
	public <T> T mapFirst(Function<Result, T> function) {
		return target.mapFirst(function);
	}

	@Override
	public List<Selectable> getSelection() {
		return target.getSelection();
	}

	@Override
	public Table getFrom() {
		return target.getFrom();
	}

	@Override
	public List<Join> getJoins() {
		return target.getJoins();
	}

	@Override
	public Where getWhere() {
		return target.getWhere();
	}

	@Override
	public OrderBy getOrderBy() {
		return target.getOrderBy();
	}

	@Override
	public GroupBy getGroupBy() {
		return target.getGroupBy();
	}

	@Override
	public void setGroupBy(GroupBy groupBy) {
		target.setGroupBy(groupBy);
	}

}
