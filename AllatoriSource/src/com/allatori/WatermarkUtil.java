package com.allatori;

public class WatermarkUtil {
	
	/* OK */

	private static boolean extract = false;
	private static String watermarkValue;
	private static String watermarkKey;

	public static String getValue() {
		return watermarkValue;
	}

	public static void setKey(String newKey) {
		watermarkKey = newKey;
	}

	public static void setValue(String newValue) {
		watermarkValue = newValue;
	}

	public static boolean getBool() {
		return extract;
	}

	public static String getExtract() {
		return watermarkKey;
	}

	public static void setExtract(boolean newBool) {
		extract = newBool;
	}
}
