/* NEW - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;
import org.apache.bcel.ExceptionConstants;

public class NEW extends CPInstruction
    implements LoadClass, AllocationInstruction, ExceptionThrower,
	       StackProducer
{
    private static final long serialVersionUID = 5773167897857305796L;
    
    NEW() {
	/* empty */
    }
    
    public NEW(int index) {
	super((short) 187, index);
    }
    
    public Class[] getExceptions() {
	Class[] cs
	    = new Class[2 + (ExceptionConstants
			     .EXCS_CLASS_AND_INTERFACE_RESOLUTION).length];
	System.arraycopy((ExceptionConstants
			  .EXCS_CLASS_AND_INTERFACE_RESOLUTION),
			 0, cs, 0,
			 (ExceptionConstants
			  .EXCS_CLASS_AND_INTERFACE_RESOLUTION).length);
	cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length + 1]
	    = ExceptionConstants.INSTANTIATION_ERROR;
	cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length]
	    = ExceptionConstants.ILLEGAL_ACCESS_ERROR;
	return cs;
    }
    
    public ObjectType getLoadClassType(ConstantPoolGen cpg) {
	return (ObjectType) getType(cpg);
    }
    
    public void accept(Visitor v) {
	v.visitLoadClass(this);
	v.visitAllocationInstruction(this);
	v.visitExceptionThrower(this);
	v.visitStackProducer(this);
	v.visitTypedInstruction(this);
	v.visitCPInstruction(this);
	v.visitNEW(this);
    }
}
