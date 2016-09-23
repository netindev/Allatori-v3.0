package com.allatori;

import org.apache.bcel.classfile.AccessFlags;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

public class MethodUtils {

	private static boolean method1446(AccessFlags var0) {
		return !var0.isPublic() && !var0.isProtected() && !var0.isPrivate();
	}

	private static String getPackage(String packageName) {
		int i = packageName.lastIndexOf(46);
		return i == -1 ? "" : packageName.substring(0, i);
	}

	public static void isPublic(AccessFlags accessFlags) {
		setPublic(accessFlags);
	}

	public static Type method1449(Type var0, RenamingMap var1) {
		return method1453(var0, var1);
	}

	public static boolean method1450(AccessFlags var0) {
		return method1446(var0);
	}

	private static void setPublic(AccessFlags accessFlags) {
		accessFlags.isPrivate(false);
		accessFlags.isProtected(false);
		accessFlags.isPublic(true);
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

	public static String method1454(String var0) {
		return method1452(var0);
	}

	public static String getPackageName(String packageName) {
		return getPackage(packageName);
	}
}
