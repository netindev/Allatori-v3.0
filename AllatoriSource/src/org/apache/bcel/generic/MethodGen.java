/* MethodGen - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.Annotations;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.ExceptionTable;
import org.apache.bcel.classfile.LineNumber;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.LocalVariableTypeTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.ParameterAnnotationEntry;
import org.apache.bcel.classfile.ParameterAnnotations;
import org.apache.bcel.classfile.RuntimeVisibleParameterAnnotations;
import org.apache.bcel.classfile.Utility;
import org.apache.bcel.util.BCELComparator;

public class MethodGen extends FieldGenOrMethodGen {
	private static final long serialVersionUID = -3924667713338957720L;
	private String class_name;
	private Type[] arg_types;
	private String[] arg_names;
	private int max_locals;
	private int max_stack;
	private InstructionList il;
	private boolean strip_attributes;
	private final List variable_vec = new ArrayList();
	private final List line_number_vec = new ArrayList();
	private final List exception_vec = new ArrayList();
	private final List throws_vec = new ArrayList();
	private final List code_attrs_vec = new ArrayList();
	private List[] param_annotations;
	private boolean hasParameterAnnotations = false;
	private boolean haveUnpackedParameterAnnotations = false;
	private static BCELComparator _cmp = new BCELComparator() {
		@Override
		public boolean equals(Object o1, Object o2) {
			final MethodGen THIS = (MethodGen) o1;
			final MethodGen THAT = (MethodGen) o2;
			return (THIS.getName().equals(THAT.getName()) && THIS.getSignature().equals(THAT.getSignature()));
		}

		@Override
		public int hashCode(Object o) {
			final MethodGen THIS = (MethodGen) o;
			return THIS.getSignature().hashCode() ^ THIS.getName().hashCode();
		}
	};
	private List observers;

	static final class BranchStack {
		Stack branchTargets = new Stack();
		Hashtable visitedTargets = new Hashtable();

		public void push(InstructionHandle target, int stackDepth) {
			if (!visited(target))
				branchTargets.push(visit(target, stackDepth));
		}

		public BranchTarget pop() {
			if (!branchTargets.empty()) {
				final BranchTarget bt = (BranchTarget) branchTargets.pop();
				return bt;
			}
			return null;
		}

		private final BranchTarget visit(InstructionHandle target, int stackDepth) {
			final BranchTarget bt = new BranchTarget(target, stackDepth);
			visitedTargets.put(target, bt);
			return bt;
		}

		private final boolean visited(InstructionHandle target) {
			return visitedTargets.get(target) != null;
		}
	}

	static final class BranchTarget {
		InstructionHandle target;
		int stackDepth;

		BranchTarget(InstructionHandle target, int stackDepth) {
			this.target = target;
			this.stackDepth = stackDepth;
		}
	}

	public MethodGen(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name,
			String class_name, InstructionList il, ConstantPoolGen cp) {
		setAccessFlags(access_flags);
		setType(return_type);
		setArgumentTypes(arg_types);
		setArgumentNames(arg_names);
		setName(method_name);
		setClassName(class_name);
		setInstructionList(il);
		setConstantPool(cp);
		final boolean abstract_ = isAbstract() || isNative();
		InstructionHandle start = null;
		InstructionHandle end = null;
		if (!abstract_) {
			start = il.getStart();
			end = il.getEnd();
			if (!isStatic() && class_name != null)
				addLocalVariable("this", new ObjectType(class_name), start, end);
		}
		if (arg_types != null) {
			final int size = arg_types.length;
			for (int i = 0; i < size; i++) {
				if (Type.VOID == arg_types[i])
					throw new ClassGenException("'void' is an illegal argument type for a method");
			}
			if (arg_names != null) {
				if (size != arg_names.length)
					throw new ClassGenException(new StringBuilder().append("Mismatch in argument array lengths: ")
							.append(size).append(" vs. ").append(arg_names.length).toString());
			} else {
				arg_names = new String[size];
				for (int i = 0; i < size; i++)
					arg_names[i] = new StringBuilder().append("arg").append(i).toString();
				setArgumentNames(arg_names);
			}
			if (!abstract_) {
				for (int i = 0; i < size; i++)
					addLocalVariable(arg_names[i], arg_types[i], start, end);
			}
		}
	}

	public MethodGen(Method m, String class_name, ConstantPoolGen cp) {
		this(m.getAccessFlags(), Type.getReturnType(m.getSignature()), Type.getArgumentTypes(m.getSignature()), null,
				m.getName(), class_name,
				((m.getAccessFlags() & 0x500) == 0 ? new InstructionList(m.getCode().getCode()) : null), cp);
		final Attribute[] attributes = m.getAttributes();
		for (int i = 0; i < attributes.length; i++) {
			Attribute a = attributes[i];
			if (a instanceof Code) {
				final Code c = (Code) a;
				setMaxStack(c.getMaxStack());
				setMaxLocals(c.getMaxLocals());
				final CodeException[] ces = c.getExceptionTable();
				if (ces != null) {
					for (int j = 0; j < ces.length; j++) {
						final CodeException ce = ces[j];
						final int type = ce.getCatchType();
						ObjectType c_type = null;
						if (type > 0) {
							final String cen = m.getConstantPool().getConstantString(type, (byte) 7);
							c_type = new ObjectType(cen);
						}
						final int end_pc = ce.getEndPC();
						final int length = m.getCode().getCode().length;
						InstructionHandle end;
						if (length == end_pc)
							end = il.getEnd();
						else {
							end = il.findHandle(end_pc);
							end = end.getPrev();
						}
						addExceptionHandler(il.findHandle(ce.getStartPC()), end, il.findHandle(ce.getHandlerPC()),
								c_type);
					}
				}
				final Attribute[] c_attributes = c.getAttributes();
				for (int j = 0; j < c_attributes.length; j++) {
					a = c_attributes[j];
					if (a instanceof LineNumberTable) {
						final LineNumber[] ln = ((LineNumberTable) a).getLineNumberTable();
						for (int k = 0; k < ln.length; k++) {
							final LineNumber l = ln[k];
							final InstructionHandle ih = il.findHandle(l.getStartPC());
							if (ih != null)
								addLineNumber(ih, l.getLineNumber());
						}
					} else if (a instanceof LocalVariableTable) {
						final LocalVariable[] lv = ((LocalVariableTable) a).getLocalVariableTable();
						removeLocalVariables();
						for (int k = 0; k < lv.length; k++) {
							final LocalVariable l = lv[k];
							InstructionHandle start = il.findHandle(l.getStartPC());
							InstructionHandle end = il.findHandle(l.getStartPC() + l.getLength());
							if (null == start)
								start = il.getStart();
							if (null == end)
								end = il.getEnd();
							addLocalVariable(l.getName(), Type.getType(l.getSignature()), l.getIndex(), start, end);
						}
					} else if (a instanceof LocalVariableTypeTable) {
						final LocalVariable[] lv = ((LocalVariableTypeTable) a).getLocalVariableTypeTable();
						removeLocalVariables();
						for (int k = 0; k < lv.length; k++) {
							final LocalVariable l = lv[k];
							InstructionHandle start = il.findHandle(l.getStartPC());
							InstructionHandle end = il.findHandle(l.getStartPC() + l.getLength());
							if (null == start)
								start = il.getStart();
							if (null == end)
								end = il.getEnd();
							addLocalVariable(l.getName(), Type.getType(l.getSignature()), l.getIndex(), start, end);
						}
					} else
						addCodeAttribute(a);
				}
			} else if (a instanceof ExceptionTable) {
				final String[] names = ((ExceptionTable) a).getExceptionNames();
				for (int j = 0; j < names.length; j++)
					addException(names[j]);
			} else if (a instanceof Annotations) {
				final Annotations runtimeAnnotations = (Annotations) a;
				final AnnotationEntry[] aes = runtimeAnnotations.getAnnotationEntries();
				for (int k = 0; k < aes.length; k++) {
					final AnnotationEntry element = aes[k];
					addAnnotationEntry(new AnnotationEntryGen(element, cp, false));
				}
			} else
				addAttribute(a);
		}
	}

	public LocalVariableGen addLocalVariable(String name, Type type, int slot, InstructionHandle start,
			InstructionHandle end) {
		final byte t = type.getType();
		if (t != 16) {
			final int add = type.getSize();
			if (slot + add > max_locals)
				max_locals = slot + add;
			final LocalVariableGen l = new LocalVariableGen(slot, name, type, start, end);
			int i;
			if ((i = variable_vec.indexOf(l)) >= 0)
				variable_vec.set(i, l);
			else
				variable_vec.add(l);
			return l;
		}
		throw new IllegalArgumentException(new StringBuilder().append("Can not use ").append(type)
				.append(" as type for local variable").toString());
	}

	public LocalVariableGen addLocalVariable(String name, Type type, InstructionHandle start, InstructionHandle end) {
		return addLocalVariable(name, type, max_locals, start, end);
	}

	public void removeLocalVariable(LocalVariableGen l) {
		variable_vec.remove(l);
	}

	public void removeLocalVariables() {
		variable_vec.clear();
	}

	private static final void sort(LocalVariableGen[] vars, int l, int r) {
		int i = l;
		int j = r;
		final int m = vars[(l + r) / 2].getIndex();
		for (;;) {
			if (vars[i].getIndex() < m)
				i++;
			else {
				for (/**/; m < vars[j].getIndex(); j--) {
					/* empty */
				}
				if (i <= j) {
					final LocalVariableGen h = vars[i];
					vars[i] = vars[j];
					vars[j] = h;
					i++;
					j--;
				}
				if (i > j)
					break;
			}
		}
		if (l < j)
			sort(vars, l, j);
		if (i < r)
			sort(vars, i, r);
	}

	public LocalVariableGen[] getLocalVariables() {
		final int size = variable_vec.size();
		final LocalVariableGen[] lg = new LocalVariableGen[size];
		variable_vec.toArray(lg);
		for (int i = 0; i < size; i++) {
			if (lg[i].getStart() == null)
				lg[i].setStart(il.getStart());
			if (lg[i].getEnd() == null)
				lg[i].setEnd(il.getEnd());
		}
		if (size > 1)
			sort(lg, 0, size - 1);
		return lg;
	}

	public LocalVariableTable getLocalVariableTable(ConstantPoolGen cp) {
		final LocalVariableGen[] lg = getLocalVariables();
		final int size = lg.length;
		final LocalVariable[] lv = new LocalVariable[size];
		for (int i = 0; i < size; i++)
			lv[i] = lg[i].getLocalVariable(cp);
		return new LocalVariableTable(cp.addUtf8("LocalVariableTable"), 2 + lv.length * 10, lv, cp.getConstantPool());
	}

	public LineNumberGen addLineNumber(InstructionHandle ih, int src_line) {
		final LineNumberGen l = new LineNumberGen(ih, src_line);
		line_number_vec.add(l);
		return l;
	}

	public void removeLineNumber(LineNumberGen l) {
		line_number_vec.remove(l);
	}

	public void removeLineNumbers() {
		line_number_vec.clear();
	}

	public LineNumberGen[] getLineNumbers() {
		final LineNumberGen[] lg = new LineNumberGen[line_number_vec.size()];
		line_number_vec.toArray(lg);
		return lg;
	}

	public LineNumberTable getLineNumberTable(ConstantPoolGen cp) {
		final int size = line_number_vec.size();
		final LineNumber[] ln = new LineNumber[size];
		try {
			for (int i = 0; i < size; i++)
				ln[i] = ((LineNumberGen) line_number_vec.get(i)).getLineNumber();
		} catch (final ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
			/* empty */
		}
		return new LineNumberTable(cp.addUtf8("LineNumberTable"), 2 + ln.length * 4, ln, cp.getConstantPool());
	}

	public CodeExceptionGen addExceptionHandler(InstructionHandle start_pc, InstructionHandle end_pc,
			InstructionHandle handler_pc, ObjectType catch_type) {
		if (start_pc == null || end_pc == null || handler_pc == null)
			throw new ClassGenException("Exception handler target is null instruction");
		final CodeExceptionGen c = new CodeExceptionGen(start_pc, end_pc, handler_pc, catch_type);
		exception_vec.add(c);
		return c;
	}

	public void removeExceptionHandler(CodeExceptionGen c) {
		exception_vec.remove(c);
	}

	public void removeExceptionHandlers() {
		exception_vec.clear();
	}

	public CodeExceptionGen[] getExceptionHandlers() {
		final CodeExceptionGen[] cg = new CodeExceptionGen[exception_vec.size()];
		exception_vec.toArray(cg);
		return cg;
	}

	private CodeException[] getCodeExceptions() {
		final int size = exception_vec.size();
		final CodeException[] c_exc = new CodeException[size];
		try {
			for (int i = 0; i < size; i++) {
				final CodeExceptionGen c = (CodeExceptionGen) exception_vec.get(i);
				c_exc[i] = c.getCodeException(cp);
			}
		} catch (final ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
			/* empty */
		}
		return c_exc;
	}

	public void addException(String class_name) {
		throws_vec.add(class_name);
	}

	public void removeException(String c) {
		throws_vec.remove(c);
	}

	public void removeExceptions() {
		throws_vec.clear();
	}

	public String[] getExceptions() {
		final String[] e = new String[throws_vec.size()];
		throws_vec.toArray(e);
		return e;
	}

	private ExceptionTable getExceptionTable(ConstantPoolGen cp) {
		final int size = throws_vec.size();
		final int[] ex = new int[size];
		try {
			for (int i = 0; i < size; i++)
				ex[i] = cp.addClass((String) throws_vec.get(i));
		} catch (final ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
			/* empty */
		}
		return new ExceptionTable(cp.addUtf8("Exceptions"), 2 + 2 * size, ex, cp.getConstantPool());
	}

	public void addCodeAttribute(Attribute a) {
		code_attrs_vec.add(a);
	}

	public void removeCodeAttribute(Attribute a) {
		code_attrs_vec.remove(a);
	}

	public void removeCodeAttributes() {
		code_attrs_vec.clear();
	}

	public Attribute[] getCodeAttributes() {
		final Attribute[] attributes = new Attribute[code_attrs_vec.size()];
		code_attrs_vec.toArray(attributes);
		return attributes;
	}

	public void addAnnotationsAsAttribute(ConstantPoolGen cp) {
		final Attribute[] attrs = Utility.getAnnotationAttributes(cp, annotation_vec);
		for (int i = 0; i < attrs.length; i++)
			addAttribute(attrs[i]);
	}

	public void addParameterAnnotationsAsAttribute(ConstantPoolGen cp) {
		if (hasParameterAnnotations) {
			final Attribute[] attrs = Utility.getParameterAnnotationAttributes(cp, param_annotations);
			if (attrs != null) {
				for (int i = 0; i < attrs.length; i++)
					addAttribute(attrs[i]);
			}
		}
	}

	public Method getMethod() {
		final String signature = getSignature();
		final int name_index = cp.addUtf8(name);
		final int signature_index = cp.addUtf8(signature);
		byte[] byte_code = null;
		if (il != null)
			byte_code = il.getByteCode();
		LineNumberTable lnt = null;
		LocalVariableTable lvt = null;
		if (variable_vec.size() > 0 && !strip_attributes)
			addCodeAttribute(lvt = getLocalVariableTable(cp));
		if (line_number_vec.size() > 0 && !strip_attributes)
			addCodeAttribute(lnt = getLineNumberTable(cp));
		final Attribute[] code_attrs = getCodeAttributes();
		int attrs_len = 0;
		for (int i = 0; i < code_attrs.length; i++)
			attrs_len += code_attrs[i].getLength() + 6;
		final CodeException[] c_exc = getCodeExceptions();
		final int exc_len = c_exc.length * 8;
		Code code = null;
		if (il != null && !isAbstract() && !isNative()) {
			final Attribute[] attributes = getAttributes();
			for (int i = 0; i < attributes.length; i++) {
				final Attribute a = attributes[i];
				if (a instanceof Code)
					removeAttribute(a);
			}
			code = new Code(cp.addUtf8("Code"), 8 + byte_code.length + 2 + exc_len + 2 + attrs_len, max_stack,
					max_locals, byte_code, c_exc, code_attrs, cp.getConstantPool());
			addAttribute(code);
		}
		addAnnotationsAsAttribute(cp);
		addParameterAnnotationsAsAttribute(cp);
		ExceptionTable et = null;
		if (throws_vec.size() > 0)
			addAttribute(et = getExceptionTable(cp));
		final Method m = new Method(access_flags, name_index, signature_index, getAttributes(), cp.getConstantPool());
		if (lvt != null)
			removeCodeAttribute(lvt);
		if (lnt != null)
			removeCodeAttribute(lnt);
		if (code != null)
			removeAttribute(code);
		if (et != null)
			removeAttribute(et);
		return m;
	}

	public void removeNOPs() {
		if (il != null) {
			InstructionHandle next;
			for (InstructionHandle ih = il.getStart(); ih != null; ih = next) {
				next = ih.next;
				if (next != null && ih.getInstruction() instanceof NOP) {
					try {
						il.delete(ih);
					} catch (final TargetLostException e) {
						final InstructionHandle[] targets = e.getTargets();
						for (int i = 0; i < targets.length; i++) {
							final InstructionTargeter[] targeters = targets[i].getTargeters();
							for (int j = 0; j < targeters.length; j++)
								targeters[j].updateTarget(targets[i], next);
						}
					}
				}
			}
		}
	}

	public void setMaxLocals(int m) {
		max_locals = m;
	}

	public int getMaxLocals() {
		return max_locals;
	}

	public void setMaxStack(int m) {
		max_stack = m;
	}

	public int getMaxStack() {
		return max_stack;
	}

	public String getClassName() {
		return class_name;
	}

	public void setClassName(String class_name) {
		this.class_name = class_name;
	}

	public void setReturnType(Type return_type) {
		setType(return_type);
	}

	public Type getReturnType() {
		return getType();
	}

	public void setArgumentTypes(Type[] arg_types) {
		this.arg_types = arg_types;
	}

	public Type[] getArgumentTypes() {
		return arg_types.clone();
	}

	public void setArgumentType(int i, Type type) {
		arg_types[i] = type;
	}

	public Type getArgumentType(int i) {
		return arg_types[i];
	}

	public void setArgumentNames(String[] arg_names) {
		this.arg_names = arg_names;
	}

	public String[] getArgumentNames() {
		return arg_names.clone();
	}

	public void setArgumentName(int i, String name) {
		arg_names[i] = name;
	}

	public String getArgumentName(int i) {
		return arg_names[i];
	}

	public InstructionList getInstructionList() {
		return il;
	}

	public void setInstructionList(InstructionList il) {
		this.il = il;
	}

	@Override
	public String getSignature() {
		return Type.getMethodSignature(type, arg_types);
	}

	public void setMaxStack() {
		if (il != null)
			max_stack = getMaxStack(cp, il, getExceptionHandlers());
		else
			max_stack = 0;
	}

	public void setMaxLocals() {
		if (il != null) {
			int max = isStatic() ? 0 : 1;
			if (arg_types != null) {
				for (int i = 0; i < arg_types.length; i++)
					max += arg_types[i].getSize();
			}
			for (InstructionHandle ih = il.getStart(); ih != null; ih = ih.getNext()) {
				final Instruction ins = ih.getInstruction();
				if (ins instanceof LocalVariableInstruction || ins instanceof RET || ins instanceof IINC) {
					final int index = (((IndexedInstruction) ins).getIndex()
							+ ((TypedInstruction) ins).getType(cp).getSize());
					if (index > max)
						max = index;
				}
			}
			max_locals = max;
		} else
			max_locals = 0;
	}

	public void stripAttributes(boolean flag) {
		strip_attributes = flag;
	}

	public static int getMaxStack(ConstantPoolGen cp, InstructionList il, CodeExceptionGen[] et) {
		final BranchStack branchTargets = new BranchStack();
		for (int i = 0; i < et.length; i++) {
			final InstructionHandle handler_pc = et[i].getHandlerPC();
			if (handler_pc != null)
				branchTargets.push(handler_pc, 1);
		}
		int stackDepth = 0;
		int maxStackDepth = 0;
		InstructionHandle ih = il.getStart();
		while (ih != null) {
			final Instruction instruction = ih.getInstruction();
			final short opcode = instruction.getOpcode();
			final int delta = instruction.produceStack(cp) - instruction.consumeStack(cp);
			stackDepth += delta;
			if (stackDepth > maxStackDepth)
				maxStackDepth = stackDepth;
			if (instruction instanceof BranchInstruction) {
				final BranchInstruction branch = (BranchInstruction) instruction;
				if (instruction instanceof Select) {
					final Select select = (Select) branch;
					final InstructionHandle[] targets = select.getTargets();
					for (int i = 0; i < targets.length; i++)
						branchTargets.push(targets[i], stackDepth);
					ih = null;
				} else if (!(branch instanceof IfInstruction)) {
					if (opcode == 168 || opcode == 201)
						branchTargets.push(ih.getNext(), stackDepth - 1);
					ih = null;
				}
				branchTargets.push(branch.getTarget(), stackDepth);
			} else if (opcode == 191 || opcode == 169 || opcode >= 172 && opcode <= 177)
				ih = null;
			if (ih != null)
				ih = ih.getNext();
			if (ih == null) {
				final BranchTarget bt = branchTargets.pop();
				if (bt != null) {
					ih = bt.target;
					stackDepth = bt.stackDepth;
				}
			}
		}
		return maxStackDepth;
	}

	public void addObserver(MethodObserver o) {
		if (observers == null)
			observers = new ArrayList();
		observers.add(o);
	}

	public void removeObserver(MethodObserver o) {
		if (observers != null)
			observers.remove(o);
	}

	public void update() {
		if (observers != null) {
			final Iterator i$ = observers.iterator();
			while (i$.hasNext()) {
				final MethodObserver observer = (MethodObserver) i$.next();
				observer.notify(this);
			}
		}
	}

	@Override
	public final String toString() {
		final String access = Utility.accessToString(access_flags);
		String signature = Type.getMethodSignature(type, arg_types);
		signature = Utility.methodSignatureToString(signature, name, access, true, getLocalVariableTable(cp));
		final StringBuilder buf = new StringBuilder(signature);
		for (int i = 0; i < getAttributes().length; i++) {
			final Attribute a = getAttributes()[i];
			if (!(a instanceof Code) && !(a instanceof ExceptionTable))
				buf.append(" [").append(a.toString()).append("]");
		}
		if (throws_vec.size() > 0) {
			final Iterator i$ = throws_vec.iterator();
			while (i$.hasNext()) {
				final String throwsDescriptor = (String) i$.next();
				buf.append("\n\t\tthrows ").append(throwsDescriptor);
			}
		}
		return buf.toString();
	}

	public MethodGen copy(String class_name, ConstantPoolGen cp) {
		final Method m = ((MethodGen) clone()).getMethod();
		final MethodGen mg = new MethodGen(m, class_name, this.cp);
		if (this.cp != cp) {
			mg.setConstantPool(cp);
			mg.getInstructionList().replaceConstantPool(this.cp, cp);
		}
		return mg;
	}

	public List getAnnotationsOnParameter(int i) {
		ensureExistingParameterAnnotationsUnpacked();
		if (!hasParameterAnnotations || i > arg_types.length)
			return null;
		return param_annotations[i];
	}

	private void ensureExistingParameterAnnotationsUnpacked() {
		if (!haveUnpackedParameterAnnotations) {
			final Attribute[] attrs = getAttributes();
			ParameterAnnotations paramAnnVisAttr = null;
			ParameterAnnotations paramAnnInvisAttr = null;
			for (int i = 0; i < attrs.length; i++) {
				final Attribute attribute = attrs[i];
				if (attribute instanceof ParameterAnnotations) {
					if (!hasParameterAnnotations) {
						param_annotations = new List[arg_types.length];
						for (int j = 0; j < arg_types.length; j++)
							param_annotations[j] = new ArrayList();
					}
					hasParameterAnnotations = true;
					final ParameterAnnotations rpa = (ParameterAnnotations) attribute;
					if (rpa instanceof RuntimeVisibleParameterAnnotations)
						paramAnnVisAttr = rpa;
					else
						paramAnnInvisAttr = rpa;
					for (int j = 0; j < arg_types.length; j++) {
						final ParameterAnnotationEntry immutableArray = rpa.getParameterAnnotationEntries()[j];
						final List mutable = makeMutableVersion(immutableArray.getAnnotationEntries());
						param_annotations[j].addAll(mutable);
					}
				}
			}
			if (paramAnnVisAttr != null)
				removeAttribute(paramAnnVisAttr);
			if (paramAnnInvisAttr != null)
				removeAttribute(paramAnnInvisAttr);
			haveUnpackedParameterAnnotations = true;
		}
	}

	private List makeMutableVersion(AnnotationEntry[] mutableArray) {
		final List result = new ArrayList();
		for (int i = 0; i < mutableArray.length; i++)
			result.add(new AnnotationEntryGen(mutableArray[i], getConstantPool(), false));
		return result;
	}

	public void addParameterAnnotation(int parameterIndex, AnnotationEntryGen annotation) {
		ensureExistingParameterAnnotationsUnpacked();
		if (!hasParameterAnnotations) {
			param_annotations = new List[arg_types.length];
			hasParameterAnnotations = true;
		}
		final List existingAnnotations = param_annotations[parameterIndex];
		if (existingAnnotations != null)
			existingAnnotations.add(annotation);
		else {
			final List l = new ArrayList();
			l.add(annotation);
			param_annotations[parameterIndex] = l;
		}
	}

	public static BCELComparator getComparator() {
		return _cmp;
	}

	public static void setComparator(BCELComparator comparator) {
		_cmp = comparator;
	}

	@Override
	public boolean equals(Object obj) {
		return _cmp.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return _cmp.hashCode(this);
	}
}
