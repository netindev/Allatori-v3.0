package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.ExceptionConstants;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.util.ByteSequence;

public class MULTIANEWARRAY extends CPInstruction implements LoadClass, AllocationInstruction, ExceptionThrower {
	private static final long serialVersionUID = -7439639244808941662L;
	private short dimensions;

	MULTIANEWARRAY() {
	}

	public MULTIANEWARRAY(int index, short dimensions) {
		super((short) 197, index);
		if (dimensions < 1)
			throw new ClassGenException(
					new StringBuilder().append("Invalid dimensions value: ").append(dimensions).toString());
		this.dimensions = dimensions;
		length = (short) 4;
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		out.writeByte(opcode);
		out.writeShort(index);
		out.writeByte(dimensions);
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		super.initFromFile(bytes, wide);
		dimensions = bytes.readByte();
		length = (short) 4;
	}

	public final short getDimensions() {
		return dimensions;
	}

	@Override
	public String toString(boolean verbose) {
		return new StringBuilder().append(super.toString(verbose)).append(" ").append(index).append(" ")
				.append(dimensions).toString();
	}

	@Override
	public String toString(ConstantPool cp) {
		return new StringBuilder().append(super.toString(cp)).append(" ").append(dimensions).toString();
	}

	@Override
	public int consumeStack(ConstantPoolGen cpg) {
		return dimensions;
	}

	@Override
	public Class<?>[] getExceptions() {
		final Class<?>[] cs = new Class[2 + (ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION).length];
		System.arraycopy((ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION), 0, cs, 0,
				(ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION).length);
		cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length
				+ 1] = ExceptionConstants.NEGATIVE_ARRAY_SIZE_EXCEPTION;
		cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length] = ExceptionConstants.ILLEGAL_ACCESS_ERROR;
		return cs;
	}

	@Override
	public ObjectType getLoadClassType(ConstantPoolGen cpg) {
		Type t = getType(cpg);
		if (t instanceof ArrayType)
			t = ((ArrayType) t).getBasicType();
		return t instanceof ObjectType ? (ObjectType) t : null;
	}

	@Override
	public void accept(Visitor v) {
		v.visitLoadClass(this);
		v.visitAllocationInstruction(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitCPInstruction(this);
		v.visitMULTIANEWARRAY(this);
	}
}
