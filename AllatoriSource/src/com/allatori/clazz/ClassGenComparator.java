package com.allatori.clazz;

import org.apache.bcel.generic.ClassGen;

import java.util.Comparator;

public class ClassGenComparator implements Comparator {


    public ClassGenComparator() {
    }

    public int compare(Object var1, Object var2) {
        String var3 = ((ClassGen) var1).getClassName();
        String var4 = ((ClassGen) var2).getClassName();
        return var3.compareTo(var4);
    }
}
