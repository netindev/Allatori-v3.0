package com.allatori;

public class NameRepository {

	private final RenamingMap unkNameMap;
	private final RenamingMap methodNameMap;
	private final IObfStyle obfStyle;
	private final RenamingMap signatureMap;
	private final RenamingMap constantNameMap;

	public static RenamingMap getMethodRenamingMap(NameRepository nameRepository) {
		return nameRepository.methodNameMap;
	}

	public static RenamingMap getConstantNamingMap(NameRepository nameRepository) {
		return nameRepository.constantNameMap;
	}

	public NameRepository(Class172 unkClass, EmptyClass empClass) { /* Not used param */
		this(unkClass);
	}

	private NameRepository(Class172 unkClass) { /* Not used param */
		this.obfStyle = RenamingUtils.method333();
		this.constantNameMap = new RenamingMap();
		this.signatureMap = new RenamingMap();
		this.methodNameMap = new RenamingMap();
		this.unkNameMap = new RenamingMap();
	}

	public static RenamingMap method1534(NameRepository nameRepository) {
		return nameRepository.unkNameMap;
	}

	public static RenamingMap getSignatureNamingMap(NameRepository nameRepository) {
		return nameRepository.signatureMap;
	}

	public static IObfStyle getObfStyle(NameRepository nameRepository) {
		return nameRepository.obfStyle;
	}
}
