package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

public class Join implements Partial {
	public final static String TYPE_INNER = "INNER";
	public final static String TYPE_LEFT = "LEFT";
	public final static String TYPE_RIGHT = "RIGHT";

	private final String type;

	private final Table table;

	private final List<Condition> conditions;

	public Join(Table table) {
		this(TYPE_INNER, table);
	}

	public Join(String type, Table table, Condition... conditions) {
		super();

		this.type = Query.requireValidName(type);
		this.table = Objects.requireNonNull(table);
		this.conditions = new ArrayList<>();
		setConditions(conditions);
	}

	public void setConditions(Condition... conditions) {
		this.conditions.clear();
		for(Condition condition : conditions) {
			this.conditions.add(Objects.requireNonNull(condition));
		}
	}


	public void addCondition(Condition condition) {
		conditions.add(Objects.requireNonNull(condition));
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		sb.append(" ");
		sb.append(type);
		sb.append(" JOIN ");
		table.buildSelectionStatement(sb);

		if(!conditions.isEmpty()) {
			sb.append(" ON ");
			for(Iterator<Condition> i = conditions.iterator(); i.hasNext(); ) {
				i.next().buildStatement(sb);
				if(i.hasNext()) {
					sb.append(" AND ");
				}
			}
		}
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		int i = 0;

		i += table.prepareSelectionStatement(statement, index);

		for(Condition condition : conditions) {
			i += condition.prepareStatement(statement, index + i);
		}

		return i;
	}


	public static class Inner extends Join {
		public Inner(Table table, Condition... conditions) {
			super(TYPE_INNER, table, conditions);
		}
	}

	public static class Left extends Join {
		public Left(Table table, Condition... conditions) {
			super(TYPE_LEFT, table, conditions);
		}
	}

	public static class Right extends Join {
		public Right(Table table, Condition... conditions) {
			super(TYPE_RIGHT, table, conditions);
		}
	}
}
