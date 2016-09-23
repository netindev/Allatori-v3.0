package com.allatori;

public class AnotherNameRepoLow2 {
	
	/* OK */

	private final RenamingMap secondAnotherNameRepoLow;
	private final RenamingMap anotherNameRepoLow;
	private final IObfuscationStyle obfuscationStyle;

	public static RenamingMap getAnotherNameRepoLow(AnotherNameRepoLow2 anotherNameRepoLow) {
		return anotherNameRepoLow.anotherNameRepoLow;
	}

	public static IObfuscationStyle getObfuscationStyle(AnotherNameRepoLow2 anotherNameRepoLow) {
		return anotherNameRepoLow.obfuscationStyle;
	}

	private AnotherNameRepoLow2(AnotherNameRepo anotherNameRepo) { /* Not used param */
		this.obfuscationStyle = ObfuscationStyleUtils.geObfStyleLow();
		this.anotherNameRepoLow = new RenamingMap();
		this.secondAnotherNameRepoLow = new RenamingMap();
	}

	public static RenamingMap method1522(AnotherNameRepoLow2 anotherNameRepoLow) {
		return anotherNameRepoLow.secondAnotherNameRepoLow;
	}

	public AnotherNameRepoLow2(AnotherNameRepo anotherNameRepo, EmptyClass emptyClass) { /* Not used param */
		this(anotherNameRepo);
	}
}
