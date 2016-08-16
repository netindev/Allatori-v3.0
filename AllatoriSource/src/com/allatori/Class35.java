package com.allatori;

import org.apache.bcel.generic.ClassGen;

import java.util.Comparator;

public class Class35 implements Comparator {


    public Class35() {
    }

    public int compare(Object var1, Object var2) {
        boolean var3 = ((ClassGen) var1).isInterface();
        boolean var4 = ((ClassGen) var2).isInterface();
        return var3 == var4 ? 0 : (var3 ? -1 : 1);
    }
}
