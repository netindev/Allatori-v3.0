/* ConstantInteger - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;
import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantInteger extends Constant implements ConstantObject
{
    private static final long serialVersionUID = -7040676276945754375L;
    private int bytes;
    
    public ConstantInteger(int bytes) {
	super((byte) 3);
	this.bytes = bytes;
    }
    
    public ConstantInteger(ConstantInteger c) {
	this(c.getBytes());
    }
    
    ConstantInteger(DataInput file) throws IOException {
	this(file.readInt());
    }
    
    public void accept(Visitor v) {
	v.visitConstantInteger(this);
    }
    
    public final void dump(DataOutputStream file) throws IOException {
	file.writeByte(tag);
	file.writeInt(bytes);
    }
    
    public final int getBytes() {
	return bytes;
    }
    
    public final void setBytes(int bytes) {
	this.bytes = bytes;
    }
    
    public final String toString() {
	return new StringBuilder().append(super.toString()).append
		   ("(bytes = ").append
		   (bytes).append
		   (")").toString();
    }
    
    public Object getConstantValue(ConstantPool cp) {
	return Integer.valueOf(bytes);
    }
}
