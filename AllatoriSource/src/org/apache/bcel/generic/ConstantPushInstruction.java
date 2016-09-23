package org.apache.bcel.generic;

public interface ConstantPushInstruction extends PushInstruction, TypedInstruction {
	public Number getValue();
}
