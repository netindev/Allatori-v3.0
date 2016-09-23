package com.allatori;

import org.apache.bcel.classfile.AccessFlags;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.Type;

public class Class115 {

	public static boolean method1388(Type[] var0, String var1) {
		final StringBuffer var2 = new StringBuffer("");

		int var3;
		for (int var10000 = var3 = var0.length - 1; var10000 >= 0; var10000 = var3) {
			var2.insert(0, var0[var3].toString() + ",");
			--var3;
		}

		if (var2.length() > 0) {
			var2.deleteCharAt(var2.length() - 1);
		}

		return var2.toString().matches(var1);
	}

	public static boolean method1389(String string) {
		return string.indexOf("public") >= 0 ? true
				: (string.indexOf("protected") >= 0 ? true
						: (string.indexOf("package") >= 0 ? true
								: (string.indexOf("private") >= 0 ? true
										: (string.indexOf("static") >= 0 ? true : (string.indexOf("final") >= 0 ? true
												: (string.indexOf("synchronized") >= 0 ? true
														: (string.indexOf("volatile") >= 0 ? true
																: (string.indexOf("transient") >= 0 ? true
																		: (string.indexOf("native") >= 0 ? true
																				: (string.indexOf("interface") >= 0 ? true
																						: (string.indexOf("abstract") >= 0
																								? true
																								: string.indexOf(
																										"strictfp") >= 0)))))))))));
	}

	public static boolean method1390(String var0) {
		return var0.equals("public") ? true
				: (var0.equals("protected") ? true
						: (var0.equals("package") ? true
								: (var0.equals("private") ? true
										: (var0.equals("protected+") ? true : (var0.equals("package+") ? true
												: var0.equals("private+"))))));
	}

	private static String method1391(String var0) {
		return var0.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
	}

	public static boolean method1392(String[] var0, String[] var1) {
		int var2;
		int var10000 = var2 = var1.length - 1;

		while (var10000 >= 0) {
			boolean var3 = false;
			int var4;
			var10000 = var4 = var0.length - 1;

			while (true) {
				if (var10000 >= 0) {
					if (!var0[var4].matches(var1[var2])) {
						--var4;
						var10000 = var4;
						continue;
					}

					var3 = true;
				}

				if (!var3) {
					return false;
				}

				--var2;
				var10000 = var2;
				break;
			}
		}

		return true;
	}

	public static int parseAccess(String var0) {
		var0 = var0.trim();
		int var1 = 0;
		if (var0.indexOf("public") >= 0) {
			var1 |= 1;
		}

		if (var0.indexOf("protected") >= 0) {
			var1 |= 2;
		}

		if (var0.indexOf("package") >= 0) {
			var1 |= 4;
		}

		if (var0.indexOf("private") >= 0) {
			var1 |= 8;
		}

		if (var0.indexOf("protected+") >= 0) {
			var1 |= 3;
		}

		if (var0.indexOf("package+") >= 0) {
			var1 |= 7;
		}

		if (var0.indexOf("private+") >= 0) {
			var1 |= 15;
		}

		if (var1 == 0) {
			var1 = 15;
		}

		if (var0.indexOf("static") >= 0) {
			var1 |= 16;
		}

		if (var0.indexOf("final") >= 0) {
			var1 |= 32;
		}

		if (var0.indexOf("synchronized") >= 0) {
			var1 |= 64;
		}

		if (var0.indexOf("volatile") >= 0) {
			var1 |= 128;
		}

		if (var0.indexOf("transient") >= 0) {
			var1 |= 256;
		}

		if (var0.indexOf("native") >= 0) {
			var1 |= 512;
		}

		if (var0.indexOf("interface") >= 0) {
			var1 |= 1024;
		}

		if (var0.indexOf("abstract") >= 0) {
			var1 |= 2048;
		}

		if (var0.indexOf("strictfp") >= 0) {
			var1 |= 4096;
		}

		return var1;
	}

	public static String localParsePattern(String string) {
		return string == null ? ".*" : method1391(string.trim());
	}

	public static String parsePattern(String string) {
		return localParsePattern(string);
	}

	public static String replaceAll(String string) {
		return string.replaceAll("\\s+", "").replaceAll("\\.", "\\\\.").replaceAll("\\*\\*", "%@#")
				.replaceAll("\\*", "[^,]*").replaceAll("\\%\\@\\#", ".*");
	}

	public static String[] method1397(String var0) {
		if (var0 == null) {
			return new String[0];
		} else {
			String[] var1;
			if ((var1 = var0.split("\\s*,\\s*")) == null) {
				return new String[0];
			} else {
				int var2;
				for (int var10000 = var2 = var1.length - 1; var10000 >= 0; var10000 = var2) {
					var1[var2] = method1391(var1[var2]);
					--var2;
				}

				return var1;
			}
		}
	}

	public static boolean method1398(ClassStorage var0, String var1, String var2) {
		if (var1 == null) {
			return false;
		} else if (var1.matches(var2)) {
			return true;
		} else {
			ClassGen var3;
			int var10000;
			if ((var3 = var0.getClassGen(var1)) != null) {
				String[] var8;
				int var9;
				for (var10000 = var9 = (var8 = var3.getInterfaceNames()).length - 1; var10000 >= 0; var10000 = var9) {
					if (method1398(var0, var8[var9], var2)) {
						return true;
					}

					--var9;
				}

				return method1398(var0, var3.getSuperclassName(), var2);
			} else {
				try {
					Class<?> var4;
					Class<?>[] var5;
					int var6;
					for (var10000 = var6 = (var5 = (var4 = Class.forName(var1, false, var0.getClassLoader()))
							.getInterfaces()).length - 1; var10000 >= 0; var10000 = var6) {
						if (method1398(var0, var5[var6].getName(), var2)) {
							return true;
						}

						--var6;
					}

					return var4.getSuperclass() != null ? method1398(var0, var4.getSuperclass().getName(), var2)
							: false;
				} catch (final ClassNotFoundException var7) {
					return true;
				}
			}
		}
	}

	public static boolean method1399(AccessFlags accessFlags, int i) {
		if (accessFlags.isPublic()) {
			if ((i & 1) == 0) {
				return false;
			}
		} else if (accessFlags.isProtected()) {
			if ((i & 2) == 0) {
				return false;
			}
		} else if (accessFlags.isPrivate()) {
			if ((i & 8) == 0) {
				return false;
			}
		} else if ((i & 4) == 0) {
			return false;
		}
		return !((i & 16) > 0 && !accessFlags.isStatic()) && (!((i & 32) > 0 && !accessFlags.isFinal()) && (!((i & 64) > 0
				&& !accessFlags.isSynchronized())
				&& (!((i & 128) > 0 && !accessFlags.isVolatile()) && (!((i & 256) > 0 && !accessFlags.isTransient())
						&& (!((i & 512) > 0 && !accessFlags.isNative()) && (!((i & 1024) > 0 && !accessFlags.isInterface())
								&& (!((i & 2048) > 0 && !accessFlags.isAbstract())
										&& ((i & 4096) <= 0 || accessFlags.isStrictfp()))))))));
	}
}
