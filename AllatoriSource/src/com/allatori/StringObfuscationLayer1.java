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

	/* OK */

	private static String decryptStringMethodName = "DecryptString";
	private boolean addClass = false;
	public static String rAllatoriStr = "allatori-12321-32123:";
	private static String stringDecryptorClassName = "com_allatori_string_decryptor";
	private final Collection<String> collection = new TreeSet<String>();
	private final ClassStorage classStorage;
	private static String decryptString2MethodName = "DecryptString2";

	private void createStrongDecryptFirstMethod(ClassGen classGen) {
		final InstructionFactory instructionFactory = new InstructionFactory(classGen);
		final ConstantPoolGen constantPoolGen = classGen.getConstantPool();
		final InstructionList instructionList = new InstructionList();
		final MethodGen methodGen = new MethodGen(9, Type.STRING, new Type[] { Type.STRING }, new String[] { "s" },
				decryptStringMethodName, classGen.getClassName(), instructionList, constantPoolGen);
		instructionList.append(instructionFactory.createNew("java.lang.String"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		instructionList.append(
				instructionFactory.createInvoke("java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
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
		instructionList.append(instructionFactory.createInvoke("java.lang.String", "charAt", Type.CHAR,
				new Type[] { Type.INT }, (short) 182));
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
		instructionList.append(instructionFactory.createInvoke("java.lang.String", "charAt", Type.CHAR,
				new Type[] { Type.INT }, (short) 182));
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
		final InstructionHandle instructionHandle = instructionList
				.append(InstructionFactory.createLoad(Type.OBJECT, 1));
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
		final InstructionFactory instructionFactory = new InstructionFactory(classGen);
		final ConstantPoolGen constantPoolGen = classGen.getConstantPool();
		final InstructionList instructionList = new InstructionList();
		final MethodGen methodGen = new MethodGen(9, Type.STRING, new Type[] { Type.STRING }, new String[] { "s" },
				decryptStringMethodName, classGen.getClassName(), instructionList, constantPoolGen);
		final int lStr = constantPoolGen.addClass("java.lang.String");
		final int cp = constantPoolGen.addClass("[C");
		final int stackMap = constantPoolGen.addUtf8("StackMap");
		final ConstantPool constantPool = constantPoolGen.getConstantPool();
		final StackMapEntry stackMapEntry = new StackMapEntry(27, 4,
				new StackMapType[] { new StackMapType((byte) 7, lStr, constantPool),
						new StackMapType((byte) 7, cp, constantPool), new StackMapType((byte) 1, 0, constantPool),
						new StackMapType((byte) 1, 0, constantPool) },
				0, new StackMapType[0], constantPool);
		final StackMapEntry stackMapEntry0 = new StackMapEntry(58, 4,
				new StackMapType[] { new StackMapType((byte) 7, lStr, constantPool),
						new StackMapType((byte) 7, cp, constantPool), new StackMapType((byte) 1, 0, constantPool),
						new StackMapType((byte) 1, 0, constantPool) },
				0, new StackMapType[0], constantPool);
		final StackMap stackMap0 = new StackMap(stackMap, 30, new StackMapEntry[] { stackMapEntry, stackMapEntry0 },
				constantPool);
		methodGen.addCodeAttribute(stackMap0);
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		instructionList.append(
				instructionFactory.createInvoke("java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
		instructionList.append(instructionFactory.createNewArray(Type.CHAR, (short) 1));
		instructionList.append(new PUSH(constantPoolGen, 1));
		instructionList.append(new DUP());
		instructionList.append(new POP2());
		instructionList.append(InstructionFactory.createStore(Type.OBJECT, 1));
		instructionList.append(new PUSH(constantPoolGen, 85));
		instructionList.append(new PUSH(constantPoolGen, 1));
		instructionList.append(new DUP());
		instructionList.append(new POP2());
		instructionList.append(InstructionFactory.createStore(Type.INT, 2));
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 1));
		instructionList.append(InstructionConstants.ARRAYLENGTH);
		instructionList.append(new PUSH(constantPoolGen, 1));
		instructionList.append(new PUSH(constantPoolGen, 1));
		instructionList.append(new DUP());
		instructionList.append(new POP2());
		instructionList.append(InstructionConstants.ISUB);
		instructionList.append(InstructionFactory.createStore(Type.INT, 3));
		final BranchInstruction branchInstruction = InstructionFactory.createBranchInstruction((short) 167, null);
		instructionList.append(branchInstruction);
		final InstructionHandle instructionHandle = instructionList
				.append(InstructionFactory.createLoad(Type.OBJECT, 1));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		instructionList.append(instructionFactory.createInvoke("java.lang.String", "charAt", Type.CHAR,
				new Type[] { Type.INT }, (short) 182));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 2));
		instructionList.append(InstructionConstants.IXOR);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(new PUSH(constantPoolGen, 1));
		instructionList.append(new DUP());
		instructionList.append(new POP2());
		instructionList.append(InstructionConstants.CASTORE);
		instructionList.append(InstructionFactory.createLoad(Type.INT, 2));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		instructionList.append(InstructionConstants.IXOR);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(InstructionFactory.createStore(Type.INT, 2));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 2));
		instructionList.append(new PUSH(constantPoolGen, 63));
		instructionList.append(new PUSH(constantPoolGen, 1));
		instructionList.append(new DUP());
		instructionList.append(new POP2());
		instructionList.append(InstructionConstants.IAND);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(InstructionFactory.createStore(Type.INT, 2));
		instructionList.append(new IINC(3, -1));
		final InstructionHandle instructionHandle0 = instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		final BranchInstruction branchInstruction0 = InstructionFactory.createBranchInstruction((short) 156,
				instructionHandle);
		instructionList.append(branchInstruction0);
		instructionList.append(instructionFactory.createNew("java.lang.String"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 1));
		instructionList.append(instructionFactory.createInvoke("java.lang.String", "<init>", Type.VOID,
				new Type[] { new ArrayType(Type.CHAR, 1) }, (short) 183));
		instructionList.append(InstructionFactory.createReturn(Type.OBJECT));
		branchInstruction.setTarget(instructionHandle0);
		methodGen.setMaxStack();
		methodGen.setMaxLocals();
		classGen.addMethod(methodGen.getMethod());
		instructionList.dispose();
	}

	public static String encryptHard(String string, String variableXor) {
		final int l0 = variableXor.length() - 1;
		int l1 = variableXor.length() - 1;
		final char[] strRemp = new char[string.length()];
		char opcode = 85;
		for (int i = strRemp.length - 1; i >= 0; i--) {
			strRemp[i] = (char) (string.charAt(i) ^ opcode ^ variableXor.charAt(l1));
			opcode = (char) ((char) (opcode ^ i ^ variableXor.charAt(l1)) & 63);
			--l1;
			if (l1 < 0) {
				l1 = l0;
			}
		}
		return new String(strRemp);
	}

	@Override
	public void terminate() {
		if (this.addClass) {
			this.classStorage.addClass(this.createDecryptionClass());
		}
	}

	@Override
	public void execute(ClassGen classGen) {
		if (Tuning.getStringObfuscationType(this.classStorage, classGen, null) != Tuning.STRING_ENCRYPTION_DISABLE) {
			final InstructionFactory instructionFactory = new InstructionFactory(classGen);
			final Method[] methods = classGen.getMethods();
			for (int i = 0; i < methods.length; i++) {
				final Method method = methods[i];
				if (method.getCode() != null) {
					final MethodGen methodGen = InitUtils.createMethodGen(method, classGen.getClassName(),
							classGen.getConstantPool(), classGen.getConstantPool().getConstantPool());
					final InstructionList instructionList = methodGen.getInstructionList();
					InstructionHandle actualInstruction = instructionList.getStart();
					for (InstructionHandle instructionHandle = actualInstruction; instructionHandle != null; instructionHandle = actualInstruction) {
						if (actualInstruction.getInstruction() instanceof LDC_W) {
							final LDC_W ldc = (LDC_W) actualInstruction.getInstruction();
							final Constant constant = classGen.getConstantPool().getConstant(ldc.getIndex());
							if (constant instanceof ConstantString) {
								final String strByt = ((ConstantUtf8) classGen.getConstantPool()
										.getConstant(((ConstantString) constant).getStringIndex())).getBytes();
								if (this.classStorage.getClassGen(strByt) != null || this.collection.contains(strByt)) {
									actualInstruction = actualInstruction.getNext();
									continue;
								}
								int obfTyp = Tuning.stringEncryptionIsWeak(this.classStorage, classGen, methodGen);
								final String className = classGen.getClassName();
								if (className.startsWith("com.allatori")) {
									if (className.startsWith("com.allatori.obfuscate.opt")) {
										obfTyp = Tuning.STRING_ENCRYPTION_DEFAULT_TYPE;
									}
									if (className.startsWith("com.allatori.obfuscate.ren")) {
										obfTyp = Tuning.STRING_ENCRYPTION_DEFAULT_TYPE;
									}
									if (className.startsWith("com.allatori.io.re")) {
										obfTyp = Tuning.STRING_ENCRYPTION_DEFAULT_TYPE;
									}
								} else if (className.startsWith("org.apache.bcel")) {
									obfTyp = Tuning.STRING_ENCRYPTION_DEFAULT_TYPE;
								}
								if (obfTyp == Tuning.STRING_ENCRYPTION_STRONG_TYPE) {
									ldc.setIndex(classGen.getConstantPool().addString(rAllatoriStr + strByt));
									instructionList.append(actualInstruction,
											instructionFactory.createInvoke(stringDecryptorClassName,
													decryptString2MethodName, Type.STRING, new Type[] { Type.STRING },
													(short) 184));
								} else {
									ldc.setIndex(classGen.getConstantPool().addString(this.encryptWeak(strByt)));
									instructionList.append(actualInstruction,
											instructionFactory.createInvoke(stringDecryptorClassName,
													decryptStringMethodName, Type.STRING, new Type[] { Type.STRING },
													(short) 184));
								}
								this.addClass = true;
							}
						}
						actualInstruction = actualInstruction.getNext();
					}
					methodGen.setMaxStack();
					classGen.replaceMethod(method, methodGen.getMethod());
				}
			}
		}
	}

	private String encryptWeak(String string) {
		final char[] strRemp = new char[string.length()];
		char opcode = 85;
		for (int i = strRemp.length - 1; i >= 0; i--) {
			strRemp[i] = (char) (string.charAt(i) ^ opcode);
			opcode = (char) ((char) (opcode ^ i) & 63);
			--i;
		}
		return new String(strRemp);
	}

	private void encryptDefault(ClassGen classGen) {
		final Field[] fields = classGen.getFields();
		for (int i = fields.length - 1; i >= 0; i--) {
			final ConstantValue constantValue = fields[i].getConstantValue();
			Constant constant;
			if (constantValue != null && (constant = classGen.getConstantPool()
					.getConstant(constantValue.getConstantValueIndex())) instanceof ConstantString) {
				final String string = ((ConstantUtf8) classGen.getConstantPool()
						.getConstant(((ConstantString) constant).getStringIndex())).getBytes();
				this.collection.add(string);
			}
		}
		final Method[] methods = classGen.getMethods();
		for (int i = methods.length - 1; i >= 0; i--) {
			final Method method = methods[i];
			if (method.getCode() != null) {
				InstructionHandle actualInstruction = InitUtils.createMethodGen(method, classGen.getClassName(),
						classGen.getConstantPool(), classGen.getConstantPool().getConstantPool()).getInstructionList()
						.getStart();
				for (InstructionHandle instructionHandle = actualInstruction; instructionHandle != null; instructionHandle = actualInstruction) {
					if ((actualInstruction.getInstruction() instanceof PUTSTATIC
							|| actualInstruction.getInstruction() instanceof PUTFIELD)
							&& actualInstruction.getPrev() != null) {
						final InstructionHandle prevInstruction = actualInstruction.getPrev();
						if (prevInstruction.getInstruction() instanceof LDC_W) {
							final LDC_W ldc = (LDC_W) prevInstruction.getInstruction();
							final Constant constant = classGen.getConstantPool().getConstant(ldc.getIndex());
							if (constant instanceof ConstantString) {
								final String string = ((ConstantUtf8) classGen.getConstantPool()
										.getConstant(((ConstantString) constant).getStringIndex())).getBytes();
								this.collection.add(string);
							}
						}
					}
					actualInstruction = actualInstruction.getNext();
				}
			}
		}
	}

	private void encryptMaximum(ClassGen classGen) {
		final Field[] fields = classGen.getFields();
		for (int i = fields.length - 1; i >= 0; i--) {
			final Field field = fields[i];
			final ConstantValue constantValue = field.getConstantValue();
			Constant constant;
			if (constantValue != null
					&& (constant = classGen.getConstantPool()
							.getConstant(constantValue.getConstantValueIndex())) instanceof ConstantString
					&& field.isFinal()) {
				if (!field.isStatic()) {
					final FieldGen fieldGen = new FieldGen(field, classGen.getConstantPool());
					fieldGen.cancelInitValue();
					classGen.replaceField(field, fieldGen.getField());
				} else {
					Method clinit = null;
					final Method[] methodArr = classGen.getMethods();
					for (int j = methodArr.length - 1; j >= 0; j--) {
						final Method method = methodArr[j];
						if ("<clinit>".equals(method.getName())) {
							clinit = method;
							break;
						}
					}
					InstructionList instructionList = new InstructionList();
					instructionList.insert(InstructionFactory.createReturn(Type.VOID));
					MethodGen methodGen = InitUtils.createMethodGen(clinit, classGen.getClassName(),
							classGen.getConstantPool(), classGen.getConstantPool().getConstantPool());
					if (clinit != null) {
						instructionList = methodGen.getInstructionList();
					} else {
						methodGen = new MethodGen(8, Type.VOID, Type.NO_ARGS, new String[0], "<clinit>",
								classGen.getClassName(), instructionList, classGen.getConstantPool());
					}
					final String string = ((ConstantUtf8) classGen.getConstantPool()
							.getConstant(((ConstantString) constant).getStringIndex())).getBytes();
					final InstructionFactory instructionFactory = new InstructionFactory(classGen);
					instructionList.insert(instructionFactory.createFieldAccess(classGen.getClassName(),
							field.getName(), Type.STRING, (short) 179));
					instructionList.insert(new PUSH(classGen.getConstantPool(), string));
					methodGen.setMaxStack();
					methodGen.setMaxLocals();
					if (clinit != null) {
						classGen.replaceMethod(clinit, methodGen.getMethod());
					} else {
						classGen.addMethod(methodGen.getMethod());
					}
					instructionList.dispose();
					final FieldGen fieldGen = new FieldGen(field, classGen.getConstantPool());
					fieldGen.cancelInitValue();
					classGen.replaceField(field, fieldGen.getField());
				}
			}
		}
	}

	private ClassGen createDecryptionClass() {
		final ClassGen classGen = new ClassGen(stringDecryptorClassName, "java.lang.Object", "", 33, new String[0]);
		if (Tuning.isWeakStringEncryption()) {
			this.createWeakDecryptMethod(classGen);
		} else {
			this.createStrongDecryptFirstMethod(classGen);
			this.createStrongDecryptSecondMethod(classGen);
		}
		return classGen;
	}

	public StringObfuscationLayer1(ClassStorage classStorage) {
		this.classStorage = classStorage;
		Iterator<?> actualIterator;
		for (Iterator<?> iterator = actualIterator = classStorage.valuesIterator(); iterator
				.hasNext(); iterator = actualIterator) {
			final ClassGen classGen = (ClassGen) actualIterator.next();
			final int type = Tuning.getStringObfuscationType(classStorage, classGen, null);
			if (type == Tuning.STRING_ENCRYPTION_DEFAULT) {
				this.encryptDefault(classGen);
			} else if (type == Tuning.STRING_ENCRYPTION_MAXIMUM) {
				this.encryptMaximum(classGen);
			}
		}
	}

	private void createStrongDecryptSecondMethod(ClassGen classGen) {
		final InstructionFactory instructionFactory = new InstructionFactory(classGen);
		final ConstantPoolGen constantPoolGen = classGen.getConstantPool();
		final InstructionList instructionList = new InstructionList();
		final MethodGen methodGen = new MethodGen(9, Type.STRING, new Type[] { Type.STRING }, new String[] { "arg0" },
				decryptString2MethodName, classGen.getClassName(), instructionList, constantPoolGen);
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		instructionList.append(new PUSH(constantPoolGen, 1));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(InstructionConstants.DUP_X2);
		instructionList.append(instructionFactory.createNew("java.lang.Exception"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(
				instructionFactory.createInvoke("java.lang.Exception", "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
		instructionList.append(instructionFactory.createInvoke("java.lang.Exception", "getStackTrace",
				new ArrayType(new ObjectType("java.lang.StackTraceElement"), 1), Type.NO_ARGS, (short) 182));
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(InstructionConstants.AALOAD);
		instructionList.append(instructionFactory.createNew("java.lang.StringBuffer"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(instructionFactory.createInvoke("java.lang.StringBuffer", "<init>", Type.VOID,
				Type.NO_ARGS, (short) 183));
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(instructionFactory.createInvoke("java.lang.StackTraceElement", "getClassName",
				Type.STRING, Type.NO_ARGS, (short) 182));
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(instructionFactory.createInvoke("java.lang.StackTraceElement", "getMethodName",
				Type.STRING, Type.NO_ARGS, (short) 182));
		instructionList.append(InstructionConstants.DUP_X2);
		instructionList.append(InstructionConstants.POP);
		instructionList.append(instructionFactory.createInvoke("java.lang.StringBuffer", "append", Type.STRINGBUFFER,
				new Type[] { Type.STRING }, (short) 182));
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(instructionFactory.createInvoke("java.lang.StringBuffer", "append", Type.STRINGBUFFER,
				new Type[] { Type.STRING }, (short) 182));
		instructionList.append(instructionFactory.createInvoke("java.lang.StringBuffer", "toString", Type.STRING,
				Type.NO_ARGS, (short) 182));
		instructionList.append(InstructionConstants.DUP_X1);
		instructionList.append(
				instructionFactory.createInvoke("java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(InstructionConstants.ISUB);
		instructionList.append(InstructionConstants.DUP_X1);
		instructionList.append(InstructionFactory.createStore(Type.INT, 3));
		instructionList.append(InstructionFactory.createStore(Type.OBJECT, 2));
		instructionList.append(InstructionFactory.createStore(Type.INT, 4));
		instructionList.append(
				instructionFactory.createInvoke("java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(instructionFactory.createNewArray(Type.CHAR, (short) 1));
		instructionList.append(InstructionFactory.createStore(Type.OBJECT, 5));
		instructionList.append(new PUSH(constantPoolGen, 85));
		instructionList.append(InstructionFactory.createStore(Type.INT, 6));
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(InstructionConstants.ISUB);
		instructionList.append(InstructionFactory.createStore(Type.INT, 7));
		final BranchInstruction branchInstruction = InstructionFactory.createBranchInstruction((short) 167, null);
		instructionList.append(branchInstruction);
		final InstructionHandle instructionHandle = instructionList.append(new PUSH(constantPoolGen, 63));
		instructionList.append(InstructionConstants.DUP_X2);
		instructionList.append(InstructionConstants.POP);
		instructionList.append(InstructionConstants.DUP_X1);
		instructionList.append(InstructionConstants.POP);
		instructionList.append(InstructionFactory.createLoad(Type.INT, 6));
		instructionList.append(InstructionConstants.DUP_X2);
		instructionList.append(InstructionConstants.POP);
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 2));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 4));
		instructionList.append(instructionFactory.createInvoke("java.lang.String", "charAt", Type.CHAR,
				new Type[] { Type.INT }, (short) 182));
		instructionList.append(InstructionConstants.DUP_X1);
		instructionList.append(InstructionFactory.createLoad(Type.INT, 7));
		instructionList.append(InstructionConstants.DUP_X1);
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(instructionFactory.createInvoke("java.lang.String", "charAt", Type.CHAR,
				new Type[] { Type.INT }, (short) 182));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 6));
		instructionList.append(InstructionConstants.IXOR);
		instructionList.append(InstructionConstants.IXOR);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(InstructionConstants.CASTORE);
		instructionList.append(InstructionConstants.IXOR);
		instructionList.append(InstructionConstants.IXOR);
		instructionList.append(InstructionConstants.IAND);
		instructionList.append(InstructionConstants.I2C);
		instructionList.append(InstructionFactory.createStore(Type.INT, 6));
		instructionList.append(new IINC(4, -1));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 4));
		final BranchInstruction branchInstruction0 = InstructionFactory.createBranchInstruction((short) 156, null);
		instructionList.append(branchInstruction0);
		instructionList.append(InstructionFactory.createLoad(Type.INT, 3));
		instructionList.append(InstructionFactory.createStore(Type.INT, 4));
		final InstructionHandle instructionHandle0 = instructionList.append(new IINC(7, -1));
		final InstructionHandle instructionHandle1 = instructionList
				.append(InstructionFactory.createLoad(Type.OBJECT, 5));
		instructionList.append(InstructionFactory.createLoad(Type.INT, 7));
		instructionList.append(InstructionConstants.DUP);
		final BranchInstruction branchInstruction1 = InstructionFactory.createBranchInstruction((short) 156,
				instructionHandle);
		instructionList.append(branchInstruction1);
		instructionList.append(InstructionConstants.POP2);
		instructionList.append(instructionFactory.createNew("java.lang.String"));
		instructionList.append(InstructionFactory.createLoad(Type.OBJECT, 5));
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(InstructionConstants.DUP_X1);
		instructionList.append(InstructionConstants.SWAP);
		instructionList.append(instructionFactory.createInvoke("java.lang.String", "<init>", Type.VOID,
				new Type[] { new ArrayType(Type.CHAR, 1) }, (short) 183));
		instructionList.append(InstructionFactory.createReturn(Type.OBJECT));
		branchInstruction.setTarget(instructionHandle1);
		branchInstruction0.setTarget(instructionHandle0);
		methodGen.setMaxStack();
		methodGen.setMaxLocals();
		classGen.addMethod(methodGen.getMethod());
		instructionList.dispose();
	}

}
