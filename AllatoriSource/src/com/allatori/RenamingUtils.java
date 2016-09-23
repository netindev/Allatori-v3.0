package com.allatori;

public class RenamingUtils {

	private final IObfuscationStyle obfStyle;

	public RenamingUtils(AnotherNameRepo var1, EmptyClass var2) {
		this(var1);
	}

	private RenamingUtils(AnotherNameRepo var1) {
		this.obfStyle = ObfuscationStyleUtils.variableNamingType();
	}

	public static IObfuscationStyle getObfSyle(RenamingUtils renamingUtils) {
		return renamingUtils.obfStyle;
	}
}
