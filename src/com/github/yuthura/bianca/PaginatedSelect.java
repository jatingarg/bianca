package com.github.yuthura.bianca;

import java.util.*;
import java.util.function.*;

import com.github.yuthura.bianca.functions.*;

public class PaginatedSelect extends Select {
	private int page = 1;

	private int per = 30;

	public PaginatedSelect(Select select, int page, int per) {
		super(select);
		paginate(page, per);
	}


	@Override
	public PaginatedSelect paginate(int page, int per) {
		page(page);
		per(per);
		return this;
	}


	public PaginatedSelect page(int page) {
		if(page < 1) {
			throw new IllegalArgumentException();
		}

		this.page = page;
		return this;
	}

	public PaginatedSelect per(int per) {
		if(per < 1) {
			throw new IllegalArgumentException();
		}

		this.per = per;
		return this;
	}


	@Override
	public PaginatedSelect select(Selectable... selection) {
		super.select(selection);
		return this;
	}

	@Override
	public PaginatedSelect from(Table table) {
		super.from(table);
		return this;
	}

	@Override
	public PaginatedSelect join(Table table, Condition... conditions) {
		super.join(table, conditions);
		return this;
	}

	@Override
	public PaginatedSelect innerJoin(Table table, Condition... conditions) {
		super.innerJoin(table, conditions);
		return this;
	}

	@Override
	public PaginatedSelect leftJoin(Table table, Condition... conditions) {
		super.leftJoin(table, conditions);
		return this;
	}

	@Override
	public PaginatedSelect rightJoin(Table table, Condition... conditions) {
		super.rightJoin(table, conditions);
		return this;
	}

	@Override
	public PaginatedSelect where(Condition... conditions) {
		super.where(conditions);
		return this;
	}

	@Override
	public PaginatedSelect groupBy(Partial... groupings) {
		super.groupBy(groupings);
		return this;
	}

	@Override
	public PaginatedSelect orderBy(Partial partial, OrderBy.Direction direction) {
		super.orderBy(partial, direction);
		return this;
	}

	@Override
	public PaginatedSelect orderBy(Partial partial) {
		super.orderBy(partial);
		return this;
	}

	@Override
	public PaginatedSelect ascendBy(Partial partial) {
		super.ascendBy(partial);
		return this;
	}

	@Override
	public PaginatedSelect descendBy(Partial partial) {
		super.descendBy(partial);
		return this;
	}

	@Override
	public PaginatedSelect limit(int limit) {
		throw new UnsupportedOperationException("cannot manually set limit on paginated select");
	}

	@Override
	public PaginatedSelect offset(int offset) {
		throw new UnsupportedOperationException("cannot manually set offset on paginated select");
	}

	@Override
	public PaginatedSelect limit(int offset, int limit) {
		throw new UnsupportedOperationException("cannot manually set limit or offset on paginated select");
	}




	@Override
	public void forEach(ConnectionFactory connectionFactory, Consumer<Result> consumer) {
		runQuery(connectionFactory, results -> {
			while(results.next()) {
				consumer.accept(new Result(results));
			}
		});
	}

	@Override
	public <T, C extends Collection<T>> C mapEach(ConnectionFactory connectionFactory, C collection, Function<Result, T> function) {
		forEach(connectionFactory, result -> {
			collection.add(function.apply(result));
		});

		return collection;
	}

	@Override
	public <T, C extends Collection<T>> C mapEach(ConnectionFactory connectionFactory, C collection, Column<T> column) {
		return mapEach(connectionFactory, collection, result -> result.get(column));
	}

	@Override
	public <T, C extends Collection<T>> C mapEach(ConnectionFactory connectionFactory, C collection, Selectable selectable, Type<T> type) {
		return mapEach(connectionFactory, collection, result -> result.get(selectable, type));
	}

	@Override
	public void forFirst(ConnectionFactory connectionFactory, Consumer<Result> consumer) {
		throw new UnsupportedOperationException("cannot only get first record on paginated select");
	}

	@Override
	public <T> T mapFirst(ConnectionFactory connectionFactory, Function<Result, T> function) {
		throw new UnsupportedOperationException("cannot only get first record on paginated select");
	}

	@Override
	public <T> T mapFirst(ConnectionFactory connectionFactory, Column<T> column) {
		throw new UnsupportedOperationException("cannot only get first record on paginated select");
	}

	@Override
	public <T> T mapFirst(ConnectionFactory connectionFactory, Selectable selectable, Type<T> type) {
		throw new UnsupportedOperationException("cannot only get first record on paginated select");
	}

	@Override
	public boolean isEmpty(ConnectionFactory connectionFactory) {
		throw new UnsupportedOperationException("cannot test for emptiness on paginated select");
	}




	private int limitCache = -1;
	private int offsetCache = -1;
	private int totalCountCache = -1;
	private int pageCountCache = -1;

	public int totalCount() {
		if(totalCountCache < 0) {
			throw new IllegalStateException();
		}

		return totalCountCache;
	}

	public int pageCount() {
		if(pageCountCache < 0) {
			throw new IllegalStateException();
		}

		return pageCountCache;
	}


	@Override
	protected void runQuery(ConnectionFactory connectionFactory, ResultSetHandler consumer) {
		limitCache = per;
		offsetCache = per * (page - 1);

		List<Selectable> _selection = selection();
		Alias count = new Count(Partial.ALL).as("total_count");
		select(count);

		super.runQuery(connectionFactory, resultSet -> {
			if(resultSet.next()) {
				totalCountCache = new Result(resultSet).get(count, Type.INTEGER);
			} else {
				totalCountCache = 0;
			}
		});

		int remainder = totalCountCache % per;
		pageCountCache = (totalCountCache - remainder) / per;
		if(remainder > 0) {
			pageCountCache++;
		}

		select(_selection.toArray(new Selectable[_selection.size()]));

		if(totalCountCache > 0 && totalCountCache >= offsetCache) {
			super.limit(offsetCache, limitCache);

			super.runQuery(connectionFactory, consumer);
		}
	}


}
