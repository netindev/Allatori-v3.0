package com.allatori;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

public class LocalVariables {
	
	/* OK */

	private static ObfuscationConstraint obfuscationConstraint = new ObfuscationConstraint(1);
	private static int integer = 1;

	public static int getType(ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
		return obfuscationConstraint.getType(classStorage, classGen, methodGen);
	}

	public static void setLocalVariableNamingType(int i) {
		integer = i;
	}

	public static void addObfuscationTypeConstraint(ObfuscationTypeConstraint obfuscationTypeConstraint) {
		obfuscationConstraint.addConstraint(obfuscationTypeConstraint);
	}

	public static int getInt() {
		return integer;
	}
}
