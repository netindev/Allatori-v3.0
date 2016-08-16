package com.allatori;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class Class55 {

    public static String method688(File var0) throws Exception {
        StringBuilder var2 = new StringBuilder((int) var0.length());
        BufferedReader var4;
        BufferedReader var10000;
        if ("UTF-8" != null) {
            var4 = new BufferedReader(new InputStreamReader(new FileInputStream(var0), "UTF-8"));
            var10000 = var4;
        } else {
            var10000 = var4 = new BufferedReader(new FileReader(var0));
        }

        int var3;
        while ((var3 = var10000.read()) != -1) {
            var2.append((char) var3);
            var10000 = var4;
        }

        var4.close();
        return var2.toString();
    }

    public static void method689(File var0, File var1) throws IOException {
        FileChannel var2 = null;
        FileChannel var3 = null;

        try {
            var2 = (new FileInputStream(var0)).getChannel();
            var3 = (new FileOutputStream(var1)).getChannel();
            MappedByteBuffer var4 = var2.map(MapMode.READ_ONLY, 0L, var2.size());
            var3.write(var4);
        } finally {
            if (var2 != null) {
                var2.close();
            }

            if (var3 != null) {
                var3.close();
            }

        }

    }

    public static void method690(File var0, String var2) throws Exception {
        BufferedWriter var3;
        if ("UTF-8" != null) {
            var3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(var0), "UTF-8"));
        } else {
            var3 = new BufferedWriter(new FileWriter(var0));
        }

        var3.write(var2);
        var3.close();
    }
}
