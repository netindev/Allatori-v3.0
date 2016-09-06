package com.allatori;

import org.apache.bcel.util.BCELComparator;
import org.apache.bcel.classfile.Constant;

public class Class93 implements BCELComparator {

    public int hashCode(Object var1) {
        return ((Constant) var1).toString().hashCode();
    }

    public boolean equals(Object var1, Object var2) {
        Constant var3 = (Constant) var1;
        Constant var4 = (Constant) var2;
        return var3.toString().equals(var4.toString());
    }
}
