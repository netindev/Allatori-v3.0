package com.allatori;

public class RenamingUtils {

	private static int anInt518 = 2;
	private static int anInt519 = 2;

	public static void setMethodNamingType(int var0) {
		anInt518 = var0;
	}

	public static IObfStyle method333() {
		return anInt518 == 1 ? new ObfuscationStyleLowCase() : (anInt518 == 3 ? new Keywords() : new ObfuscationStyleDifCase());
	}

	public static IObfStyle method334() {
		return new ObfuscationStyleLowCase();
	}

	public static IObfStyle method335() {
		return new ObfuscationStyleLowCase();
	}

	public static IObfStyle method336() {
		return anInt519 == 1 ? new ObfuscationStyleLowCase() : (anInt519 == 3 ? new Keywords() : new ObfuscationStyleDifCase());
	}

	public static IObfStyle method337() {
		return LocalVariables.getInt() == 2 ? new ObfuscationStyleLowCase()
				: (LocalVariables.getInt() == 1 ? new ObfuscationStyleUnk() : new ObfuscationStyleLowCase());
	}

	public static void setFieldNamingType(int i) {
		anInt519 = i;
	}

	public static IObfStyle method339() {
		return new ObfuscationStyleLowCase();
	}

}
