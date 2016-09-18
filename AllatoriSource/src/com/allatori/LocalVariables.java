package com.allatori;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

public class LocalVariables {
	
	/* OK */

	private static ObfuscationConstraint obfuscationConstraint = new ObfuscationConstraint(1);
	private static int namingType = 1;

	public static int getType(ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
		return obfuscationConstraint.getType(classStorage, classGen, methodGen);
	}

	public static void setLocalVariableNamingType(int newNamingType) {
		namingType = newNamingType;
	}

	public static void addObfuscationTypeConstraint(ObfuscationTypeConstraint obfuscationTypeConstraint) {
		obfuscationConstraint.addConstraint(obfuscationTypeConstraint);
	}

	public static int getLocalVariableNamingType() {
		return namingType;
	}
}
