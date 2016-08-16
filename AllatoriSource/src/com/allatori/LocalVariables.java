package com.allatori;

import org.apache.bcel.generic.*;

public class LocalVariables {

    public static final int anInt526 = 2;
    public static final int anInt527 = 4;
    private static ObfuscationConstraint aObfuscationConstraint_528 = new ObfuscationConstraint(1);
    public static final int anInt529 = 3;
    public static final int anInt530 = 1;
    private static int anInt531 = 1;
    public static final int anInt532 = 5;


    public static int method369(ClassStorage var0, ClassGen var1, MethodGen var2) {
        return aObfuscationConstraint_528.getType(var0, var1, var2);
    }

    public static void setLocalVariableNamingType(int var0) {
        anInt531 = var0;
    }

    public static void addObfuscationTypeConstraint(ObfuscationTypeConstraint var0) {
        aObfuscationConstraint_528.addConstraint(var0);
    }

    public static int method372() {
        return anInt531;
    }
}
