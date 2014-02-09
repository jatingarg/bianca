package com.github.yuthura.bianca;

import java.sql.*;

public interface Partial {
	public void buildStatement(StringBuilder sb);

	public int prepareStatement(PreparedStatement statement, int index) throws SQLException;



	public final Partial NULL = new Partial() {
		@Override
		public void buildStatement(StringBuilder sb) {
			sb.append("NULL");
		}

		@Override
		public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
			return 0;
		}
	};




	public static Partial wrap(Object object) {
		if(object == null) {
			return Partial.NULL;

		} else if(object instanceof Partial) {
			return (Partial)object;

		} else if(object instanceof Integer) {
			return Type.INTEGER.asPartial((Integer)object);

		} else if(object instanceof Double) {
			return Type.DOUBLE.asPartial((Double)object);

		} else if(object instanceof String) {
			return Type.STRING.asPartial((String)object);

		} else {
			throw new CoercionException();
		}
	}
}
