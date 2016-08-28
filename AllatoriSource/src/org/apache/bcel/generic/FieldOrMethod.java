package org.apache.bcel.generic;

import org.apache.bcel.classfile.ConstantCP;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;

public abstract class FieldOrMethod extends CPInstruction implements LoadClass {
	private static final long serialVersionUID = 2036985877748835708L;

	FieldOrMethod() {
	}

	protected FieldOrMethod(short opcode, int index) {
		super(opcode, index);
	}

	public String getSignature(ConstantPoolGen cpg) {
		final ConstantPool cp = cpg.getConstantPool();
		final ConstantCP cmr = (ConstantCP) cp.getConstant(index);
		final ConstantNameAndType cnat = (ConstantNameAndType) cp.getConstant(cmr.getNameAndTypeIndex());
		return ((ConstantUtf8) cp.getConstant(cnat.getSignatureIndex())).getBytes();
	}

	public String getName(ConstantPoolGen cpg) {
		final ConstantPool cp = cpg.getConstantPool();
		final ConstantCP cmr = (ConstantCP) cp.getConstant(index);
		final ConstantNameAndType cnat = (ConstantNameAndType) cp.getConstant(cmr.getNameAndTypeIndex());
		return ((ConstantUtf8) cp.getConstant(cnat.getNameIndex())).getBytes();
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public String getClassName(ConstantPoolGen cpg) {
		final ConstantPool cp = cpg.getConstantPool();
		final ConstantCP cmr = (ConstantCP) cp.getConstant(index);
		final String className = cp.getConstantString(cmr.getClassIndex(), (byte) 7);
		if (className.startsWith("["))
			return "java.lang.Object";
		return className.replace('/', '.');
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public ObjectType getClassType(ConstantPoolGen cpg) {
		return new ObjectType(getClassName(cpg));
	}

	public ReferenceType getReferenceType(ConstantPoolGen cpg) {
		final ConstantPool cp = cpg.getConstantPool();
		final ConstantCP cmr = (ConstantCP) cp.getConstant(index);
		String className = cp.getConstantString(cmr.getClassIndex(), (byte) 7);
		if (className.startsWith("["))
			return (ArrayType) Type.getType(className);
		className = className.replace('/', '.');
		return new ObjectType(className);
	}

	@Override
	public ObjectType getLoadClassType(ConstantPoolGen cpg) {
		return getClassType(cpg);
	}
}
