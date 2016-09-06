package com.allatori;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.util.BCELComparator;

public class Class49 implements BCELComparator {

    public int hashCode(Object var1) {
        return ((ClassGen) var1).getClassName().hashCode();
    }

    public boolean equals(Object var1, Object var2) {
        ClassGen var3 = (ClassGen) var1;
        ClassGen var4 = (ClassGen) var2;
        return var3.getClassName().equals(var4.getClassName());
    }
}
