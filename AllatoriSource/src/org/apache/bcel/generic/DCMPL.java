package org.apache.bcel.generic;

public class DCMPL extends Instruction implements TypedInstruction, StackProducer, StackConsumer {
	private static final long serialVersionUID = 2514357528484232014L;

	public DCMPL() {
		super((short) 151, (short) 1);
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return Type.DOUBLE;
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitDCMPL(this);
	}
}
