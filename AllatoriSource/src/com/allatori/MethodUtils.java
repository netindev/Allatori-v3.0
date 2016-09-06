package com.allatori;

import org.apache.bcel.classfile.AccessFlags;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

public class MethodUtils {

	private static boolean method1446(AccessFlags var0) {
		return !var0.isPublic() && !var0.isProtected() && !var0.isPrivate();
	}

	private static String getPackage(String var0) {
		int var1;
		return (var1 = var0.lastIndexOf(46)) == -1 ? "" : var0.substring(0, var1);
	}

	// $FF: synthetic method
	public static void method1448(AccessFlags var0) {
		method1451(var0);
	}

	// $FF: synthetic method
	public static Type method1449(Type var0, RenamingMap var1) {
		return method1453(var0, var1);
	}

	// $FF: synthetic method
	public static boolean method1450(AccessFlags var0) {
		return method1446(var0);
	}

	private static void method1451(AccessFlags var0) {
		var0.isPrivate(false);
		var0.isProtected(false);
		var0.isPublic(true);
	}

	private static String method1452(String var0) {
		return var0.substring(0, var0.lastIndexOf(41));
	}

	private static Type method1453(Type var0, RenamingMap var1) {
		Object var2 = null;
		if (var0 instanceof ObjectType) {
			final String var4 = ((ObjectType) var0).getSignature();
			String var5;
			if ((var5 = var1.get(var4)) != null && !var5.equals(var4)) {
				var2 = new ObjectType(var5);
			}
		} else {
			ArrayType var8;
			Type var9;
			if (var0 instanceof ArrayType && (var9 = (var8 = (ArrayType) var0).getBasicType()) instanceof ObjectType) {
				final String var6 = ((ObjectType) var9).getSignature();
				String var7;
				if ((var7 = var1.get(var6)) != null && !var7.equals(var6)) {
					var2 = new ArrayType(var7, var8.getDimensions());
				}
			}
		}

		return (Type) var2;
	}

	// $FF: synthetic method
	public static String method1454(String var0) {
		return method1452(var0);
	}

	// $FF: synthetic method
	public static String getPackageName(String var0) {
		return getPackage(var0);
	}
}
