package com.allatori;

public class Packaging {

	private static String defaultPackage;
	private static String aString582;
	private static boolean forceDefaultPackage = false;

	public static boolean forceDefaultPackage() {
		return forceDefaultPackage;
	}

	public static String getDefaultPackage() {
		return defaultPackage;
	}

	public static void method577(String var0) {
		aString582 = var0;
	}

	public static boolean defaultPackageIsNull() {
		return defaultPackage != null;
	}

	public static boolean method579() {
		return aString582 != null;
	}

	public static void forceDefaultPackage(boolean bool) {
		forceDefaultPackage = bool;
	}

	public static void setDefaultPackage(String str) {
		defaultPackage = str;
	}

	public static String method582() {
		return aString582;
	}
}
