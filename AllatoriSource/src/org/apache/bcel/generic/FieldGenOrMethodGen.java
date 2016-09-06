package org.apache.bcel.generic;

import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.classfile.AccessFlags;
import org.apache.bcel.classfile.Attribute;

public abstract class FieldGenOrMethodGen extends AccessFlags implements NamedAndTyped, Cloneable {
	private static final long serialVersionUID = -2549303846821589647L;
	protected String name;
	protected Type type;
	protected ConstantPoolGen cp;
	private final List<Attribute> attribute_vec = new ArrayList<Attribute>();
	protected List<AnnotationEntryGen> annotation_vec = new ArrayList<AnnotationEntryGen>();

	protected FieldGenOrMethodGen() {
		/* empty */
	}

	@Override
	public void setType(Type type) {
		if (type.getType() == 16)
			throw new IllegalArgumentException(new StringBuilder().append("Type can not be ").append(type).toString());
		this.type = type;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public ConstantPoolGen getConstantPool() {
		return cp;
	}

	public void setConstantPool(ConstantPoolGen cp) {
		this.cp = cp;
	}

	public void addAttribute(Attribute a) {
		attribute_vec.add(a);
	}

	public void addAnnotationEntry(AnnotationEntryGen ag) {
		annotation_vec.add(ag);
	}

	public void removeAttribute(Attribute a) {
		attribute_vec.remove(a);
	}

	public void removeAnnotationEntry(AnnotationEntryGen ag) {
		annotation_vec.remove(ag);
	}

	public void removeAttributes() {
		attribute_vec.clear();
	}

	public void removeAnnotationEntries() {
		annotation_vec.clear();
	}

	public Attribute[] getAttributes() {
		final Attribute[] attributes = new Attribute[attribute_vec.size()];
		attribute_vec.toArray(attributes);
		return attributes;
	}

	public AnnotationEntryGen[] getAnnotationEntries() {
		final AnnotationEntryGen[] annotations = new AnnotationEntryGen[annotation_vec.size()];
		annotation_vec.toArray(annotations);
		return annotations;
	}

	public abstract String getSignature();

	@Override
	public Object clone() {
		Object object;
		try {
			object = super.clone();
		} catch (final CloneNotSupportedException e) {
			System.err.println(e);
			return null;
		}
		return object;
	}
}
