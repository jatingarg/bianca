package com.github.yuthura.bianca.schema;

import java.util.*;

import com.github.yuthura.bianca.*;

public class SchemaTableAlias implements Table {
	private final Map<String, Column<?>> columns;

	private final SchemaTable table;

	private final String name;

	public SchemaTableAlias(SchemaTable table, String name) {
		super();

		this.table = Objects.requireNonNull(table);
		this.name = Query.requireValidName(name);
		columns = new HashMap<>();
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		quote(sb, getName());
	}

	@Override
	public void buildSelectionStatement(StringBuilder sb) {
		quote(sb, table.getName());
		sb.append(" AS ");
		quote(sb, getName());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Table as(String alias) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Column<?> column(String name) {
		Column<?> column = columns.get(name);
		if(column == null) {
			column = table.column(name).new Wrapper(this);
			columns.put(name, column);
		}

		return column;
	}

	@Override
	public Set<Column<?>> getColumns() {
		for(SchemaColumn<?> column : table.getColumns()) {
			if(!columns.containsKey(column.getName())) {
				columns.put(column.getName(), column.new Wrapper(this));
			}
		}

		return new HashSet<>(columns.values());
	}
}