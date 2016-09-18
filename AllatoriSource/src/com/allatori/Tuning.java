package com.allatori;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

public class Tuning {

	private static boolean controlFlowObfuscationEnabled = true;
	public static int STRING_ENCRYPTION_MAXIMUM = 2;
	private static boolean isFinalizingEnabled = false;
	public static int STRING_ENCRYPTION_DISABLE = 0;
	private static boolean aBoolean882 = true;
	public static int STRING_ENCRYPTION_DEFAULT = 1;
	public static int STRING_ENCRYPTION_DEFAULT_TYPE = 0;
	private static String randomSeed = null;
	private static ObfuscationConstraint aObfuscationConstraint_886 = new ObfuscationConstraint(
			STRING_ENCRYPTION_DEFAULT);
	private static ObfuscationConstraint aObfuscationConstraint_887 = new ObfuscationConstraint(
			STRING_ENCRYPTION_DEFAULT_TYPE);
	public static int STRING_ENCRYPTION_STRONG_TYPE = 1;
	private static boolean aBoolean890 = false;
	private static int stringEncryptionLevel = STRING_ENCRYPTION_DEFAULT;

	public static String getRandomSeed() {
		return randomSeed;
	}

	public static void enableMemberReorder(boolean var0) {
		aBoolean882 = var0;
	}

	public static void enableControlFlowObfuscation(boolean var0) {
		controlFlowObfuscationEnabled = var0;
	}

	public static int method1716(ClassStorage var0, ClassGen var1, MethodGen var2) {
		return aBoolean890 ? STRING_ENCRYPTION_DEFAULT_TYPE : aObfuscationConstraint_887.getType(var0, var1, var2);
	}

	public static void setStringEncryptionLevel(int var0) {
		stringEncryptionLevel = var0;
	}

	public static void setRandomSeed(String var0) {
		randomSeed = var0;
	}

	public static void setStringEncryptionTypeConstraint(ObfuscationTypeConstraint var0) {
		aObfuscationConstraint_887.addConstraint(var0);
	}

	public static boolean isWeakStringEncryption() {
		return aBoolean890;
	}

	public static boolean isStringObfuscationLayer2Enabled() {
		return stringEncryptionLevel != 0 || aObfuscationConstraint_886.size() > 1;
	}

	public static boolean isControlFlowObfuscationEnabled() {
		return controlFlowObfuscationEnabled;
	}

	public static void method1723() {
		aBoolean890 = true;
	}
	
	public static void setStringObfuscationConstraint(ObfuscationTypeConstraint var0) {
		aObfuscationConstraint_886.addConstraint(var0);
	}

	public static int method1726(ClassStorage var0, ClassGen var1, MethodGen var2) {
		return aObfuscationConstraint_886.getType(var0, var1, var2);
	}

	public static void enableFinalizing(boolean var0) {
		isFinalizingEnabled = var0;
	}

	public static boolean rearrangeClassMembers() {
		return aBoolean882;
	}

	public static boolean isFinalizingEnabled() {
		return isFinalizingEnabled;
	}

	public static void setStringEncryptionType(int var0) {
	}

}
