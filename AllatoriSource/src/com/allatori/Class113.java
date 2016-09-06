package com.allatori;

public class Class113 implements Interface22 {

    private static final int anInt1166 = 26;
    private long aLong1167 = 0L;


    public void method263() {
        this.aLong1167 = 0L;
    }

    public String method262() {
        String var1 = "";
        long var2 = this.aLong1167;

        do {
            String aString1165 = "abcdefghijklmnopqrstuvwxyz";
            var1 = var1 + aString1165.charAt((int) (var2 % 26L));
        } while ((var2 /= 26L) > 0L);

        ++this.aLong1167;
        return var1;
    }
}
