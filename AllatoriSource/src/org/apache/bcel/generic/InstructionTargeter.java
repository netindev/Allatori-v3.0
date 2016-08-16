/* InstructionTargeter - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public interface InstructionTargeter
{
    public boolean containsTarget(InstructionHandle instructionhandle);
    
    public void updateTarget(InstructionHandle instructionhandle,
			     InstructionHandle instructionhandle_0_);
}
