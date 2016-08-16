/* LCONST - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LCONST extends Instruction implements ConstantPushInstruction
{
    private static final long serialVersionUID = 909025807621177822L;
    private long value;
    
    LCONST() {
	/* empty */
    }
    
    public LCONST(long l) {
	super((short) 9, (short) 1);
	if (l == 0L)
	    opcode = (short) 9;
	else if (l == 1L)
	    opcode = (short) 10;
	else
	    throw new ClassGenException
		      (new StringBuilder().append
			   ("LCONST can be used only for 0 and 1: ").append
			   (l).toString());
	value = l;
    }
    
    public Number getValue() {
	return Long.valueOf(value);
    }
    
    public Type getType(ConstantPoolGen cp) {
	return Type.LONG;
    }
    
    public void accept(Visitor v) {
	v.visitPushInstruction(this);
	v.visitStackProducer(this);
	v.visitTypedInstruction(this);
	v.visitConstantPushInstruction(this);
	v.visitLCONST(this);
    }
}
