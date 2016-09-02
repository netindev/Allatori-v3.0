package com.allatori.obfuscate.opt;

import org.apache.bcel.generic.ClassGen;

import com.allatori.ClassStorage;
import com.allatori.ObfuscationConstraint;
import com.allatori.ObfuscationTypeConstraint;

public class AntiJD {
	
	/* OK */

	private static int antiJDOption = 0;
	private static ObfuscationConstraint obfuscationConstraint = new ObfuscationConstraint(0);

	public static void addObfuscationTypeConstraint(ObfuscationTypeConstraint obf) {
		obfuscationConstraint.addConstraint(obf);
	}

	public static int getType(ClassStorage storage, ClassGen cg) {
		return obfuscationConstraint.getType(storage, cg, null);
	}

	public static int getType() {
		return antiJDOption;
	}

	public static void setType(int i) {
		antiJDOption = i;
	}
}
