package com.allatori.obfuscate.opt;

import com.allatori.ClassStorage;
import com.allatori.ObfuscationConstraint;
import com.allatori.ObfuscationTypeConstraint;
import org.apache.bcel.generic.ClassGen;

public class AntiJD {

    private static int antiJDOption = 0;
    private static ObfuscationConstraint obfuscationConstraint = new ObfuscationConstraint(0);


    public static void addObfuscationTypeConstraint(ObfuscationTypeConstraint var0) {
        obfuscationConstraint.addConstraint(var0);
    }

    public static int getType(ClassStorage storage, ClassGen _cg) {
        return obfuscationConstraint.getType(storage, _cg, null);
    }

    public static int getType() {
        return antiJDOption;
    }

    public static void setType(int var0) {
        antiJDOption = var0;
    }
}
