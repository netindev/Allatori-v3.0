package com.allatori;

public class WatermarkUtil {

	private static boolean bool = false;
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
		return bool;
	}

	public static String getKey() {
		return watermarkKey;
	}

	public static void setBool(boolean newBool) {
		bool = newBool;
	}
}
