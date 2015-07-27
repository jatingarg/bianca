package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;
import java.util.function.*;

/**
 * Constructs a SELECT query from chained method calls. When done building the statement,
 * call one of <code>forEach</code>, <code>forFirst</code>, <code>mapEach</code> or <code>mapFirst</code>
 * and pass in a {@link ConnectionFactory} to handle or map the result set.
 *
 * <pre>
 * {@code
 * Select sql = new Select();
 * sql.select( Products.id, Products.name, Products.price );
 * sql.from( Products.table );
 * sql.where( gt(Products.price, 10.0) );
 * sql.ascendBy( Products.price );
 * sql.limit( 30 * (page - 1), 30 ); // 30 per page pagination
 *
 * // you will generally want to perform this somewhere central to your application
 * ConnectionFactory cf = new JDBCConnectionFactory("database", "username", "password");
 *
 * List<Product> products = sql.mapEach(cf, new ArrayList<Product>(30), result -> {
 * 	Product p = new Product();
 * 	p.setId( r.get(Products.id) ); // automatic type conversion
 * 	p.setName( r.get(Products.name) );
 * 	p.setPrice( r.get(Products.price) );
 *
 * 	return p;
 * });
 * }
 * </pre>
 *
 * @author Yuthura
 */
public class Select implements Query, Partial {
	private final List<Selectable> selection;

	private Table from;

	private final List<Join> joins;

	private Where where;

	private GroupBy groupBy;

	private Having having;

	private OrderBy orderBy;

	private Limit limit;

	private boolean distinct = false;


	/**
	 * Constructs a new SELECT query without configuration; unusable until further configured
	 * with at the very least a FROM clause.
	 */
	public Select() {
		super();

		selection = new ArrayList<>();
		joins = new ArrayList<>();
	}

	public Select(Select select) {
		this();

		selection.addAll(select.selection);
		from = select.from;
		joins.addAll(select.joins);
		where = select.where;
		groupBy = select.groupBy;
		having = select.having;
		orderBy = select.orderBy;
		limit = select.limit;
	}


	public PaginatedSelect paginate(int page, int per) {
		return new PaginatedSelect(this, page, per);
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



	protected List<Selectable> selection() {
		return new ArrayList<Selectable>(selection);
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


	public Select distinct(Selectable... selection) {
		distinct(true);
		return select(selection);
	}

	public Select distinct() {
		return distinct(true);
	}

	public Select distinct(boolean distinct) {
		this.distinct = distinct;
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
	 * Alias for {@link #innerJoin(Table, Condition...)}; adds an INNER JOIN clause to the statement.
	 *
	 * @param table the table to join on
	 * @param conditions the conditions to join on
	 * @return this
	 */
	public Select join(Table table, Condition... conditions) {
		return innerJoin(table, conditions);
	}

	/**
	 * Adds an INNER JOIN clause to the statement, based on the given {@link Table} and
	 * the given {@link Condition}s. All {@link Join} clauses are concatenated to the final
	 * statement in the order they are added to <code>this</code> select.
	 *
	 * @param table the table to join on
	 * @param conditions the conditions to join on
	 * @return this
	 */
	public Select innerJoin(Table table, Condition... conditions) {
		joins.add(new Join.Inner(table, conditions));
		return this;
	}

	/**
	 * Adds a LEFT OUTER JOIN clause to the statement, based on the given {@link Table} and
	 * the given {@link Condition}s. All {@link Join} clauses are concatenated to the final
	 * statement in the order they are added to <code>this</code> select.
	 *
	 * @param table the tabel to join on
	 * @param conditions the conditions to join on
	 * @return this
	 */
	public Select leftJoin(Table table, Condition... conditions) {
		joins.add(new Join.Left(table, conditions));
		return this;
	}

	/**
	 * Adds a RIGHT OUTER JOIN clause to the statement, based on the given {@link Table} and
	 * the given {@link Condition}s. All {@link Join} clauses are concatenated to the final
	 * statement in the order they are added to <code>this</code> select.
	 *
	 * @param table the table to join on
	 * @param conditions the conditions to join on
	 * @return this
	 */
	public Select rightJoin(Table table, Condition... conditions) {
		joins.add(new Join.Right(table, conditions));
		return this;
	}


	/**
	 * Adds a WHERE clause with the given {@link Condition}s to this statement if called for the first time
	 * on <code>this</code> select. If called subsequent times, adds the given <code>Condition</code>s to
	 * the existing {@link Where}.
	 *
	 * @param conditions the conditions for the WHERE clause
	 * @return this
	 */
	public Select where(Condition... conditions) {
		if(where == null) {
			where = new Where();
		}

		where.addConditions(conditions);
		return this;
	}


	/**
	 * Adds a GROUP BY clause with the given {@link Partial}s to this statement if called for the first time
	 * on <code>this</code> select. If called subsequent times, adds the given groupings to the
	 * existing {@link GroupBy}. All given groupings are concatenated to the final statement
	 * in the order they are added to <code>this</code> select.
	 *
	 * @param groupings the partials to group on
	 * @return this
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


	public Select having(Condition... conditions) {
		if(having == null) {
			having = new Having();
		}

		having.addConditions(conditions);
		return this;
	}


	/**
	 * Adds an ORDER BY clause with the given {@link Partial} in the given {@link OrderBy.Direction} if called
	 * for the first time. If called subsequent times, adds the given ordering to the existing {@link OrderBy}.
	 * All order by calls are concatenated to the final statement in the order they are
	 * added to <code>this</code> select.
	 *
	 * @param partial the partial to order by
	 * @param direction the direction to order in
	 * @return this
	 */
	public Select orderBy(Partial partial, OrderBy.Direction direction) {
		if(orderBy == null) {
			orderBy = new OrderBy();
		}

		orderBy.addOrdering(partial, direction);
		return this;
	}

	/**
	 * Alias for <code>orderBy(partial, null)</code>, a direction-less ordering (will
	 * generally be forced to <code>ASC</code> by the database driver)
	 *
	 * @param partial the partial to order by
	 * @return this
	 */
	public Select orderBy(Partial partial) {
		return orderBy(partial, null);
	}

	/**
	 * Alias for <code>orderBy(partial, OrderBy.Direction.ASCENDING)</code>.
	 *
	 * @param partial the partial to order by
	 * @return this
	 */
	public Select ascendBy(Partial partial) {
		return orderBy(partial, OrderBy.Direction.ASCENDING);
	}

	/**
	 * Alias for <code>orderBy(partial, OrderBy.Direction.DESCENDING)</code>.
	 *
	 * @param partial the partial to order by
	 * @return this
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
	 * Runs the SELECT statement as built so far and iterates over the resulting <code>ResultSet</code>, yielding
	 * the <code>ResultSet</code> wrapped in a {@link Result} to the <code>consumer</code> each iteration.
	 * Automatically closes the result set, the statement and the connection after all iterations are complete.
	 * If any <code>SQLException</code> occurs during iteration, it is caught and rethrown wrapped in
	 * a {@link QueryException}.
	 *
	 * @param connectionFactory the connection factory that will provide the connection to run this query on
	 * @param consumer the consumer that will handle the result
	 */
	public void forEach(ConnectionFactory connectionFactory, Consumer<Result> consumer) {
		runQuery(connectionFactory, results -> {
			Result result = new Result(results);
			while(results.next()) {
				consumer.accept(result);
			}
		});
	}


	/**
	 * Runs the SELECT statement as built so far and iterates over the resulting <code>ResultSet</code>, yielding
	 * the <code>ResultSet</code> wrapped in a {@link Result} to the <code>function</code> each iteration.
	 * Automatically collects the return values from the <code>function</code> and places them in the given
	 * <code>collection</code>. Automatically closes the result set, the statement and the connection after all
	 * iterations are complete. If any <code>SQLException</code> occurs during iteration, it is caught and rethrown
	 * wrapped in a {@link QueryException}.
	 *
	 * This method is especially handy to fetch records from the database and then map them to a collection of a
	 * business logic data type, like such:
	 *
	 * <pre>
	 * {@code
	 * List<Product> products = select(Products.table).limit(30)
	 * 	.mapEach(connection, new ArrayList<>(30), result -> Products.fromResult(result));
	 * }
	 * </pre>
	 *
	 * @param connectionFactory the connection factory that will provide the connection to run this query on
	 * @param collection the collection that will hold the return values of the function
	 * @param function the function that will handle the result
	 * @return the given collection
	 */
	public <T, C extends Collection<T>> C mapEach(ConnectionFactory connectionFactory, C collection, Function<Result, T> function) {
		forEach(connectionFactory, result -> {
			collection.add(function.apply(result));
		});

		return collection;
	}


	/**
	 * Same as {@link #mapEach(ConnectionFactory, Collection, Function)}, but a simple way to quickly map to a selected
	 * column, similar to Rails' pluck method.
	 *
	 * @param connectionFactory
	 * @param collection
	 * @param column
	 * @return
	 */
	public <T, C extends Collection<T>> C mapEach(ConnectionFactory connectionFactory, C collection, Column<T> column) {
		return mapEach(connectionFactory, collection, result -> result.get(column));
	}

	public <T, C extends Collection<T>> C mapEach(ConnectionFactory connectionFactory, C collection, Selectable selectable, Type<T> type) {
		return mapEach(connectionFactory, collection, result -> result.get(selectable, type));
	}


	/**
	 * Exactly the same as {@link #forEach(ConnectionFactory, Consumer)}, but forces a call to <code>limit(1)</code> and
	 * instead of iterating over all results, forcefully only yields the first result.
	 *
	 * @param connectionFactory the connection factory that will provide the connection to run this query on
	 * @param consumer the consumer that will handle the result
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
	 * Exactly the same as {@link #mapEach(ConnectionFactory, Collection, Function)}, but uses
	 * {@link #forFirst(ConnectionFactory, Consumer)} instead of {@link #forEach(ConnectionFactory, Consumer)},
	 * and returns the first result found, instead of a collection.
	 *
	 * @param connectionFactory the connection factory that will provide the connection to run this query on
	 * @param function the function that will handle the result
	 * @return the first result found, or null if no results were found
	 */
	// TODO: force limit
	public <T> T mapFirst(ConnectionFactory connectionFactory, Function<Result, T> function) {
		List<T> list = new ArrayList<>(1);

		forFirst(connectionFactory, result -> {
			list.add(function.apply(result));
		});

		return list.isEmpty() ? null : list.get(0);
	}



	public <T> T mapFirst(ConnectionFactory connectionFactory, Column<T> column) {
		return mapFirst(connectionFactory, result -> result.get(column));
	}

	public <T> T mapFirst(ConnectionFactory connectionFactory, Selectable selectable, Type<T> type) {
		return mapFirst(connectionFactory, result -> result.get(selectable, type));
	}


	public boolean isEmpty(ConnectionFactory connectionFactory) {
		List<Integer> list = new ArrayList<>(1);

		forFirst(connectionFactory, result -> {
			list.add(Integer.valueOf(1));
		});

		return list.isEmpty();
	}


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

		if(distinct) {
			sb.append("DISTINCT ");
		}

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

		if(having != null) {
			having.buildStatement(sb);
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

		if(having != null) {
			i += having.prepareStatement(statement, index + i);
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
