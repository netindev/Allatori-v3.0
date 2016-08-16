package org.apache.bcel.generic;

public interface LoadClass {
	public ObjectType getLoadClassType(ConstantPoolGen constantpoolgen);

	public Type getType(ConstantPoolGen constantpoolgen);
}
