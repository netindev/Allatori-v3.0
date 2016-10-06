package com.allatori;

import java.util.Vector;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;

public class ContainsInst {

	/* OK */

	private static Vector<ClassConstraint> vClassConstraint = new Vector<ClassConstraint>();
	private static ClassConstraint classConstraint;

	public static void addFieldConstraint(FieldConstraint fieldConstraint) {
		classConstraint.addFieldConstraint(fieldConstraint);
	}

	static {
		try {
			classConstraint = new ClassConstraint("class *", true, false);
			final ClassConstraint classConstraint = new ClassConstraint("class * extends java.lang.Enum", true, false);
			classConstraint.addFieldConstraint(new FieldConstraint("private+ *"));
			classConstraint.addMethodConstraint(new MethodConstraint("private+ *(**)"));
			add(classConstraint);
		} catch (final TemplateException e) {
			e.printStackTrace();
		}
	}

	public static void add(ClassConstraint classConstraint) {
		vClassConstraint.add(classConstraint);
	}

	private static boolean containsMethod(ClassStorage classStorage, ClassGen classGen, Method method) {
		return contains(classStorage, classGen);
	}

	public static boolean checkIfContainsField(ClassStorage classStorage, ClassGen classGen, Field field) {
		if (containsField(classStorage, classGen, field)) {
			return true;
		} else {
			for (int i = 0; i < vClassConstraint.size(); i++) {
				final ClassConstraint classConstraint = vClassConstraint.get(i);
				if (classConstraint.hasFieldConstraints() && classConstraint.apply(classStorage, classGen)
						&& classConstraint.apply(field)) {
					return false;
				}
				if (classConstraint.getIfYes2() && classConstraint.apply(classStorage, classGen)) {
					return true;
				}
			}
			if (classConstraint.hasFieldConstraints() && classConstraint.apply(field)) {
				return false;
			} else {
				return true;
			}
		}
	}

	private static boolean containsField(ClassStorage classStorage, ClassGen classGen, Field field) {
		return contains(classStorage, classGen);
	}

	private static boolean contains(ClassStorage classStorage, ClassGen classGen) {
		return classStorage.getVector().contains(classGen);
	}

	public static boolean checkIfContainsMethod(ClassStorage classStorage, ClassGen classGen, Method method) {
		if (containsMethod(classStorage, classGen, method)) {
			return true;
		} else {
			for (int i = 0; i < vClassConstraint.size(); i++) {
				final ClassConstraint classConstraint = vClassConstraint.get(i);
				if (classConstraint.hasMethodConstraints() && classConstraint.apply(classStorage, classGen)
						&& classConstraint.apply(method)) {
					return false;
				}
				if (classConstraint.getIfYes2() && classConstraint.apply(classStorage, classGen)) {
					return true;
				}
			}
			if (classConstraint.hasMethodConstraints() && classConstraint.apply(method)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public static void addMethodConstraint(MethodConstraint methodConstraint) {
		classConstraint.addMethodConstraint(methodConstraint);
	}

	public static boolean checkIfContains(ClassStorage classStorage, ClassGen classGen) {
		if (contains(classStorage, classGen)) {
			return true;
		} else {
			for (int i = 0; i < vClassConstraint.size(); i++) {
				final ClassConstraint classConstraint = vClassConstraint.get(i);
				if (!classConstraint.getIfYes1()
						|| classConstraint.getIfYes2() && classConstraint.apply(classStorage, classGen)) {
					return classConstraint.getIfYes2();
				}
			}
			return true;
		}
	}
}
