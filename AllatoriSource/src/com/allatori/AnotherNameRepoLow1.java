package com.allatori;

public class AnotherNameRepoLow1 {

	/* OK */

	private final RenamingMap secondNameRepositoryLow;
	private final RenamingMap nameRepositoryLow;
	private final IObfuscationStyle obfuscationStyle;
	private final RenamingMap thirdNameRepositoryLow;

	public static RenamingMap getNameRepositoryLow(AnotherNameRepoLow1 nameRepositoryLow) {
		return nameRepositoryLow.nameRepositoryLow;
	}

	private AnotherNameRepoLow1(
			AnotherNameRepo anotherNameRepo) { /* Not used param */
		this.obfuscationStyle = ObfuscationStyleUtils.getThirdObfStyleLow();
		this.secondNameRepositoryLow = new RenamingMap();
		this.thirdNameRepositoryLow = new RenamingMap();
		this.nameRepositoryLow = new RenamingMap();
	}

	public static RenamingMap getSecondNameRepositoryLow(AnotherNameRepoLow1 nameRepositoryLow) {
		return nameRepositoryLow.secondNameRepositoryLow;
	}

	public AnotherNameRepoLow1(AnotherNameRepo anotherNameRepo,
			EmptyClass emptyClass) { /* Not used param */
		this(anotherNameRepo);
	}

	public static IObfuscationStyle getObfuscationStyle(AnotherNameRepoLow1 nameRepositoryLow) {
		return nameRepositoryLow.obfuscationStyle;
	}

	public static RenamingMap getThirdNameRepositoryLow(AnotherNameRepoLow1 nameRepositoryLow) {
		return nameRepositoryLow.thirdNameRepositoryLow;
	}
}
