package com.allatori;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassConstraint {

    private String superNamePattern;
    private boolean aBoolean756;
    private int accessType;
    private Vector fieldConstraints = new Vector();
    private String instanceofPattern;
    private String[] interfacePattern;
    private boolean aBoolean761;
    private Vector methodConstraints = new Vector();
    private String namePattern;


    private void parseTemplate(String var1) throws TemplateException {
        Matcher var2;
        if ((var2 = Pattern.compile("(.*?(?:class|interface))\\s+(.+?)(?:\\s+extends\\s+(.+?))?(?:\\s+implements\\s+(.+?))?(?:\\s+instanceof\\s+(.+?))?").matcher(var1)).matches()) {
            String accessflags = var2.group(1);
            this.accessType = Class115.parseAccess(accessflags);
            String className = var2.group(2);
            this.namePattern = Class115.parsePattern(className);
            String superName = var2.group(3);
            this.superNamePattern = Class115.parsePattern(superName);
            String interfaceClasses = var2.group(4);
            this.interfacePattern = Class115.method1397(interfaceClasses);
            String instanceClassName = var2.group(5);
            this.instanceofPattern = Class115.parsePattern(instanceClassName);
        } else {
            throw new TemplateException("Invalid template.");
        }
    }

    public ClassConstraint clone() {
        ClassConstraint var1;
        (var1 = new ClassConstraint()).aBoolean756 = this.aBoolean756;
        var1.accessType = this.accessType;
        var1.namePattern = this.namePattern;
        var1.superNamePattern = this.superNamePattern;
        var1.instanceofPattern = this.instanceofPattern;
        var1.interfacePattern = this.interfacePattern;
        return var1;
    }

    public boolean apply(MethodGen var1) {
        int var2;
        for (int var10000 = var2 = this.methodConstraints.size() - 1; var10000 >= 0; var10000 = var2) {
            if (((MethodConstraint) this.methodConstraints.get(var2)).apply(var1)) {
                return true;
            }

            --var2;
        }

        return false;
    }

    private ClassConstraint() {
    }

    public void addMethodConstraint(MethodConstraint var1) {
        this.methodConstraints.add(var1);
    }

    public boolean apply(Method var1) {
        int var2;
        for (int var10000 = var2 = this.methodConstraints.size() - 1; var10000 >= 0; var10000 = var2) {
            if (((MethodConstraint) this.methodConstraints.get(var2)).apply(var1)) {
                return true;
            }

            --var2;
        }

        return false;
    }

    public boolean hasFieldConstraints() {
        return this.fieldConstraints.size() > 0;
    }

    public boolean apply(ClassStorage var1, ClassGen var2) {
        return Class115.method1399(var2, this.accessType) && (var2.getClassName().matches(this.namePattern) && (var2.getSuperclassName().matches(this.superNamePattern) && (Class115.method1392(var2.getInterfaceNames(), this.interfacePattern) && Class115.method1398(var1, var2.getClassName(), this.instanceofPattern))));
    }

    public void addFieldConstraint(FieldConstraint var1) {
        this.fieldConstraints.add(var1);
    }

    public boolean method1465() {
        return this.aBoolean761;
    }

    public ClassConstraint(String var1, boolean var2, boolean var3) throws TemplateException {
        this.aBoolean756 = var2;
        this.aBoolean761 = var3;
        this.parseTemplate(var1);
    }

    public boolean method1466() {
        return this.aBoolean756;
    }

    public boolean apply(Field var1) {
        int var2;
        for (int var10000 = var2 = this.fieldConstraints.size() - 1; var10000 >= 0; var10000 = var2) {
            if (((FieldConstraint) this.fieldConstraints.get(var2)).apply(var1)) {
                return true;
            }

            --var2;
        }

        return false;
    }

    public boolean hasMethodConstraints() {
        return this.methodConstraints.size() > 0;
    }
}
