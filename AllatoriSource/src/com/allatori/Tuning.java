package com.allatori;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

public class Tuning {
	
	/* OK */

	private static boolean controlFlowObfuscationEnabled = true;
	public static final int STRING_ENCRYPTION_MAXIMUM = 2;
	private static boolean isFinalizingEnabled = false;
	public static final int STRING_ENCRYPTION_DISABLE = 0;
	private static boolean rearrangeClassMembers = true;
	public static final int STRING_ENCRYPTION_DEFAULT = 1;
	public static final int STRING_ENCRYPTION_DEFAULT_TYPE = 0;
	private static String randomSeed = null;
	private static ObfuscationConstraint obfuscationConstraintStrDef = new ObfuscationConstraint(
			STRING_ENCRYPTION_DEFAULT);
	private static ObfuscationConstraint stringEncryptionTypeConstraint = new ObfuscationConstraint(
			STRING_ENCRYPTION_DEFAULT_TYPE);
	public static final int STRING_ENCRYPTION_STRONG_TYPE = 1;
	private static boolean isWeakStringEncryption = false;
	private static int stringEncryptionLevel = STRING_ENCRYPTION_DEFAULT;

	public static String getRandomSeed() {
		return randomSeed;
	}

	public static void enableMemberReorder(boolean bool) {
		rearrangeClassMembers = bool;
	}

	public static void enableControlFlowObfuscation(boolean bool) {
		controlFlowObfuscationEnabled = bool;
	}

	public static int stringEncryptionIsWeak(ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
		return isWeakStringEncryption ? STRING_ENCRYPTION_DEFAULT_TYPE
				: stringEncryptionTypeConstraint.getType(classStorage, classGen, methodGen);
	}

	public static void setStringEncryptionLevel(int i) {
		stringEncryptionLevel = i;
	}

	public static void setRandomSeed(String string) {
		randomSeed = string;
	}

	public static void setStringEncryptionTypeConstraint(ObfuscationTypeConstraint obfuscationTypeConstraint) {
		stringEncryptionTypeConstraint.addConstraint(obfuscationTypeConstraint);
	}

	public static boolean isWeakStringEncryption() {
		return isWeakStringEncryption;
	}

	public static boolean isStringObfuscationLayer2Enabled() {
		return stringEncryptionLevel != 0 || obfuscationConstraintStrDef.size() > 1;
	}

	public static boolean isControlFlowObfuscationEnabled() {
		return controlFlowObfuscationEnabled;
	}

	public static void setIsWeakStringEncryption() {
		isWeakStringEncryption = true;
	}

	public static void setStringObfuscationConstraint(ObfuscationTypeConstraint var0) {
		obfuscationConstraintStrDef.addConstraint(var0);
	}

	public static int getStringObfuscationType(ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
		return obfuscationConstraintStrDef.getType(classStorage, classGen, methodGen);
	}

	public static void enableFinalizing(boolean bool) {
		isFinalizingEnabled = bool;
	}

	public static boolean rearrangeClassMembers() {
		return rearrangeClassMembers;
	}

	public static boolean isFinalizingEnabled() {
		return isFinalizingEnabled;
	}

}
