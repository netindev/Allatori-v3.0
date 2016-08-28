package com.allatori;

public class Packaging {

	private static String aString581;
	private static String aString582;
	private static boolean aBoolean583 = false;

	public static boolean method575() {
		return aBoolean583;
	}

	public static String method576() {
		return aString581;
	}

	public static void method577(String var0) {
		aString582 = var0;
	}

	public static boolean method578() {
		return aString581 != null;
	}

	public static boolean method579() {
		return aString582 != null;
	}

	public static void forceDefaultPackage(boolean var0) {
		aBoolean583 = var0;
	}

	public static void setDefaultPackage(String var0) {
		aString581 = var0;
	}

	public static String method582() {
		return aString582;
	}
}
