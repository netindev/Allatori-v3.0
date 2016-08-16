package com.allatori.string;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

import com.allatori.Class95;
import com.allatori.obfuscate.ObfuscationType;
import com.allatori.util.InitUtils;

public class StringObfuscationLayer2 implements ObfuscationType {

    public Class95 aClass95_1146 = new Class95();


    public void terminate() {
    }

    private void patch(ClassGen var1) {
        Method[] var2 = var1.getMethods();

        int var3;
        for (int var10000 = var3 = 0; var10000 < var2.length; var10000 = var3) {
            Method var4;
            if ((var4 = var2[var3]).getCode() != null) {
                MethodGen var5;
                InstructionHandle var7 = (var5 = InitUtils.createMethodGen(var4, var1.getClassName(), var1.getConstantPool(), var1.getConstantPool().getConstantPool())).getInstructionList().getStart();

                for (InstructionHandle var12 = var7; var12 != null; var12 = var7) {
                    if (var7.getInstruction() instanceof LDC_W) {
                        LDC_W var8 = (LDC_W) var7.getInstruction();
                        Constant var9 = var1.getConstantPool().getConstant(var8.getIndex());
                        if (var9 instanceof ConstantString) {
                            String var10 = ((ConstantUtf8) var1.getConstantPool().getConstant(((ConstantString) var9).getStringIndex())).getBytes();
                            if (var10 != null && var10.startsWith(StringObfuscationLayer1.aString1150)) {
                                var10 = var10.substring(StringObfuscationLayer1.aString1150.length());
                                String var11 = var1.getClassName() + var5.getName();
                                var8.setIndex(var1.getConstantPool().addString(StringObfuscationLayer1.encryptHard(var10, var11)));
                            }
                        }
                    }

                    var7 = var7.getNext();
                }

                var5.setMaxStack();
                var1.replaceMethod(var4, var5.getMethod());
            }

            ++var3;
        }

    }

    public void execute(ClassGen var1) {
        try {
            this.patch(var1);
        } catch (Exception var3) {
            this.aClass95_1146.method1258(var1);
            this.patch(var1);
        }

    }
}
