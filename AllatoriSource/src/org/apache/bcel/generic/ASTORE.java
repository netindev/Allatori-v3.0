package org.apache.bcel.generic;

public class ASTORE extends StoreInstruction {
	private static final long serialVersionUID = 3598929416636143200L;

	ASTORE() {
		super((short) 58, (short) 75);
	}

	public ASTORE(int n) {
		super((short) 58, (short) 75, n);
	}

	@Override
	public void accept(Visitor v) {
		super.accept(v);
		v.visitASTORE(this);
	}
}
