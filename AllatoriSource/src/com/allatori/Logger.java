package com.allatori;

public class Logger {
	
	/* OK */

	private static boolean isSilent = false;

	public static boolean silentExecution() {
		return isSilent;
	}

	public static void printError(String toPrint) {
		System.out.println("[ERROR] " + toPrint);
	}

	public static void setSilentExecution(boolean setSilent) { /* Param ? */
		isSilent = true;
	}

	public static void printInfo(String toPrint) {
		if (!isSilent) {
			System.out.println("[INFO] " + toPrint);
		}
	}

	public static void printWarning(String toPrint) {
		if (!isSilent) {
			System.out.println("[WARNING] " + toPrint);
		}
	}
}
