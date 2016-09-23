package com.allatori;

public class NameRepository {

	private final RenamingMap aRenamingMap_792;
	private final RenamingMap methodNameMap;
	private final IObfuscationStyle obfuscationStyle;
	private final RenamingMap signatureMap;
	private final RenamingMap constantNameMap;

	public static RenamingMap getMethodRenamingMap(NameRepository var0) {
		return var0.methodNameMap;
	}

	public static RenamingMap getConstantNamingMap(NameRepository var0) {
		return var0.constantNameMap;
	}

	public NameRepository(AnotherNameRepo var1, EmptyClass var2) {
		this(var1);
	}

	private NameRepository(AnotherNameRepo var1) {
		this.obfuscationStyle = ObfuscationStyleUtils.returnMethodNamingType();
		this.constantNameMap = new RenamingMap();
		this.signatureMap = new RenamingMap();
		this.methodNameMap = new RenamingMap();
		this.aRenamingMap_792 = new RenamingMap();
	}

	public static RenamingMap method1534(NameRepository nameRepository) {
		return nameRepository.aRenamingMap_792;
	}

	public static RenamingMap getSignatureNamingMap(NameRepository nameRepository) {
		return nameRepository.signatureMap;
	}

	public static IObfuscationStyle getObfuscationStyle(NameRepository nameRepository) {
		return nameRepository.obfuscationStyle;
	}
}
