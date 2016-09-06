package com.allatori;

public class NameRepository {

	private final RenamingMap aRenamingMap_792;
	private final RenamingMap methodNameMap;
	private final Interface22 anInterface22_794;
	private final RenamingMap signatureMap;
	private final RenamingMap constantNameMap;

	public static RenamingMap getMethodRenamingMap(NameRepository var0) {
		return var0.methodNameMap;
	}

	public static RenamingMap getConstantNamingMap(NameRepository var0) {
		return var0.constantNameMap;
	}

	public NameRepository(Class172 var1, EmptyClass var2) {
		this(var1);
	}

	private NameRepository(Class172 var1) {
		this.anInterface22_794 = Unknown.method333();
		this.constantNameMap = new RenamingMap();
		this.signatureMap = new RenamingMap();
		this.methodNameMap = new RenamingMap();
		this.aRenamingMap_792 = new RenamingMap();
	}

	public static RenamingMap method1534(NameRepository var0) {
		return var0.aRenamingMap_792;
	}

	public static RenamingMap getSignatureNamingMap(NameRepository var0) {
		return var0.signatureMap;
	}

	public static Interface22 method1536(NameRepository var0) {
		return var0.anInterface22_794;
	}
}
