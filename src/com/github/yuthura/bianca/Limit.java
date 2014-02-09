package com.github.yuthura.bianca;

import java.sql.*;

public class Limit implements Partial {
	private int offset;

	private int limit;

	public Limit(int limit) {
		this(-1, limit);
	}

	public Limit(int offset, int limit) {
		super();

		this.offset = offset;
		this.limit = limit;
	}


	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}


	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}


	public void removeOffset() {
		offset = -1;
	}

	public void removeLimit() {
		limit = -1;
	}


	@Override
	public void buildStatement(StringBuilder sb) {
		if(limit < 0) {
			return;
		}

		sb.append(" LIMIT ");
		if(offset >= 0) {
			sb.append(offset);
			sb.append(", ");
		}

		sb.append(limit);
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		return 0;
	}
}
