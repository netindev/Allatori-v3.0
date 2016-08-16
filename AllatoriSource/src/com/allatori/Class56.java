package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.Type;

import com.allatori.clazz.ClassGenComparator;
import com.allatori.clazz.ClassStorage;

import java.lang.reflect.Modifier;
import java.util.*;

public class Class56 {

    private static final Vector aVector605 = new Vector();
    private static final String aString606 = "-=404=-";
    private Hashtable aHashtable607 = new Hashtable();
    private ClassStorage aClassStorage_608;
    private Vector aVector609;
    private Hashtable aHashtable610 = new Hashtable();
    private static final Set aSet611 = new TreeSetImpl();
    private Set aSet612 = new TreeSet();


    public Vector method691(String var1) {
        Vector var2;
        return (var2 = (Vector) this.aHashtable607.get(var1)) != null ? var2 : aVector605;
    }

    private Set method692(Set var1, Set var2) {
        TreeSet var3 = new TreeSet();
        Set var4 = var1.size() < var2.size() ? var1 : var2;
        Set var5 = var1.size() < var2.size() ? var2 : var1;

        Iterator var6;
        for (Iterator var10000 = var6 = var4.iterator(); var10000.hasNext(); var10000 = var6) {
            Object var7 = var6.next();
            if (var5.contains(var7)) {
                var3.add(var7);
            }
        }

        return var3;
    }

    private Set method693(Set var1) {
        if (var1.contains("-=404=-")) {
            return aSet611;
        } else {
            TreeSet var2 = new TreeSet();

            Iterator var3;
            for (Iterator var10000 = var3 = var1.iterator(); var10000.hasNext(); var10000 = var3) {
                String var4 = (String) var3.next();
                if (this.aClassStorage_608.getClassGen(var4) == null) {
                    int var7;
                    try {
                        java.lang.reflect.Method[] var6;
                        for (int var10 = var7 = (var6 = Class.forName(var4, false, this.aClassStorage_608.method675()).getDeclaredMethods()).length - 1; var10 >= 0; var10 = var7) {
                            java.lang.reflect.Method var8 = var6[var7];
                            if (this.method706(var8)) {
                                var2.add(var8.getName() + "&" + Type.getSignature(var8));
                            }

                            --var7;
                        }
                    } catch (ClassNotFoundException var9) {
                    }
                }
            }

            return var2;
        }
    }

    public boolean method694(String var1, String var2, String var3) {
        return this.aSet612.contains(var1 + "&" + var2 + "&" + var3);
    }

    private void method695(ClassGen var1) {
        this.method701(var1.getSuperclassName(), var1);

        String[] var2;
        int var3;
        for (int var10000 = var3 = (var2 = var1.getInterfaceNames()).length - 1; var10000 >= 0; var10000 = var3) {
            this.method701(var2[var3], var1);
            --var3;
        }

    }

    private Set method696(ClassGen var1) {
        TreeSet var2 = new TreeSet();

        Method[] var3;
        int var4;
        for (int var10000 = var4 = (var3 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var4) {
            Method var5 = var3[var4];
            if (this.method698(var5)) {
                var2.add(var5.getName() + "&" + var5.getSignature());
            }

            --var4;
        }

        return var2;
    }

    private void method697() {
        Hashtable var1 = new Hashtable();

        int var2;
        for (int var10000 = var2 = 0; var10000 < this.aVector609.size(); var10000 = var2) {
            ClassGen var3 = (ClassGen) this.aVector609.get(var2);
            var1.put(var3, "");
            Vector var4 = this.method691(var3.getClassName());

            int var5;
            for (var10000 = var5 = 0; var10000 < var4.size(); var10000 = var5) {
                ClassGen var6 = (ClassGen) var4.get(var5);
                if (var1.containsKey(var6)) {
                    Logger.printError("Incorrect classes order");
                }

                ++var5;
            }

            ++var2;
        }

    }

    private boolean method698(Method var1) {
        return !var1.isPrivate() && !var1.isStatic();
    }

    public void method699() {
        Iterator var1;
        for (Iterator var10000 = var1 = this.aClassStorage_608.method671(); var10000.hasNext(); var10000 = var1) {
            ClassGen var2 = (ClassGen) var1.next();
            this.method695(var2);
            this.method707(var2);
        }

        this.method705();
    }

    private void method700(Set var1, Set var2) {
        Iterator var3;
        for (Iterator var10000 = var3 = var1.iterator(); var10000.hasNext(); var10000 = var3) {
            String var4 = (String) var3.next();
            ClassGen var5;
            if (!"-=404=-".equals(var4) && (var5 = this.aClassStorage_608.getClassGen(var4)) != null && var5.isInterface()) {
                Set var6 = this.method696(var5);
                Iterator var8 = this.method692(var6, var2).iterator();

                for (var10000 = var8; var10000.hasNext(); var10000 = var8) {
                    this.aSet612.add(var5.getClassName() + "&" + var8.next());
                }
            }
        }

    }

    public Class56(ClassStorage var1) {
        this.aClassStorage_608 = var1;
    }

    private void method701(String var1, ClassGen var2) {
        if (this.aClassStorage_608.getClassGen(var1) != null) {
            Vector var3;
            if ((var3 = (Vector) this.aHashtable607.get(var1)) == null) {
                var3 = new Vector();
            }

            var3.add(var2);
            this.aHashtable607.put(var1, var3);
        }

    }

    private Set method702(ClassGen var1) {
        TreeSet var2 = new TreeSet();
        this.method708(var2, var1.getClassName());
        return var2;
    }

    public Vector method703() {
        return this.aVector609;
    }

    private void method704(ClassGen var1) {
        this.aVector609.remove(var1);
        this.aVector609.add(0, var1);
        String var2 = var1.getSuperclassName();
        ClassGen var3;
        if ((var3 = this.aClassStorage_608.getClassGen(var2)) != null) {
            this.method704(var3);
        }

        String[] var4;
        int var5;
        for (int var10000 = var5 = (var4 = var1.getInterfaceNames()).length - 1; var10000 >= 0; var10000 = var5) {
            ClassGen var6;
            if ((var6 = this.aClassStorage_608.getClassGen(var4[var5])) != null) {
                this.method704(var6);
            }

            --var5;
        }

    }

    private void method705() {
        Vector var1 = new Vector();

        Iterator var2;
        Iterator var10000;
        for (var10000 = var2 = this.aClassStorage_608.method671(); var10000.hasNext(); var10000 = var2) {
            var1.add(var2.next());
        }

        Collections.sort(var1, new ClassGenComparator());
        this.aVector609 = new Vector();

        Iterator var3;
        for (var10000 = var3 = var1.iterator(); var10000.hasNext(); var10000 = var3) {
            ClassGen var4 = (ClassGen) var3.next();
            if (this.method691(var4.getClassName()).size() == 0) {
                this.method704(var4);
            }
        }

        Collections.sort(this.aVector609, new Class35());
        this.method697();
    }

    private boolean method706(java.lang.reflect.Method var1) {
        return !Modifier.isPrivate(var1.getModifiers()) && !Modifier.isStatic(var1.getModifiers());
    }

    private void method707(ClassGen var1) {
        Set var2 = this.method696(var1);
        Set var3 = this.method702(var1);
        Set var4 = this.method693(var3);
        Iterator var6 = this.method692(var2, var4).iterator();

        for (Iterator var10000 = var6; var10000.hasNext(); var10000 = var6) {
            this.aSet612.add(var1.getClassName() + "&" + var6.next());
        }

        this.method700(var3, var4);
    }

    private void method708(Set var1, String var2) {
        var1.add(var2);
        ClassGen var3;
        int var10000;
        if ((var3 = this.aClassStorage_608.getClassGen(var2)) != null) {
            this.method708(var1, var3.getSuperclassName());

            String[] var4;
            int var5;
            for (var10000 = var5 = (var4 = var3.getInterfaceNames()).length - 1; var10000 >= 0; var10000 = var5) {
                this.method708(var1, var4[var5]);
                --var5;
            }
        } else {
            try {
                Class var8;
                if ((var8 = Class.forName(var2, false, this.aClassStorage_608.method675())).getSuperclass() != null) {
                    this.method708(var1, var8.getSuperclass().getName());
                }

                int var6;
                Class[] var9;
                for (var10000 = var6 = (var9 = var8.getInterfaces()).length - 1; var10000 >= 0; var10000 = var6) {
                    this.method708(var1, var9[var6].getName());
                    --var6;
                }
            } catch (ClassNotFoundException var7) {
                var1.add("-=404=-");
                if (!this.aHashtable610.containsKey(var2)) {
                    this.aHashtable610.put(var2, "");
                    Logger.printWarning("Class \'" + var2 + "\' cannot be found. It may result in weaker obfuscation. Add needed jars to the \'classpath\' element of the configuration file.");
                }
            }
        }

    }

}
