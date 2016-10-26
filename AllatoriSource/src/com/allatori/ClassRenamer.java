package com.allatori;

public class ClassRenamer {

	/* OK */

	private final IObfuscationStyle obfuscationStyle;
	private final RenamingMap secondAnotherNameRepoField;
	private final RenamingMap thirdAnotherNameRepoField;
	private final RenamingMap anotherNameRepoField;

	public static RenamingMap getAnotherNameRepoField(ClassRenamer anotherNameRepoField) {
		return anotherNameRepoField.anotherNameRepoField;
	}

	public ClassRenamer(DefaultRenamer anotherNameRepoField,
			EmptyClass emptyClass) { /* Not used param */
		this(anotherNameRepoField);
	}

	public static RenamingMap getSecondAnotherNameRepoField(ClassRenamer anotherNameRepoField) {
		return anotherNameRepoField.secondAnotherNameRepoField;
	}

	public static IObfuscationStyle getObfuscationStyle(ClassRenamer anotherNameRepoField) {
		return anotherNameRepoField.obfuscationStyle;
	}

	private ClassRenamer(
			DefaultRenamer anotherNameRepo) { /* Not used param */
		this.obfuscationStyle = ObfuscationStyleUtils.returnFieldNamingType();
		this.secondAnotherNameRepoField = new RenamingMap();
		this.anotherNameRepoField = new RenamingMap();
		this.thirdAnotherNameRepoField = new RenamingMap();
	}

	public static RenamingMap getThirdAnotherNameRepoField(ClassRenamer anotherNameRepoField) {
		return anotherNameRepoField.thirdAnotherNameRepoField;
	}
}
