package com.allatori;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

public class LocalVariables {

	private static ObfuscationConstraint aObfuscationConstraint_528 = new ObfuscationConstraint(1);
	private static int anInt531 = 1;

	public static int method369(ClassStorage var0, ClassGen var1, MethodGen var2) {
		return aObfuscationConstraint_528.getType(var0, var1, var2);
	}

	public static void setLocalVariableNamingType(int var0) {
		anInt531 = var0;
	}

	public static void addObfuscationTypeConstraint(ObfuscationTypeConstraint var0) {
		aObfuscationConstraint_528.addConstraint(var0);
	}

	public static int method372() {
		return anInt531;
	}
}
