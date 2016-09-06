package com.allatori;

public class Logger {

	private static boolean isSilent = false;

	public static boolean silentExecution() {
		return isSilent;
	}

	public static void printError(String var0) {
		System.out.println("[ERROR] " + var0);
	}

	public static void setSilentExecution(boolean var0) {
		isSilent = true;
	}

	public static void printInfo(String var0) {
		if (!isSilent) {
			System.out.println("[INFO] " + var0);
		}
	}

	public static void printWarning(String var0) {
		if (!isSilent) {
			System.out.println("[WARNING] " + var0);
		}
	}
}
