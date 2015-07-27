package com.github.yuthura.bianca.conditions;

import java.sql.*;

import com.github.yuthura.bianca.*;

/**
 * <p>
 * Represents a comparison statement that takes a left operand, a right operand and an operator. The operands can be any
 * type of object and will be wrapped in a {@link Partial} using {@link Partial#wrap(Object)}; if an operand represents
 * an object type that cannot be wrapped, a {@link CoercionException} will be thrown. This abstract class assumes the
 * setup for all subclasses is the same, namely <code>[left operand] [operator] [right operand]</code>; if this is not the
 * case for a subclass, it should implement <code>buildStatement</code>.
 * </p>
 *
 * @author Yuthura
 */
public class BinaryCondition implements Condition {
	/**
	 * The operator that this condition represents, usually specified by the subclass. Cannot be <code>null</code>, cannot be
	 * an empty string or a string consisting only of whitespace and must not contain the quote character (<code>`</code>).
	 */
	private final String operator;

	/**
	 * The partial that represents the left operand. In te case of a <code>null</code> value, will usually be wrapped as
	 * {@link Partial#NULL} to avoid complexity during query construction.
	 */
	private final Partial left;

	/**
	 * The partial that represents the right operand. In the case of a <code>null</code> value, will usually be wrapped as
	 * {@link Partial#NULL} to avoid complexity during query construction.
	 */
	private final Partial right;

	/**
	 * <p>
	 * Constructs a new condition with an operator, a left operand and a right operand. The operator must be a valid string
	 * as specified by {@link Query#requireValidName(String)} or an <code>IllegalArgumentException</code> will be thrown.
	 * Both operands will be wrapped in a {@link Partial} using {@link Partial#wrap(Object)}; if an operator cannot be wrapped,
	 * a {@link CoercionException} will be thrown.
	 * </p>
	 * <p>
	 * If an operator is <code>null</code>, it will normally be wrapped in a {@link Partial#NULL}, but subclasses must not
	 * depend on this behavior and must take into account that both <code>left</code> and <code>right</code> may be
	 * <code>null</code>.
	 * </p>
	 *
	 * @param operator the operator to apply to the operands, usually specified by a subclass
	 * @param left the left operand that the operator applies to, will be wrapped in a {@link Partial}
	 * @param right the right operand that the operator applies to, will be wrapped in a {@link Partial}
	 *
	 * @throws NullPointerException if <code>operator</code> is <code>null</code>
	 * @throws IllegalArgumentException if <code>operator</code> is an empty string or a string consisting of only whitespace
	 *                                  characters, or if it contains the quote character (<code>`</code>)
	 * @throws CoercionException if either <code>left</code> or <code>right</code> cannot be wrapped in a {@link Partial}
	 */
	public BinaryCondition(String operator, Object left, Object right) {
		super();

		this.operator = Query.requireValidName(operator);
		this.left = Partial.wrap(left);
		this.right = Partial.wrap(right);
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		buildStatement(sb, left, right);
	}

	@Override
	public int prepareStatement(PreparedStatement statement, int index) throws SQLException {
		return prepareStatement(statement, index, left, right);
	}


	protected void buildStatement(StringBuilder sb, Partial left, Partial right) {
		left.buildStatement(sb);
		sb.append(" ");
		sb.append(operator);
		sb.append(" ");
		right.buildStatement(sb);
	}

	protected int prepareStatement(PreparedStatement statement, int index, Partial left, Partial right) throws SQLException {
		int i = 0;

		if(left != null) {
			i += left.prepareStatement(statement, index + i);
		}

		if(right != null) {
			i += right.prepareStatement(statement, index + i);
		}

		return i;
	}
}
