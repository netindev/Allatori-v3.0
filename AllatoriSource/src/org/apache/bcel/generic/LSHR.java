/* LSHR - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LSHR extends ArithmeticInstruction
{
    private static final long serialVersionUID = -6376870568784516963L;
    
    public LSHR() {
	super((short) 123);
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitArithmeticInstruction(this);
	v.visitLSHR(this);
    }
}
