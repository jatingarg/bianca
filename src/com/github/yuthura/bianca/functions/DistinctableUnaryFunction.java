package com.github.yuthura.bianca.functions;

public class DistinctableUnaryFunction extends UnaryFunction {

	private boolean distinct;

	public DistinctableUnaryFunction(String operator, Object argument) {
		super(operator, argument);
	}

	public DistinctableUnaryFunction distinct() {
		return distinct(true);
	}

	public DistinctableUnaryFunction distinct(boolean distinct) {
		this.distinct = distinct;
		return this;
	}

	public boolean isDistinct() {
		return distinct;
	}

	@Override
	public void buildStatement(StringBuilder sb) {
		sb.append(getOperator());
		sb.append("(");
		if(isDistinct()) {
			sb.append("DISTINCT ");
		}
		getArgument().buildStatement(sb);
		sb.append(")");
	}

}
