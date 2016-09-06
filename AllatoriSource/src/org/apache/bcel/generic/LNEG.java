/* LNEG - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LNEG extends ArithmeticInstruction
{
    private static final long serialVersionUID = 7749253327528155126L;
    
    public LNEG() {
	super((short) 117);
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitArithmeticInstruction(this);
	v.visitLNEG(this);
    }
}
