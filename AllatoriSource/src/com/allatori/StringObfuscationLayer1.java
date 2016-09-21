package com.allatori;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.StackMap;
import org.apache.bcel.classfile.StackMapEntry;
import org.apache.bcel.classfile.StackMapType;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.BranchHandle;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.IINC;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LDC_W;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.POP2;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.PUTFIELD;
import org.apache.bcel.generic.PUTSTATIC;
import org.apache.bcel.generic.Type;

public class StringObfuscationLayer1 implements ObfuscationType {

	private static String decryptStringMethodName = "DecryptString";
	private boolean addClass = false;
	public static String rAllatoriStr = "allatori-12321-32123:";
	private static String string_decryptor_class_name = "com_allatori_string_decryptor";
	private final Collection<String> collection = new TreeSet<String>();
	private final ClassStorage classStorage;
	private static String decryptString2MethodName = "DecryptString2";

	private void createStrongDecrypt1Method(ClassGen classGen) {
		final InstructionFactory instructionFactory = new InstructionFactory(classGen);
		final ConstantPoolGen constantPoolGen = classGen.getConstantPool();
		final InstructionList instructionList = new InstructionList();
		final MethodGen methodGen = new MethodGen(9, Type.STRING, new Type[] { Type.STRING }, new String[] { "s" },
				decryptStringMethodName, classGen.getClassName(), instructionList, constantPoolGen);
		instructionList.append(instructionFactory.createNew("java.lang.String"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		instructionList.append(instructionFactory.createInvoke("java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
		instructionList.append(instructionFactory.createNewArray(Type.CHAR, (short) 1));
		instructionList.append(new PUSH(constantPoolGen, 1));
		instructionList.append(new DUP());
		instructionList.append(new POP2());
		instructionList.append(new PUSH(constantPoolGen, 85));
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(InstructionConstants.ARRAYLENGTH);
		instructionList.append(new PUSH(constantPoolGen, 1));
		instructionList.append(InstructionConstants.ISUB);
		instructionList.append(InstructionConstants.DUP_X2);
		instructionList.append(InstructionFactory.createStore(Type.INT, 3));
		instructionList.append(InstructionFactory.createStore(Type.OBJECT, 1));
		instructionList.append(InstructionFactory.createStore(Type.INT, 2));
		final BranchInstruction branchInstruction = InstructionFactory.createBranchInstruction((short) 155, null);
		final BranchHandle branchHandle = instructionList.append(branchInstruction);
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 1));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		instructionList.append(
				instructionFactory.createInvoke("java.lang.String", "charAt", Type.CHAR, new Type[] { Type.INT }, (short) 182));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 2));
		instructionList.append(InstructionConstants.IXOR);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(InstructionConstants.DUP_X1);
		instructionList.append(InstructionFactory.createLoad(Type.INT, 2));
		instructionList.append(InstructionConstants.IXOR);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(new PUSH(constantPoolGen, 63));
		instructionList.append(InstructionConstants.IAND);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(InstructionFactory.createStore(Type.INT, 2));
		instructionList.append(InstructionConstants.CASTORE);
		instructionList.append(new IINC(3, -1));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		final BranchInstruction branchInstruction0 = InstructionFactory.createBranchInstruction((short) 155, null);
		instructionList.append(branchInstruction0);
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 1));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		instructionList.append(
				instructionFactory.createInvoke("java.lang.String", "charAt", Type.CHAR, new Type[] { Type.INT }, (short) 182));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 2));
		instructionList.append(InstructionConstants.IXOR);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(InstructionConstants.DUP_X1);
		instructionList.append(InstructionFactory.createLoad(Type.INT, 2));
		instructionList.append(InstructionConstants.IXOR);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(new PUSH(constantPoolGen, 63));
		instructionList.append(InstructionConstants.IAND);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(InstructionFactory.createStore(Type.INT, 2));
		instructionList.append(InstructionConstants.CASTORE);
		instructionList.append(new IINC(3, -1));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		instructionList.append(InstructionFactory.createBranchInstruction((short) 167, branchHandle));
		final InstructionHandle instructionHandle = instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 1));
		instructionList.append(instructionFactory.createInvoke("java.lang.String", "<init>", Type.VOID,
				new Type[] { new ArrayType(Type.CHAR, 1) }, (short) 183));
		instructionList.append(InstructionFactory.createReturn(Type.OBJECT));
		branchInstruction.setTarget(instructionHandle);
		branchInstruction0.setTarget(instructionHandle);
		methodGen.setMaxStack();
		methodGen.setMaxLocals();
		classGen.addMethod(methodGen.getMethod());
		instructionList.dispose();
	}

	private void createWeakDecryptMethod(ClassGen classGen) {
		final InstructionFactory var2 = new InstructionFactory(classGen);
		final ConstantPoolGen var4 = classGen.getConstantPool();
		final InstructionList var5 = new InstructionList();
		final MethodGen var6 = new MethodGen(9, Type.STRING, new Type[] { Type.STRING }, new String[] { "s" },
				decryptStringMethodName, classGen.getClassName(), var5, var4);
		final int var7 = var4.addClass("java.lang.String");
		final int var8 = var4.addClass("[C");
		final int var9 = var4.addUtf8("StackMap");
		final ConstantPool var10 = var4.getConstantPool();
		final StackMapEntry var11 = new StackMapEntry(27, 4,
				new StackMapType[] { new StackMapType((byte) 7, var7, var10), new StackMapType((byte) 7, var8, var10),
						new StackMapType((byte) 1, 0, var10), new StackMapType((byte) 1, 0, var10) },
				0, new StackMapType[0], var10);
		final StackMapEntry var12 = new StackMapEntry(58, 4,
				new StackMapType[] { new StackMapType((byte) 7, var7, var10), new StackMapType((byte) 7, var8, var10),
						new StackMapType((byte) 1, 0, var10), new StackMapType((byte) 1, 0, var10) },
				0, new StackMapType[0], var10);
		final StackMap var13 = new StackMap(var9, 30, new StackMapEntry[] { var11, var12 }, var10);
		var6.addCodeAttribute(var13);
		var5.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		var5.append(var2.createInvoke("java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
		var5.append(var2.createNewArray(Type.CHAR, (short) 1));
		var5.append(new PUSH(var4, 1));
		var5.append(new DUP());
		var5.append(new POP2());
		var5.append(InstructionFactory.createStore(Type.OBJECT, 1));
		var5.append(new PUSH(var4, 85));
		var5.append(new PUSH(var4, 1));
		var5.append(new DUP());
		var5.append(new POP2());
		var5.append(InstructionFactory.createStore(Type.INT, 2));
		var5.append(InstructionFactory.createLoad(Type.OBJECT, 1));
		var5.append(InstructionConstants.ARRAYLENGTH);
		var5.append(new PUSH(var4, 1));
		var5.append(new PUSH(var4, 1));
		var5.append(new DUP());
		var5.append(new POP2());
		var5.append(InstructionConstants.ISUB);
		var5.append(InstructionFactory.createStore(Type.INT, 3));
		final BranchInstruction var14 = InstructionFactory.createBranchInstruction((short) 167, null);
		var5.append(var14);
		final InstructionHandle var15 = var5.append(InstructionFactory.createLoad(Type.OBJECT, 1));
		var5.append(InstructionFactory.createLoad(Type.INT, 3));
		var5.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		var5.append(InstructionFactory.createLoad(Type.INT, 3));
		var5.append(var2.createInvoke("java.lang.String", "charAt", Type.CHAR, new Type[] { Type.INT }, (short) 182));
		var5.append(InstructionFactory.createLoad(Type.INT, 2));
		var5.append(InstructionConstants.IXOR);
		var5.append(InstructionConstants.I2C);
		var5.append(new PUSH(var4, 1));
		var5.append(new DUP());
		var5.append(new POP2());
		var5.append(InstructionConstants.CASTORE);
		var5.append(InstructionFactory.createLoad(Type.INT, 2));
		var5.append(InstructionFactory.createLoad(Type.INT, 3));
		var5.append(InstructionConstants.IXOR);
		var5.append(InstructionConstants.I2C);
		var5.append(InstructionFactory.createStore(Type.INT, 2));
		var5.append(InstructionFactory.createLoad(Type.INT, 2));
		var5.append(new PUSH(var4, 63));
		var5.append(new PUSH(var4, 1));
		var5.append(new DUP());
		var5.append(new POP2());
		var5.append(InstructionConstants.IAND);
		var5.append(InstructionConstants.I2C);
		var5.append(InstructionFactory.createStore(Type.INT, 2));
		var5.append(new IINC(3, -1));
		final InstructionHandle var16 = var5.append(InstructionFactory.createLoad(Type.INT, 3));
		final BranchInstruction var17 = InstructionFactory.createBranchInstruction((short) 156, var15);
		var5.append(var17);
		var5.append(var2.createNew("java.lang.String"));
		var5.append(InstructionConstants.DUP);
		var5.append(InstructionFactory.createLoad(Type.OBJECT, 1));
		var5.append(var2.createInvoke("java.lang.String", "<init>", Type.VOID,
				new Type[] { new ArrayType(Type.CHAR, 1) }, (short) 183));
		var5.append(InstructionFactory.createReturn(Type.OBJECT));
		var14.setTarget(var16);
		var6.setMaxStack();
		var6.setMaxLocals();
		classGen.addMethod(var6.getMethod());
		var5.dispose();
	}

	public static String encryptHard(String string, String variableXor) {
		final int var2 = variableXor.length() - 1;
		int var3 = variableXor.length() - 1;
		final char[] var4 = new char[string.length()];
		char opcode = 85;
		int var6;
		for (int var10000 = var6 = var4.length - 1; var10000 >= 0; var10000 = var6) {
			var4[var6] = (char) (string.charAt(var6) ^ opcode ^ variableXor.charAt(var3));
			opcode = (char) ((char) (opcode ^ var6 ^ variableXor.charAt(var3)) & 63);
			--var3;
			if (var3 < 0) {
				var3 = var2;
			}
			--var6;
		}
		return new String(var4);
	}

	@Override
	public void terminate() {
		if (this.addClass) {
			this.classStorage.addClass(this.createDecryptionClass());
		}
	}

	@Override
	public void execute(ClassGen _cg) {
		if (Tuning.method1726(this.classStorage, _cg, null) != Tuning.STRING_ENCRYPTION_DISABLE) {
			final InstructionFactory var2 = new InstructionFactory(_cg);
			final Method[] methods = _cg.getMethods();
			int var4;
			for (int var10000 = var4 = 0; var10000 < methods.length; var10000 = var4) {
				Method method;
				if ((method = methods[var4]).getCode() != null) {
					MethodGen _mgen;
					InstructionList il;
					InstructionHandle var8;
					for (InstructionHandle var14 = var8 = (il = (_mgen = InitUtils.createMethodGen(method,
							_cg.getClassName(), _cg.getConstantPool(), _cg.getConstantPool().getConstantPool()))
									.getInstructionList()).getStart(); var14 != null; var14 = var8) {
						if (var8.getInstruction() instanceof LDC_W) {
							final LDC_W var9 = (LDC_W) var8.getInstruction();
							Constant var10;
							if ((var10 = _cg.getConstantPool()
									.getConstant(var9.getIndex())) instanceof ConstantString) {
								final String var11 = ((ConstantUtf8) _cg.getConstantPool()
										.getConstant(((ConstantString) var10).getStringIndex())).getBytes();
								if (this.classStorage.getClassGen(var11) != null
										|| this.collection.contains(var11)) {
									var8 = var8.getNext();
									continue;
								}

								int var12 = Tuning.method1716(this.classStorage, _cg, _mgen);
								String var13;
								if ((var13 = _cg.getClassName()).startsWith("com.allatori")) {
									if (var13.startsWith("com.allatori.obfuscate.opt")) {
										var12 = Tuning.STRING_ENCRYPTION_DEFAULT_TYPE;
									}

									if (var13.startsWith("com.allatori.obfuscate.ren")) {
										var12 = Tuning.STRING_ENCRYPTION_DEFAULT_TYPE;
									}

									if (var13.startsWith("com.allatori.io.re")) {
										var12 = Tuning.STRING_ENCRYPTION_DEFAULT_TYPE;
									}
								} else if (var13.startsWith("org.apache.bcel")) {
									var12 = Tuning.STRING_ENCRYPTION_DEFAULT_TYPE;
								}

								if (var12 == Tuning.STRING_ENCRYPTION_STRONG_TYPE) {
									var9.setIndex(_cg.getConstantPool().addString(rAllatoriStr + var11));
									il.append(var8,
											var2.createInvoke(string_decryptor_class_name, decryptString2MethodName,
													Type.STRING, new Type[] { Type.STRING }, (short) 184));
								} else {
									var9.setIndex(_cg.getConstantPool().addString(this.encryptWeak(var11)));
									il.append(var8,
											var2.createInvoke(string_decryptor_class_name, decryptStringMethodName,
													Type.STRING, new Type[] { Type.STRING }, (short) 184));
								}

								this.addClass = true;
							}
						}

						var8 = var8.getNext();
					}

					_mgen.setMaxStack();
					_cg.replaceMethod(method, _mgen.getMethod());
				}

				++var4;
			}

		}
	}

	private String encryptWeak(String var1) {
		final char[] var2 = new char[var1.length()];
		char var3 = 85;

		int var4;
		for (int var10000 = var4 = var2.length - 1; var10000 >= 0; var10000 = var4) {
			var2[var4] = (char) (var1.charAt(var4) ^ var3);
			var3 = (char) ((char) (var3 ^ var4) & 63);
			--var4;
		}

		return new String(var2);
	}

	private void method1366(ClassGen var1) {
		Field[] var2;
		int var3;
		int var10000;
		for (var10000 = var3 = (var2 = var1.getFields()).length - 1; var10000 >= 0; var10000 = var3) {
			ConstantValue var5;
			Constant var6;
			if ((var5 = var2[var3].getConstantValue()) != null && (var6 = var1.getConstantPool()
					.getConstant(var5.getConstantValueIndex())) instanceof ConstantString) {
				final String var7 = ((ConstantUtf8) var1.getConstantPool()
						.getConstant(((ConstantString) var6).getStringIndex())).getBytes();
				this.collection.add(var7);
			}

			--var3;
		}

		Method[] var4;
		int var15;
		for (var10000 = var15 = (var4 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var15) {
			Method var16;
			if ((var16 = var4[var15]).getCode() != null) {
				InstructionHandle var9 = InitUtils.createMethodGen(var16, var1.getClassName(), var1.getConstantPool(),
						var1.getConstantPool().getConstantPool()).getInstructionList().getStart();

				for (InstructionHandle var14 = var9; var14 != null; var14 = var9) {
					if ((var9.getInstruction() instanceof PUTSTATIC || var9.getInstruction() instanceof PUTFIELD)
							&& var9.getPrev() != null) {
						final InstructionHandle var10 = var9.getPrev();
						if (var10.getInstruction() instanceof LDC_W) {
							final LDC_W var11 = (LDC_W) var10.getInstruction();
							final Constant var12 = var1.getConstantPool().getConstant(var11.getIndex());
							if (var12 instanceof ConstantString) {
								final String var13 = ((ConstantUtf8) var1.getConstantPool()
										.getConstant(((ConstantString) var12).getStringIndex())).getBytes();
								this.collection.add(var13);
							}
						}
					}

					var9 = var9.getNext();
				}
			}

			--var15;
		}

	}

	private void method1367(ClassGen var1) {
		Field[] var2;
		int var3;
		for (int var10000 = var3 = (var2 = var1.getFields()).length - 1; var10000 >= 0; var10000 = var3) {
			Field var4;
			ConstantValue var5;
			Constant var6;
			if ((var5 = (var4 = var2[var3]).getConstantValue()) != null && (var6 = var1.getConstantPool()
					.getConstant(var5.getConstantValueIndex())) instanceof ConstantString && var4.isFinal()) {
				if (!var4.isStatic()) {
					FieldGen var15;
					(var15 = new FieldGen(var4, var1.getConstantPool())).cancelInitValue();
					var1.replaceField(var4, var15.getField());
				} else {
					Method clinit = null;

					Method[] var8;
					int var9;
					for (var10000 = var9 = (var8 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var9) {
						final Method var10 = var8[var9];
						if ("<clinit>".equals(var10.getName())) {
							clinit = var10;
							break;
						}

						--var9;
					}

					InstructionList var11;
					(var11 = new InstructionList()).insert(InstructionFactory.createReturn(Type.VOID));
					MethodGen var16;
					if (clinit != null) {
						var11 = (var16 = InitUtils.createMethodGen(clinit, var1.getClassName(), var1.getConstantPool(),
								var1.getConstantPool().getConstantPool())).getInstructionList();
					} else {
						var16 = new MethodGen(8, Type.VOID, Type.NO_ARGS, new String[0], "<clinit>",
								var1.getClassName(), var11, var1.getConstantPool());
					}

					final String var12 = ((ConstantUtf8) var1.getConstantPool()
							.getConstant(((ConstantString) var6).getStringIndex())).getBytes();
					final InstructionFactory var13 = new InstructionFactory(var1);
					var11.insert(
							var13.createFieldAccess(var1.getClassName(), var4.getName(), Type.STRING, (short) 179));
					var11.insert(new PUSH(var1.getConstantPool(), var12));
					var16.setMaxStack();
					var16.setMaxLocals();
					if (clinit != null) {
						var1.replaceMethod(clinit, var16.getMethod());
					} else {
						var1.addMethod(var16.getMethod());
					}

					var11.dispose();
					FieldGen var14;
					(var14 = new FieldGen(var4, var1.getConstantPool())).cancelInitValue();
					var1.replaceField(var4, var14.getField());
				}
			}

			--var3;
		}

	}

	private ClassGen createDecryptionClass() {
		final ClassGen var1 = new ClassGen(string_decryptor_class_name, "java.lang.Object", "", 33, new String[0]);
		if (Tuning.isWeakStringEncryption()) {
			this.createWeakDecryptMethod(var1);
		} else {
			this.createStrongDecrypt1Method(var1);
			this.createStrongDecrypt2Method(var1);
		}

		return var1;
	}

	public StringObfuscationLayer1(ClassStorage var1) {
		this.classStorage = var1;

		Iterator<?> var2;
		for (Iterator<?> var10000 = var2 = var1.valuesIterator(); var10000.hasNext(); var10000 = var2) {
			final ClassGen var3 = (ClassGen) var2.next();
			int var4;
			if ((var4 = Tuning.method1726(var1, var3, null)) == Tuning.STRING_ENCRYPTION_DEFAULT) {
				this.method1366(var3);
			} else if (var4 == Tuning.STRING_ENCRYPTION_MAXIMUM) {
				this.method1367(var3);
			}
		}

	}

	private void createStrongDecrypt2Method(ClassGen var1) {
		final InstructionFactory var2 = new InstructionFactory(var1);
		final ConstantPoolGen cp = var1.getConstantPool();
		final InstructionList il = new InstructionList();
		final MethodGen var6 = new MethodGen(9, Type.STRING, new Type[] { Type.STRING }, new String[] { "arg0" },
				decryptString2MethodName, var1.getClassName(), il, cp);
		il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		il.append(new PUSH(cp, 1));
		il.append(InstructionConstants.DUP);
		il.append(InstructionConstants.DUP_X2);
		il.append(var2.createNew("java.lang.Exception"));
		il.append(InstructionConstants.DUP);
		il.append(var2.createInvoke("java.lang.Exception", "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
		il.append(var2.createInvoke("java.lang.Exception", "getStackTrace",
				new ArrayType(new ObjectType("java.lang.StackTraceElement"), 1), Type.NO_ARGS, (short) 182));
		il.append(InstructionConstants.SWAP);
		il.append(InstructionConstants.AALOAD);
		il.append(var2.createNew("java.lang.StringBuffer"));
		il.append(InstructionConstants.DUP);
		il.append(var2.createInvoke("java.lang.StringBuffer", "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
		il.append(InstructionConstants.SWAP);
		il.append(InstructionConstants.DUP);
		il.append(var2.createInvoke("java.lang.StackTraceElement", "getClassName", Type.STRING, Type.NO_ARGS,
				(short) 182));
		il.append(InstructionConstants.SWAP);
		il.append(var2.createInvoke("java.lang.StackTraceElement", "getMethodName", Type.STRING, Type.NO_ARGS,
				(short) 182));
		il.append(InstructionConstants.DUP_X2);
		il.append(InstructionConstants.POP);
		il.append(var2.createInvoke("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.STRING },
				(short) 182));
		il.append(InstructionConstants.SWAP);
		il.append(var2.createInvoke("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.STRING },
				(short) 182));
		il.append(var2.createInvoke("java.lang.StringBuffer", "toString", Type.STRING, Type.NO_ARGS, (short) 182));
		il.append(InstructionConstants.DUP_X1);
		il.append(var2.createInvoke("java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
		il.append(InstructionConstants.SWAP);
		il.append(InstructionConstants.ISUB);
		il.append(InstructionConstants.DUP_X1);
		il.append(InstructionFactory.createStore(Type.INT, 3));
		il.append(InstructionFactory.createStore(Type.OBJECT, 2));
		il.append(InstructionFactory.createStore(Type.INT, 4));
		il.append(var2.createInvoke("java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
		il.append(InstructionConstants.DUP);
		il.append(var2.createNewArray(Type.CHAR, (short) 1));
		il.append(InstructionFactory.createStore(Type.OBJECT, 5));
		il.append(new PUSH(cp, 85));
		il.append(InstructionFactory.createStore(Type.INT, 6));
		il.append(InstructionConstants.SWAP);
		il.append(InstructionConstants.ISUB);
		il.append(InstructionFactory.createStore(Type.INT, 7));
		final BranchInstruction var7 = InstructionFactory.createBranchInstruction((short) 167, null);
		il.append(var7);
		final InstructionHandle var8 = il.append(new PUSH(cp, 63));
		il.append(InstructionConstants.DUP_X2);
		il.append(InstructionConstants.POP);
		il.append(InstructionConstants.DUP_X1);
		il.append(InstructionConstants.POP);
		il.append(InstructionFactory.createLoad(Type.INT, 6));
		il.append(InstructionConstants.DUP_X2);
		il.append(InstructionConstants.POP);
		il.append(InstructionFactory.createLoad(Type.OBJECT, 2));
		il.append(InstructionFactory.createLoad(Type.INT, 4));
		il.append(var2.createInvoke("java.lang.String", "charAt", Type.CHAR, new Type[] { Type.INT }, (short) 182));
		il.append(InstructionConstants.DUP_X1);
		il.append(InstructionFactory.createLoad(Type.INT, 7));
		il.append(InstructionConstants.DUP_X1);
		il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		il.append(InstructionConstants.SWAP);
		il.append(var2.createInvoke("java.lang.String", "charAt", Type.CHAR, new Type[] { Type.INT }, (short) 182));
		il.append(InstructionFactory.createLoad(Type.INT, 6));
		il.append(InstructionConstants.IXOR);
		il.append(InstructionConstants.IXOR);
		il.append(InstructionConstants.I2C);
		il.append(InstructionConstants.CASTORE);
		il.append(InstructionConstants.IXOR);
		il.append(InstructionConstants.IXOR);
		il.append(InstructionConstants.IAND);
		il.append(InstructionConstants.I2C);
		il.append(InstructionFactory.createStore(Type.INT, 6));
		il.append(new IINC(4, -1));
		il.append(InstructionFactory.createLoad(Type.INT, 4));
		final BranchInstruction var9 = InstructionFactory.createBranchInstruction((short) 156, null);
		il.append(var9);
		il.append(InstructionFactory.createLoad(Type.INT, 3));
		il.append(InstructionFactory.createStore(Type.INT, 4));
		final InstructionHandle var10 = il.append(new IINC(7, -1));
		final InstructionHandle var11 = il.append(InstructionFactory.createLoad(Type.OBJECT, 5));
		il.append(InstructionFactory.createLoad(Type.INT, 7));
		il.append(InstructionConstants.DUP);
		final BranchInstruction var12 = InstructionFactory.createBranchInstruction((short) 156, var8);
		il.append(var12);
		il.append(InstructionConstants.POP2);
		il.append(var2.createNew("java.lang.String"));
		il.append(InstructionFactory.createLoad(Type.OBJECT, 5));
		il.append(InstructionConstants.SWAP);
		il.append(InstructionConstants.DUP_X1);
		il.append(InstructionConstants.SWAP);
		il.append(var2.createInvoke("java.lang.String", "<init>", Type.VOID, new Type[] { new ArrayType(Type.CHAR, 1) },
				(short) 183));
		il.append(InstructionFactory.createReturn(Type.OBJECT));
		var7.setTarget(var11);
		var9.setTarget(var10);
		var6.setMaxStack();
		var6.setMaxLocals();
		var1.addMethod(var6.getMethod());
		il.dispose();
	}

}
