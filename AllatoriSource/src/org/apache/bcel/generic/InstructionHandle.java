package org.apache.bcel.generic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.bcel.classfile.Utility;

public class InstructionHandle implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	InstructionHandle next, prev;
	Instruction instruction;
	protected int i_position = -1;
	private Set<InstructionTargeter> targeters;
	private Map<Object, Object> attributes;

	public final InstructionHandle getNext() {
		return next;
	}

	public final InstructionHandle getPrev() {
		return prev;
	}

	public final Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction i) {
		if (i == null) {
			throw new ClassGenException("Assigning null to handle");
		}
		if ((this.getClass() != BranchHandle.class) && (i instanceof BranchInstruction)) {
			throw new ClassGenException("Assigning branch instruction " + i + " to plain handle");
		}
		if (instruction != null) {
			instruction.dispose();
		}
		instruction = i;
	}

	public Instruction swapInstruction(Instruction i) {
		final Instruction oldInstruction = instruction;
		instruction = i;
		return oldInstruction;
	}

	protected InstructionHandle(Instruction i) {
		setInstruction(i);
	}

	private static InstructionHandle ih_list = null;

	static final InstructionHandle getInstructionHandle(Instruction i) {
		if (ih_list == null) {
			return new InstructionHandle(i);
		} else {
			final InstructionHandle ih = ih_list;
			ih_list = ih.next;
			ih.setInstruction(i);
			return ih;
		}
	}

	protected int updatePosition(int offset, int max_offset) {
		i_position += offset;
		return 0;
	}

	public int getPosition() {
		return i_position;
	}

	void setPosition(int pos) {
		i_position = pos;
	}

	protected void addHandle() {
		next = ih_list;
		ih_list = this;
	}

	void dispose() {
		next = prev = null;
		instruction.dispose();
		instruction = null;
		i_position = -1;
		attributes = null;
		removeAllTargeters();
		addHandle();
	}

	public void removeAllTargeters() {
		if (targeters != null) {
			targeters.clear();
		}
	}

	public void removeTargeter(InstructionTargeter t) {
		if (targeters != null) {
			targeters.remove(t);
		}
	}

	public void addTargeter(InstructionTargeter t) {
		if (targeters == null) {
			targeters = new HashSet<InstructionTargeter>();
		}
		targeters.add(t);
	}

	public boolean hasTargeters() {
		return (targeters != null) && (targeters.size() > 0);
	}

	public InstructionTargeter[] getTargeters() {
		if (!hasTargeters()) {
			return null;
		}
		final InstructionTargeter[] t = new InstructionTargeter[targeters.size()];
		targeters.toArray(t);
		return t;
	}

	public String toString(boolean verbose) {
		return Utility.format(i_position, 4, false, ' ') + ": " + instruction.toString(verbose);
	}

	@Override
	public String toString() {
		return toString(true);
	}

	public void addAttribute(Object key, Object attr) {
		if (attributes == null) {
			attributes = new HashMap<Object, Object>(3);
		}
		attributes.put(key, attr);
	}

	public void removeAttribute(Object key) {
		if (attributes != null) {
			attributes.remove(key);
		}
	}

	public Object getAttribute(Object key) {
		if (attributes != null) {
			return attributes.get(key);
		}
		return null;
	}

	public Collection<Object> getAttributes() {
		if (attributes == null) {
			attributes = new HashMap<Object, Object>(3);
		}
		return attributes.values();
	}

	public void accept(Visitor v) {
		instruction.accept(v);
	}
}
