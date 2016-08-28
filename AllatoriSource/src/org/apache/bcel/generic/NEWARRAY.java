package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.Constants;
import org.apache.bcel.ExceptionConstants;
import org.apache.bcel.util.ByteSequence;

public class NEWARRAY extends Instruction implements AllocationInstruction, ExceptionThrower, StackProducer {
	private static final long serialVersionUID = 7048445841018649405L;
	private byte type;

	NEWARRAY() {
	}

	public NEWARRAY(byte type) {
		super((short) 188, (short) 2);
		this.type = type;
	}

	public NEWARRAY(BasicType type) {
		this(type.getType());
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		out.writeByte(opcode);
		out.writeByte(type);
	}

	public final byte getTypecode() {
		return type;
	}

	public final Type getType() {
		return new ArrayType(BasicType.getType(type), 1);
	}

	@Override
	public String toString(boolean verbose) {
		return new StringBuilder().append(super.toString(verbose)).append(" ").append(Constants.TYPE_NAMES[type])
				.toString();
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		type = bytes.readByte();
		length = (short) 2;
	}

	@Override
	public Class[] getExceptions() {
		return (new Class[] { ExceptionConstants.NEGATIVE_ARRAY_SIZE_EXCEPTION });
	}

	@Override
	public void accept(Visitor v) {
		v.visitAllocationInstruction(this);
		v.visitExceptionThrower(this);
		v.visitStackProducer(this);
		v.visitNEWARRAY(this);
	}
}
