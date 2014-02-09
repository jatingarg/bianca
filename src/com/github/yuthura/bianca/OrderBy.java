package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

public class OrderBy implements Partial {
	private final List<Ordering> orderings;

	public OrderBy() {
		super();

		orderings = new ArrayList<>();
	}


	public void addOrdering(Partial partial, Direction direction) {
		orderings.add(new Ordering(partial, direction));
	}

	public void clearOrdering() {
		orderings.clear();
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		if(!orderings.isEmpty()) {
			sb.append(" ORDER BY ");
			for(Iterator<Ordering> i = orderings.iterator(); i.hasNext(); ) {
				i.next().buildStatement(sb);
				if(i.hasNext()) {
					sb.append(", ");
				}
			}
		}
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		int i = 0;

		for(Ordering ordering : orderings) {
			i += ordering.prepareStatement(statement, index + i);
		}

		return i;
	}

	private class Ordering implements Partial {
		private final Partial partial;

		private final Direction direction;

		private Ordering(Partial partial, Direction direction) {
			this.partial = partial;
			this.direction = direction;
		}

		@Override
		public void buildStatement(StringBuilder sb) {
			partial.buildStatement(sb);
			if(direction != null) {
				sb.append(" ");
				sb.append(direction);
			}
		}

		@Override
		public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
			return 0;
		}
	}

	public static enum Direction {
		ASCENDING("ASC"),
		DESCENDING("DESC");

		private final String sql;

		Direction(String sql) {
			this.sql = sql;
		}

		@Override
		public String toString() {
			return sql;
		}
	}
}
