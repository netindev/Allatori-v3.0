package com.allatori;

import java.io.*;

public class StackTrace {

    public static void main(String[] var0) {
        System.out.println(Configurable.printSplash());

        try {
            if (var0.length != 3) {
                printUsage();
                System.exit(0);
            }

            Class166 log = null;
            String logFileName = var0[0];

            try {
                log = StackTraceLogFileReader.readLogFile(new FileReader(logFileName));
            } catch (FileNotFoundException var10) {
                Logger.printError("Log file not found: " + var10.getMessage());
                System.exit(0);
            } catch (Exception var11) {
                Logger.printError("Error reading log file: " + var11.getMessage());
                System.exit(0);
            }

            String inputFileName = var0[1];
            String outputFileName = var0[2];
            BufferedReader inputReader = null;

            try {
                inputReader = new BufferedReader(new FileReader(inputFileName));
            } catch (FileNotFoundException var9) {
                Logger.printError("Input file not found: " + var9.getMessage());
                System.exit(0);
            }

            PrintWriter outputWriter = null;

            try {
                outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));
            } catch (IOException var8) {
                Logger.printError("Error writing output file: " + var8.getMessage());
                System.exit(0);
            }

            StackTraceUtils.translateStackTrace(inputReader, log, outputWriter);
            inputReader.close();
            outputWriter.close();
        } catch (Exception var12) {
            System.out.println("Error: " + var12.getMessage());
        }

    }

    private static void printUsage() {
        System.out.println("Restore Stack Trace Utility");
        System.out.println("Usage:");
        System.out.println("com.allatori.StackTrace <log file> <input file> <output file>");
    }
}
