package com.allatori;

import org.apache.bcel.util.BCELComparator;
import org.apache.bcel.classfile.Method;

public class Class25 implements BCELComparator {

    public boolean equals(Object var1, Object var2) {
        Method var3 = (Method) var1;
        Method var4 = (Method) var2;
        return var3.getName().equals(var4.getName()) && var3.getSignature().equals(var4.getSignature());
    }

    public int hashCode(Object var1) {
        Method var2;
        return (var2 = (Method) var1).getSignature().hashCode() ^ var2.getName().hashCode();
    }
}
