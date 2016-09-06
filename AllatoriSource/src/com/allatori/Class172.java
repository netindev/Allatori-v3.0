package com.allatori;

public class Class172 {

	private final Class141 aClass141_871;
	private final NameRepository aNameRepository_873;
	private final RenamingUtils aClass156_874;
	private final Class159 aClass159_875;
	private final Class165 aClass165_876;

	public static NameRepository method1707(Class172 var0) {
		return var0.aNameRepository_873;
	}

	public static Class165 method1708(Class172 var0) {
		return var0.aClass165_876;
	}

	private Class172(Renamer var1) {
		this.aClass141_871 = new Class141(this, null);
		this.aClass165_876 = new Class165(this, null);
		this.aNameRepository_873 = new NameRepository(this, null);
		this.aClass159_875 = new Class159(this, null);
		this.aClass156_874 = new RenamingUtils(this, null);
	}

	public Class172(Renamer var1, EmptyClass var2) {
		this(var1);
	}

	public static RenamingUtils method1709(Class172 var0) {
		return var0.aClass156_874;
	}

	public static Class159 method1710(Class172 var0) {
		return var0.aClass159_875;
	}

	public static Class141 method1711(Class172 var0) {
		return var0.aClass141_871;
	}
}
