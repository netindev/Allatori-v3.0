package com.allatori;

import com.allatori.obfuscate.opt.AntiJDTransform;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import java.util.Iterator;
import java.util.Vector;

public class Obfuscator {

    private ClassStorage classes;


    private void executeRenamer() {
        try {
            (new Renamer(this.classes)).run();
        } catch (Exception_Sub2 var4) {
            try {
                Logger.printWarning("Rerunning obfuscation due to un-renamed packages.");
                (new Renamer(this.classes)).run();
            } catch (Exception_Sub2 var3) {
            }
        }

    }

    private void executeTransforms() {
        Vector transformsVector = new Vector();
        transformsVector.add(new DemoWaterMark());
        transformsVector.add(new Class63(this.classes));
        transformsVector.add(new ExpiryObfuscation(this.classes));
        if (Tuning.rearrangeClassMembers()) {
            transformsVector.add(new ClassMemberRearranger());
        }

        if (Tuning.isStringObfuscationLayer2Enabled()) {
            transformsVector.add(new StringObfuscationLayer1(this.classes));
        }

        if (Tuning.isControlFlowObfuscationEnabled()) {
            transformsVector.add(new ControlFlow());
        }

        transformsVector.add(new AntiJDTransform(this.classes));

        Iterator var2;
        Iterator var10000;
        for (var10000 = var2 = this.classes.method671(); var10000.hasNext(); var10000 = var2) {
            ClassGen var3 = (ClassGen) var2.next();

            Iterator var4;
            for (var10000 = var4 = transformsVector.iterator(); var10000.hasNext(); var10000 = var4) {
                ((ObfuscationType) var4.next()).execute(var3);
            }
        }

        Iterator var5;
        for (var10000 = var5 = transformsVector.iterator(); var10000.hasNext(); var10000 = var5) {
            ((ObfuscationType) var5.next()).terminate();
        }

    }

    public Obfuscator(ClassStorage var1) {
        this.classes = var1;
        Method.setComparator(Class42.method652());
    }

    private void method1525() {
        Class95 var1 = new Class95();

        Iterator var2;
        for (Iterator var10000 = var2 = this.classes.method671(); var10000.hasNext(); var10000 = var2) {
            var1.method1258((ClassGen) var2.next());
        }

    }

    public void method1526() {
        Iterator var1;
        Iterator var10000;
        for (var10000 = var1 = this.classes.method671(); var10000.hasNext(); var10000 = var1) {
            if (((ClassGen) var1.next()).getSuperclassName().startsWith("javax.microedition.")) {
                Tuning.method1723();
                break;
            }
        }

        this.executeTransforms();
        this.executeRenamer();
        if (Tuning.isStringObfuscationLayer2Enabled()) {
            StringObfuscationLayer2 var2 = new StringObfuscationLayer2();

            Iterator var3;
            for (var10000 = var3 = this.classes.method671(); var10000.hasNext(); var10000 = var3) {
                ClassGen var4 = (ClassGen) var3.next();
                var2.execute(var4);
            }
        }

        this.method1525();
    }
}
