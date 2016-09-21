package com.allatori;

public class Obfuscate extends Configurable {

	public static void main(String[] args) {
		if (args.length > 1 && "-silent".equals(args[1])) {
			Logger.setSilentExecution(true);
		} else {
			System.out.println(Configurable.printSplash());
		}
		if (args.length != 1 && !Logger.silentExecution()) {
			printUsage();
			System.exit(0);
		}
		Configurable.parseConfigFile(args[0]);
		try {
			execute();
		} catch (final Exception e) {
			Logger.printError(e.getMessage());
			System.out.println("############### EXCEPTION ###############");
			e.printStackTrace();
			System.out.println("#########################################");
			System.exit(-1);
		}
	}

	private static void method1276(ClassStorage classStorage) throws Exception {
		new Obfuscator(classStorage).method1526();
	}

	private static void printUsage() {
		System.out.println(Info.name() + " " + Info.version());
		System.out.println("Usage:");
		System.out.println("com.allatori.Obfuscate <config file>");
	}

	public static void execute() throws Exception {
		ClassStorage classStorage = Configurable.getClasses();
		method1276(classStorage);
		if (WatermarkUtil.getKey() != null && WatermarkUtil.getValue() != null) {
			Configurable.method1266(classStorage);
		}
		Configurable.method1275(classStorage);
		Logger.printInfo("Obfuscation completed. Writing log file...");
		LogFile.writeLogFile();
	}
}
