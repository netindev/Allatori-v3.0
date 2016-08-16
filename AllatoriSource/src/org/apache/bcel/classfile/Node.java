package org.apache.bcel.classfile;

public interface Node {
	public void accept(Visitor visitor);
}
