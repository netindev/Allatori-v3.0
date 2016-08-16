package com.allatori;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.generic.ClassGen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class Class20 {

    private static final String aString543 = ".tmp";
    private static Hashtable aHashtable544;


    public static void method446(String[] var0, String var1) throws Exception {
        byte[] var2 = new byte[65536];
        JarOutputStream var3 = new JarOutputStream(new FileOutputStream(var1 + ".tmp"));
        Hashtable var4 = new Hashtable();

        int var5;
        for (int var10000 = var5 = 0; var10000 < var0.length; var10000 = var5) {
            JarFile var6;
            Enumeration var7;
            for (Enumeration var12 = var7 = (var6 = new JarFile(var0[var5])).entries(); var12.hasMoreElements(); var12 = var7) {
                JarEntry var8 = (JarEntry) var7.nextElement();
                if (!var4.containsKey(var8.getName().toLowerCase())) {
                    var4.put(var8.getName().toLowerCase(), "");
                    InputStream var9 = var6.getInputStream(var8);
                    JarEntry var10 = new JarEntry(var8.getName());
                    var3.putNextEntry(var10);

                    int var11;
                    for (InputStream var13 = var9; (var11 = var13.read(var2)) > 0; var13 = var9) {
                        var3.write(var2, 0, var11);
                    }

                    var3.closeEntry();
                }
            }

            var6.close();
            ++var5;
        }

        if (!Tuning.isWeakStringEncryption()) {
            method448(var3);
        }

        var3.finish();
        var3.close();
        method452(var1 + ".tmp", var1);
    }

    private static void method447(String var0, JarOutputStream var1) throws Exception {
        String var2 = "";

        for (String var10000 = var0; var10000.indexOf(47) > 0; var10000 = var0) {
            var2 = var2 + var0.substring(0, var0.indexOf(47) + 1);
            var0 = var0.substring(var0.indexOf(47) + 1);
            if (!aHashtable544.containsKey(var2)) {
                aHashtable544.put(var2, ".tmp");
                var1.putNextEntry(new JarEntry(var2));
                var1.closeEntry();
            }
        }

    }

    private static void method448(JarOutputStream var0) {
        var0.setComment("Obfuscation by " + Info.name() + " " + Info.version() + "\n" + Info.website());
    }

    private static void method449(Vector var0, JarOutputStream var1, String var2) throws Exception {
        int var3;
        for (int var10000 = var3 = var0.size() - 1; var10000 >= 0; var10000 = var3) {
            method451((ClassGen) var0.get(var3), var1, var2);
            --var3;
        }

    }

    public static void method450(String var0, String var1, ClassStorage var2) throws Exception {
        aHashtable544 = new Hashtable();
        byte[] var3 = new byte[65536];
        String var4 = null;
        JarFile var5 = new JarFile(var0);
        JarOutputStream var6 = new JarOutputStream(new FileOutputStream(var1 + ".tmp"));
        Vector var7 = new Vector();

        Enumeration var8;
        for (Enumeration var10000 = var8 = var5.entries(); var10000.hasMoreElements(); var10000 = var8) {
            JarEntry var9;
            if (!(var9 = (JarEntry) var8.nextElement()).isDirectory()) {
                if (var9.getName().endsWith(".class")) {
                    if (var4 == null) {
                        var4 = method453(var5, var9);
                    }

                    String var10 = (var10 = var9.getName()).substring(var4.length(), var10.length() - 6).replace('/', '.');
                    ClassGen var11;
                    if ((var11 = var2.getClassGen(var10)) != null) {
                        var7.add(var11);
                        continue;
                    }
                }

                InputStream var15 = var5.getInputStream(var9);
                JarEntry var14 = new JarEntry(var9.getName());
                method447(var9.getName(), var6);
                var6.putNextEntry(var14);

                int var12;
                for (InputStream var13 = var15; (var12 = var13.read(var3)) > 0; var13 = var15) {
                    var6.write(var3, 0, var12);
                }

                var6.closeEntry();
            }
        }

        method449(var7, var6, var4);
        method449(var2.method674(), var6, var4);
        var5.close();
        if (!Tuning.isWeakStringEncryption()) {
            method448(var6);
        }

        var6.finish();
        var6.close();
        method452(var1 + ".tmp", var1);
    }

    private static void method451(ClassGen var0, JarOutputStream var1, String var2) throws Exception {
        String var3 = var0.getClassName();
        var3 = var2 + var3.replace('.', '/') + ".class";
        JarEntry var4 = new JarEntry(var3);
        method447(var3, var1);

        try {
            var1.putNextEntry(var4);
            var1.write(var0.getJavaClass().getBytes());
            var1.closeEntry();
        } catch (Exception var6) {
        }

    }

    private static void method452(String var0, String var1) {
        File var2 = new File(var0);
        File var3;
        if ((var3 = new File(var1)).exists() && !var3.delete()) {
            Logger.printWarning("Cannot delete \'" + var3.getPath() + "\'");
        }

        if (!var2.renameTo(var3)) {
            Logger.printWarning("Cannot rename \'" + var2.getPath() + "\' to \'" + var3.getPath() + "\'");
            Logger.printWarning("Resulting file is \'" + var2.getPath() + "\'");
        }

    }

    private static String method453(JarFile var0, JarEntry var1) {
        try {
            InputStream var2 = var0.getInputStream(var1);
            ClassGen var3 = new ClassGen((new ClassParser(var2, var1.getName())).parse());
            var2.close();
            return var1.getName().substring(0, var1.getName().length() - var3.getClassName().length() - 6);
        } catch (Exception var4) {
            return "";
        }
    }
}
