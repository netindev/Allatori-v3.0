package com.allatori;

public class AnotherNameRepoField {

	/* OK */

	private final IObfuscationStyle obfuscationStyle;
	private final RenamingMap secondAnotherNameRepoField;
	private final RenamingMap thirdAnotherNameRepoField;
	private final RenamingMap anotherNameRepoField;

	public static RenamingMap getAnotherNameRepoField(AnotherNameRepoField anotherNameRepoField) {
		return anotherNameRepoField.anotherNameRepoField;
	}

	public AnotherNameRepoField(AnotherNameRepo anotherNameRepoField,
			EmptyClass emptyClass) { /* Not used param */
		this(anotherNameRepoField);
	}

	public static RenamingMap getSecondAnotherNameRepoField(AnotherNameRepoField anotherNameRepoField) {
		return anotherNameRepoField.secondAnotherNameRepoField;
	}

	public static IObfuscationStyle getObfuscationStyle(AnotherNameRepoField anotherNameRepoField) {
		return anotherNameRepoField.obfuscationStyle;
	}

	private AnotherNameRepoField(
			AnotherNameRepo anotherNameRepo) { /* Not used param */
		this.obfuscationStyle = ObfuscationStyleUtils.returnFieldNamingType();
		this.secondAnotherNameRepoField = new RenamingMap();
		this.anotherNameRepoField = new RenamingMap();
		this.thirdAnotherNameRepoField = new RenamingMap();
	}

	public static RenamingMap getThirdAnotherNameRepoField(AnotherNameRepoField anotherNameRepoField) {
		return anotherNameRepoField.thirdAnotherNameRepoField;
	}
}
