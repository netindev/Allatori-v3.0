package org.apache.bcel.generic;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.util.ByteSequence;

public class InstructionList implements Serializable {

	private InstructionHandle start = null, end = null;
	private int length = 0;
	private int[] byte_positions;

	public InstructionList() {
	}

	public InstructionList(Instruction i) {
		append(i);
	}

	public InstructionList(BranchInstruction i) {
		append(i);
	}

	public InstructionList(CompoundInstruction c) {
		append(c.getInstructionList());
	}

	public boolean isEmpty() {
		return start == null;
	}

	public static InstructionHandle findHandle(InstructionHandle[] ihs, int[] pos, int count, int target) {
		int l = 0, r = count - 1;
		do {
			final int i = (l + r) / 2;
			final int j = pos[i];
			if (j == target) {
				return ihs[i];
			} else if (target < j) {
				r = i - 1;
			} else {
				l = i + 1;
			}
		} while (l <= r);
		return null;
	}

	public InstructionHandle findHandle(int pos) {
		final InstructionHandle[] ihs = getInstructionHandles();
		return findHandle(ihs, byte_positions, length, pos);
	}

	public InstructionList(byte[] code) {
		final ByteSequence bytes = new ByteSequence(code);
		final InstructionHandle[] ihs = new InstructionHandle[code.length];
		final int[] pos = new int[code.length];
		int count = 0;
		try {
			while (bytes.available() > 0) {
				final int off = bytes.getIndex();
				pos[count] = off;
				final Instruction i = Instruction.readInstruction(bytes);
				InstructionHandle ih;
				if (i instanceof BranchInstruction) {
					ih = append((BranchInstruction) i);
				} else {
					ih = append(i);
				}
				ih.setPosition(off);
				ihs[count] = ih;
				count++;
			}
		} catch (final IOException e) {
			throw new ClassGenException(e.toString());
		}
		byte_positions = new int[count];
		System.arraycopy(pos, 0, byte_positions, 0, count);
		for (int i = 0; i < count; i++) {
			if (ihs[i] instanceof BranchHandle) {
				final BranchInstruction bi = (BranchInstruction) ihs[i].instruction;
				int target = bi.position + bi.getIndex();
				InstructionHandle ih = findHandle(ihs, pos, count, target);
				if (ih == null) {
					throw new ClassGenException("Couldn't find target for branch: " + bi);
				}
				bi.setTarget(ih);
				if (bi instanceof Select) {
					final Select s = (Select) bi;
					final int[] indices = s.getIndices();
					for (int j = 0; j < indices.length; j++) {
						target = bi.position + indices[j];
						ih = findHandle(ihs, pos, count, target);
						if (ih == null) {
							throw new ClassGenException("Couldn't find target for switch: " + bi);
						}
						s.setTarget(j, ih);
					}
				}
			}
		}
	}

	public InstructionHandle append(InstructionHandle ih, InstructionList il) {
		if (il == null) {
			throw new ClassGenException("Appending null InstructionList");
		}
		if (il.isEmpty()) {
			return ih;
		}
		final InstructionHandle next = ih.next, ret = il.start;
		ih.next = il.start;
		il.start.prev = ih;
		il.end.next = next;
		if (next != null) {
			next.prev = il.end;
		} else {
			end = il.end;
		}
		length += il.length;
		il.clear();
		return ret;
	}

	public InstructionHandle append(Instruction i, InstructionList il) {
		InstructionHandle ih;
		if ((ih = findInstruction2(i)) == null) {
			throw new ClassGenException("Instruction " + i + " is not contained in this list.");
		}
		return append(ih, il);
	}

	public InstructionHandle append(InstructionList il) {
		if (il == null) {
			throw new ClassGenException("Appending null InstructionList");
		}
		if (il.isEmpty()) {
			return null;
		}
		if (isEmpty()) {
			start = il.start;
			end = il.end;
			length = il.length;
			il.clear();
			return start;
		} else {
			return append(end, il);
		}
	}

	private void append(InstructionHandle ih) {
		if (isEmpty()) {
			start = end = ih;
			ih.next = ih.prev = null;
		} else {
			end.next = ih;
			ih.prev = end;
			ih.next = null;
			end = ih;
		}
		length++;
	}

	public InstructionHandle append(Instruction i) {
		final InstructionHandle ih = InstructionHandle.getInstructionHandle(i);
		append(ih);
		return ih;
	}

	public BranchHandle append(BranchInstruction i) {
		final BranchHandle ih = BranchHandle.getBranchHandle(i);
		append(ih);
		return ih;
	}

	public InstructionHandle append(Instruction i, Instruction j) {
		return append(i, new InstructionList(j));
	}

	public InstructionHandle append(Instruction i, CompoundInstruction c) {
		return append(i, c.getInstructionList());
	}

	public InstructionHandle append(CompoundInstruction c) {
		return append(c.getInstructionList());
	}

	public InstructionHandle append(InstructionHandle ih, CompoundInstruction c) {
		return append(ih, c.getInstructionList());
	}

	public InstructionHandle append(InstructionHandle ih, Instruction i) {
		return append(ih, new InstructionList(i));
	}

	public BranchHandle append(InstructionHandle ih, BranchInstruction i) {
		final BranchHandle bh = BranchHandle.getBranchHandle(i);
		final InstructionList il = new InstructionList();
		il.append(bh);
		append(ih, il);
		return bh;
	}

	public InstructionHandle insert(InstructionHandle ih, InstructionList il) {
		if (il == null) {
			throw new ClassGenException("Inserting null InstructionList");
		}
		if (il.isEmpty()) {
			return ih;
		}
		final InstructionHandle prev = ih.prev, ret = il.start;
		ih.prev = il.end;
		il.end.next = ih;
		il.start.prev = prev;
		if (prev != null) {
			prev.next = il.start;
		} else {
			start = il.start;
		}
		length += il.length;
		il.clear();
		return ret;
	}

	public InstructionHandle insert(InstructionList il) {
		if (isEmpty()) {
			append(il);
			return start;
		} else {
			return insert(start, il);
		}
	}

	private void insert(InstructionHandle ih) {
		if (isEmpty()) {
			start = end = ih;
			ih.next = ih.prev = null;
		} else {
			start.prev = ih;
			ih.next = start;
			ih.prev = null;
			start = ih;
		}
		length++;
	}

	public InstructionHandle insert(Instruction i, InstructionList il) {
		InstructionHandle ih;
		if ((ih = findInstruction1(i)) == null) {
			throw new ClassGenException("Instruction " + i + " is not contained in this list.");
		}
		return insert(ih, il);
	}

	public InstructionHandle insert(Instruction i) {
		final InstructionHandle ih = InstructionHandle.getInstructionHandle(i);
		insert(ih);
		return ih;
	}

	public BranchHandle insert(BranchInstruction i) {
		final BranchHandle ih = BranchHandle.getBranchHandle(i);
		insert(ih);
		return ih;
	}

	public InstructionHandle insert(Instruction i, Instruction j) {
		return insert(i, new InstructionList(j));
	}

	public InstructionHandle insert(Instruction i, CompoundInstruction c) {
		return insert(i, c.getInstructionList());
	}

	public InstructionHandle insert(CompoundInstruction c) {
		return insert(c.getInstructionList());
	}

	public InstructionHandle insert(InstructionHandle ih, Instruction i) {
		return insert(ih, new InstructionList(i));
	}

	public InstructionHandle insert(InstructionHandle ih, CompoundInstruction c) {
		return insert(ih, c.getInstructionList());
	}

	public BranchHandle insert(InstructionHandle ih, BranchInstruction i) {
		final BranchHandle bh = BranchHandle.getBranchHandle(i);
		final InstructionList il = new InstructionList();
		il.append(bh);
		insert(ih, il);
		return bh;
	}

	public void move(InstructionHandle start, InstructionHandle end, InstructionHandle target) {
		if ((start == null) || (end == null)) {
			throw new ClassGenException("Invalid null handle: From " + start + " to " + end);
		}
		if ((target == start) || (target == end)) {
			throw new ClassGenException("Invalid range: From " + start + " to " + end + " contains target " + target);
		}
		for (InstructionHandle ih = start; ih != end.next; ih = ih.next) {
			if (ih == null) {
				throw new ClassGenException("Invalid range: From " + start + " to " + end);
			} else if (ih == target) {
				throw new ClassGenException(
						"Invalid range: From " + start + " to " + end + " contains target " + target);
			}
		}
		final InstructionHandle prev = start.prev;
		InstructionHandle next = end.next;
		if (prev != null) {
			prev.next = next;
		} else {
			this.start = next;
		}
		if (next != null) {
			next.prev = prev;
		} else {
			this.end = prev;
		}
		start.prev = end.next = null;
		if (target == null) {
			if (this.start != null) {
				this.start.prev = end;
			}
			end.next = this.start;
			this.start = start;
		} else {
			next = target.next;
			target.next = start;
			start.prev = target;
			end.next = next;
			if (next != null) {
				next.prev = end;
			} else {
				this.end = end;
			}
		}
	}

	public void move(InstructionHandle ih, InstructionHandle target) {
		move(ih, ih, target);
	}

	private void remove(InstructionHandle prev, InstructionHandle next) throws TargetLostException {
		InstructionHandle first, last;
		if ((prev == null) && (next == null)) {
			first = last = start;
			start = end = null;
		} else {
			if (prev == null) {
				first = start;
				start = next;
			} else {
				first = prev.next;
				prev.next = next;
			}
			if (next == null) {
				last = end;
				end = prev;
			} else {
				last = next.prev;
				next.prev = prev;
			}
		}
		first.prev = null;
		last.next = null;
		final List target_vec = new ArrayList();
		for (InstructionHandle ih = first; ih != null; ih = ih.next) {
			ih.getInstruction().dispose();
		}
		final StringBuffer buf = new StringBuffer("{ ");
		for (InstructionHandle ih = first; ih != null; ih = next) {
			next = ih.next;
			length--;
			if (ih.hasTargeters()) {
				target_vec.add(ih);
				buf.append(ih.toString(true) + " ");
				ih.next = ih.prev = null;
			} else {
				ih.dispose();
			}
		}
		buf.append("}");
		if (!target_vec.isEmpty()) {
			final InstructionHandle[] targeted = new InstructionHandle[target_vec.size()];
			target_vec.toArray(targeted);
			throw new TargetLostException(targeted, buf.toString());
		}
	}

	public void delete(InstructionHandle ih) throws TargetLostException {
		remove(ih.prev, ih.next);
	}

	public void delete(Instruction i) throws TargetLostException {
		InstructionHandle ih;
		if ((ih = findInstruction1(i)) == null) {
			throw new ClassGenException("Instruction " + i + " is not contained in this list.");
		}
		delete(ih);
	}

	public void delete(InstructionHandle from, InstructionHandle to) throws TargetLostException {
		remove(from.prev, to.next);
	}

	public void delete(Instruction from, Instruction to) throws TargetLostException {
		InstructionHandle from_ih, to_ih;
		if ((from_ih = findInstruction1(from)) == null) {
			throw new ClassGenException("Instruction " + from + " is not contained in this list.");
		}
		if ((to_ih = findInstruction2(to)) == null) {
			throw new ClassGenException("Instruction " + to + " is not contained in this list.");
		}
		delete(from_ih, to_ih);
	}

	private InstructionHandle findInstruction1(Instruction i) {
		for (InstructionHandle ih = start; ih != null; ih = ih.next) {
			if (ih.instruction == i) {
				return ih;
			}
		}
		return null;
	}

	private InstructionHandle findInstruction2(Instruction i) {
		for (InstructionHandle ih = end; ih != null; ih = ih.prev) {
			if (ih.instruction == i) {
				return ih;
			}
		}
		return null;
	}

	public boolean contains(InstructionHandle i) {
		if (i == null) {
			return false;
		}
		for (InstructionHandle ih = start; ih != null; ih = ih.next) {
			if (ih == i) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(Instruction i) {
		return findInstruction1(i) != null;
	}

	public void setPositions() {
		setPositions(false);
	}

	public void setPositions(boolean check) {
		int max_additional_bytes = 0, additional_bytes = 0;
		int index = 0, count = 0;
		final int[] pos = new int[length];
		if (check) {
			for (InstructionHandle ih = start; ih != null; ih = ih.next) {
				final Instruction i = ih.instruction;
				if (i instanceof BranchInstruction) {
					Instruction inst = ((BranchInstruction) i).getTarget().instruction;
					if (!contains(inst)) {
						throw new ClassGenException("Branch target of " + Constants.OPCODE_NAMES[i.opcode] + ":" + inst
								+ " not in instruction list");
					}
					if (i instanceof Select) {
						final InstructionHandle[] targets = ((Select) i).getTargets();
						for (int j = 0; j < targets.length; j++) {
							inst = targets[j].instruction;
							if (!contains(inst)) {
								throw new ClassGenException("Branch target of " + Constants.OPCODE_NAMES[i.opcode] + ":"
										+ inst + " not in instruction list");
							}
						}
					}
					if (!(ih instanceof BranchHandle)) {
						throw new ClassGenException("Branch instruction " + Constants.OPCODE_NAMES[i.opcode] + ":"
								+ inst + " not contained in BranchHandle.");
					}
				}
			}
		}
		for (InstructionHandle ih = start; ih != null; ih = ih.next) {
			final Instruction i = ih.instruction;
			ih.setPosition(index);
			pos[count++] = index;
			switch (i.getOpcode()) {
			case Constants.JSR:
			case Constants.GOTO:
				max_additional_bytes += 2;
				break;
			case Constants.TABLESWITCH:
			case Constants.LOOKUPSWITCH:
				max_additional_bytes += 3;
				break;
			}
			index += i.getLength();
		}
		for (InstructionHandle ih = start; ih != null; ih = ih.next) {
			additional_bytes += ih.updatePosition(additional_bytes, max_additional_bytes);
		}
		index = count = 0;
		for (InstructionHandle ih = start; ih != null; ih = ih.next) {
			final Instruction i = ih.instruction;
			ih.setPosition(index);
			pos[count++] = index;
			index += i.getLength();
		}
		byte_positions = new int[count];
		System.arraycopy(pos, 0, byte_positions, 0, count);
	}

	public byte[] getByteCode() {
		setPositions();
		final ByteArrayOutputStream b = new ByteArrayOutputStream();
		final DataOutputStream out = new DataOutputStream(b);
		try {
			for (InstructionHandle ih = start; ih != null; ih = ih.next) {
				final Instruction i = ih.instruction;
				i.dump(out);
			}
		} catch (final IOException e) {
			System.err.println(e);
			return null;
		}
		return b.toByteArray();
	}

	public Instruction[] getInstructions() {
		final ByteSequence bytes = new ByteSequence(getByteCode());
		final List instructions = new ArrayList();
		try {
			while (bytes.available() > 0) {
				instructions.add(Instruction.readInstruction(bytes));
			}
		} catch (final IOException e) {
			throw new ClassGenException(e.toString());
		}
		return (Instruction[]) instructions.toArray(new Instruction[instructions.size()]);
	}

	@Override
	public String toString() {
		return toString(true);
	}

	public String toString(boolean verbose) {
		final StringBuffer buf = new StringBuffer();
		for (InstructionHandle ih = start; ih != null; ih = ih.next) {
			buf.append(ih.toString(verbose)).append("\n");
		}
		return buf.toString();
	}

	public Iterator iterator() {
		return new Iterator() {

			private InstructionHandle ih = start;

			@Override
			public Object next() {
				final InstructionHandle i = ih;
				ih = ih.next;
				return i;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean hasNext() {
				return ih != null;
			}
		};
	}

	public InstructionHandle[] getInstructionHandles() {
		final InstructionHandle[] ihs = new InstructionHandle[length];
		InstructionHandle ih = start;
		for (int i = 0; i < length; i++) {
			ihs[i] = ih;
			ih = ih.next;
		}
		return ihs;
	}

	public int[] getInstructionPositions() {
		return byte_positions;
	}

	public InstructionList copy() {
		final Map map = new HashMap();
		final InstructionList il = new InstructionList();
		for (InstructionHandle ih = start; ih != null; ih = ih.next) {
			final Instruction i = ih.instruction;
			final Instruction c = i.copy(); // Use clone for shallow copy
			if (c instanceof BranchInstruction) {
				map.put(ih, il.append((BranchInstruction) c));
			} else {
				map.put(ih, il.append(c));
			}
		}
		InstructionHandle ih = start;
		InstructionHandle ch = il.start;
		while (ih != null) {
			final Instruction i = ih.instruction;
			final Instruction c = ch.instruction;
			if (i instanceof BranchInstruction) {
				final BranchInstruction bi = (BranchInstruction) i;
				final BranchInstruction bc = (BranchInstruction) c;
				final InstructionHandle itarget = bi.getTarget();
				bc.setTarget((InstructionHandle) map.get(itarget));
				if (bi instanceof Select) {
					final InstructionHandle[] itargets = ((Select) bi).getTargets();
					final InstructionHandle[] ctargets = ((Select) bc).getTargets();
					for (int j = 0; j < itargets.length; j++) {
						ctargets[j] = (InstructionHandle) map.get(itargets[j]);
					}
				}
			}
			ih = ih.next;
			ch = ch.next;
		}
		return il;
	}

	public void replaceConstantPool(ConstantPoolGen old_cp, ConstantPoolGen new_cp) {
		for (InstructionHandle ih = start; ih != null; ih = ih.next) {
			final Instruction i = ih.instruction;
			if (i instanceof CPInstruction) {
				final CPInstruction ci = (CPInstruction) i;
				final Constant c = old_cp.getConstant(ci.getIndex());
				ci.setIndex(new_cp.addConstant(c, old_cp));
			}
		}
	}

	private void clear() {
		start = end = null;
		length = 0;
	}

	public void dispose() {
		for (InstructionHandle ih = end; ih != null; ih = ih.prev) {
			ih.dispose();
		}
		clear();
	}

	public InstructionHandle getStart() {
		return start;
	}

	public InstructionHandle getEnd() {
		return end;
	}

	public int getLength() {
		return length;
	}

	public int size() {
		return length;
	}

	public void redirectBranches(InstructionHandle old_target, InstructionHandle new_target) {
		for (InstructionHandle ih = start; ih != null; ih = ih.next) {
			final Instruction i = ih.getInstruction();
			if (i instanceof BranchInstruction) {
				final BranchInstruction b = (BranchInstruction) i;
				final InstructionHandle target = b.getTarget();
				if (target == old_target) {
					b.setTarget(new_target);
				}
				if (b instanceof Select) {
					final InstructionHandle[] targets = ((Select) b).getTargets();
					for (int j = 0; j < targets.length; j++) {
						if (targets[j] == old_target) {
							((Select) b).setTarget(j, new_target);
						}
					}
				}
			}
		}
	}

	public void redirectLocalVariables(LocalVariableGen[] lg, InstructionHandle old_target,
			InstructionHandle new_target) {
		for (int i = 0; i < lg.length; i++) {
			final InstructionHandle start = lg[i].getStart();
			final InstructionHandle end = lg[i].getEnd();
			if (start == old_target) {
				lg[i].setStart(new_target);
			}
			if (end == old_target) {
				lg[i].setEnd(new_target);
			}
		}
	}

	public void redirectExceptionHandlers(CodeExceptionGen[] exceptions, InstructionHandle old_target,
			InstructionHandle new_target) {
		for (int i = 0; i < exceptions.length; i++) {
			if (exceptions[i].getStartPC() == old_target) {
				exceptions[i].setStartPC(new_target);
			}
			if (exceptions[i].getEndPC() == old_target) {
				exceptions[i].setEndPC(new_target);
			}
			if (exceptions[i].getHandlerPC() == old_target) {
				exceptions[i].setHandlerPC(new_target);
			}
		}
	}

	private List observers;

	public void addObserver(InstructionListObserver o) {
		if (observers == null) {
			observers = new ArrayList();
		}
		observers.add(o);
	}

	public void removeObserver(InstructionListObserver o) {
		if (observers != null) {
			observers.remove(o);
		}
	}

	public void update() {
		if (observers != null) {
			for (final Iterator e = observers.iterator(); e.hasNext();) {
				((InstructionListObserver) e.next()).notify(this);
			}
		}
	}
}
