/* LREM - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;
import org.apache.bcel.ExceptionConstants;

public class LREM extends ArithmeticInstruction implements ExceptionThrower
{
    private static final long serialVersionUID = -3646771608200042527L;
    
    public LREM() {
	super((short) 113);
    }
    
    public Class[] getExceptions() {
	return new Class[] { ExceptionConstants.ARITHMETIC_EXCEPTION };
    }
    
    public void accept(Visitor v) {
	v.visitExceptionThrower(this);
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitArithmeticInstruction(this);
	v.visitLREM(this);
    }
}
