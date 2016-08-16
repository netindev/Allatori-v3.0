package com.allatori;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

import java.util.Vector;

public class ObfuscationConstraint {

    private Vector obfuscationConstraints = new Vector();
    private int defaultType;


    public void addConstraint(ObfuscationTypeConstraint typeConstraint) {
        this.obfuscationConstraints.add(typeConstraint);
    }

    public int getType(ClassStorage var1, ClassGen var2, MethodGen var3) {
        for (Object constraint : this.obfuscationConstraints) {
            ObfuscationTypeConstraint current = (ObfuscationTypeConstraint) constraint;
            if (current.classConstraint.apply(var1, var2)) {
                if (var3 == null) {
                    return current.type;
                }

                if (current.classConstraint.apply(var3)) {
                    return current.type;
                }
            }


        }

        return this.defaultType;
    }

    public ObfuscationConstraint(int defaultType) {
        this.defaultType = defaultType;
    }

    public int size() {
        return this.obfuscationConstraints.size();
    }
}
