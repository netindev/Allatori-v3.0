/* LMUL - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LMUL extends ArithmeticInstruction
{
    private static final long serialVersionUID = 3538398481425889023L;
    
    public LMUL() {
	super((short) 105);
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitArithmeticInstruction(this);
	v.visitLMUL(this);
    }
}
