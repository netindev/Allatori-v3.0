package com.allatori;

import org.apache.bcel.util.BCELComparator;
import org.apache.bcel.classfile.JavaClass;

public class Class86 implements BCELComparator {

    public int hashCode(Object var1) {
        return ((JavaClass) var1).getClassName().hashCode();
    }

    public boolean equals(Object var1, Object var2) {
        JavaClass var3 = (JavaClass) var1;
        JavaClass var4 = (JavaClass) var2;
        return var3.getClassName().equals(var4.getClassName());
    }
}
