package com.github.yuthura.bianca;

import com.github.yuthura.bianca.conditions.*;
import com.github.yuthura.bianca.functions.*;

public class Helpers {
	public static SelectChain select(Selectable... selection) {
		return new Select(selection);
	}


	public static And and(Object... partials) {
		return new And(partials);
	}

	public static Or or(Object... partials) {
		return new Or(partials);
	}


	public static EqualTo equalTo(Object left, Object right) {
		return new EqualTo(left, right);
	}

	public static EqualTo eq(Object left, Object right) {
		return equalTo(left, right);
	}


	public static NotEqualTo notEqualTo(Object left, Object right) {
		return new NotEqualTo(left, right);
	}

	public static NotEqualTo neq(Object left, Object right) {
		return notEqualTo(left, right);
	}


	public static GreaterThan greaterThan(Object left, Object right) {
		return new GreaterThan(left, right);
	}

	public static GreaterThan gt(Object left, Object right) {
		return greaterThan(left, right);
	}


	public static GreaterThanOrEqualTo greaterThanOrEqualTo(Object left, Object right) {
		return new GreaterThanOrEqualTo(left, right);
	}

	public static GreaterThanOrEqualTo gte(Object left, Object right) {
		return greaterThanOrEqualTo(left, right);
	}


	public static LessThan lessThan(Object left, Object right) {
		return new LessThan(left, right);
	}

	public static LessThan lt(Object left, Object right) {
		return lessThan(left, right);
	}


	public static LessThanOrEqualTo lessThanOrEqualTo(Object left, Object right) {
		return new LessThanOrEqualTo(left, right);
	}

	public static LessThanOrEqualTo lte(Object left, Object right) {
		return lessThanOrEqualTo(left, right);
	}



	public static In in(Object left, Object... right) {
		return new In(left, right);
	}

	public static NotIn notIn(Object left, Object... right) {
		return new NotIn(left, right);
	}

	public static NotIn nin(Object left, Object... right) {
		return notIn(left, right);
	}



	public static Sum sum(Object object) {
		return new Sum(object);
	}

	public static Year year(Object object) {
		return new Year(object);
	}
}
