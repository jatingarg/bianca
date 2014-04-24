package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

public class Insert implements InsertChain {
	private final Table into;

	private final List<Column<?>> columns;

	private final List<List<Partial>> values;

	private Select select;

	public Insert(Table into) {
		super();

		this.into = Objects.requireNonNull(into);
		columns = new ArrayList<>();
		values = new ArrayList<>();
	}


	@Override
	public void setColumns(Column<?>... columns) {
		this.columns.clear();
		for(Column<?> column : columns) {
			this.columns.add(Objects.requireNonNull(column));
		}
	}


	@Override
	public void addValues(Object... values) {
		if(select != null) {
			throw new IllegalStateException();
		}

		List<Partial> list = new ArrayList<>(values.length);
		for(Object value : values) {
			list.add(Partial.wrap(value));
		}

		this.values.add(list);
	}


	@Override
	public void setSelect(Select select) {
		if(!values.isEmpty()) {
			throw new IllegalStateException();
		}

		this.select = select;
	}



	@Override
	public int run() {
		throw new UnsupportedOperationException();
	}




	@Override
	public void buildStatement(StringBuilder sb) {
		if(columns.isEmpty() || (select == null && values.isEmpty())) {
			throw new IllegalStateException();
		}

		sb.append("INSERT INTO ");
		into.buildStatement(sb);

		sb.append("(");
		for(Iterator<Column<?>> i = columns.iterator(); i.hasNext(); ) {
			i.next().buildStatement(sb);
			if(i.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append(") ");

		if(select != null) {
			select.buildStatement(sb);
		} else {
			sb.append(" VALUES ");
			for(Iterator<List<Partial>> v = values.iterator(); v.hasNext(); ) {
				sb.append("(");
				for(Iterator<Partial> i = v.next().iterator(); i.hasNext(); ) {
					i.next().buildStatement(sb);
					if(i.hasNext()) {
						sb.append(", ");
					}
				}
				sb.append(")");
				if(v.hasNext()) {
					sb.append(", ");
				}
			}
		}
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		int i = 0;

		i += into.prepareStatement(statement, index);

		for(Column<?> column : columns) {
			i += column.prepareStatement(statement, index + i);
		}

		if(select != null) {
			i += select.prepareStatement(statement, index + i);
		} else {
			for(List<Partial> l : values) {
				for(Partial p : l) {
					i += p.prepareStatement(statement, index + i);
				}
			}
		}

		return i;
	}
}
