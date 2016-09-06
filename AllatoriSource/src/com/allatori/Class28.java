package com.allatori;

import java.util.Vector;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;

public class Class28 {

	private static Vector aVector570 = new Vector();
	private static ClassConstraint aClassConstraint_571;

	public static void method501(FieldConstraint var0) {
		aClassConstraint_571.addFieldConstraint(var0);
	}

	static {
		try {
			aClassConstraint_571 = new ClassConstraint("class *", true, false);
			ClassConstraint var0;
			(var0 = new ClassConstraint("class * extends java.lang.Enum", true, false))
					.addFieldConstraint(new FieldConstraint("private+ *"));
			var0.addMethodConstraint(new MethodConstraint("private+ *(**)"));
			method502(var0);
		} catch (final TemplateException var1) {
			var1.printStackTrace();
		}

	}

	public static void method502(ClassConstraint var0) {
		aVector570.add(var0);
	}

	private static boolean method503(ClassStorage var0, ClassGen var1, Method var2) {
		return method506(var0, var1);
	}

	public static boolean method504(ClassStorage var0, ClassGen var1, Field var2) {
		if (method505(var0, var1, var2)) {
			return true;
		} else {
			int var3;
			for (int var10000 = var3 = 0; var10000 < aVector570.size(); var10000 = var3) {
				ClassConstraint var4;
				if ((var4 = (ClassConstraint) aVector570.get(var3)).hasFieldConstraints() && var4.apply(var0, var1)
						&& var4.apply(var2)) {
					return false;
				}

				if (var4.method1465() && var4.apply(var0, var1)) {
					return true;
				}

				++var3;
			}

			if (aClassConstraint_571.hasFieldConstraints() && aClassConstraint_571.apply(var2)) {
				return false;
			} else {
				return true;
			}
		}
	}

	private static boolean method505(ClassStorage var0, ClassGen var1, Field var2) {
		return method506(var0, var1);
	}

	private static boolean method506(ClassStorage var0, ClassGen var1) {
		return var0.method674().contains(var1);
	}

	public static boolean method507(ClassStorage var0, ClassGen var1, Method var2) {
		if (method503(var0, var1, var2)) {
			return true;
		} else {
			int var3;
			for (int var10000 = var3 = 0; var10000 < aVector570.size(); var10000 = var3) {
				ClassConstraint var4;
				if ((var4 = (ClassConstraint) aVector570.get(var3)).hasMethodConstraints() && var4.apply(var0, var1)
						&& var4.apply(var2)) {
					return false;
				}

				if (var4.method1465() && var4.apply(var0, var1)) {
					return true;
				}

				++var3;
			}

			if (aClassConstraint_571.hasMethodConstraints() && aClassConstraint_571.apply(var2)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public static void method508(MethodConstraint var0) {
		aClassConstraint_571.addMethodConstraint(var0);
	}

	public static boolean method509(ClassStorage var0, ClassGen var1) {
		if (method506(var0, var1)) {
			return true;
		} else {
			int var2;
			for (int var10000 = var2 = 0; var10000 < aVector570.size(); var10000 = var2) {
				ClassConstraint var3;
				if ((!(var3 = (ClassConstraint) aVector570.get(var2)).method1466() || var3.method1465())
						&& var3.apply(var0, var1)) {
					return var3.method1465();
				}

				++var2;
			}

			return true;
		}
	}
}
