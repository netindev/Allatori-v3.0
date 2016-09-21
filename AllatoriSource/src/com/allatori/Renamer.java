package com.allatori;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantCP;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.Deprecated;
import org.apache.bcel.classfile.EnclosingMethod;
import org.apache.bcel.classfile.ExceptionTable;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.InnerClasses;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.PMGClass;
import org.apache.bcel.classfile.Signature;
import org.apache.bcel.classfile.SourceFile;
import org.apache.bcel.classfile.StackMap;
import org.apache.bcel.classfile.Synthetic;
import org.apache.bcel.classfile.Unknown;
import org.apache.bcel.generic.AnnotationElementValueGen;
import org.apache.bcel.generic.AnnotationEntryGen;
import org.apache.bcel.generic.ArrayElementValueGen;
import org.apache.bcel.generic.ClassElementValueGen;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ElementValueGen;
import org.apache.bcel.generic.ElementValuePairGen;
import org.apache.bcel.generic.EnumElementValueGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.SimpleElementValueGen;
import org.apache.bcel.generic.Type;

public class Renamer {

	private final ClassStorage aClassStorage_504;
	private static boolean aBoolean506 = false;
	private static RenamingMap namingMap = new RenamingMap();
	private final Class56 aClass56_508;
	private final Class172 aClass172_509 = new Class172(this, (EmptyClass) null);
	private final Class26 aClass26_510 = new Class26();

	private void method296(ElementValueGen var1, ConstantPoolGen var2) {
		if (var1 instanceof AnnotationElementValueGen) {
			this.renameSignatures(((AnnotationElementValueGen) var1).getAnnotation(), var2);
		} else {
			int var5;
			if (var1 instanceof ArrayElementValueGen) {
				List<?> var4;
				for (int var10000 = var5 = (var4 = ((ArrayElementValueGen) var1).getElementValues()).size()
						- 1; var10000 >= 0; var10000 = var5) {
					this.method296((ElementValueGen) var4.get(var5), var2);
					--var5;
				}
			} else if (!(var1 instanceof ClassElementValueGen)) {
				if (var1 instanceof EnumElementValueGen) {
					EnumElementValueGen var9;
					final String var10 = (var9 = (EnumElementValueGen) var1).getEnumTypeString();
					String var11 = null;
					String var6;
					String var7;
					if (var10.startsWith("L") && var10.endsWith(";")) {
						var11 = var6 = var10.substring(1, var10.length() - 1).replace('/', '.');
						if ((var7 = Class165.method1653(Class172.method1708(this.aClass172_509)).get(var6)) != null) {
							var7 = "L" + var7.replace('.', '/') + ";";
							var9.setTypeIndex(var2.addUtf8(var7));
						}
					}

					if (var11 != null) {
						var6 = var9.getEnumValueString();
						var7 = var11 + "&" + var6 + "&" + var10;
						if (Class159.method1626(Class172.method1710(this.aClass172_509)).containsKey(var7)) {
							final String var8 = Class159.method1626(Class172.method1710(this.aClass172_509)).get(var7);
							var9.setValueIndex(var2.addUtf8(var8));
						}
					}
				} else if (var1 instanceof SimpleElementValueGen) {
				}
			}
		}

	}

	private void method297() {
		Iterator<?> var1;
		for (Iterator<?> var10000 = var1 = this.aClass56_508.method703().iterator(); var10000.hasNext(); var10000 = var1) {
			final ClassGen var2 = (ClassGen) var1.next();
			this.renameConstantPoolConstants(var2);
			final ConstantPoolGen var3 = var2.getConstantPool();
			this.method312(var2.getAttributes(), var3);

			Method[] var4;
			int var5;
			for (int var6 = var5 = (var4 = var2.getMethods()).length - 1; var6 >= 0; var6 = var5) {
				this.method312(var4[var5].getAttributes(), var3);
				--var5;
			}
		}

	}

	private void method298(ClassGen var1) {
		final String var2 = var1.getClassName();

		Method[] var3;
		int var4;
		String var6;
		int var10000;
		for (var10000 = var4 = (var3 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var4) {
			final Method var5 = var3[var4];
			if (this.method307(var2, var5) || !Class28.method507(this.aClassStorage_504, var1, var5)) {
				var6 = var5.getName();
				final String var7 = var5.getSignature();
				if (!NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
						.containsKey(var2 + "&" + var6 + "&" + var7)) {
					NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
							.put(var2 + "&" + var6 + "&" + var7, var6);
					NameRepository.getMethodRenamingMap(Class172.method1707(this.aClass172_509))
							.put(var2 + "&" + var6 + "&" + MethodUtils.method1454(var7), "&");
					if (!var5.isPrivate()) {
						this.method319(var1, var6, var6, var7, var1.isInterface(), MethodUtils.method1450(var5));
					}
				} else if (!var5.isPrivate()) {
					this.method319(var1, var6,
							NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
									.get(var2 + "&" + var6 + "&" + var7),
							var7, var1.isInterface(), MethodUtils.method1450(var5));
				}
			}

			--var4;
		}

		ClassGen var12 = var1;

		do {
			var6 = var12.getSuperclassName();
			int var8;
			if ((var12 = this.aClassStorage_504.getClassGen(var6)) != null) {
				Method[] var13;
				for (var10000 = var8 = (var13 = var12.getMethods()).length - 1; var10000 >= 0; var10000 = var8) {
					final Method var9 = var13[var8];
					if (!this.method307(var6, var9) && !Class28.method507(this.aClassStorage_504, var1, var9)) {
						final String var10 = var9.getName();
						final String var11 = var9.getSignature();
						if (!NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
								.containsKey(var6 + "&" + var10 + "&" + var11)) {
							NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
									.put(var6 + "&" + var10 + "&" + var11, var10);
							NameRepository.getMethodRenamingMap(Class172.method1707(this.aClass172_509))
									.put(var6 + "&" + var10 + "&" + MethodUtils.method1454(var11), "&");
							if (!var9.isPrivate()) {
								this.method319(var12, var10, var10, var11, var12.isInterface(),
										MethodUtils.method1450(var9));
							}
						} else if (!var9.isPrivate()) {
							this.method319(var12, var10,
									NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
											.get(var6 + "&" + var10 + "&" + var11),
									var11, var12.isInterface(), MethodUtils.method1450(var9));
						}
					}

					--var8;
				}
			}
		} while (var12 != null);

	}

	// $FF: synthetic method
	public static boolean method299() {
		return aBoolean506;
	}

	// $FF: synthetic method
	public static ClassStorage method300(Renamer var0) {
		return var0.aClassStorage_504;
	}

	private void method301() throws RunException {
		Iterator<?> var1;
		for (Iterator<?> var10000 = var1 = this.aClass56_508.method703().iterator(); var10000.hasNext(); var10000 = var1) {
			final ClassGen var2 = (ClassGen) var1.next();
			this.unrenamePackage(var2);
			this.method298(var2);
			this.renameClassName(var2);
		}

		if (!aBoolean506 && namingMap.size() > 0) {
			aBoolean506 = true;
			throw new RunException();
		}
	}

	private void renameFields(ClassGen var1) {
		Field[] var2;
		int var3;
		for (int var10000 = var3 = (var2 = var1.getFields()).length - 1; var10000 >= 0; var10000 = var3) {
			final Field var4 = var2[var3];
			final String var5 = Class159.method1626(Class172.method1710(this.aClass172_509))
					.get(var1.getClassName() + "&" + var4.getName() + "&" + var4.getSignature());
			final Type var6 = MethodUtils.method1449(var4.getType(),
					Class165.method1653(Class172.method1708(this.aClass172_509)));
			boolean var7 = false;
			if ((Class165.method1652(Class172.method1708(this.aClass172_509)).containsKey(var1.getClassName())
					|| Packaging.method575()) && !var4.isPrivate()) {
				var7 = true;
			}

			if (!var5.equals(var4.getName()) || var6 != null || var7 || var4.getAnnotationEntries().length > 0) {
				final FieldGen var8 = new FieldGen(var4, var1.getConstantPool());
				if (!var5.equals(var4.getName())) {
					var8.setName(var5);
				}

				if (var6 != null) {
					var8.setType(var6);
					Class159.method1628(Class172.method1710(this.aClass172_509)).put(var4.getSignature(),
							var8.getSignature());
				}

				if (var7) {
					MethodUtils.method1448(var8);
				}

				this.method331(var8.getAnnotationEntries(), var1.getConstantPool());
				var1.replaceField(var4, var8.getField());
			}

			--var3;
		}

	}

	private void renamePackage(ClassGen var1) {
		final String var2 = var1.getClassName();
		if (!Class165.method1653(Class172.method1708(this.aClass172_509)).containsKey(var2)) {
			final String var3 = MethodUtils.getPackageName(var2);
			String var4 = this.method314(var3);
			if (Packaging.method578() && (!var3.equals(var4) || Packaging.method575())) {
				var4 = Packaging.method576();
				Class165.method1652(Class172.method1708(this.aClass172_509)).put(var2, "&");
			}

			if (var4.length() > 0) {
				var4 = var4 + ".";
			}

			Class165.method1654(Class172.method1708(this.aClass172_509)).reset();

			String var5;
			do {
				do {
					var5 = var4 + Class165.method1654(Class172.method1708(this.aClass172_509)).next();
				} while (Class165.method1655(Class172.method1708(this.aClass172_509)).containsKey(var5));
			} while (this.aClassStorage_504.getClassGen(var5) != null);

			Class165.method1653(Class172.method1708(this.aClass172_509)).put(var2, var5);
			Class165.method1655(Class172.method1708(this.aClass172_509)).put(var5, var2);
		}

	}

	private void renameClassName(ClassGen var1) {
		final String var2 = var1.getClassName();

		Field[] var3;
		int var4;
		String var6;
		int var10000;
		for (var10000 = var4 = (var3 = var1.getFields()).length - 1; var10000 >= 0; var10000 = var4) {
			final Field var5 = var3[var4];
			if (!Class28.method504(this.aClassStorage_504, var1, var5)) {
				var6 = var5.getName();
				final String var7 = var5.getSignature();
				if (!Class159.method1626(Class172.method1710(this.aClass172_509))
						.containsKey(var2 + "&" + var6 + "&" + var7)) {
					Class159.method1626(Class172.method1710(this.aClass172_509)).put(var2 + "&" + var6 + "&" + var7,
							var6);
					Class159.method1625(Class172.method1710(this.aClass172_509)).put(var2 + "&" + var6 + "&" + var7,
							"&");
					if (!var5.isPrivate()) {
						this.method306(var1, var6, var6, var7);
					}
				} else if (!var5.isPrivate()) {
					this.method306(var1, var6, Class159.method1626(Class172.method1710(this.aClass172_509))
							.get(var2 + "&" + var6 + "&" + var7), var7);
				}
			}

			--var4;
		}

		ClassGen var12 = var1;

		do {
			var6 = var12.getSuperclassName();
			int var8;
			if ((var12 = this.aClassStorage_504.getClassGen(var6)) != null) {
				Field[] var13;
				for (var10000 = var8 = (var13 = var12.getFields()).length - 1; var10000 >= 0; var10000 = var8) {
					final Field var9 = var13[var8];
					if (!Class28.method504(this.aClassStorage_504, var1, var9)) {
						final String var10 = var9.getName();
						final String var11 = var9.getSignature();
						if (!Class159.method1626(Class172.method1710(this.aClass172_509))
								.containsKey(var6 + "&" + var10 + "&" + var11)) {
							Class159.method1626(Class172.method1710(this.aClass172_509))
									.put(var6 + "&" + var10 + "&" + var11, var10);
							Class159.method1625(Class172.method1710(this.aClass172_509))
									.put(var6 + "&" + var10 + "&" + var11, "&");
							if (!var9.isPrivate()) {
								this.method306(var12, var10, var10, var11);
							}
						} else if (!var9.isPrivate()) {
							this.method306(var12, var10, Class159.method1626(Class172.method1710(this.aClass172_509))
									.get(var6 + "&" + var10 + "&" + var11), var11);
						}
					}

					--var8;
				}
			}
		} while (var12 != null);

	}

	private String method305(String var1) {
		int var2;
		for (int var10000 = var2 = var1.length() - 1; var10000 >= 0; var10000 = var2) {
			if (var1.charAt(var2) == 76) {
				int var3 = var1.indexOf(59, var2);
				final int var4 = var1.indexOf(60, var2);
				if (var3 == -1) {
					var3 = var4;
				}

				if (var4 != -1 && var4 < var3) {
					var3 = var4;
				}

				if (var3 > var2) {
					final String var5 = var1.substring(var2 + 1, var3).replace('/', '.');
					String var6;
					if ((var6 = Class165.method1653(Class172.method1708(this.aClass172_509)).get(var5)) != null) {
						var1 = var1.substring(0, var2 + 1) + var6.replace('.', '/')
								+ var1.substring(var2 + var5.length() + 1);
					}
				}
			}

			--var2;
		}

		return var1;
	}

	private void method306(ClassGen var1, String var2, String var3, String var4) {
		Vector<?> var5;
		int var6;
		for (int var10000 = var6 = (var5 = this.aClass56_508.method691(var1.getClassName())).size()
				- 1; var10000 >= 0; var10000 = var6) {
			final ClassGen var7 = (ClassGen) var5.get(var6);
			Class159.method1626(Class172.method1710(this.aClass172_509))
					.put(var7.getClassName() + "&" + var2 + "&" + var4, var3);
			Class159.method1625(Class172.method1710(this.aClass172_509))
					.put(var7.getClassName() + "&" + var3 + "&" + var4, "&");
			this.method306(var7, var2, var3, var4);
			--var6;
		}

	}

	private boolean method307(String var1, Method var2) {
		return !var2.isNative() && !this.aClass56_508.method694(var1, var2.getName(), var2.getSignature())
				? "<init>".equals(var2.getName()) || "<clinit>".equals(var2.getName())
						|| "main".equals(var2.getName()) && "([Ljava/lang/String;)V".equals(var2.getSignature())
				: true;
	}

	private void method308() {
		Iterator<?> var1;
		for (Iterator<?> var10000 = var1 = this.aClass56_508.method703().iterator(); var10000.hasNext(); var10000 = var1) {
			final ClassGen var2 = (ClassGen) var1.next();
			this.renameFields(var2);
			this.renameMethods(var2);
			this.method330(var2);
		}

	}

	private void unrenamePackage(ClassGen var1) {
		if (this.method328(var1) || !Class28.method509(this.aClassStorage_504, var1)) {
			final String var2 = var1.getClassName();
			boolean var3 = false;
			if (Class165.method1653(Class172.method1708(this.aClass172_509)).containsKey(var2)
					&& !var2.equals(Class165.method1653(Class172.method1708(this.aClass172_509)).get(var2))) {
				var3 = true;
			}

			String var4;
			String var5;
			String var10000;
			if (!var3) {
				Class165.method1653(Class172.method1708(this.aClass172_509)).put(var2, var2);
				Class165.method1655(Class172.method1708(this.aClass172_509)).put(var2, var2);
				var4 = MethodUtils.getPackageName(var2);
				if (Packaging.method579()
						&& (var5 = Class141.method1520(Class172.method1711(this.aClass172_509)).get(var4)) != null
						&& !var5.equals(var4)) {
					if (namingMap.get(var5) == null) {
						Logger.printWarning("Package \'" + var5 + "\' should be un-renamed.");
					}

					namingMap.put(var5, var4);
				}

				Class141.method1520(Class172.method1711(this.aClass172_509)).put(var4, var4);
				Class141.method1522(Class172.method1711(this.aClass172_509)).put(var4, "&");

				for (var10000 = var4; var10000.lastIndexOf(46) > 0; var10000 = var4) {
					var4 = var4.substring(0, var4.lastIndexOf(46));
					if (Packaging.method579()
							&& (var5 = Class141.method1520(Class172.method1711(this.aClass172_509)).get(var4)) != null
							&& !var5.equals(var4)) {
						if (namingMap.get(var5) == null) {
							Logger.printWarning("Package \'" + var5 + "\' should be un-renamed.");
						}

						namingMap.put(var5, var4);
					}

					Class141.method1520(Class172.method1711(this.aClass172_509)).put(var4, var4);
					Class141.method1522(Class172.method1711(this.aClass172_509)).put(var4, "&");
				}

			} else {
				var4 = MethodUtils.getPackageName(var2);
				var5 = MethodUtils
						.getPackageName(Class165.method1653(Class172.method1708(this.aClass172_509)).get(var2));
				if (!Packaging.method578() || !var5.equals(Packaging.method576())) {
					if ((new StringTokenizer(var4, ".")).countTokens() == (new StringTokenizer(var5, "."))
							.countTokens()) {
						Class141.method1520(Class172.method1711(this.aClass172_509)).put(var4, var5);
						Class141.method1522(Class172.method1711(this.aClass172_509)).put(var5, "&");

						for (var10000 = var4; var10000.lastIndexOf(46) > 0; var10000 = var4) {
							var4 = var4.substring(0, var4.lastIndexOf(46));
							var5 = var5.substring(0, var5.lastIndexOf(46));
							Class141.method1520(Class172.method1711(this.aClass172_509)).put(var4, var5);
							Class141.method1522(Class172.method1711(this.aClass172_509)).put(var5, "&");
						}

					}
				}
			}
		}
	}

	public static RenamingMap getRenamerMap() {
		return namingMap;
	}

	public static Class26 method311(Renamer var0) {
		return var0.aClass26_510;
	}

	private void method312(Attribute[] var1, ConstantPoolGen var2) {
		int var3;
		for (int var10000 = var3 = var1.length - 1; var10000 >= 0; var10000 = var3) {
			Attribute var4;
			if ((var4 = var1[var3]) instanceof Code) {
				final Code var5 = (Code) var4;
				this.method312(var5.getAttributes(), var2);
			} else if (!(var4 instanceof ConstantValue) && !(var4 instanceof Deprecated)
					&& !(var4 instanceof ExceptionTable) && !(var4 instanceof InnerClasses)
					&& !(var4 instanceof LineNumberTable) && !(var4 instanceof LocalVariableTable)
					&& !(var4 instanceof PMGClass)) {
				String var7;
				if (var4 instanceof Signature) {
					Signature var15;
					final String var6 = (var15 = (Signature) var4).getSignature();
					var7 = this.method305(var6);
					var15.setSignatureIndex(var2.addUtf8(var7));
				} else if (!(var4 instanceof SourceFile) && !(var4 instanceof StackMap)
						&& !(var4 instanceof Synthetic)) {
					if (var4 instanceof EnclosingMethod) {
						EnclosingMethod var16;
						if ((var16 = (EnclosingMethod) var4).getEnclosingMethodIndex() != 0) {
							final ConstantPool var17 = var2.getConstantPool();
							var7 = var16.getEnclosingClass().getBytes(var17).replace('/', '.');
							String var8;
							if ((var8 = Class165.method1655(Class172.method1708(this.aClass172_509))
									.get(var7)) != null) {
								var7 = var8;
							}

							ConstantNameAndType var9;
							if ((var9 = var16.getEnclosingMethod()) != null) {
								final String var10 = var9.getName(var17);
								final String var11 = var9.getSignature(var17);
								String var12 = NameRepository
										.getConstantNamingMap(Class172.method1707(this.aClass172_509))
										.get(var7 + "&" + var10 + "&" + var11);
								String var13 = NameRepository.method1534(Class172.method1707(this.aClass172_509))
										.get(var11);
								if (var12 != null && !var12.equals(var10) || var13 != null) {
									if (var12 == null) {
										var12 = var10;
									}

									if (var13 == null) {
										var13 = var11;
									}

									final int var14 = var2.addNameAndType(var12, var13);
									var16.setEnclosingMethodIndex(var14);
								}
							}
						}
					} else if (var4 instanceof Unknown) {
					}
				}
			}

			--var3;
		}

	}

	private void renameMethods(ClassGen var1) {
		Method[] var2;
		int var3;
		for (int var10000 = var3 = (var2 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var3) {
			Method var4;
			final MethodGen var5 = InitUtils.createMethodGen(var4 = var2[var3], var1.getClassName(),
					var1.getConstantPool(), var1.getConstantPool().getConstantPool());
			this.method321(var1, var5);
			final String var6 = NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
					.get(var1.getClassName() + "&" + var4.getName() + "&" + var4.getSignature());
			var5.setName(var6);
			boolean var7 = false;

			Type[] var8;
			int var9;
			Type var10;
			for (var10000 = var9 = (var8 = var5.getArgumentTypes()).length - 1; var10000 >= 0; var10000 = var9) {
				if ((var10 = MethodUtils.method1449(var8[var9],
						Class165.method1653(Class172.method1708(this.aClass172_509)))) != null) {
					var5.setArgumentType(var9, var10);
					var7 = true;
				}

				--var9;
			}

			if ((var10 = MethodUtils.method1449(var5.getReturnType(),
					Class165.method1653(Class172.method1708(this.aClass172_509)))) != null) {
				var5.setReturnType(var10);
				var7 = true;
			}

			if (var7) {
				NameRepository.method1534(Class172.method1707(this.aClass172_509)).put(var4.getSignature(),
						var5.getSignature());
			}

			if ((Class165.method1652(Class172.method1708(this.aClass172_509)).containsKey(var1.getClassName())
					|| Packaging.method575()) && !var4.isPrivate()) {
				MethodUtils.method1448(var5);
			}

			this.aClass26_510.method494(var5);
			this.method331(var5.getAnnotationEntries(), var1.getConstantPool());

			int var11;
			for (var10000 = var11 = var5.getArgumentTypes().length - 1; var10000 >= 0; var10000 = var11) {
				this.method320(var5.getAnnotationsOnParameter(var11), var1.getConstantPool());
				--var11;
			}

			var1.replaceMethod(var4, var5.getMethod());
			--var3;
		}

	}

	private String method314(String var1) {
		String var2;
		if ((var2 = Class141.method1520(Class172.method1711(this.aClass172_509)).get(var1)) != null) {
			return var2;
		} else {
			final StringTokenizer var3 = new StringTokenizer(var1, ".");
			var2 = "";

			for (StringTokenizer var10000 = var3; var10000.hasMoreTokens(); var10000 = var3) {
				if (var2.length() > 0) {
					var2 = var2 + ".";
				}

				var2 = var2 + var3.nextToken();
				if (!Class141.method1520(Class172.method1711(this.aClass172_509)).containsKey(var2)) {
					Class141.method1521(Class172.method1711(this.aClass172_509)).reset();
					String var5 = MethodUtils.getPackageName(var2);
					if ((var5 = Class141.method1520(Class172.method1711(this.aClass172_509)).get(var5)).length() > 0) {
						var5 = var5 + ".";
					}

					String var4;
					do {
						var4 = var5 + Class141.method1521(Class172.method1711(this.aClass172_509)).next();
					} while (Class141.method1522(Class172.method1711(this.aClass172_509)).containsKey(var4));

					Class141.method1520(Class172.method1711(this.aClass172_509)).put(var2, var4);
					Class141.method1522(Class172.method1711(this.aClass172_509)).put(var4, "&");
				}
			}

			return Class141.method1520(Class172.method1711(this.aClass172_509)).get(var1);
		}
	}

	private void renameConstantPoolConstants(ClassGen var1) {
		ConstantPoolGen var2;
		int var3;
		String var7;
		String var8;
		String var11;
		int var10000;
		for (var10000 = var3 = (var2 = var1.getConstantPool()).getSize() - 1; var10000 >= 0; var10000 = var3) {
			Constant var4;
			if (!((var4 = var2.getConstant(var3)) instanceof ConstantClass)) {
				if (var4 instanceof ConstantCP) {
					final ConstantPool var5 = var2.getConstantPool();
					ConstantCP var6;
					var7 = (var6 = (ConstantCP) var4).getClass(var5);
					if ((var8 = Class165.method1655(Class172.method1708(this.aClass172_509)).get(var7)) != null) {
						var7 = var8;
					}

					ConstantNameAndType var9;
					final String var10 = (var9 = (ConstantNameAndType) var2.getConstant(var6.getNameAndTypeIndex()))
							.getName(var5);
					var11 = var9.getSignature(var5);
					String var12 = null;
					String var13 = null;
					if (!(var6 instanceof ConstantMethodref) && !(var6 instanceof ConstantInterfaceMethodref)) {
						if (var6 instanceof ConstantFieldref) {
							var12 = Class159.method1626(Class172.method1710(this.aClass172_509))
									.get(var7 + "&" + var10 + "&" + var11);
							var13 = Class159.method1628(Class172.method1710(this.aClass172_509)).get(var11);
						}
					} else {
						var12 = NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
								.get(var7 + "&" + var10 + "&" + var11);
						var13 = NameRepository.method1534(Class172.method1707(this.aClass172_509)).get(var11);
					}

					if (var12 != null && !var12.equals(var10) || var13 != null) {
						if (var12 == null) {
							var12 = var10;
						}

						if (var13 == null) {
							var13 = var11;
						}

						final int var14 = var2.addNameAndType(var12, var13);
						var6.setNameAndTypeIndex(var14);
					}
				} else if (var4 instanceof ConstantUtf8) {
				}
			}

			--var3;
		}

		int var15;
		for (var10000 = var15 = var2.getSize() - 1; var10000 >= 0; var10000 = var15) {
			Constant var16;
			if ((var16 = var2.getConstant(var15)) instanceof ConstantClass) {
				final ConstantPool var18 = var2.getConstantPool();
				ConstantClass var19;
				var8 = ((String) (var19 = (ConstantClass) var16).getConstantValue(var18)).replace('/', '.');
				boolean var20 = false;

				int var22;
				for (var10000 = var22 = 0; var10000 < var8.length() && var8.charAt(var22) == 91; var10000 = var22) {
					++var22;
				}

				if (var22 > 0 && var8.endsWith(";") && var8.charAt(var22) == 76) {
					var8 = var8.substring(var22 + 1, var8.length() - 1);
					var20 = true;
				}

				if ((var11 = Class165.method1653(Class172.method1708(this.aClass172_509)).get(var8)) != null) {
					var11 = var11.replace('.', '/');
					if (var20) {
						var11 = "[L" + var11 + ";";

						for (var10000 = var22; var10000 > 1; var10000 = var22) {
							var11 = "[" + var11;
							--var22;
						}
					}

					final int var23 = var2.addUtf8(var11);
					var19.setNameIndex(var23);
				}
			} else if (!(var16 instanceof ConstantCP) && var16 instanceof ConstantUtf8) {
				ConstantUtf8 var17;
				var7 = (var17 = (ConstantUtf8) var16).getBytes();
				if ((var8 = Class165.method1653(Class172.method1708(this.aClass172_509)).get(var7)) != null) {
					var17.setBytes(var8);
				} else {
					label152: {
						int var21;
						for (var10000 = var21 = 0; var10000 < var7.length()
								&& var7.charAt(var21) == 91; var10000 = var21) {
							++var21;
						}

						if (var21 > 0 && var7.endsWith(";") && var7.charAt(var21) == 76) {
							var7 = var7.substring(var21 + 1, var7.length() - 1).replace('/', '.');
							if ((var8 = Class165.method1653(Class172.method1708(this.aClass172_509))
									.get(var7)) != null) {
								var8 = "[L" + var8.replace('.', '/') + ";";

								for (var10000 = var21; var10000 > 1; var10000 = var21) {
									var8 = "[" + var8;
									--var21;
								}

								var17.setBytes(var8);
								break label152;
							}
						}

						if (var7.startsWith("L") && var7.endsWith(";")) {
							var7 = var7.substring(1, var7.length() - 1).replace('/', '.');
							if ((var8 = Class165.method1653(Class172.method1708(this.aClass172_509))
									.get(var7)) != null) {
								var8 = "L" + var8.replace('.', '/') + ";";
								var17.setBytes(var8);
							}
						}
					}
				}
			}

			--var15;
		}

	}

	public Renamer(ClassStorage var1) {
		this.aClassStorage_504 = var1;
		if (Packaging.method579()) {
			try {
				DefaultHandler.method1851(new DefaultHandler(this, null));
			} catch (final Exception var3) {
				throw new RuntimeException(var3);
			}
		}

		this.aClass56_508 = new Class56(var1);
	}

	private String method316(ClassGen var1, String var2, String var3) {
		String var4;
		if ((var4 = NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
				.get(var1.getClassName() + "&" + var2 + "&" + var3)) != null) {
			return var4;
		} else {
			Vector<?> var5;
			int var6;
			for (int var10000 = var6 = (var5 = this.aClass56_508.method691(var1.getClassName())).size()
					- 1; var10000 >= 0; var10000 = var6) {
				if ((var4 = this.method316((ClassGen) var5.get(var6), var2, var3)) != null) {
					return var4;
				}

				--var6;
			}

			return null;
		}
	}

	private void method318() {
		Class141.method1520(Class172.method1711(this.aClass172_509)).put("", "");

		Iterator<?> var1;
		for (Iterator<?> var10000 = var1 = this.aClass56_508.method703().iterator(); var10000.hasNext(); var10000 = var1) {
			final ClassGen var2 = (ClassGen) var1.next();
			this.renamePackage(var2);
			this.method323(var2);
			this.method327(var2);
		}

	}

	private void method319(ClassGen var1, String var2, String var3, String var4, boolean var5, boolean var6) {
		if (var5) {
			ClassGen var7 = var1;

			do {
				final String var8 = var7.getSuperclassName();
				if ((var7 = this.aClassStorage_504.getClassGen(var8)) != null) {
					NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
							.put(var7.getClassName() + "&" + var2 + "&" + var4, var3);
					if (var7.isAnnotation()) {
						NameRepository.getSignatureNamingMap(Class172.method1707(this.aClass172_509))
								.put(var7.getClassName() + "&" + var2, var3);
					}

					NameRepository.getMethodRenamingMap(Class172.method1707(this.aClass172_509))
							.put(var7.getClassName() + "&" + var3 + "&" + MethodUtils.method1454(var4), "&");
				}
			} while (var7 != null);
		}

		Vector<?> var10;
		int var11;
		for (int var10000 = var11 = (var10 = this.aClass56_508.method691(var1.getClassName())).size()
				- 1; var10000 >= 0; var10000 = var11) {
			final ClassGen var9 = (ClassGen) var10.get(var11);
			if (var6 && Packaging.method578() && !MethodUtils.getPackageName(var1.getClassName())
					.equals(MethodUtils.getPackageName(var9.getClassName()))) {
				NameRepository.getMethodRenamingMap(Class172.method1707(this.aClass172_509))
						.put(var9.getClassName() + "&" + var3 + "&" + MethodUtils.method1454(var4), "&");
			} else {
				NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
						.put(var9.getClassName() + "&" + var2 + "&" + var4, var3);
				if (var9.isAnnotation()) {
					NameRepository.getSignatureNamingMap(Class172.method1707(this.aClass172_509))
							.put(var9.getClassName() + "&" + var2, var3);
				}

				NameRepository.getMethodRenamingMap(Class172.method1707(this.aClass172_509))
						.put(var9.getClassName() + "&" + var3 + "&" + MethodUtils.method1454(var4), "&");
			}

			this.method319(var9, var2, var3, var4, var5, var6);
			--var11;
		}

	}

	private void method320(List<?> var1, ConstantPoolGen var2) {
		if (var1 != null) {
			int var3;
			for (int var10000 = var3 = var1.size() - 1; var10000 >= 0; var10000 = var3) {
				this.renameSignatures((AnnotationEntryGen) var1.get(var3), var2);
				--var3;
			}

		}
	}

	private void method321(ClassGen var1, MethodGen var2) {
		int var3;
		if ((var3 = LocalVariables.getType(this.aClassStorage_504, var1, var2)) == 4) {
			var2.removeLocalVariables();
		} else {
			final boolean var4 = var3 == 3;
			final LocalVariableGen[] var5 = var2.getLocalVariables();
			int var6 = var2.getArgumentNames().length;
			if (!var2.isStatic()) {
				++var6;
			}

			RenamingUtils.method1603(Class172.method1709(this.aClass172_509)).reset();

			int var7;
			for (int var10000 = var7 = var5.length - 1; var10000 >= 0; var10000 = var7) {
				LocalVariableGen var8;
				Type var9;
				if ((var9 = MethodUtils.method1449((var8 = var5[var7]).getType(),
						Class165.method1653(Class172.method1708(this.aClass172_509)))) != null) {
					var8.setType(var9);
				}

				if ((var3 != 5 || var7 >= var6) && !var4) {
					var8.setName(RenamingUtils.method1603(Class172.method1709(this.aClass172_509)).next());
				}

				--var7;
			}

		}
	}

	private boolean method322(ClassGen var1, String var2, String var3, boolean var4, boolean var5) {
		if (var5) {
			ClassGen var6 = var1;

			do {
				final String var7 = var6.getSuperclassName();
				if ((var6 = this.aClassStorage_504.getClassGen(var7)) != null
						&& NameRepository.getMethodRenamingMap(Class172.method1707(this.aClass172_509))
								.containsKey(var6.getClassName() + "&" + var2 + "&" + var3)) {
					return true;
				}
			} while (var6 != null);
		}

		if (NameRepository.getMethodRenamingMap(Class172.method1707(this.aClass172_509))
				.containsKey(var1.getClassName() + "&" + var2 + "&" + var3)) {
			return true;
		} else if (!var4) {
			return false;
		} else {
			Vector<?> var8;
			int var9;
			for (int var10000 = var9 = (var8 = this.aClass56_508.method691(var1.getClassName())).size()
					- 1; var10000 >= 0; var10000 = var9) {
				if (this.method322((ClassGen) var8.get(var9), var2, var3, var4, var5)) {
					return true;
				}

				--var9;
			}

			return false;
		}
	}

	private void method323(ClassGen var1) {
		final String var2 = var1.getClassName();

		Method[] var3;
		int var4;
		for (int var10000 = var4 = (var3 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var4) {
			Method var5;
			final String var6 = (var5 = var3[var4]).getName();
			final String var7 = var5.getSignature();
			String var8;
			if (!var5.isPrivate()) {
				var8 = this.method316(var1, var6, var7);
			} else {
				var8 = NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
						.get(var2 + "&" + var6 + "&" + var7);
			}

			if (var8 == null) {
				NameRepository.method1536(Class172.method1707(this.aClass172_509)).reset();

				do {
					var8 = NameRepository.method1536(Class172.method1707(this.aClass172_509)).next();
				} while (this.method322(var1, var8, MethodUtils.method1454(var7), !var5.isPrivate(),
						var1.isInterface()));
			}

			NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509))
					.put(var2 + "&" + var6 + "&" + var7, var8);
			if (var1.isAnnotation()) {
				NameRepository.getSignatureNamingMap(Class172.method1707(this.aClass172_509)).put(var2 + "&" + var6,
						var8);
			}

			NameRepository.getMethodRenamingMap(Class172.method1707(this.aClass172_509))
					.put(var2 + "&" + var8 + "&" + MethodUtils.method1454(var7), "&");
			if (!var5.isPrivate()) {
				this.method319(var1, var6, var8, var7, var1.isInterface(), MethodUtils.method1450(var5));
			}

			--var4;
		}

	}

	// $FF: synthetic method
	public static Class172 method324(Renamer var0) {
		return var0.aClass172_509;
	}

	public void run() throws RunException {
		this.aClass56_508.method699();
		this.method301();
		this.method318();
		this.method308();
		this.method297();
		LogFile.setObfuscationHandler(new ObfuscationHandler(Class165.method1653(Class172.method1708(this.aClass172_509)),
				NameRepository.getConstantNamingMap(Class172.method1707(this.aClass172_509)),
				Class159.method1626(Class172.method1710(this.aClass172_509)), this.aClass26_510.method496(),
				this.aClass26_510.method497(),
				NameRepository.getSignatureNamingMap(Class172.method1707(this.aClass172_509))));
	}

	private boolean method326(ClassGen var1, String var2, String var3, boolean var4) {
		if (Class159.method1625(Class172.method1710(this.aClass172_509))
				.containsKey(var1.getClassName() + "&" + var2 + "&" + var3)) {
			return true;
		} else if (!var4) {
			return false;
		} else {
			Vector<?> var5;
			int var6;
			for (int var10000 = var6 = (var5 = this.aClass56_508.method691(var1.getClassName())).size()
					- 1; var10000 >= 0; var10000 = var6) {
				if (this.method326((ClassGen) var5.get(var6), var2, var3, var4)) {
					return true;
				}

				--var6;
			}

			return false;
		}
	}

	private void method327(ClassGen var1) {
		final String var2 = var1.getClassName();
		final Field[] var3 = var1.getFields();
		Class159.method1627(Class172.method1710(this.aClass172_509)).reset();

		int var4;
		for (int var10000 = var4 = var3.length - 1; var10000 >= 0; var10000 = var4) {
			Field var5;
			final String var6 = (var5 = var3[var4]).getName();
			final String var7 = var5.getSignature();
			String var8;
			if ((var8 = Class159.method1626(Class172.method1710(this.aClass172_509))
					.get(var2 + "&" + var6 + "&" + var7)) == null) {
				var8 = Class159.method1627(Class172.method1710(this.aClass172_509)).next();

				for (Renamer var9 = this; var9.method326(var1, var8, var7, !var5.isPrivate()); var9 = this) {
					var8 = Class159.method1627(Class172.method1710(this.aClass172_509)).next();
				}

				Class159.method1626(Class172.method1710(this.aClass172_509)).put(var2 + "&" + var6 + "&" + var7, var8);
				Class159.method1625(Class172.method1710(this.aClass172_509)).put(var2 + "&" + var8 + "&" + var7, "&");
			}

			if (!var5.isPrivate()) {
				this.method306(var1, var6, var8, var7);
			}

			--var4;
		}

	}

	private boolean method328(ClassGen var1) {
		Method[] var2;
		int var3;
		for (int var10000 = var3 = (var2 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var3) {
			if (var2[var3].isNative()) {
				return true;
			}

			--var3;
		}

		return false;
	}

	private void renameSignatures(AnnotationEntryGen var1, ConstantPoolGen var2) {
		final String var3 = var1.getTypeSignature();
		String var4 = null;
		if (var3.startsWith("L") && var3.endsWith(";")) {
			String var5;
			var4 = var5 = var3.substring(1, var3.length() - 1).replace('/', '.');
			String var6;
			if ((var6 = Class165.method1653(Class172.method1708(this.aClass172_509)).get(var5)) != null) {
				var6 = "L" + var6.replace('.', '/') + ";";
				var1.setTypeIndex(var2.addUtf8(var6));
			}
		}

		List<?> var11;
		int var12;
		for (int var10000 = var12 = (var11 = var1.getValues()).size() - 1; var10000 >= 0; var10000 = var12) {
			final ElementValuePairGen var7 = (ElementValuePairGen) var11.get(var12);
			if (var4 != null) {
				final String var8 = var7.getNameString();
				final String var9 = var4 + "&" + var8;
				if (NameRepository.getSignatureNamingMap(Class172.method1707(this.aClass172_509)).containsKey(var9)) {
					final String var10 = NameRepository.getSignatureNamingMap(Class172.method1707(this.aClass172_509))
							.get(var9);
					var7.setNameIndex(var2.addUtf8(var10));
				}
			}

			this.method296(var7.getValue(), var2);
			--var12;
		}

	}

	private void method330(ClassGen var1) {
		if (Class165.method1652(Class172.method1708(this.aClass172_509)).containsKey(var1.getClassName())
				|| Packaging.method575()) {
			MethodUtils.method1448(var1);
		}

		if (Tuning.isFinalizingEnabled() && !var1.isAbstract() && !var1.isInterface() && !var1.isAnnotation()
				&& !var1.isEnum() && this.aClass56_508.method691(var1.getClassName()).isEmpty()) {
			var1.isFinal(true);
		}

		final String var2 = Class165.method1653(Class172.method1708(this.aClass172_509)).get(var1.getClassName());
		var1.setClassName(var2);
		this.aClass26_510.method498(var1);

		String[] var3;
		int var4;
		for (int var10000 = var4 = (var3 = var1.getInterfaceNames()).length - 1; var10000 >= 0; var10000 = var4) {
			final String var5 = var3[var4];
			String var6;
			if ((var6 = Class165.method1653(Class172.method1708(this.aClass172_509)).get(var5)) != null) {
				var1.removeInterface(var5);
				var1.addInterface(var6);
				var1.getConstantPool().addClass(var6);
			}

			--var4;
		}

		this.method331(var1.getAnnotationEntries(), var1.getConstantPool());
	}

	private void method331(AnnotationEntryGen[] var1, ConstantPoolGen var2) {
		if (var1 != null) {
			int var3;
			for (int var10000 = var3 = var1.length - 1; var10000 >= 0; var10000 = var3) {
				this.renameSignatures(var1[var3], var2);
				--var3;
			}

		}
	}
}
