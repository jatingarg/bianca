package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;
import java.util.function.*;

public class Select implements Query, Partial {
	/**
	 * Holds the list of {@link Selectable} elements for the SELECT clause.
	 */
	private final List<Selectable> selection;

	/**
	 * Holds the {@link Table} for the FROM clause.
	 */
	private Table from;

	/**
	 * Holds the list of {@link Join} elements for any JOIN clauses.
	 */
	private final List<Join> joins;

	/**
	 * Holds the {@link Where} for the WHERE clause.
	 */
	private Where where;

	/**
	 * Holds the {@link GroupBy} for the GROUP BY clause.
	 */
	private GroupBy groupBy;

	/**
	 * Holds the {@link OrderBy} for the ORDER BY clause.
	 */
	private OrderBy orderBy;

	/**
	 * Holds the {@link Limit} for the LIMIT clause.
	 */
	private Limit limit;


	/**
	 * Constructs a new SELECT query without configuration; unusable until further configured
	 * with at the very least a FROM clause.
	 */
	public Select() {
		super();

		selection = new ArrayList<>();
		joins = new ArrayList<>();
	}

	/**
	 * Constructs a new SELECT query with the given {@link Selectable} selection.
	 *
	 * @param selection the selection for the SELECT clause
	 */
	public Select(Selectable... selection) {
		this();

		select(selection);
	}



	/**
	 * Sets the selection for the SELECT clause and returns <code>this</code> select,
	 * removes any existing selection configuration. Throws a <code>NullPointerException</code>
	 * if any of the {@link Selectable}s are <code>null</code>.
	 *
	 * @param selection the selection for the SELECT clause
	 * @return this
	 */
	public Select select(Selectable... selection) {
		this.selection.clear();

		for(Selectable selectable : Objects.requireNonNull(selection)) {
			this.selection.add(Objects.requireNonNull(selectable));
		}

		return this;
	}



	/**
	 * Sets the table for the FROM clause and returns <code>this</code> select. Throws a
	 * <code>NullPointerException</code> if <code>table</code> is <code>null</code>.
	 *
	 * @param table the table for the FROM clause
	 * @return this
	 */
	public Select from(Table table) {
		from = Objects.requireNonNull(table);
		return this;
	}



	/**
	 * TODO
	 *
	 * @param table
	 * @param conditions
	 * @return
	 */
	public Select join(Table table, Condition... conditions) {
		return innerJoin(table, conditions);
	}

	/**
	 * TODO
	 *
	 * @param table
	 * @param conditions
	 * @return
	 */
	public Select innerJoin(Table table, Condition... conditions) {
		joins.add(new Join.Inner(table, conditions));
		return this;
	}

	/**
	 * TODO
	 *
	 * @param table
	 * @param conditions
	 * @return
	 */
	public Select leftJoin(Table table, Condition... conditions) {
		joins.add(new Join.Left(table, conditions));
		return this;
	}

	/**
	 * TODO
	 *
	 * @param table
	 * @param conditions
	 * @return
	 */
	public Select rightJoin(Table table, Condition... conditions) {
		joins.add(new Join.Right(table, conditions));
		return this;
	}


	/**
	 * TODO
	 *
	 * @param conditions
	 * @return
	 */
	public Select where(Condition... conditions) {
		if(where == null) {
			where = new Where();
		}

		where.addConditions(conditions);
		return this;
	}


	/**
	 * TODO
	 *
	 * @param groupings
	 * @return
	 */
	public Select groupBy(Partial... groupings) {
		if(groupBy == null) {
			groupBy = new GroupBy();
		}

		for(Partial grouping : groupings) {
			groupBy.addGrouping(grouping);
		}

		return this;
	}

	/**
	 * TODO
	 *
	 * @param partial
	 * @param direction
	 * @return
	 */
	public Select orderBy(Partial partial, OrderBy.Direction direction) {
		if(orderBy == null) {
			orderBy = new OrderBy();
		}

		orderBy.addOrdering(partial, direction);
		return this;
	}

	/**
	 * TODO
	 *
	 * @param partial
	 * @return
	 */
	public Select orderBy(Partial partial) {
		return orderBy(partial, null);
	}

	/**
	 * TODO
	 *
	 * @param partial
	 * @return
	 */
	public Select ascendBy(Partial partial) {
		return orderBy(partial, OrderBy.Direction.ASCENDING);
	}

	/**
	 * TODO
	 *
	 * @param partial
	 * @return
	 */
	public Select descendBy(Partial partial) {
		return orderBy(partial, OrderBy.Direction.DESCENDING);
	}



	/**
	 * TODO
	 *
	 * @param limit
	 * @return
	 */
	public Select limit(int limit) {
		return _limit(Integer.MIN_VALUE, limit);
	}

	/**
	 * TODO
	 *
	 * @param offset
	 * @return
	 */
	public Select offset(int offset) {
		return _limit(offset, Integer.MIN_VALUE);
	}

	/**
	 * TODO
	 *
	 * @param offset
	 * @param limit
	 * @return
	 */
	public Select limit(int offset, int limit) {
		return _limit(offset, limit);
	}


	/**
	 * TODO
	 *
	 * @param off
	 * @param lim
	 * @return
	 */
	private Select _limit(int off, int lim) {
		if(limit == null) {
			limit = new Limit(-1, -1);
		}

		if(off != Integer.MIN_VALUE) {
			limit.setOffset(off);
		}

		if(lim != Integer.MIN_VALUE) {
			limit.setLimit(lim);
		}

		return this;
	}







	/**
	 * TODO
	 *
	 * @param connectionFactory
	 * @param consumer
	 */
	public void forEach(ConnectionFactory connectionFactory, Consumer<Result> consumer) {
		runQuery(connectionFactory, results -> {
			while(results.next()) {
				consumer.accept(new Result(results));
			}
		});
	}


	/**
	 * TODO
	 *
	 * @param connectionFactory
	 * @param collection
	 * @param function
	 * @return
	 */
	public <T> Collection<T> mapEach(ConnectionFactory connectionFactory, Collection<T> collection, Function<Result, T> function) {
		forEach(connectionFactory, result -> {
			collection.add(function.apply(result));
		});

		return collection;
	}

	/**
	 * TODO
	 *
	 * @param connectionFactory
	 * @param consumer
	 */
	// TODO: force limit
	public void forFirst(ConnectionFactory connectionFactory, Consumer<Result> consumer) {
		limit(1);

		runQuery(connectionFactory, results -> {
			if(results.next()) {
				consumer.accept(new Result(results));
			}
		});
	}

	/**
	 * TODO
	 *
	 * @param connectionFactory
	 * @param function
	 * @return
	 */
	// TODO: force limit
	public <T> T mapFirst(ConnectionFactory connectionFactory, Function<Result, T> function) {
		List<T> list = new ArrayList<>(1);

		forFirst(connectionFactory, result -> {
			list.add(function.apply(result));
		});

		return list.isEmpty() ? null : list.get(0);
	}


	/**
	 * TODO
	 *
	 * @param consumer
	 */
	protected void runQuery(ConnectionFactory connectionFactory, ResultSetHandler consumer) {
		StringBuilder sql = new StringBuilder();
		buildStatement(sql);

		Query.log(sql);

		try(Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {
			prepareStatement(statement, 1);
			try(ResultSet results = statement.executeQuery()) {
				consumer.handle(results);
			}
		} catch(SQLException x) {
			throw new QueryException(x);
		}
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

		if(groupBy != null) {
			groupBy.buildStatement(sb);
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

		if(groupBy != null) {
			i += groupBy.prepareStatement(statement, index + i);
		}

		if(orderBy != null) {
			i += orderBy.prepareStatement(statement, index + i);
		}

		if(limit != null) {
			i += limit.prepareStatement(statement, index + i);
		}

		return i;
	}
}
