/* LineNumber - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;
import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class LineNumber implements Cloneable, Node, Serializable
{
    private static final long serialVersionUID = 169537400672820016L;
    private int start_pc;
    private int line_number;
    
    public LineNumber(LineNumber c) {
	this(c.getStartPC(), c.getLineNumber());
    }
    
    LineNumber(DataInput file) throws IOException {
	this(file.readUnsignedShort(), file.readUnsignedShort());
    }
    
    public LineNumber(int start_pc, int line_number) {
	this.start_pc = start_pc;
	this.line_number = line_number;
    }
    
    public void accept(Visitor v) {
	v.visitLineNumber(this);
    }
    
    public final void dump(DataOutputStream file) throws IOException {
	file.writeShort(start_pc);
	file.writeShort(line_number);
    }
    
    public final int getLineNumber() {
	return line_number;
    }
    
    public final int getStartPC() {
	return start_pc;
    }
    
    public final void setLineNumber(int line_number) {
	this.line_number = line_number;
    }
    
    public final void setStartPC(int start_pc) {
	this.start_pc = start_pc;
    }
    
    public final String toString() {
	return new StringBuilder().append("LineNumber(").append(start_pc)
		   .append
		   (", ").append
		   (line_number).append
		   (")").toString();
    }
    
    public LineNumber copy() {
	LineNumber linenumber;
	try {
	    linenumber = (LineNumber) this.clone();
	} catch (CloneNotSupportedException clonenotsupportedexception) {
	    return null;
	}
	return linenumber;
    }
}
