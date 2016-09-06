package com.allatori.obfuscate.opt;

import org.apache.bcel.generic.ClassGen;

import com.allatori.ClassStorage;
import com.allatori.ObfuscationConstraint;
import com.allatori.ObfuscationTypeConstraint;

public class AntiJD {
	
	/* OK */

	private static ObfuscationConstraint obfuscationConstraint = new ObfuscationConstraint(0);

	public static void addObfuscationTypeConstraint(ObfuscationTypeConstraint obf) {
		obfuscationConstraint.addConstraint(obf);
	}

	public static int getType(ClassStorage storage, ClassGen cg) {
		return obfuscationConstraint.getType(storage, cg, null);
	}
}