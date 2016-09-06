package com.allatori;

public class LogFileRepository {

	private static String logFile;

	public static void setLogFile(String string) {
		logFile = string;
	}

	public static String getLogFile() {
		return logFile;
	}
}
