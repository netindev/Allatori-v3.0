package com.allatori;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StackTrace {

	public static void main(String[] args) {
		System.out.println(Configurable.printSplash());
		try {
			if (args.length != 3) {
				printUsage();
				System.exit(0);
			}
			StackTraceLog log = null;
			final String logFileName = args[0];
			try {
				log = StackTraceLogFileReader.readLogFile(new FileReader(logFileName));
			} catch (final FileNotFoundException e) {
				Logger.printError("Log file not found: " + e.getMessage());
				System.exit(0);
			} catch (final Exception e) {
				Logger.printError("Error reading log file: " + e.getMessage());
				System.exit(0);
			}
			final String inputFileName = args[1];
			final String outputFileName = args[2];
			BufferedReader inputReader = null;
			try {
				inputReader = new BufferedReader(new FileReader(inputFileName));
			} catch (final FileNotFoundException e) {
				Logger.printError("Input file not found: " + e.getMessage());
				System.exit(0);
			}
			PrintWriter outputWriter = null;
			try {
				outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));
			} catch (final IOException e) {
				Logger.printError("Error writing output file: " + e.getMessage());
				System.exit(0);
			}
			StackTraceUtils.translateStackTrace(inputReader, log, outputWriter);
			inputReader.close();
			outputWriter.close();
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void printUsage() {
		System.out.println("Restore Stack Trace Utility");
		System.out.println("Usage:");
		System.out.println("com.allatori.StackTrace <log file> <input file> <output file>");
	}
}
