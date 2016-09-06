/* CodeException - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.bcel.Constants;

public final class CodeException implements Cloneable, Constants, Node, Serializable {
	private static final long serialVersionUID = 2972500041254967221L;
	private int start_pc;
	private int end_pc;
	private int handler_pc;
	private int catch_type;

	public CodeException(CodeException c) {
		this(c.getStartPC(), c.getEndPC(), c.getHandlerPC(), c.getCatchType());
	}

	CodeException(DataInput file) throws IOException {
		this(file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort());
	}

	public CodeException(int start_pc, int end_pc, int handler_pc, int catch_type) {
		this.start_pc = start_pc;
		this.end_pc = end_pc;
		this.handler_pc = handler_pc;
		this.catch_type = catch_type;
	}

	@Override
	public void accept(Visitor v) {
		v.visitCodeException(this);
	}

	public final void dump(DataOutputStream file) throws IOException {
		file.writeShort(start_pc);
		file.writeShort(end_pc);
		file.writeShort(handler_pc);
		file.writeShort(catch_type);
	}

	public final int getCatchType() {
		return catch_type;
	}

	public final int getEndPC() {
		return end_pc;
	}

	public final int getHandlerPC() {
		return handler_pc;
	}

	public final int getStartPC() {
		return start_pc;
	}

	public final void setCatchType(int catch_type) {
		this.catch_type = catch_type;
	}

	public final void setEndPC(int end_pc) {
		this.end_pc = end_pc;
	}

	public final void setHandlerPC(int handler_pc) {
		this.handler_pc = handler_pc;
	}

	public final void setStartPC(int start_pc) {
		this.start_pc = start_pc;
	}

	@Override
	public final String toString() {
		return new StringBuilder().append("CodeException(start_pc = ").append(start_pc).append(", end_pc = ")
				.append(end_pc).append(", handler_pc = ").append(handler_pc).append(", catch_type = ")
				.append(catch_type).append(")").toString();
	}

	public final String toString(ConstantPool cp, boolean verbose) {
		String str;
		if (catch_type == 0)
			str = "<Any exception>(0)";
		else
			str = new StringBuilder()
					.append(Utility.compactClassName(cp.getConstantString(catch_type, (byte) 7), false))
					.append(verbose ? new StringBuilder().append("(").append(catch_type).append(")").toString() : "")
					.toString();
		return new StringBuilder().append(start_pc).append("\t").append(end_pc).append("\t").append(handler_pc)
				.append("\t").append(str).toString();
	}

	public final String toString(ConstantPool cp) {
		return toString(cp, true);
	}

	public CodeException copy() {
		CodeException codeexception;
		try {
			codeexception = (CodeException) this.clone();
		} catch (final CloneNotSupportedException clonenotsupportedexception) {
			return null;
		}
		return codeexception;
	}
}
