package com.github.yuthura.bianca;

import java.sql.*;
import java.util.*;

public interface Alias extends Selectable {
	public String getName();



	public static class Simple implements Alias {
		private final Selectable original;

		private final String name;

		public Simple(Selectable original, String name) {
			super();

			this.original = Objects.requireNonNull(original);
			this.name = Query.requireValidName(name);
		}


		protected Selectable getOriginal() {
			return original;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void buildStatement(StringBuilder sb) {
			getOriginal().buildStatement(sb);
			sb.append(" AS ");
			quote(sb, getName());
		}

		@Override
		public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
			return getOriginal().prepareStatement(statement, index);
		}

		@Override
		public String getSelectionName() {
			return getName();
		}


		public void quote(StringBuilder sb, String name) {
			sb.append("`");
			sb.append(Query.requireValidName(name));
			sb.append("`");
		}
	}
}
