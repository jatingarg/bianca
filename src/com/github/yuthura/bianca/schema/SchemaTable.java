package com.github.yuthura.bianca.schema;

import java.util.*;
import java.util.function.*;

import com.github.yuthura.bianca.*;

public class SchemaTable implements Table {
	private final Map<String, SchemaColumn<?>> columns;

	private final String name;

	public SchemaTable(String name) {
		this(name, null);
	}

	public SchemaTable(String name, Consumer<SchemaTable> init) {
		super();

		this.name = Query.requireValidName(name);
		columns = new HashMap<>();

		if(init != null) {
			init.accept(this);
		}
	}


	@Override
	public String getName() {
		return name;
	}

	@Override
	public Set<SchemaColumn<?>> getColumns() {
		return new HashSet<>(columns.values());
	}


	public <T> SchemaColumn<T> addColumn(String name, Type<T> type) {
		if(columns.containsKey(name)) {
			throw new IllegalArgumentException();
		}

		SchemaColumn<T> column = new SchemaColumn<>(this, name, type);
		columns.put(name, column);

		return column;
	}


	@Override
	public SchemaColumn<?> column(String name) {
		SchemaColumn<?> column = columns.get(name);
		if(column == null) {
			throw new IllegalArgumentException();
		}

		return column;
	}


	@Override
	public Table as(String alias) {
		return new SchemaTableAlias(this, alias);
	}
}
