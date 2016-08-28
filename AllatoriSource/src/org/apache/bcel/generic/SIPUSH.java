package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public class SIPUSH extends Instruction implements ConstantPushInstruction {
	private static final long serialVersionUID = -5670145672311191256L;
	private short b;

	SIPUSH() {
	}

	public SIPUSH(short b) {
		super((short) 17, (short) 3);
		this.b = b;
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		super.dump(out);
		out.writeShort(b);
	}

	@Override
	public String toString(boolean verbose) {
		return new StringBuilder().append(super.toString(verbose)).append(" ").append(b).toString();
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		length = (short) 3;
		b = bytes.readShort();
	}

	@Override
	public Number getValue() {
		return Integer.valueOf(b);
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return Type.SHORT;
	}

	@Override
	public void accept(Visitor v) {
		v.visitPushInstruction(this);
		v.visitStackProducer(this);
		v.visitTypedInstruction(this);
		v.visitConstantPushInstruction(this);
		v.visitSIPUSH(this);
	}
}
