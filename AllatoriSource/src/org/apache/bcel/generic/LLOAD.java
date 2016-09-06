/* LLOAD - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LLOAD extends LoadInstruction {
	private static final long serialVersionUID = 379331602405124174L;

	LLOAD() {
		super((short) 22, (short) 30);
	}

	public LLOAD(int n) {
		super((short) 22, (short) 30, n);
	}

	@Override
	public void accept(Visitor v) {
		super.accept(v);
		v.visitLLOAD(this);
	}
}
