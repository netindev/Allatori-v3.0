package com.allatori;


public class StringDecryptionClass {

    public static String decrypt(String var0) {

        char[] var10002 = new char[var0.length()];
        int var3;
        int var10003 = var3 = var0.length() - 1;
        for (char var2 = 85; var10003 >= 0; var10003 = var3) {
            char var10006 = (char) (var0.charAt(var3) ^ var2);
            var2 = (char) ((char) (var3 ^ var2) & 63);
            (var10002)[var3] = var10006;
            --var3;
            if (var3 < 0) {
                break;
            }

            var10006 = (char) (var0.charAt(var3) ^ var2);
            var2 = (char) ((char) (var3 ^ var2) & 63);
            (var10002)[var3] = var10006;
            --var3;
        }
        return new String(var10002);
    }

    public static String decrypt2(String var0) {
        StackTraceElement var10003 = (new Exception()).getStackTrace()[1];
        String var10004 = var10003.getClassName() + var10003.getMethodName();
        int var3;
        int var4 = var3 = var10004.length() - 1;
        int var10001 = var0.length();
        char[] var5 = new char[var10001];
        char var6 = 85;

        for (int var7 = var10001 - 1; var7 >= 0; --var7) {
            char var10005 = var10004.charAt(var4);
            var5[var7] = (char) (var10005 ^ var0.charAt(var7) ^ var6);
            var6 = (char) (63 & (var6 ^ var7 ^ var10005));
            --var4;
            if (var4 < 0) {
                var4 = var3;
            }
        }

        return new String(var5);
    }
}
