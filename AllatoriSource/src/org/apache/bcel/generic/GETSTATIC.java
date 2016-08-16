/* GETSTATIC - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;
import org.apache.bcel.ExceptionConstants;

public class GETSTATIC extends FieldInstruction
    implements PushInstruction, ExceptionThrower
{
    private static final long serialVersionUID = -477185594622953478L;
    
    GETSTATIC() {
	/* empty */
    }
    
    public GETSTATIC(int index) {
	super((short) 178, index);
    }
    
    public int produceStack(ConstantPoolGen cpg) {
	return getFieldSize(cpg);
    }
    
    public Class[] getExceptions() {
	Class[] cs = new Class[1 + (ExceptionConstants
				    .EXCS_FIELD_AND_METHOD_RESOLUTION).length];
	System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION,
			 0, cs, 0,
			 (ExceptionConstants
			  .EXCS_FIELD_AND_METHOD_RESOLUTION).length);
	cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length]
	    = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
	return cs;
    }
    
    public void accept(Visitor v) {
	v.visitStackProducer(this);
	v.visitPushInstruction(this);
	v.visitExceptionThrower(this);
	v.visitTypedInstruction(this);
	v.visitLoadClass(this);
	v.visitCPInstruction(this);
	v.visitFieldOrMethod(this);
	v.visitFieldInstruction(this);
	v.visitGETSTATIC(this);
    }
}
