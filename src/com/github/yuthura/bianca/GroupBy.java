package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

public class GroupBy implements Partial {
	private final List<Partial> groupings;

	public GroupBy() {
		super();

		groupings = new ArrayList<>();
	}

	public void setGroupings(Partial... groupings) {
		this.groupings.clear();
		for(Partial grouping : groupings) {
			this.groupings.add(Objects.requireNonNull(grouping));
		}
	}

	public void addGrouping(Partial grouping) {
		groupings.add(grouping);
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		if(!groupings.isEmpty()) {
			sb.append(" GROUP BY ");
			for(Iterator<Partial> i = groupings.iterator(); i.hasNext(); ) {
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

		for(Partial grouping : groupings) {
			i += grouping.prepareStatement(statement, index + i);
		}

		return i;
	}
}
