/* LocalVariableGen - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;
import java.io.Serializable;

import org.apache.bcel.classfile.LocalVariable;

public class LocalVariableGen
    implements InstructionTargeter, NamedAndTyped, Cloneable, Serializable
{
    private static final long serialVersionUID = -3810966319065955534L;
    private int index;
    private String name;
    private Type type;
    private InstructionHandle start;
    private InstructionHandle end;
    
    public LocalVariableGen(int index, String name, Type type,
			    InstructionHandle start, InstructionHandle end) {
	if (index < 0 || index > 65535)
	    throw new ClassGenException(new StringBuilder().append
					    ("Invalid index index: ").append
					    (index).toString());
	this.name = name;
	this.type = type;
	this.index = index;
	setStart(start);
	setEnd(end);
    }
    
    public LocalVariable getLocalVariable(ConstantPoolGen cp) {
	int start_pc = start.getPosition();
	int length = end.getPosition() - start_pc;
	if (end.getNext() == null)
	    length += end.getInstruction().getLength();
	int name_index = cp.addUtf8(name);
	int signature_index = cp.addUtf8(type.getSignature());
	return new LocalVariable(start_pc, length, name_index, signature_index,
				 index, cp.getConstantPool());
    }
    
    public void setIndex(int index) {
	this.index = index;
    }
    
    public int getIndex() {
	return index;
    }
    
    public void setName(String name) {
	this.name = name;
    }
    
    public String getName() {
	return name;
    }
    
    public void setType(Type type) {
	this.type = type;
    }
    
    public Type getType() {
	return type;
    }
    
    public InstructionHandle getStart() {
	return start;
    }
    
    public InstructionHandle getEnd() {
	return end;
    }
    
    public void setStart(InstructionHandle start) {
	BranchInstruction.notifyTarget(this.start, start, this);
	this.start = start;
    }
    
    public void setEnd(InstructionHandle end) {
	BranchInstruction.notifyTarget(this.end, end, this);
	this.end = end;
    }
    
    public void updateTarget(InstructionHandle old_ih,
			     InstructionHandle new_ih) {
	boolean targeted = false;
	if (start == old_ih) {
	    targeted = true;
	    setStart(new_ih);
	}
	if (end == old_ih) {
	    targeted = true;
	    setEnd(new_ih);
	}
	if (!targeted)
	    throw new ClassGenException(new StringBuilder().append
					    ("Not targeting ").append
					    (old_ih).append
					    (", but {").append
					    (start).append
					    (", ").append
					    (end).append
					    ("}").toString());
    }
    
    public boolean containsTarget(InstructionHandle ih) {
	return start == ih || end == ih;
    }
    
    public int hashCode() {
	int hc = index ^ name.hashCode() ^ type.hashCode();
	return hc;
    }
    
    public boolean equals(Object o) {
	if (!(o instanceof LocalVariableGen))
	    return false;
	LocalVariableGen l = (LocalVariableGen) o;
	return l.index == index && l.start == start && l.end == end;
    }
    
    public String toString() {
	return new StringBuilder().append("LocalVariableGen(").append(name)
		   .append
		   (", ").append
		   (type).append
		   (", ").append
		   (start).append
		   (", ").append
		   (end).append
		   (")").toString();
    }
    
    public Object clone() {
	Object object;
	try {
	    object = super.clone();
	} catch (CloneNotSupportedException e) {
	    System.err.println(e);
	    return null;
	}
	return object;
    }
}
