package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public abstract class BranchInstruction extends Instruction implements InstructionTargeter {
	private static final long serialVersionUID = 3225905281842405051L;
	protected int index;
	protected InstructionHandle target;
	protected int position;

	BranchInstruction() {
	}

	protected BranchInstruction(short opcode, InstructionHandle target) {
		super(opcode, (short) 3);
		setTarget(target);
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		out.writeByte(opcode);
		index = getTargetOffset();
		if (Math.abs(index) >= 32767)
			throw new ClassGenException(
					new StringBuilder().append("Branch target offset too large for short: ").append(index).toString());
		out.writeShort(index);
	}

	protected int getTargetOffset(InstructionHandle _target) {
		if (_target == null)
			throw new ClassGenException(new StringBuilder().append("Target of ").append(super.toString(true))
					.append(" is invalid null handle").toString());
		final int t = _target.getPosition();
		if (t < 0)
			throw new ClassGenException(new StringBuilder().append("Invalid branch target position offset for ")
					.append(super.toString(true)).append(":").append(t).append(":").append(_target).toString());
		return t - position;
	}

	protected int getTargetOffset() {
		return getTargetOffset(target);
	}

	protected int updatePosition(int offset, int max_offset) {
		position += offset;
		return 0;
	}

	@Override
	public String toString(boolean verbose) {
		final String s = super.toString(verbose);
		String t = "null";
		if (verbose) {
			if (target != null) {
				if (target.getInstruction() == this)
					t = "<points to itself>";
				else if (target.getInstruction() == null)
					t = "<null instruction!!!?>";
				else
					t = target.getInstruction().toString(false);
			}
		} else if (target != null) {
			index = getTargetOffset();
			t = new StringBuilder().append("").append(index + position).toString();
		}
		return new StringBuilder().append(s).append(" -> ").append(t).toString();
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		length = (short) 3;
		index = bytes.readShort();
	}

	public final int getIndex() {
		return index;
	}

	public InstructionHandle getTarget() {
		return target;
	}

	public void setTarget(InstructionHandle target) {
		notifyTarget(this.target, target, this);
		this.target = target;
	}

	static final void notifyTarget(InstructionHandle old_ih, InstructionHandle new_ih, InstructionTargeter t) {
		if (old_ih != null)
			old_ih.removeTargeter(t);
		if (new_ih != null)
			new_ih.addTargeter(t);
	}

	@Override
	public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
		if (target == old_ih)
			setTarget(new_ih);
		else
			throw new ClassGenException(new StringBuilder().append("Not targeting ").append(old_ih).append(", but ")
					.append(target).toString());
	}

	@Override
	public boolean containsTarget(InstructionHandle ih) {
		return target == ih;
	}

	@Override
	void dispose() {
		setTarget(null);
		index = -1;
		position = -1;
	}
}
