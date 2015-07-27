package com.github.yuthura.bianca.functions;

public class If extends TernaryFunction {
	public static final String OPERATOR = "IF";

	public If(Object condition, Object ifTrue, Object ifFalse) {
		super(OPERATOR, condition, ifTrue, ifFalse);
	}

}
