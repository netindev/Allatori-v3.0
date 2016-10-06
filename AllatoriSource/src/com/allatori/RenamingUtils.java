package com.allatori;

public class RenamingUtils {

	/* OK */

	private final IObfuscationStyle obfStyle;

	public RenamingUtils(AnotherNameRepo anotherNameRepo, EmptyClass emptyClass) {
		this(anotherNameRepo);
	}

	private RenamingUtils(AnotherNameRepo anotherNameRepo) {
		this.obfStyle = ObfuscationStyleUtils.variableNamingType();
	}

	public static IObfuscationStyle getObfSyle(RenamingUtils renamingUtils) {
		return renamingUtils.obfStyle;
	}
}
