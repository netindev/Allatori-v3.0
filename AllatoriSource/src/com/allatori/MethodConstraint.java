package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.MethodGen;

public class MethodConstraint {

    private String aString698;
    private String aString699;
    private int anInt700;
    private String aString701;


    public boolean apply(Method var1) {
        return !Class115.method1399(var1, this.anInt700) ? false : (!var1.getName().matches(this.aString698) ? false : (!var1.getReturnType().toString().matches(this.aString701) ? false : Class115.method1388(var1.getArgumentTypes(), this.aString699)));
    }

    private void parseTemplate(String var1) throws TemplateException {
        String[] var2;
        if ((var2 = var1.split("\\(|\\)")).length != 2 && (var2.length != 1 || !var1.endsWith("()"))) {
            throw new TemplateException("Invalid template.");
        } else {
            if (var2.length == 1) {
                this.aString699 = "";
            } else {
                String var3 = var2[1];
                this.aString699 = Class115.method1396(var3);
            }

            String[] var6;
            if ((var6 = (var1 = var2[0]).split("\\s+")) != null && var6.length != 0) {
                this.aString698 = Class115.method1395(var6[var6.length - 1]);
                if (var6.length > 1 && !Class115.method1389(var6[var6.length - 2])) {
                    this.aString701 = Class115.parsePattern(var6[var6.length - 2]);
                } else {
                    this.aString701 = ".*";
                }

                String var4 = "";

                int var5;
                for (int var10000 = var5 = var6.length - 3; var10000 >= 0; var10000 = var5) {
                    var4 = var4 + " " + var6[var5];
                    --var5;
                }

                if (var6.length >= 2 && Class115.method1389(var6[var6.length - 2])) {
                    var4 = var4 + " " + var6[var6.length - 2];
                }

                if (var4.equals("")) {
                    var4 = "*";
                }

                this.anInt700 = Class115.parseAccess(var4);
            } else {
                throw new TemplateException("Invalid template.");
            }
        }
    }

    public MethodConstraint(String var1) throws TemplateException {
        this.parseTemplate(var1);
    }

    public boolean apply(MethodGen var1) {
        return !Class115.method1399(var1, this.anInt700) ? false : (!var1.getName().matches(this.aString698) ? false : (!var1.getReturnType().toString().matches(this.aString701) ? false : Class115.method1388(var1.getArgumentTypes(), this.aString699)));
    }
}
