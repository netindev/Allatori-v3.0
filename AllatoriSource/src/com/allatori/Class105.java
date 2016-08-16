package com.allatori;

public class Class105 implements Interface22 {
	
	// arryn
	
    private String aString1162 = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    private long aLong1163;
    private int anInt1164;


    public Class105() {
        this.anInt1164 = this.aString1162.length();
        this.aLong1163 = 0L;
    }

    public void method263() {
        this.aLong1163 = 0L;
    }

    public String method262() {
        String var1 = "";
        long var2 = this.aLong1163;

        do {
            var1 = var1 + this.aString1162.charAt((int) (var2 % (long) this.anInt1164));
        } while ((var2 /= (long) this.anInt1164) > 0L);

        ++this.aLong1163;
        return var1;
    }
}
