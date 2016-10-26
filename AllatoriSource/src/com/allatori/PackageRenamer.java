package com.allatori;

public class PackageRenamer {

	/* OK */

	private final RenamingMap secondNameRepositoryLow;
	private final RenamingMap nameRepositoryLow;
	private final IObfuscationStyle obfuscationStyle;
	private final RenamingMap thirdNameRepositoryLow;

	public static RenamingMap getNameRepositoryLow(PackageRenamer nameRepositoryLow) {
		return nameRepositoryLow.nameRepositoryLow;
	}

	private PackageRenamer(
			DefaultRenamer anotherNameRepo) { /* Not used param */
		this.obfuscationStyle = ObfuscationStyleUtils.getThirdObfStyleLow();
		this.secondNameRepositoryLow = new RenamingMap();
		this.thirdNameRepositoryLow = new RenamingMap();
		this.nameRepositoryLow = new RenamingMap();
	}

	public static RenamingMap getSecondNameRepositoryLow(PackageRenamer nameRepositoryLow) {
		return nameRepositoryLow.secondNameRepositoryLow;
	}

	public PackageRenamer(DefaultRenamer anotherNameRepo,
			EmptyClass emptyClass) { /* Not used param */
		this(anotherNameRepo);
	}

	public static IObfuscationStyle getObfuscationStyle(PackageRenamer nameRepositoryLow) {
		return nameRepositoryLow.obfuscationStyle;
	}

	public static RenamingMap getThirdNameRepositoryLow(PackageRenamer nameRepositoryLow) {
		return nameRepositoryLow.thirdNameRepositoryLow;
	}
}
