package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

/**
 * INSERT INTO {table} (column) VALUES (value), (value)
 *	INSERT INTO table SET column=value, column=value
 * INSERT INTO table1 (col_name) SELECT * FROM table2
 */
public class Insert implements Query {
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

	public Insert(Table into, Column<?>... columns) {
		this(into);

		columns(columns);
	}


	public Insert columns(Column<?>... columns) {
		for(Column<?> column : columns) {
			this.columns.add(Objects.requireNonNull(column));
		}
		return this;
	}

	public Insert values(Object... values) {
		if(select != null) {
			throw new IllegalStateException();
		}

		List<Partial> list = new ArrayList<>(values.length);
		for(Object value : values) {
			list.add(Partial.wrap(value));
		}

		this.values.add(list);
		return this;
	}

	public Insert select(Select select) {
		if(!values.isEmpty()) {
			throw new IllegalStateException();
		}

		this.select = select;
		return this;
	}



	public Integer[] run(ConnectionFactory connectionFactory) {
		return run(connectionFactory, Type.INTEGER);
	}

	public <T> T[] run(ConnectionFactory connectionFactory, Type<T> generatedKeys) {
		return runQuery(connectionFactory, generatedKeys);
	}

	@SuppressWarnings("unchecked")
	protected <T> T[] runQuery(ConnectionFactory connectionFactory, Type<T> generatedKeys) {
		StringBuilder sql = new StringBuilder();
		buildStatement(sql);

		Query.log(sql);

		try(Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
			prepareStatement(statement, 1);
			int count = statement.executeUpdate();
			List<T> keys = new ArrayList<>();
			if(count > 0) {
				try(ResultSet results = statement.getGeneratedKeys()) {
					String column = results.getMetaData().getColumnLabel(1);
					while(results.next()) {
						keys.add(generatedKeys.get(results, column));
					}
				}
			}

			return keys.toArray((T[])java.lang.reflect.Array.newInstance(generatedKeys.getTypeClass(), keys.size()));
		} catch(SQLException x) {
			throw new QueryException(x);
		}
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
