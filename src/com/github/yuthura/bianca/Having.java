package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

import com.github.yuthura.bianca.conditions.*;

public class Having implements Partial {
	private final List<Condition> conditions;

	public Having(Condition... conditions) {
		this.conditions = new ArrayList<>(conditions.length);
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

	public void addConditions(Condition... conditions) {
		if(conditions.length <= 1) {
			for(Condition condition : conditions) {
				addCondition(condition);
			}
		} else {
			addCondition(new And((Object[])conditions));
		}
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		if(!conditions.isEmpty()) {
			sb.append(" HAVING ");
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

		for(Condition condition : conditions) {
			i += condition.prepareStatement(statement, index + i);
		}

		return i;
	}
}
