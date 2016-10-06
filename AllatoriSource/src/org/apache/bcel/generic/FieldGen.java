package org.apache.bcel.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.Annotations;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.ConstantObject;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Utility;
import org.apache.bcel.util.BCELComparator;

public class FieldGen extends FieldGenOrMethodGen {
	private static final long serialVersionUID = -6050448955000008261L;
	private Object value = null;
	private static BCELComparator _cmp = new BCELComparator() {
		@Override
		public boolean equals(Object o1, Object o2) {
			final FieldGen THIS = (FieldGen) o1;
			final FieldGen THAT = (FieldGen) o2;
			return (THIS.getName().equals(THAT.getName()) && THIS.getSignature().equals(THAT.getSignature()));
		}

		@Override
		public int hashCode(Object o) {
			final FieldGen THIS = (FieldGen) o;
			return THIS.getSignature().hashCode() ^ THIS.getName().hashCode();
		}
	};
	private List<FieldObserver> observers;

	public FieldGen(int access_flags, Type type, String name, ConstantPoolGen cp) {
		setAccessFlags(access_flags);
		setType(type);
		setName(name);
		setConstantPool(cp);
	}

	public FieldGen(Field field, ConstantPoolGen cp) {
		this(field.getAccessFlags(), Type.getType(field.getSignature()), field.getName(), cp);
		final Attribute[] attrs = field.getAttributes();
		for (int i = 0; i < attrs.length; i++) {
			if (attrs[i] instanceof ConstantValue)
				setValue(((ConstantValue) attrs[i]).getConstantValueIndex());
			else if (attrs[i] instanceof Annotations) {
				final Annotations runtimeAnnotations = (Annotations) attrs[i];
				final AnnotationEntry[] annotationEntries = runtimeAnnotations.getAnnotationEntries();
				for (int j = 0; j < annotationEntries.length; j++) {
					final AnnotationEntry element = annotationEntries[j];
					addAnnotationEntry(new AnnotationEntryGen(element, cp, false));
				}
			} else
				addAttribute(attrs[i]);
		}
	}

	private void setValue(int index) {
		final ConstantPool cp = this.cp.getConstantPool();
		final org.apache.bcel.classfile.Constant c = cp.getConstant(index);
		value = ((ConstantObject) c).getConstantValue(cp);
	}

	public void setInitValue(String str) {
		checkType(new ObjectType("java.lang.String"));
		if (str != null)
			value = str;
	}

	public void setInitValue(long l) {
		checkType(Type.LONG);
		if (l != 0L)
			value = new Long(l);
	}

	public void setInitValue(int i) {
		checkType(Type.INT);
		if (i != 0)
			value = Integer.valueOf(i);
	}

	public void setInitValue(short s) {
		checkType(Type.SHORT);
		if (s != 0)
			value = Integer.valueOf(s);
	}

	public void setInitValue(char c) {
		checkType(Type.CHAR);
		if (c != 0)
			value = Integer.valueOf(c);
	}

	public void setInitValue(byte b) {
		checkType(Type.BYTE);
		if (b != 0)
			value = Integer.valueOf(b);
	}

	public void setInitValue(boolean b) {
		checkType(Type.BOOLEAN);
		if (b)
			value = Integer.valueOf(1);
	}

	public void setInitValue(float f) {
		checkType(Type.FLOAT);
		if (f != 0.0)
			value = new Float(f);
	}

	public void setInitValue(double d) {
		checkType(Type.DOUBLE);
		if (d != 0.0)
			value = new Double(d);
	}

	public void cancelInitValue() {
		value = null;
	}

	private void checkType(Type atype) {
		if (type == null)
			throw new ClassGenException("You haven't defined the type of the field yet");
		if (!isFinal())
			throw new ClassGenException("Only final fields may have an initial value!");
		if (!type.equals(atype))
			throw new ClassGenException(new StringBuilder().append("Types are not compatible: ").append(type)
					.append(" vs. ").append(atype).toString());
	}

	public Field getField() {
		final String signature = getSignature();
		final int name_index = cp.addUtf8(name);
		final int signature_index = cp.addUtf8(signature);
		if (value != null) {
			checkType(type);
			final int index = addConstant();
			addAttribute(new ConstantValue(cp.addUtf8("ConstantValue"), 2, index, cp.getConstantPool()));
		}
		addAnnotationsAsAttribute(cp);
		return new Field(access_flags, name_index, signature_index, getAttributes(), cp.getConstantPool());
	}

	private void addAnnotationsAsAttribute(ConstantPoolGen cp) {
		final Attribute[] attrs = Utility.getAnnotationAttributes(cp, annotation_vec);
		for (int i = 0; i < attrs.length; i++)
			addAttribute(attrs[i]);
	}

	private int addConstant() {
		switch (type.getType()) {
		case 4:
		case 5:
		case 8:
		case 9:
		case 10:
			return cp.addInteger(((Integer) value).intValue());
		case 6:
			return cp.addFloat(((Float) value).floatValue());
		case 7:
			return cp.addDouble(((Double) value).doubleValue());
		case 11:
			return cp.addLong(((Long) value).longValue());
		case 14:
			return cp.addString((String) value);
		default:
			throw new RuntimeException(
					new StringBuilder().append("Oops: Unhandled : ").append(type.getType()).toString());
		}
	}

	@Override
	public String getSignature() {
		return type.getSignature();
	}

	public void addObserver(FieldObserver o) {
		if (observers == null)
			observers = new ArrayList<FieldObserver>();
		observers.add(o);
	}

	public void removeObserver(FieldObserver o) {
		if (observers != null)
			observers.remove(o);
	}

	public void update() {
		if (observers != null) {
			final Iterator<FieldObserver> i$ = observers.iterator();
			while (i$.hasNext()) {
				final FieldObserver observer = i$.next();
				observer.notify(this);
			}
		}
	}

	public String getInitValue() {
		if (value != null)
			return value.toString();
		return null;
	}

	@Override
	public final String toString() {
		String access = Utility.accessToString(access_flags);
		access = (access.equals("") ? "" : new StringBuilder().append(access).append(" ").toString());
		final String signature = type.toString();
		final String name = getName();
		final StringBuilder buf = new StringBuilder(32);
		buf.append(access).append(signature).append(" ").append(name);
		final String value = getInitValue();
		if (value != null)
			buf.append(" = ").append(value);
		return buf.toString();
	}

	public FieldGen copy(ConstantPoolGen cp) {
		final FieldGen fg = (FieldGen) clone();
		fg.setConstantPool(cp);
		return fg;
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
