package org.apache.bcel.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Utility;
import org.apache.bcel.generic.AllocationInstruction;
import org.apache.bcel.generic.ArrayInstruction;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.BranchHandle;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.CHECKCAST;
import org.apache.bcel.generic.CPInstruction;
import org.apache.bcel.generic.CodeExceptionGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ConstantPushInstruction;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.bcel.generic.FieldInstruction;
import org.apache.bcel.generic.IINC;
import org.apache.bcel.generic.INSTANCEOF;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.LDC2_W;
import org.apache.bcel.generic.LocalVariableInstruction;
import org.apache.bcel.generic.MULTIANEWARRAY;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.NEWARRAY;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.RET;
import org.apache.bcel.generic.ReturnInstruction;
import org.apache.bcel.generic.Select;
import org.apache.bcel.generic.Type;

class BCELFactory extends EmptyVisitor {
	private final MethodGen _mg;
	private final PrintWriter _out;
	private final ConstantPoolGen _cp;
	private final Map branch_map = new HashMap();
	private final List branches = new ArrayList();

	BCELFactory(MethodGen mg, PrintWriter out) {
		_mg = mg;
		_cp = mg.getConstantPool();
		_out = out;
	}

	public void start() {
		if (!_mg.isAbstract() && !_mg.isNative()) {
			for (InstructionHandle ih = _mg.getInstructionList().getStart(); ih != null; ih = ih.getNext()) {
				final Instruction i = ih.getInstruction();
				if (i instanceof BranchInstruction)
					branch_map.put(i, ih);
				if (ih.hasTargeters()) {
					if (i instanceof BranchInstruction)
						_out.println(new StringBuilder().append("    InstructionHandle ih_").append(ih.getPosition())
								.append(";").toString());
					else
						_out.print(new StringBuilder().append("    InstructionHandle ih_").append(ih.getPosition())
								.append(" = ").toString());
				} else
					_out.print("    ");
				if (!visitInstruction(i))
					i.accept(this);
			}
			updateBranchTargets();
			updateExceptionHandlers();
		}
	}

	private boolean visitInstruction(Instruction i) {
		final short opcode = i.getOpcode();
		if (InstructionConstants.INSTRUCTIONS[opcode] != null && !(i instanceof ConstantPushInstruction)
				&& !(i instanceof ReturnInstruction)) {
			_out.println(new StringBuilder().append("il.append(InstructionConstants.")
					.append(i.getName().toUpperCase(Locale.ENGLISH)).append(");").toString());
			return true;
		}
		return false;
	}

	@Override
	public void visitLocalVariableInstruction(LocalVariableInstruction i) {
		final short opcode = i.getOpcode();
		final Type type = i.getType(_cp);
		if (opcode == 132)
			_out.println(new StringBuilder().append("il.append(new IINC(").append(i.getIndex()).append(", ")
					.append(((IINC) i).getIncrement()).append("));").toString());
		else {
			final String kind = opcode < 54 ? "Load" : "Store";
			_out.println(new StringBuilder().append("il.append(_factory.create").append(kind).append("(")
					.append(BCELifier.printType(type)).append(", ").append(i.getIndex()).append("));").toString());
		}
	}

	@Override
	public void visitArrayInstruction(ArrayInstruction i) {
		final short opcode = i.getOpcode();
		final Type type = i.getType(_cp);
		final String kind = opcode < 79 ? "Load" : "Store";
		_out.println(new StringBuilder().append("il.append(_factory.createArray").append(kind).append("(")
				.append(BCELifier.printType(type)).append("));").toString());
	}

	@Override
	public void visitFieldInstruction(FieldInstruction i) {
		final short opcode = i.getOpcode();
		final String class_name = i.getClassName(_cp);
		final String field_name = i.getFieldName(_cp);
		final Type type = i.getFieldType(_cp);
		_out.println(new StringBuilder().append("il.append(_factory.createFieldAccess(\"").append(class_name)
				.append("\", \"").append(field_name).append("\", ").append(BCELifier.printType(type)).append(", ")
				.append("Constants.").append(Constants.OPCODE_NAMES[opcode].toUpperCase(Locale.ENGLISH)).append("));")
				.toString());
	}

	@Override
	public void visitInvokeInstruction(InvokeInstruction i) {
		final short opcode = i.getOpcode();
		final String class_name = i.getClassName(_cp);
		final String method_name = i.getMethodName(_cp);
		final Type type = i.getReturnType(_cp);
		final Type[] arg_types = i.getArgumentTypes(_cp);
		_out.println(new StringBuilder().append("il.append(_factory.createInvoke(\"").append(class_name)
				.append("\", \"").append(method_name).append("\", ").append(BCELifier.printType(type)).append(", ")
				.append(BCELifier.printArgumentTypes(arg_types)).append(", ").append("Constants.")
				.append(Constants.OPCODE_NAMES[opcode].toUpperCase(Locale.ENGLISH)).append("));").toString());
	}

	@Override
	public void visitAllocationInstruction(AllocationInstruction i) {
		Type type;
		if (i instanceof CPInstruction)
			type = ((CPInstruction) i).getType(_cp);
		else
			type = ((NEWARRAY) i).getType();
		final short opcode = ((Instruction) i).getOpcode();
		int dim = 1;
		switch (opcode) {
		case 187:
			_out.println(new StringBuilder().append("il.append(_factory.createNew(\"")
					.append(((ObjectType) type).getClassName()).append("\"));").toString());
			break;
		case 197:
			dim = ((MULTIANEWARRAY) i).getDimensions();
			/* fall through */
		case 188:
		case 189:
			if (type instanceof ArrayType)
				type = ((ArrayType) type).getBasicType();
			_out.println(new StringBuilder().append("il.append(_factory.createNewArray(")
					.append(BCELifier.printType(type)).append(", (short) ").append(dim).append("));").toString());
			break;
		default:
			throw new RuntimeException(new StringBuilder().append("Oops: ").append(opcode).toString());
		}
	}

	private void createConstant(Object value) {
		String embed = value.toString();
		if (value instanceof String)
			embed = new StringBuilder().append('\"').append(Utility.convertString(embed)).append('\"').toString();
		else if (value instanceof Character)
			embed = new StringBuilder().append("(char)0x").append(Integer.toHexString(((Character) value).charValue()))
					.toString();
		else if (value instanceof Float)
			embed = new StringBuilder().append(embed).append("f").toString();
		else if (value instanceof Long)
			embed = new StringBuilder().append(embed).append("L").toString();
		else if (value instanceof ObjectType) {
			final ObjectType ot = (ObjectType) value;
			embed = new StringBuilder().append("new ObjectType(\"").append(ot.getClassName()).append("\")").toString();
		}
		_out.println(new StringBuilder().append("il.append(new PUSH(_cp, ").append(embed).append("));").toString());
	}

	@Override
	public void visitLDC(LDC i) {
		createConstant(i.getValue(_cp));
	}

	@Override
	public void visitLDC2_W(LDC2_W i) {
		createConstant(i.getValue(_cp));
	}

	@Override
	public void visitConstantPushInstruction(ConstantPushInstruction i) {
		createConstant(i.getValue());
	}

	@Override
	public void visitINSTANCEOF(INSTANCEOF i) {
		final Type type = i.getType(_cp);
		_out.println(new StringBuilder().append("il.append(new INSTANCEOF(_cp.addClass(")
				.append(BCELifier.printType(type)).append(")));").toString());
	}

	@Override
	public void visitCHECKCAST(CHECKCAST i) {
		final Type type = i.getType(_cp);
		_out.println(new StringBuilder().append("il.append(_factory.createCheckCast(").append(BCELifier.printType(type))
				.append("));").toString());
	}

	@Override
	public void visitReturnInstruction(ReturnInstruction i) {
		final Type type = i.getType(_cp);
		_out.println(new StringBuilder().append("il.append(_factory.createReturn(").append(BCELifier.printType(type))
				.append("));").toString());
	}

	@Override
	public void visitBranchInstruction(BranchInstruction bi) {
		final BranchHandle bh = (BranchHandle) branch_map.get(bi);
		final int pos = bh.getPosition();
		final String name = new StringBuilder().append(bi.getName()).append("_").append(pos).toString();
		if (bi instanceof Select) {
			final Select s = (Select) bi;
			branches.add(bi);
			final StringBuffer args = new StringBuffer("new int[] { ");
			final int[] matchs = s.getMatchs();
			for (int i = 0; i < matchs.length; i++) {
				args.append(matchs[i]);
				if (i < matchs.length - 1)
					args.append(", ");
			}
			args.append(" }");
			_out.print(new StringBuilder().append("Select ").append(name).append(" = new ")
					.append(bi.getName().toUpperCase(Locale.ENGLISH)).append("(").append((Object) args)
					.append(", new InstructionHandle[] { ").toString());
			for (int i = 0; i < matchs.length; i++) {
				_out.print("null");
				if (i < matchs.length - 1)
					_out.print(", ");
			}
			_out.println(" }, null);");
		} else {
			final int t_pos = bh.getTarget().getPosition();
			String target;
			if (pos > t_pos)
				target = new StringBuilder().append("ih_").append(t_pos).toString();
			else {
				branches.add(bi);
				target = "null";
			}
			_out.println(new StringBuilder().append("    BranchInstruction ").append(name)
					.append(" = _factory.createBranchInstruction(").append("Constants.")
					.append(bi.getName().toUpperCase(Locale.ENGLISH)).append(", ").append(target).append(");")
					.toString());
		}
		if (bh.hasTargeters())
			_out.println(new StringBuilder().append("    ih_").append(pos).append(" = il.append(").append(name)
					.append(");").toString());
		else
			_out.println(new StringBuilder().append("    il.append(").append(name).append(");").toString());
	}

	@Override
	public void visitRET(RET i) {
		_out.println(new StringBuilder().append("il.append(new RET(").append(i.getIndex()).append(")));").toString());
	}

	private void updateBranchTargets() {
		final Iterator i$ = branches.iterator();
		while (i$.hasNext()) {
			final BranchInstruction bi = (BranchInstruction) i$.next();
			final BranchHandle bh = (BranchHandle) branch_map.get(bi);
			final int pos = bh.getPosition();
			final String name = new StringBuilder().append(bi.getName()).append("_").append(pos).toString();
			int t_pos = bh.getTarget().getPosition();
			_out.println(new StringBuilder().append("    ").append(name).append(".setTarget(ih_").append(t_pos)
					.append(");").toString());
			if (bi instanceof Select) {
				final InstructionHandle[] ihs = ((Select) bi).getTargets();
				for (int j = 0; j < ihs.length; j++) {
					t_pos = ihs[j].getPosition();
					_out.println(new StringBuilder().append("    ").append(name).append(".setTarget(").append(j)
							.append(", ih_").append(t_pos).append(");").toString());
				}
			}
		}
	}

	private void updateExceptionHandlers() {
		final CodeExceptionGen[] handlers = _mg.getExceptionHandlers();
		for (int i = 0; i < handlers.length; i++) {
			final CodeExceptionGen h = handlers[i];
			final String type = (h.getCatchType() == null ? "null" : BCELifier.printType(h.getCatchType()));
			_out.println(new StringBuilder().append("    method.addExceptionHandler(ih_")
					.append(h.getStartPC().getPosition()).append(", ").append("ih_").append(h.getEndPC().getPosition())
					.append(", ").append("ih_").append(h.getHandlerPC().getPosition()).append(", ").append(type)
					.append(");").toString());
		}
	}
}
