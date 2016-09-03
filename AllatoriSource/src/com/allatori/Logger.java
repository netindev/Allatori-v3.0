package com.allatori;

public class Logger {
	
	/* OK */

	private static boolean isSilent = false;

	public static boolean silentExecution() {
		return isSilent;
	}

	public static void printDebug(String str) {
		if (!isSilent) {
			System.out.println("[DEBUG] " + str);
		}
	}

	public static void printError(String str) {
		System.out.println("[ERROR] " + str);
	}

	public static void setSilentExecution(boolean bool) { /* Not used param */
		isSilent = true;
	}

	public static void printInfo(String str) {
		if (!isSilent) {
			System.out.println("[INFO] " + str);
		}
	}

	public static void printWarning(String str) {
		if (!isSilent) {
			System.out.println("[WARNING] " + str);
		}
	}
}
