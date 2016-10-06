package com.allatori;

public class Obfuscate extends Configurable {
	
	/* OK */

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

	private static void process(ClassStorage classStorage) throws Exception {
		new Obfuscator(classStorage).process();
	}

	private static void printUsage() {
		System.out.println(Info.name() + " " + Info.version());
		System.out.println("Usage:");
		System.out.println("com.allatori.Obfuscate <config file>");
	}

	public static void execute() throws Exception {
		final ClassStorage classStorage = Configurable.getClasses();
		process(classStorage);
		if (WatermarkUtil.getExtract() != null && WatermarkUtil.getValue() != null) {
			Configurable.createWatermark(classStorage);
		}
		Configurable.start(classStorage);
		Logger.printInfo("Obfuscation completed. Writing log file...");
		LogFile.writeLogFile();
	}
}
