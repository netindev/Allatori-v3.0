package com.allatori;

public class Class94 implements Interface22 {

    private Class105 aClass105_1159 = new Class105();
    private int anInt1160 = 0;
    private String[] aStringArray1161;


    public String method262() {
        return this.anInt1160 < this.aStringArray1161.length ? this.aStringArray1161[this.anInt1160++] : this.aClass105_1159.method262();
    }

    public Class94(String[] var1) {
        this.aStringArray1161 = var1;
    }

    public void method263() {
        this.anInt1160 = 0;
        this.aClass105_1159.method263();
    }
}
