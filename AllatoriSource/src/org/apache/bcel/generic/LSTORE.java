package org.apache.bcel.generic;

public class LSTORE extends StoreInstruction {
	private static final long serialVersionUID = 1309214714647416201L;

	LSTORE() {
		super((short) 55, (short) 63);
	}

	public LSTORE(int n) {
		super((short) 55, (short) 63, n);
	}

	@Override
	public void accept(Visitor v) {
		super.accept(v);
		v.visitLSTORE(this);
	}
}
