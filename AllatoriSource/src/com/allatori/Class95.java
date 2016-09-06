package com.allatori;

import java.util.Arrays;
import java.util.List;

import org.apache.bcel.classfile.AnnotationDefault;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantCP;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.Deprecated;
import org.apache.bcel.classfile.EnclosingMethod;
import org.apache.bcel.classfile.ExceptionTable;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.InnerClass;
import org.apache.bcel.classfile.InnerClasses;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.PMGClass;
import org.apache.bcel.classfile.Signature;
import org.apache.bcel.classfile.SourceFile;
import org.apache.bcel.classfile.StackMap;
import org.apache.bcel.classfile.StackMapEntry;
import org.apache.bcel.classfile.StackMapType;
import org.apache.bcel.classfile.Synthetic;
import org.apache.bcel.classfile.Unknown;
import org.apache.bcel.generic.AnnotationElementValueGen;
import org.apache.bcel.generic.AnnotationEntryGen;
import org.apache.bcel.generic.ArrayElementValueGen;
import org.apache.bcel.generic.CPInstruction;
import org.apache.bcel.generic.ClassElementValueGen;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ElementValueGen;
import org.apache.bcel.generic.ElementValuePairGen;
import org.apache.bcel.generic.EnumElementValueGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.SimpleElementValueGen;

public class Class95 {

	private int[] mappedNameCPReferences;

	private void method1249(Attribute[] var1, ConstantPool var2) {
		int var3;
		for (int var10000 = var3 = var1.length - 1; var10000 >= 0; var10000 = var3) {
			Attribute var4;
			(var4 = var1[var3]).setConstantPool(var2);
			if (!(var4 instanceof LocalVariableTable)) {
				var4.setNameIndex(this.mappedNameCPReferences[var4.getNameIndex()]);
			}

			int var7;
			if (var4 instanceof Code) {
				final Code var5 = (Code) var4;
				this.method1249(var5.getAttributes(), var2);

				CodeException[] var6;
				for (var10000 = var7 = (var6 = var5.getExceptionTable()).length - 1; var10000 >= 0; var10000 = var7) {
					int var8;
					if ((var8 = var6[var7].getCatchType()) != 0) {
						var6[var7].setCatchType(this.mappedNameCPReferences[var8]);
					}

					--var7;
				}
			} else if (var4 instanceof ConstantValue) {
				ConstantValue var15;
				(var15 = (ConstantValue) var4)
						.setConstantValueIndex(this.mappedNameCPReferences[var15.getConstantValueIndex()]);
			} else if (!(var4 instanceof Deprecated)) {
				if (var4 instanceof ExceptionTable) {
					ExceptionTable var17;
					int[] var21;
					for (var10000 = var7 = (var21 = (var17 = (ExceptionTable) var4).getExceptionIndexTable()).length
							- 1; var10000 >= 0; var10000 = var7) {
						var21[var7] = this.mappedNameCPReferences[var21[var7]];
						--var7;
					}

					var17.setExceptionIndexTable(var21);
				} else if (var4 instanceof InnerClasses) {
					InnerClass[] var19;
					for (var10000 = var7 = (var19 = ((InnerClasses) var4).getInnerClasses()).length
							- 1; var10000 >= 0; var10000 = var7) {
						var19[var7].setInnerNameIndex(this.mappedNameCPReferences[var19[var7].getInnerNameIndex()]);
						var19[var7].setInnerClassIndex(this.mappedNameCPReferences[var19[var7].getInnerClassIndex()]);
						var19[var7].setOuterClassIndex(this.mappedNameCPReferences[var19[var7].getOuterClassIndex()]);
						--var7;
					}
				} else if (!(var4 instanceof LineNumberTable)) {
					if (var4 instanceof LocalVariableTable) {
						try {
							LocalVariable[] var24;
							for (var10000 = var7 = (var24 = ((LocalVariableTable) var4).getLocalVariableTable()).length
									- 1; var10000 >= 0; var10000 = var7) {
								var24[var7].setNameIndex(this.mappedNameCPReferences[var24[var7].getNameIndex()]);
								var24[var7].setSignatureIndex(
										this.mappedNameCPReferences[var24[var7].getSignatureIndex()]);
								--var7;
							}
						} catch (final ArrayIndexOutOfBoundsException var14) {
						}
					} else if (var4 instanceof PMGClass) {
						PMGClass var18;
						(var18 = (PMGClass) var4)
								.setPMGClassIndex(this.mappedNameCPReferences[var18.getPMGClassIndex()]);
						var18.setPMGIndex(this.mappedNameCPReferences[var18.getPMGIndex()]);
					} else if (var4 instanceof Signature) {
						Signature var25;
						(var25 = (Signature) var4)
								.setSignatureIndex(this.mappedNameCPReferences[var25.getSignatureIndex()]);
					} else if (var4 instanceof SourceFile) {
						SourceFile var22;
						(var22 = (SourceFile) var4)
								.setSourceFileIndex(this.mappedNameCPReferences[var22.getSourceFileIndex()]);
					} else if (var4 instanceof StackMap) {
						StackMapEntry[] var23;
						for (var10000 = var7 = (var23 = ((StackMap) var4).getStackMap()).length
								- 1; var10000 >= 0; var10000 = var7) {
							StackMapType[] var9;
							int var10;
							StackMapEntry var28;
							for (var10000 = var10 = (var9 = (var28 = var23[var7]).getTypesOfLocals()).length
									- 1; var10000 >= 0; var10000 = var10) {
								StackMapType var11;
								if ((var11 = var9[var10]).getType() == 7) {
									var11.setIndex(this.mappedNameCPReferences[var11.getIndex()]);
								}

								--var10;
							}

							int var12;
							StackMapType[] var30;
							for (var10000 = var12 = (var30 = var28.getTypesOfStackItems()).length
									- 1; var10000 >= 0; var10000 = var12) {
								StackMapType var13;
								if ((var13 = var30[var12]).getType() == 7) {
									var13.setIndex(this.mappedNameCPReferences[var13.getIndex()]);
								}

								--var12;
							}

							--var7;
						}
					} else if (!(var4 instanceof Synthetic)) {
						if (var4 instanceof EnclosingMethod) {
							EnclosingMethod var26;
							(var26 = (EnclosingMethod) var4).setEnclosingClassIndex(
									this.mappedNameCPReferences[var26.getEnclosingClassIndex()]);
							if (var26.getEnclosingMethodIndex() != 0) {
								var26.setEnclosingMethodIndex(
										this.mappedNameCPReferences[var26.getEnclosingMethodIndex()]);
							}
						} else if (var4 instanceof AnnotationDefault) {
							AnnotationDefault var29;
							final ElementValueGen var31 = ElementValueGen.copy(
									(var29 = (AnnotationDefault) var4).getDefaultValue(), new ConstantPoolGen(var2),
									false);
							this.method1262(var31);
							var29.setDefaultValue(var31.getElementValue());
						} else if (var4 instanceof Unknown) {
						}
					}
				}
			}

			--var3;
		}

	}

	private void method1250(ClassGen var1) {
		ConstantPoolGen var2;
		final int var3 = (var2 = var1.getConstantPool()).getSize();
		int var4 = 0;

		int var5;
		int var10000;
		for (var10000 = var5 = this.mappedNameCPReferences.length - 1; var10000 >= 0; var10000 = var5) {
			var4 += this.mappedNameCPReferences[var5];
			--var5;
		}

		final Constant[] var6 = new Constant[var4];
		int var7 = 0;

		int var8;
		for (var10000 = var8 = 0; var10000 < var3; var10000 = var8) {
			Constant var9 = var2.getConstant(var8);
			if (this.mappedNameCPReferences[var8] == 1) {
				if (var9 != null) {
					var9 = var9.copy();
				}

				var6[var7] = var9;
				this.mappedNameCPReferences[var8] = var7++;
			}

			++var8;
		}

		int var23;
		for (var10000 = var23 = 0; var10000 < var4; var10000 = var23) {
			Constant var10;
			if ((var10 = var6[var23]) instanceof ConstantClass) {
				ConstantClass var11;
				(var11 = (ConstantClass) var10).setNameIndex(this.mappedNameCPReferences[var11.getNameIndex()]);
			} else if (var10 instanceof ConstantCP) {
				ConstantCP var24;
				(var24 = (ConstantCP) var10).setClassIndex(this.mappedNameCPReferences[var24.getClassIndex()]);
				var24.setNameAndTypeIndex(this.mappedNameCPReferences[var24.getNameAndTypeIndex()]);
			} else if (var10 instanceof ConstantNameAndType) {
				ConstantNameAndType var27;
				(var27 = (ConstantNameAndType) var10).setNameIndex(this.mappedNameCPReferences[var27.getNameIndex()]);
				var27.setSignatureIndex(this.mappedNameCPReferences[var27.getSignatureIndex()]);
			} else if (var10 instanceof ConstantString) {
				ConstantString var28;
				(var28 = (ConstantString) var10).setStringIndex(this.mappedNameCPReferences[var28.getStringIndex()]);
			}

			++var23;
		}

		ConstantPoolGen var25;
		final ConstantPool var26 = (var25 = new ConstantPoolGen(var6)).getConstantPool();
		var1.setConstantPool(var25);
		var1.setClassNameIndex(this.mappedNameCPReferences[var1.getClassNameIndex()]);
		var1.setSuperclassNameIndex(this.mappedNameCPReferences[var1.getSuperclassNameIndex()]);
		Attribute[] var12;
		final int var13 = (var12 = var1.getAttributes()).length;

		int var14;
		for (var10000 = var14 = 0; var10000 < var13; var10000 = var14) {
			var1.removeAttribute(var12[var14]);
			++var14;
		}

		this.method1249(var12, var26);

		int var15;
		for (var10000 = var15 = 0; var10000 < var13; var10000 = var15) {
			var1.addAttribute(var12[var15]);
			++var15;
		}

		this.method1254(var1, null, null);
		this.method1265(var1.getAnnotationEntries());

		int var17;
		Method[] var16;
		for (var10000 = var17 = (var16 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var17) {
			Method var18;
			final MethodGen var19 = InitUtils.createMethodGen(var18 = var16[var17], var1.getClassName(), var25,
					var2.getConstantPool());
			this.method1264(var19);
			this.method1249(var19.getAttributes(), var26);
			this.method1249(new Attribute[] { var19.getLocalVariableTable(var25) }, var26);
			this.method1249(var19.getCodeAttributes(), var26);
			this.method1254(null, var19, null);
			this.method1265(var19.getAnnotationEntries());

			int var20;
			for (var10000 = var20 = var19.getArgumentTypes().length - 1; var10000 >= 0; var10000 = var20) {
				this.method1259(var19.getAnnotationsOnParameter(var20));
				--var20;
			}

			Method var21;
			(var21 = var19.getMethod()).setNameIndex(this.mappedNameCPReferences[var18.getNameIndex()]);
			var21.setSignatureIndex(this.mappedNameCPReferences[var18.getSignatureIndex()]);
			var1.replaceMethod(var18, var21);
			--var17;
		}

		int var29;
		Field[] var30;
		for (var10000 = var29 = (var30 = var1.getFields()).length - 1; var10000 >= 0; var10000 = var29) {
			final Field var31 = var30[var29];
			FieldGen var32;
			(var32 = new FieldGen(var31, var2)).setConstantPool(var25);
			this.method1249(var32.getAttributes(), var26);
			this.method1254(null, null, var32);
			this.method1265(var32.getAnnotationEntries());
			Field var22;
			(var22 = var32.getField()).setNameIndex(this.mappedNameCPReferences[var31.getNameIndex()]);
			var22.setSignatureIndex(this.mappedNameCPReferences[var31.getSignatureIndex()]);
			var1.replaceField(var31, var22);
			--var29;
		}

	}

	private void method1251(List var1, ConstantPoolGen var2) {
		if (var1 != null) {
			int var3;
			for (int var10000 = var3 = var1.size() - 1; var10000 >= 0; var10000 = var3) {
				this.method1263((AnnotationEntryGen) var1.get(var3), var2);
				--var3;
			}

		}
	}

	private void method1252(AnnotationEntryGen[] var1, ConstantPoolGen var2) {
		if (var1 != null) {
			int var3;
			for (int var10000 = var3 = var1.length - 1; var10000 >= 0; var10000 = var3) {
				this.method1263(var1[var3], var2);
				--var3;
			}

		}
	}

	private void method1253(MethodGen var1, ConstantPoolGen var2) {
		InstructionList var3;
		if ((var3 = var1.getInstructionList()) != null) {
			InstructionHandle[] var4;
			int var5;
			for (int var10000 = var5 = (var4 = var3.getInstructionHandles()).length
					- 1; var10000 >= 0; var10000 = var5) {
				final Instruction var7 = var4[var5].getInstruction();
				if (var7 instanceof CPInstruction) {
					final CPInstruction var8 = (CPInstruction) var7;
					this.method1260(var8.getIndex(), var2);
				}

				--var5;
			}

		}
	}

	private void method1254(ClassGen var1, MethodGen var2, FieldGen var3) {
		Attribute[] var4 = null;
		if (var1 != null) {
			var4 = var1.getAttributes();
		}

		if (var2 != null) {
			var4 = var2.getAttributes();
		}

		if (var3 != null) {
			var4 = var3.getAttributes();
		}

		int var6;
		int var10000;
		if (var2 != null) {
			Attribute[] var5;
			for (var10000 = var6 = (var5 = var2.getCodeAttributes()).length - 1; var10000 >= 0; var10000 = var6) {
				if (var5[var6] instanceof org.apache.bcel.classfile.Unknown) {
					var2.removeCodeAttribute(var5[var6]);
				}

				--var6;
			}
		}

		if (var4 != null) {
			int var7;
			for (var10000 = var7 = var4.length - 1; var10000 >= 0; var10000 = var7) {
				if (var4[var7] instanceof org.apache.bcel.classfile.Unknown) {
					if (var1 != null) {
						var1.removeAttribute(var4[var7]);
					}

					if (var2 != null) {
						var2.removeAttribute(var4[var7]);
					}

					if (var3 != null) {
						var3.removeAttribute(var4[var7]);
					}
				}

				--var7;
			}

		}
	}

	private void method1255(Attribute[] var1, ConstantPoolGen var2) {
		int var3;
		for (int var10000 = var3 = var1.length - 1; var10000 >= 0; var10000 = var3) {
			Attribute var4;
			(var4 = var1[var3]).setConstantPool(var2.getConstantPool());
			if (!(var4 instanceof org.apache.bcel.classfile.Unknown)) {
				this.method1260(var4.getNameIndex(), var2);
			}

			int var7;
			if (var4 instanceof Code) {
				final Code var5 = (Code) var4;
				this.method1255(var5.getAttributes(), var2);

				CodeException[] var6;
				for (var10000 = var7 = (var6 = var5.getExceptionTable()).length - 1; var10000 >= 0; var10000 = var7) {
					this.method1260(var6[var7].getCatchType(), var2);
					--var7;
				}
			} else if (var4 instanceof ConstantValue) {
				final ConstantValue var14 = (ConstantValue) var4;
				this.method1260(var14.getConstantValueIndex(), var2);
			} else if (!(var4 instanceof Deprecated)) {
				if (var4 instanceof ExceptionTable) {
					int[] var20;
					for (var10000 = var7 = (var20 = ((ExceptionTable) var4).getExceptionIndexTable()).length
							- 1; var10000 >= 0; var10000 = var7) {
						this.method1260(var20[var7], var2);
						--var7;
					}
				} else if (var4 instanceof InnerClasses) {
					InnerClass[] var18;
					for (var10000 = var7 = (var18 = ((InnerClasses) var4).getInnerClasses()).length
							- 1; var10000 >= 0; var10000 = var7) {
						this.method1260(var18[var7].getInnerNameIndex(), var2);
						this.method1260(var18[var7].getInnerClassIndex(), var2);
						this.method1260(var18[var7].getOuterClassIndex(), var2);
						--var7;
					}
				} else if (!(var4 instanceof LineNumberTable)) {
					if (var4 instanceof LocalVariableTable) {
						LocalVariable[] var23;
						for (var10000 = var7 = (var23 = ((LocalVariableTable) var4).getLocalVariableTable()).length
								- 1; var10000 >= 0; var10000 = var7) {
							this.method1260(var23[var7].getNameIndex(), var2);
							this.method1260(var23[var7].getSignatureIndex(), var2);
							--var7;
						}
					} else if (var4 instanceof PMGClass) {
						final PMGClass var17 = (PMGClass) var4;
						this.method1260(var17.getPMGClassIndex(), var2);
						this.method1260(var17.getPMGIndex(), var2);
					} else if (var4 instanceof Signature) {
						final Signature var24 = (Signature) var4;
						this.method1260(var24.getSignatureIndex(), var2);
					} else if (var4 instanceof SourceFile) {
						final SourceFile var21 = (SourceFile) var4;
						this.method1260(var21.getSourceFileIndex(), var2);
					} else if (var4 instanceof StackMap) {
						StackMapEntry[] var22;
						for (var10000 = var7 = (var22 = ((StackMap) var4).getStackMap()).length
								- 1; var10000 >= 0; var10000 = var7) {
							StackMapEntry var8;
							StackMapType[] var9;
							int var10;
							for (var10000 = var10 = (var9 = (var8 = var22[var7]).getTypesOfLocals()).length
									- 1; var10000 >= 0; var10000 = var10) {
								StackMapType var11;
								if ((var11 = var9[var10]).getType() == 7) {
									this.method1260(var11.getIndex(), var2);
								}

								--var10;
							}

							int var12;
							StackMapType[] var28;
							for (var10000 = var12 = (var28 = var8.getTypesOfStackItems()).length
									- 1; var10000 >= 0; var10000 = var12) {
								StackMapType var13;
								if ((var13 = var28[var12]).getType() == 7) {
									this.method1260(var13.getIndex(), var2);
								}

								--var12;
							}

							--var7;
						}
					} else if (!(var4 instanceof Synthetic)) {
						if (var4 instanceof EnclosingMethod) {
							final EnclosingMethod var25 = (EnclosingMethod) var4;
							this.method1260(var25.getEnclosingClassIndex(), var2);
							if (var25.getEnclosingMethodIndex() != 0) {
								this.method1260(var25.getEnclosingMethodIndex(), var2);
							}
						} else if (var4 instanceof AnnotationDefault) {
							final ElementValueGen var29 = ElementValueGen
									.copy(((AnnotationDefault) var4).getDefaultValue(), var2, false);
							this.method1261(var29, var2);
						} else if (var4 instanceof org.apache.bcel.classfile.Unknown) {
						}
					}
				}
			}

			--var3;
		}

	}

	private void method1256(ClassGen var1) {
		ConstantPoolGen var2;
		final int var3 = (var2 = var1.getConstantPool()).getSize();
		this.mappedNameCPReferences = new int[var3];
		Arrays.fill(this.mappedNameCPReferences, 0);
		this.method1260(0, var2);
		this.method1260(var1.getClassNameIndex(), var2);
		this.method1260(var1.getSuperclassNameIndex(), var2);

		int[] var4;
		int var5;
		int var10000;
		for (var10000 = var5 = (var4 = var1.getInterfaces()).length - 1; var10000 >= 0; var10000 = var5) {
			this.method1260(var4[var5], var2);
			--var5;
		}

		this.method1255(var1.getAttributes(), var2);
		this.method1252(var1.getAnnotationEntries(), var2);

		Method[] var6;
		int var7;
		int var10;
		for (var10000 = var7 = (var6 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var7) {
			final Method var8 = var6[var7];
			this.method1260(var8.getNameIndex(), var2);
			this.method1260(var8.getSignatureIndex(), var2);
			this.method1255(var8.getAttributes(), var2);
			final MethodGen var9 = InitUtils.createMethodGen(var8, var1.getClassName(), var2, var2.getConstantPool());
			this.method1253(var9, var2);
			this.method1252(var9.getAnnotationEntries(), var2);

			for (var10000 = var10 = var9.getArgumentTypes().length - 1; var10000 >= 0; var10000 = var10) {
				this.method1251(var9.getAnnotationsOnParameter(var10), var2);
				--var10;
			}

			--var7;
		}

		Field[] var13;
		int var14;
		for (var10000 = var14 = (var13 = var1.getFields()).length - 1; var10000 >= 0; var10000 = var14) {
			final Field var15 = var13[var14];
			this.method1260(var15.getNameIndex(), var2);
			this.method1260(var15.getSignatureIndex(), var2);
			this.method1255(var15.getAttributes(), var2);
			this.method1252((new FieldGen(var15, var2)).getAnnotationEntries(), var2);
			--var14;
		}

		for (var10000 = var10 = var2.getSize() - 1; var10000 >= 0; var10000 = var10) {
			Constant var11;
			if ((var11 = var2.getConstant(var10)) instanceof ConstantUtf8) {
				final ConstantUtf8 var12 = (ConstantUtf8) var11;
				if ("".equals(var12.getBytes())) {
					this.method1260(var10, var2);
				}
			}

			--var10;
		}

	}

	private void method1257(AnnotationEntryGen var1) {
		var1.setTypeIndex(this.mappedNameCPReferences[var1.getTypeIndex()]);

		List var2;
		int var3;
		for (int var10000 = var3 = (var2 = var1.getValues()).size() - 1; var10000 >= 0; var10000 = var3) {
			ElementValuePairGen var4;
			(var4 = (ElementValuePairGen) var2.get(var3))
					.setNameIndex(this.mappedNameCPReferences[var4.getNameIndex()]);
			this.method1262(var4.getValue());
			--var3;
		}

	}

	public void method1258(ClassGen var1) {
		this.method1256(var1);
		this.method1250(var1);
	}

	private void method1259(List var1) {
		if (var1 != null) {
			int var2;
			for (int var10000 = var2 = var1.size() - 1; var10000 >= 0; var10000 = var2) {
				this.method1257((AnnotationEntryGen) var1.get(var2));
				--var2;
			}

		}
	}

	private void method1260(int var1, ConstantPoolGen var2) {
		if (var1 != -1) {
			this.mappedNameCPReferences[var1] = 1;
			Constant var3;
			if ((var3 = var2.getConstant(var1)) != null) {
				final int var4 = var2.getSize();

				int var5;
				for (int var10000 = var5 = var1 + 1; var10000 < var4
						&& var2.getConstant(var5) == null; var10000 = var5) {
					this.mappedNameCPReferences[var5] = 1;
					++var5;
				}
			}

			if (var3 instanceof ConstantClass) {
				final ConstantClass var6 = (ConstantClass) var3;
				this.method1260(var6.getNameIndex(), var2);
			} else if (var3 instanceof ConstantCP) {
				final ConstantCP var7 = (ConstantCP) var3;
				this.method1260(var7.getClassIndex(), var2);
				this.method1260(var7.getNameAndTypeIndex(), var2);
			} else if (var3 instanceof ConstantNameAndType) {
				final ConstantNameAndType var9 = (ConstantNameAndType) var3;
				this.method1260(var9.getNameIndex(), var2);
				this.method1260(var9.getSignatureIndex(), var2);
			} else if (var3 instanceof ConstantString) {
				final ConstantString var8 = (ConstantString) var3;
				this.method1260(var8.getStringIndex(), var2);
			}

		}
	}

	private void method1261(ElementValueGen var1, ConstantPoolGen var2) {
		if (var1 instanceof AnnotationElementValueGen) {
			this.method1263(((AnnotationElementValueGen) var1).getAnnotation(), var2);
		} else if (var1 instanceof ArrayElementValueGen) {
			final List var4 = ((ArrayElementValueGen) var1).getElementValues();
			int var5 = var4.size() - 1;

			for (int var10000 = var5; var10000 >= 0; var10000 = var5) {
				this.method1261((ElementValueGen) var4.get(var5), var2);
				--var5;
			}
		} else if (var1 instanceof ClassElementValueGen) {
			this.method1260(((ClassElementValueGen) var1).getIndex(), var2);
		} else if (var1 instanceof EnumElementValueGen) {
			this.method1260(((EnumElementValueGen) var1).getTypeIndex(), var2);
			this.method1260(((EnumElementValueGen) var1).getValueIndex(), var2);
		} else if (var1 instanceof SimpleElementValueGen) {
			this.method1260(((SimpleElementValueGen) var1).getIndex(), var2);
		}

	}

	private void method1262(ElementValueGen var1) {
		if (var1 instanceof AnnotationElementValueGen) {
			this.method1257(((AnnotationElementValueGen) var1).getAnnotation());
		} else {
			int var4;
			if (var1 instanceof ArrayElementValueGen) {
				List var3;
				for (int var10000 = var4 = (var3 = ((ArrayElementValueGen) var1).getElementValues()).size()
						- 1; var10000 >= 0; var10000 = var4) {
					this.method1262((ElementValueGen) var3.get(var4));
					--var4;
				}
			} else if (var1 instanceof ClassElementValueGen) {
				ClassElementValueGen var5;
				(var5 = (ClassElementValueGen) var1).setIndex(this.mappedNameCPReferences[var5.getIndex()]);
			} else if (var1 instanceof EnumElementValueGen) {
				EnumElementValueGen var6;
				(var6 = (EnumElementValueGen) var1).setTypeIndex(this.mappedNameCPReferences[var6.getTypeIndex()]);
				var6.setValueIndex(this.mappedNameCPReferences[var6.getValueIndex()]);
			} else if (var1 instanceof SimpleElementValueGen) {
				SimpleElementValueGen var7;
				(var7 = (SimpleElementValueGen) var1).setIndex(this.mappedNameCPReferences[var7.getIndex()]);
			}
		}

	}

	private void method1263(AnnotationEntryGen var1, ConstantPoolGen var2) {
		this.method1260(var1.getTypeIndex(), var2);

		List var3;
		int var4;
		for (int var10000 = var4 = (var3 = var1.getValues()).size() - 1; var10000 >= 0; var10000 = var4) {
			final ElementValuePairGen var5 = (ElementValuePairGen) var3.get(var4);
			this.method1260(var5.getNameIndex(), var2);
			this.method1261(var5.getValue(), var2);
			--var4;
		}

	}

	private void method1264(MethodGen var1) {
		InstructionList var2;
		if ((var2 = var1.getInstructionList()) != null) {
			InstructionHandle[] var3;
			int var4;
			for (int var10000 = var4 = (var3 = var2.getInstructionHandles()).length
					- 1; var10000 >= 0; var10000 = var4) {
				final Instruction var6 = var3[var4].getInstruction();
				if (var6 instanceof CPInstruction) {
					final CPInstruction var7 = (CPInstruction) var6;
					var7.setIndex(this.mappedNameCPReferences[var7.getIndex()]);
				}

				--var4;
			}

		}
	}

	private void method1265(AnnotationEntryGen[] var1) {
		if (var1 != null) {
			int var2;
			for (int var10000 = var2 = var1.length - 1; var10000 >= 0; var10000 = var2) {
				this.method1257(var1[var2]);
				--var2;
			}

		}
	}
}
