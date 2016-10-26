package com.allatori;

public class Class101 {

	/* OK */

	private final RenamingMap secondAnotherNameRepoLow;
	private final RenamingMap anotherNameRepoLow;
	private final IObfuscationStyle obfuscationStyle;

	public static RenamingMap getAnotherNameRepoLow(Class101 anotherNameRepoLow) {
		return anotherNameRepoLow.anotherNameRepoLow;
	}

	public static IObfuscationStyle getObfuscationStyle(Class101 anotherNameRepoLow) {
		return anotherNameRepoLow.obfuscationStyle;
	}

	private Class101(
			DefaultRenamer anotherNameRepo) { /* Not used param */
		this.obfuscationStyle = ObfuscationStyleUtils.geObfStyleLow();
		this.anotherNameRepoLow = new RenamingMap();
		this.secondAnotherNameRepoLow = new RenamingMap();
	}

	public static RenamingMap method1522(Class101 anotherNameRepoLow) {
		return anotherNameRepoLow.secondAnotherNameRepoLow;
	}

	public Class101(DefaultRenamer anotherNameRepo,
			EmptyClass emptyClass) { /* Not used param */
		this(anotherNameRepo);
	}
}
