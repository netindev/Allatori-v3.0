package com.allatori;

public class ObfuscationStyleUtils {
	
	/* OK */

	private static int methodNamingType = 2;
	private static int fieldNamingType = 2;

	public static void setMethodNamingType(int toSet) {
		methodNamingType = toSet;
	}

	public static IObfuscationStyle returnMethodNamingType() {
		return methodNamingType == 1 ? new ObfuscationStyleLow()
				: (methodNamingType == 3 ? new Keywords() : new ObfuscationStyleMixed());
	}

	public static IObfuscationStyle getSecondObfStyleLow() {
		return new ObfuscationStyleLow();
	}

	public static IObfuscationStyle geObfStyleLow() {
		return new ObfuscationStyleLow();
	}

	public static IObfuscationStyle returnFieldNamingType() {
		return fieldNamingType == 1 ? new ObfuscationStyleLow()
				: (fieldNamingType == 3 ? new Keywords() : new ObfuscationStyleMixed());
	}

	public static IObfuscationStyle variableNamingType() {
		return LocalVariables.getLocalVariableNamingType() == 2 ? new ObfuscationStyleLow()
				: (LocalVariables.getLocalVariableNamingType() == 1 ? new ObfuscationStyleUnk()
						: new ObfuscationStyleLow());
	}

	public static void setFieldNamingType(int toSet) {
		fieldNamingType = toSet;
	}

	public static IObfuscationStyle getThirdObfStyleLow() {
		return new ObfuscationStyleLow();
	}

}
