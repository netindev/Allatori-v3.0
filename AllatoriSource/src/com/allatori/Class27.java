package com.allatori;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.generic.ClassGen;

import com.allatori.clazz.ClassStorage;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Class27 {

    private static ClassGen parseClass(InputStream var0, String var1) throws Exception {
        ClassGen var2 = new ClassGen((new ClassParser(var0, var1)).parse());
        var0.close();
        return var2;
    }

    public static void parseClass(String var0, ClassStorage var1) throws Exception {
        JarFile var2;
        Enumeration var3;
        for (Enumeration var10000 = var3 = (var2 = new JarFile(var0)).entries(); var10000.hasMoreElements(); var10000 = var3) {
            JarEntry var4;
            if (!(var4 = (JarEntry) var3.nextElement()).isDirectory() && var4.getName().endsWith(".class")) {
                try {
                    var1.method672(parseClass(var2.getInputStream(var4), var4.getName()));
                } catch (Exception var6) {
                    Logger.printError("Cannot parse class " + var4.getName());
                }
            }
        }

        var2.close();
    }
}
