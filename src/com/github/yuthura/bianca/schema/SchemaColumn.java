package com.github.yuthura.bianca.schema;

import java.util.*;

import com.github.yuthura.bianca.*;

public class SchemaColumn<T> implements Column<T> {
	private final Table table;

	private final String name;

	private final Type<T> type;

	// TODO: maybe this constructor should be package private?
	public SchemaColumn(Table table, String name, Type<T> type) {
		super();

		this.table = Objects.requireNonNull(table);
		this.name = Query.requireValidName(name);
		this.type = Objects.requireNonNull(type);
	}

	@Override
	public Table getTable() {
		return table;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type<T> getType() {
		return type;
	}


	@Override
	public Column<T> as(String alias) {
		return new SchemaColumnAlias<T>(this, alias);
	}


	class Wrapper implements Column<T> {
		private final Table table;

		Wrapper(Table table) {
			this.table = Objects.requireNonNull(table);
		}

		@Override
		public Table getTable() {
			return table;
		}

		@Override
		public String getName() {
			return SchemaColumn.this.getName();
		}

		@Override
		public Type<T> getType() {
			return SchemaColumn.this.getType();
		}

		@Override
		public Column<T> as(String alias) {
			return new SchemaColumnAlias<T>(this, alias);
		}
	}
}
