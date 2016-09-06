package com.allatori;

import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public class Class34 {

	private static boolean aBoolean577 = false;
	private static Class13 aClass13_578;

	public static double method566() {
		if (!aBoolean577) {
			method570();
		}

		return aClass13_578.method359();
	}

	public static void arrangeOrder(List<Object> var0) {
		if (!aBoolean577) {
			method570();
		}

		int var1;
		int var2;
		int var10000;
		if ((var1 = var0.size()) >= 5 && !(var0 instanceof RandomAccess)) {
			final Object[] var6 = var0.toArray();

			int var3;
			for (var10000 = var3 = var1; var10000 > 1; var10000 = var3) {
				method568(var6, var3 - 1, method571(var3));
				--var3;
			}

			final ListIterator<Object> var4 = var0.listIterator();

			int var5;
			for (var10000 = var5 = 0; var10000 < var6.length; var10000 = var5) {
				var4.next();
				var4.set(var6[var5]);
				++var5;
			}
		} else {
			for (var10000 = var2 = var1; var10000 > 1; var10000 = var2) {
				method569(var0, var2 - 1, method571(var2));
				--var2;
			}
		}

	}

	private static void method568(Object[] var0, int var1, int var2) {
		final Object var3 = var0[var1];
		var0[var1] = var0[var2];
		var0[var2] = var3;
	}

	private static void method569(List<Object> var0, int var1, int var2) {
		var0.set(var1, var0.set(var2, var0.get(var1)));
	}

	private static void method570() {
		String var0;
		if ((var0 = Tuning.getRandomSeed()) != null && var0.length() != 0) {
			aClass13_578 = new Class13_Sub1_Sub1(var0.hashCode());
		} else {
			aClass13_578 = new Class13_Sub1_Sub1(System.currentTimeMillis());
		}

		aBoolean577 = true;
	}

	public static int method571(int var0) {
		if (!aBoolean577) {
			method570();
		}

		return aClass13_578.method360(0, var0 - 1);
	}
}
