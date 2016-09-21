package com.allatori;

import java.util.Vector;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

public class ObfuscationConstraint {
	
	/* OK */

	private final Vector<ObfuscationTypeConstraint> obfuscationConstraints = new Vector<ObfuscationTypeConstraint>();
	private final int defaultType;

	public void addConstraint(ObfuscationTypeConstraint typeConstraint) {
		this.obfuscationConstraints.add(typeConstraint);
	}

	public int getType(ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
		for (final Object constraint : this.obfuscationConstraints) {
			final ObfuscationTypeConstraint current = (ObfuscationTypeConstraint) constraint;
			if (current.classConstraint.apply(classStorage, classGen)) {
				if (methodGen == null) {
					return current.type;
				}
				if (current.classConstraint.apply(methodGen)) {
					return current.type;
				}
			}
		}
		return this.defaultType;
	}

	public ObfuscationConstraint(int defaultType) {
		this.defaultType = defaultType;
	}

	public int size() {
		return this.obfuscationConstraints.size();
	}
}
