/* LCMP - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LCMP extends Instruction
    implements TypedInstruction, StackProducer, StackConsumer
{
    private static final long serialVersionUID = 2981727810276161294L;
    
    public LCMP() {
	super((short) 148, (short) 1);
    }
    
    public Type getType(ConstantPoolGen cp) {
	return Type.LONG;
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitLCMP(this);
    }
}
