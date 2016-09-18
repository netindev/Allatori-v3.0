package com.allatori;

public class Watermark extends Configurable {
	
	/* OK */

	private static void method1279() {
		System.out.println(Info.name() + " " + Info.version());
		System.out.println("Usage:");
		System.out.println("com.allatori.Watermark -add <config file>");
		System.out.println("or");
		System.out.println("com.allatori.Watermark -extract <config file>");
	}

	public static void main(String[] args) {
		System.out.println(Configurable.printSplash());
		if (args.length != 2) {
			method1279();
			System.exit(0);
		}
		if ("-add".equals(args[0])) {
			WatermarkUtil.setBool(false);
		} else if ("-extract".equals(args[0])) {
			WatermarkUtil.setBool(true);
		} else {
			method1279();
			System.exit(0);
		}
		Configurable.parseConfigFile(args[1]);
		try {
			final ClassStorage classStorage = Configurable.method1269();
			if (WatermarkUtil.getBool()) {
				if (WatermarkUtil.getKey() == null) {
					Logger.printError("Configuration error. Watermark key is not set.");
					System.exit(0);
				}
				System.out.println("Extracted watermark: \"" + Configurable.method1274(classStorage) + "\"");
			} else {
				if (WatermarkUtil.getKey() == null) {
					Logger.printError("Configuration error. Watermark key is not set.");
					System.exit(0);
				}
				if (WatermarkUtil.getValue() == null) {
					Logger.printError("Configuration error. Watermark value is not set.");
					System.exit(0);
				}
				Configurable.method1266(classStorage);
				Configurable.method1275(classStorage);
			}
		} catch (final Exception var2) {
			Logger.printError(var2.getMessage());
		}

	}
}
