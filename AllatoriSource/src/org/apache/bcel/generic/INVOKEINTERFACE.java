/* INVOKEINTERFACE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.ExceptionConstants;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.util.ByteSequence;

public final class INVOKEINTERFACE extends InvokeInstruction
{
    private static final long serialVersionUID = 8198753714085379482L;
    private int nargs;
    
    INVOKEINTERFACE() {
	/* empty */
    }
    
    public INVOKEINTERFACE(int index, int nargs) {
	super((short) 185, index);
	length = (short) 5;
	if (nargs < 1)
	    throw new ClassGenException
		      (new StringBuilder().append
			   ("Number of arguments must be > 0 ").append
			   (nargs).toString());
	this.nargs = nargs;
    }
    
    public void dump(DataOutputStream out) throws IOException {
	out.writeByte(opcode);
	out.writeShort(index);
	out.writeByte(nargs);
	out.writeByte(0);
    }
    
    public int getCount() {
	return nargs;
    }
    
    protected void initFromFile(ByteSequence bytes, boolean wide)
	throws IOException {
	super.initFromFile(bytes, wide);
	length = (short) 5;
	nargs = bytes.readUnsignedByte();
	bytes.readByte();
    }
    
    public String toString(ConstantPool cp) {
	return new StringBuilder().append(super.toString(cp)).append(" ")
		   .append
		   (nargs).toString();
    }
    
    public int consumeStack(ConstantPoolGen cpg) {
	return nargs;
    }
    
    public Class[] getExceptions() {
	Class[] cs = new Class[4 + (ExceptionConstants
				    .EXCS_INTERFACE_METHOD_RESOLUTION).length];
	System.arraycopy(ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION,
			 0, cs, 0,
			 (ExceptionConstants
			  .EXCS_INTERFACE_METHOD_RESOLUTION).length);
	cs[ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length + 3]
	    = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
	cs[ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length + 2]
	    = ExceptionConstants.ILLEGAL_ACCESS_ERROR;
	cs[ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length + 1]
	    = ExceptionConstants.ABSTRACT_METHOD_ERROR;
	cs[ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length]
	    = ExceptionConstants.UNSATISFIED_LINK_ERROR;
	return cs;
    }
    
    public void accept(Visitor v) {
	v.visitExceptionThrower(this);
	v.visitTypedInstruction(this);
	v.visitStackConsumer(this);
	v.visitStackProducer(this);
	v.visitLoadClass(this);
	v.visitCPInstruction(this);
	v.visitFieldOrMethod(this);
	v.visitInvokeInstruction(this);
	v.visitINVOKEINTERFACE(this);
    }
}
