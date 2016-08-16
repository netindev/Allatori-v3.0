/* IfInstruction - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public abstract class IfInstruction extends BranchInstruction
    implements StackConsumer
{
    private static final long serialVersionUID = -781053966413893941L;
    
    IfInstruction() {
	/* empty */
    }
    
    protected IfInstruction(short opcode, InstructionHandle target) {
	super(opcode, target);
    }
    
    public abstract IfInstruction negate();
}
