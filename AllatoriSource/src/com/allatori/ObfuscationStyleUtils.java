package com.allatori;

public class ObfuscationStyleUtils {

	private static int anInt518 = 2;
	private static int anInt519 = 2;

	public static void setMethodNamingType(int var0) {
		anInt518 = var0;
	}

	public static IObfuscationStyle method333() {
		return anInt518 == 1 ? new ObfuscationStyleLow()
				: (anInt518 == 3 ? new Keywords() : new ObfuscationStyleMixed());
	}

	public static IObfuscationStyle method334() {
		return new ObfuscationStyleLow();
	}

	public static IObfuscationStyle method335() {
		return new ObfuscationStyleLow();
	}

	public static IObfuscationStyle method336() {
		return anInt519 == 1 ? new ObfuscationStyleLow()
				: (anInt519 == 3 ? new Keywords() : new ObfuscationStyleMixed());
	}

	public static IObfuscationStyle method337() {
		return LocalVariables.method372() == 2 ? new ObfuscationStyleLow()
				: (LocalVariables.method372() == 1 ? new ObfuscationStyleUnk() : new ObfuscationStyleLow());
	}

	public static void setFieldNamingType(int var0) {
		anInt519 = var0;
	}

	public static IObfuscationStyle method339() {
		return new ObfuscationStyleLow();
	}

}
