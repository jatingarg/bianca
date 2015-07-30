package com.github.yuthura.bianca;

import com.github.yuthura.bianca.conditions.*;
import com.github.yuthura.bianca.functions.*;
import com.github.yuthura.bianca.operations.*;

public class Helpers {
	public static Select select(Selectable... selection) {
		return new Select(selection);
	}

	public static Select select(Table table) {
		return new Select().from(table);
	}

	public static Insert insert(Table table, Column<?>... columns) {
		return new Insert(table, columns);
	}

	public static Update update(Table table) {
		return new Update(table);
	}

	public static Delete delete(Table table, Condition... conditions) {
		return new Delete(table).where(conditions);
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

	public static Between between(Object left, Object right) {
		return new Between(left, right);
	}

	public static NotBetween notBetween(Object left, Object right) {
		return new NotBetween(left, right);
	}



	public static Sum sum(Object object) {
		return new Sum(object);
	}

	public static Year year(Object object) {
		return new Year(object);
	}

	public static Month month(Object object) {
		return new Month(object);
	}

	public static Count count(Object object) {
		return new Count(object);
	}

	public static Count count(Object object, boolean distinct) {
		return (Count)new Count(object).distinct(distinct);
	}

	public static Count countDistinct(Object object) {
		return count(object, true);
	}

	public static Now now() {
		return new Now();
	}

	public static If ifElse(Object condition, Object ifTrue, Object ifFalse) {
		return new If(condition, ifTrue, ifFalse);
	}

	public static Abs abs(Object object) {
		return new Abs(object);
	}

	public static Add add(Object left, Object right) {
		return new Add(left, right);
	}

	public static Subtract sub(Object left, Object right) {
		return new Subtract(left, right);
	}

	public static Multiply mul(Object left, Object right) {
		return new Multiply(left, right);
	}

	public static Divide div(Object left, Object right) {
		return new Divide(left, right);
	}

	public static Modulo mod(Object left, Object right) {
		return new Modulo(left, right);
	}



	public static Average avg(Object object) {
		return new Average(object);
	}

	public static Minimum min(Object object) {
		return new Minimum(object);
	}

	public static Maximum max(Object object) {
		return new Maximum(object);
	}
}
