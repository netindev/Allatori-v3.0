package com.allatori;

public class Obfuscate extends Configurable {

	public static void main(String[] arguments) {
		if (arguments.length > 1 && "-silent".equals(arguments[1])) {
			Logger.setSilentExecution(true);
		} else {
			System.out.println(Configurable.printSplash());
		}

		if (arguments.length != 1 && !Logger.silentExecution()) {
			printUsage();
			System.exit(0);
		}

		Configurable.parseConfigFile(arguments[0]);

		try {
			execute();
		} catch (final Exception var2) {
			Logger.printError(var2.getMessage());
			System.out.println("############### EXCEPTION ###############");
			var2.printStackTrace();
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
		final ClassStorage classStorage = Configurable.method1269();
		method1276(classStorage);
		if (Class167.method1660() != null && Class167.method1656() != null) {
			Configurable.method1266(classStorage);
		}
		Configurable.method1275(classStorage);
		Logger.printInfo("Obfuscation completed. Writing log file...");
		LogFile.writeLogFile();
	}
}
