package com.allatori;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StackTrace {

	public static void main(String[] var0) {
		System.out.println(Configurable.printSplash());
		try {
			if (var0.length != 3) {
				printUsage();
				System.exit(0);
			}

			Class166 log = null;
			final String logFileName = var0[0];

			try {
				log = StackTraceLogFileReader.readLogFile(new FileReader(logFileName));
			} catch (final FileNotFoundException var10) {
				Logger.printError("Log file not found: " + var10.getMessage());
				System.exit(0);
			} catch (final Exception var11) {
				Logger.printError("Error reading log file: " + var11.getMessage());
				System.exit(0);
			}

			final String inputFileName = var0[1];
			final String outputFileName = var0[2];
			BufferedReader inputReader = null;

			try {
				inputReader = new BufferedReader(new FileReader(inputFileName));
			} catch (final FileNotFoundException var9) {
				Logger.printError("Input file not found: " + var9.getMessage());
				System.exit(0);
			}

			PrintWriter outputWriter = null;

			try {
				outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));
			} catch (final IOException var8) {
				Logger.printError("Error writing output file: " + var8.getMessage());
				System.exit(0);
			}

			StackTraceUtils.translateStackTrace(inputReader, log, outputWriter);
			inputReader.close();
			outputWriter.close();
		} catch (final Exception var12) {
			System.out.println("Error: " + var12.getMessage());
		}

	}

	private static void printUsage() {
		System.out.println("Restore Stack Trace Utility");
		System.out.println("Usage:");
		System.out.println("com.allatori.StackTrace <log file> <input file> <output file>");
	}
}
