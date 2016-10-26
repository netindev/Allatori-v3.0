package com.allatori;

public class RenamingUtils {

	/* OK */

	private final IObfuscationStyle obfStyle;

	public RenamingUtils(DefaultRenamer anotherNameRepo, EmptyClass emptyClass) {
		this(anotherNameRepo);
	}

	private RenamingUtils(DefaultRenamer anotherNameRepo) {
		this.obfStyle = ObfuscationStyleUtils.variableNamingType();
	}

	public static IObfuscationStyle getObfSyle(RenamingUtils renamingUtils) {
		return renamingUtils.obfStyle;
	}
}
