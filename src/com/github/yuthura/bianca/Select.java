package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;
import java.util.function.*;

public class Select implements SelectChain {
	private final List<Selectable> selection;

	private Table from;

	private final List<Join> joins;

	private Where where;

	private OrderBy orderBy;

	private Limit limit;

	public Select() {
		super();

		selection = new ArrayList<>();
		joins = new ArrayList<>();
	}

	public Select(Selectable... selection) {
		this();

		setSelection(selection);
	}

	@Override
	public List<Selectable> getSelection() {
		return selection;
	}

	@Override
	public void setSelection(Selectable... selection) {
		this.selection.clear();

		for(Selectable selectable : Objects.requireNonNull(selection)) {
			this.selection.add(Objects.requireNonNull(selectable));
		}
	}


	@Override
	public Table getFrom() {
		return from;
	}

	@Override
	public void setFrom(Table from) {
		this.from = Objects.requireNonNull(from);
	}


	@Override
	public List<Join> getJoins() {
		return joins;
	}

	@Override
	public void setJoins(Join... joins) {
		this.joins.clear();

		for(Join join : Objects.requireNonNull(joins)) {
			this.joins.add(Objects.requireNonNull(join));
		}
	}


	@Override
	public Where getWhere() {
		return where;
	}

	@Override
	public void setWhere(Where where) {
		this.where = where;
	}


	@Override
	public OrderBy getOrderBy() {
		return orderBy;
	}

	@Override
	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
	}


	@Override
	public Limit getLimit() {
		return limit;
	}

	@Override
	public void setLimit(Limit limit) {
		this.limit = limit;
	}


	@Override
	public void buildStatement(StringBuilder sb) {
		if(from == null) {
			throw new IllegalStateException();
		}

		sb.append("SELECT ");

		List<Selectable> selection = this.selection;
		if(selection.isEmpty()) {
			selection = new ArrayList<>(from.getColumns());
		}

		for(Iterator<Selectable> i = selection.iterator(); i.hasNext(); ) {
			i.next().buildSelectionStatement(sb);
			if(i.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append(" FROM ");
		from.buildSelectionStatement(sb);

		for(Join join : joins) {
			join.buildStatement(sb);
		}

		if(where != null) {
			where.buildStatement(sb);
		}

		if(orderBy != null) {
			orderBy.buildStatement(sb);
		}

		if(limit != null) {
			limit.buildStatement(sb);
		}
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		int i = 0;

		for(Selectable selectable : selection) {
			i += selectable.prepareSelectionStatement(statement, index + i);
		}

		i += from.prepareSelectionStatement(statement, index + i);

		for(Join join : joins) {
			i += join.prepareStatement(statement, index + i);
		}

		if(where != null) {
			i += where.prepareStatement(statement, index + i);
		}

		if(orderBy != null) {
			i += orderBy.prepareStatement(statement, index + i);
		}

		if(limit != null) {
			i += limit.prepareStatement(statement, index + i);
		}

		return i;
	}




	@Override
	public void forEach(Consumer<Result> consumer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> Collection<T> mapEach(Collection<T> collection, Function<Result, T> function) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void forFirst(Consumer<Result> consumer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T mapFirst(Function<Result, T> function) {
		throw new UnsupportedOperationException();
	}
}
