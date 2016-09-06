package com.allatori;

import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.util.BCELComparator;

public class Class75 implements BCELComparator {

    public int hashCode(Object var1) {
        FieldGen var2;
        return (var2 = (FieldGen) var1).getSignature().hashCode() ^ var2.getName().hashCode();
    }

    public boolean equals(Object var1, Object var2) {
        FieldGen var3 = (FieldGen) var1;
        FieldGen var4 = (FieldGen) var2;
        return var3.getName().equals(var4.getName()) && var3.getSignature().equals(var4.getSignature());
    }
}
