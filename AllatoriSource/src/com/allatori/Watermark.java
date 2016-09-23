package com.allatori;

public class Watermark extends Configurable {
	
	/* OK */

	private static void printUsage() {
		System.out.println(Info.name() + " " + Info.version());
		System.out.println("Usage:");
		System.out.println("com.allatori.Watermark -add <config file>");
		System.out.println("or");
		System.out.println("com.allatori.Watermark -extract <config file>");
	}

	public static void main(String[] args) {
		System.out.println(Configurable.printSplash());
		if (args.length != 2) {
			printUsage();
			System.exit(0);
		}
		if ("-add".equals(args[0])) {
			WatermarkUtil.setExtract(false);
		} else if ("-extract".equals(args[0])) {
			WatermarkUtil.setExtract(true);
		} else {
			printUsage();
			System.exit(0);
		}
		Configurable.parseConfigFile(args[1]);
		try {
			final ClassStorage classStorage = Configurable.getClasses();
			if (WatermarkUtil.getBool()) {
				if (WatermarkUtil.getExtract() == null) {
					Logger.printError("Configuration error. Watermark key is not set.");
					System.exit(0);
				}
				System.out.println("Extracted watermark: \"" + Configurable.extractWatermark(classStorage) + "\"");
			} else {
				if (WatermarkUtil.getExtract() == null) {
					Logger.printError("Configuration error. Watermark key is not set.");
					System.exit(0);
				}
				if (WatermarkUtil.getValue() == null) {
					Logger.printError("Configuration error. Watermark value is not set.");
					System.exit(0);
				}
				Configurable.createWatermark(classStorage);
				Configurable.method1275(classStorage);
			}
		} catch (final Exception e) {
			Logger.printError(e.getMessage());
		}
	}
}
