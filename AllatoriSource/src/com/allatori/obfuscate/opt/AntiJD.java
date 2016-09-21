package com.allatori.obfuscate.opt;

import org.apache.bcel.generic.ClassGen;

import com.allatori.ClassStorage;
import com.allatori.ObfuscationConstraint;
import com.allatori.ObfuscationTypeConstraint;

public class AntiJD {

	private static ObfuscationConstraint obfuscationConstraint = new ObfuscationConstraint(0);

	public static void addObfuscationTypeConstraint(ObfuscationTypeConstraint obfuscationTypeConstraint) {
		obfuscationConstraint.addConstraint(obfuscationTypeConstraint);
	}

	public static int getType(ClassStorage storage, ClassGen _cg) {
		return obfuscationConstraint.getType(storage, _cg, null);
	}
}
