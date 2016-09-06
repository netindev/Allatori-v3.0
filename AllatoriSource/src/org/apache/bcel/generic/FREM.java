/* FREM - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class FREM extends ArithmeticInstruction
{
    private static final long serialVersionUID = -9122754212888086133L;
    
    public FREM() {
	super((short) 114);
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitArithmeticInstruction(this);
	v.visitFREM(this);
    }
}
