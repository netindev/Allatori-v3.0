/* FCMPL - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class FCMPL extends Instruction
    implements TypedInstruction, StackProducer, StackConsumer
{
    private static final long serialVersionUID = -5283096582947056142L;
    
    public FCMPL() {
	super((short) 149, (short) 1);
    }
    
    public Type getType(ConstantPoolGen cp) {
	return Type.FLOAT;
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitFCMPL(this);
    }
}
