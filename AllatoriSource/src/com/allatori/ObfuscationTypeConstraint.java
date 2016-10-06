package com.allatori;

public class ObfuscationTypeConstraint {

	/* OK */

	public int type;
	public ClassConstraint classConstraint;

	public ObfuscationTypeConstraint(int type, ClassConstraint classConstraint) {
		this.type = type;
		this.classConstraint = classConstraint;
	}
}
