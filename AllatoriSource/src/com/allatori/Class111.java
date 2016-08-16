package com.allatori;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.util.BCELComparator;

public class Class111 implements BCELComparator {

    public boolean equals(Object var1, Object var2) {
        Field var3 = (Field) var1;
        Field var4 = (Field) var2;
        return var3.getName().equals(var4.getName()) && var3.getSignature().equals(var4.getSignature());
    }

    public int hashCode(Object var1) {
        Field var2;
        return (var2 = (Field) var1).getSignature().hashCode() ^ var2.getName().hashCode();
    }
}
