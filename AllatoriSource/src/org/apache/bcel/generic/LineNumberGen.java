/* LineNumberGen - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.Serializable;

import org.apache.bcel.classfile.LineNumber;

public class LineNumberGen implements InstructionTargeter, Cloneable, Serializable {
	private static final long serialVersionUID = 4939965573936108738L;
	private InstructionHandle ih;
	private int src_line;

	public LineNumberGen(InstructionHandle ih, int src_line) {
		setInstruction(ih);
		setSourceLine(src_line);
	}

	@Override
	public boolean containsTarget(InstructionHandle ih) {
		return this.ih == ih;
	}

	@Override
	public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
		if (old_ih != ih)
			throw new ClassGenException(new StringBuilder().append("Not targeting ").append(old_ih).append(", but ")
					.append(ih).append("}").toString());
		setInstruction(new_ih);
	}

	public LineNumber getLineNumber() {
		return new LineNumber(ih.getPosition(), src_line);
	}

	public void setInstruction(InstructionHandle ih) {
		BranchInstruction.notifyTarget(this.ih, ih, this);
		this.ih = ih;
	}

	@Override
	public Object clone() {
		Object object;
		try {
			object = super.clone();
		} catch (final CloneNotSupportedException e) {
			System.err.println(e);
			return null;
		}
		return object;
	}

	public InstructionHandle getInstruction() {
		return ih;
	}

	public void setSourceLine(int src_line) {
		this.src_line = src_line;
	}

	public int getSourceLine() {
		return src_line;
	}
}
