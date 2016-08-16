package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

import com.allatori.util.InitUtils;

public class Class171 {

    private Method aMethod__868;
    private ClassGen aClass85_Sub2_869;
    private MethodGen aMethodGen_870;


    // $FF: synthetic method
    public static MethodGen method1704(Class171 var0) {
        return var0.aMethodGen_870;
    }

    // $FF: synthetic method
    public static ClassGen method1705(Class171 var0) {
        return var0.aClass85_Sub2_869;
    }

    public Class171(ClassGen var1, Method var2) {
        this.aClass85_Sub2_869 = var1;
        this.aMethod__868 = var2;
        this.aMethodGen_870 = InitUtils.createMethodGen(var2, var1.getClassName(), var1.getConstantPool(), var1.getConstantPool().getConstantPool());
    }

    // $FF: synthetic method
    public static Method method1706(Class171 var0) {
        return var0.aMethod__868;
    }
}
