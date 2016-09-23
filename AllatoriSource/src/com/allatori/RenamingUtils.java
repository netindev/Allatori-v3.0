package com.allatori;

public class RenamingUtils {

	private final IObfuscationStyle obfStyle;

	public RenamingUtils(Class172 var1, EmptyClass var2) {
		this(var1);
	}

	private RenamingUtils(Class172 var1) {
		this.obfStyle = ObfuscationStyleUtils.variableNamingType();
	}

	public static IObfuscationStyle getObfSyle(RenamingUtils renamingUtils) {
		return renamingUtils.obfStyle;
	}
}
