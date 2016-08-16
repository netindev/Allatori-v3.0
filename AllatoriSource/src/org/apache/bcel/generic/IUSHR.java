/* IUSHR - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IUSHR extends ArithmeticInstruction
{
    private static final long serialVersionUID = -4507658555921269426L;
    
    public IUSHR() {
	super((short) 124);
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitArithmeticInstruction(this);
	v.visitIUSHR(this);
    }
}
