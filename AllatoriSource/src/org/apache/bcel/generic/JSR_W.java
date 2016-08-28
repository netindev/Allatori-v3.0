package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public class JSR_W extends JsrInstruction {
	private static final long serialVersionUID = -7352049131416924650L;

	JSR_W() {
	}

	public JSR_W(InstructionHandle target) {
		super((short) 201, target);
		length = (short) 5;
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		index = getTargetOffset();
		out.writeByte(opcode);
		out.writeInt(index);
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		index = bytes.readInt();
		length = (short) 5;
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitBranchInstruction(this);
		v.visitJsrInstruction(this);
		v.visitJSR_W(this);
	}
}
