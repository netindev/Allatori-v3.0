package com.allatori;

public class NameRepository {

    private RenamingMap aRenamingMap_792;
    private RenamingMap methodNameMap;
    private Interface22 anInterface22_794;
    private RenamingMap signatureMap;
    private RenamingMap constantNameMap;


    // $FF: synthetic method
    public static RenamingMap getMethodRenamingMap(NameRepository var0) {
        return var0.methodNameMap;
    }

    // $FF: synthetic method
    public static RenamingMap getConstantNamingMap(NameRepository var0) {
        return var0.constantNameMap;
    }

    // $FF: synthetic method
    public NameRepository(Class172 var1, Class178 var2) {
        this(var1);
    }

    private NameRepository(Class172 var1) {
        Class172 aClass172_795 = var1;
        this.anInterface22_794 = Unknown.method333();
        this.constantNameMap = new RenamingMap();
        this.signatureMap = new RenamingMap();
        this.methodNameMap = new RenamingMap();
        this.aRenamingMap_792 = new RenamingMap();
    }

    // $FF: synthetic method
    public static RenamingMap method1534(NameRepository var0) {
        return var0.aRenamingMap_792;
    }

    // $FF: synthetic method
    public static RenamingMap getSignatureNamingMap(NameRepository var0) {
        return var0.signatureMap;
    }

    // $FF: synthetic method
    public static Interface22 method1536(NameRepository var0) {
        return var0.anInterface22_794;
    }
}
