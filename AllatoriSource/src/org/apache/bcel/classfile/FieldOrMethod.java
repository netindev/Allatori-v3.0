/* FieldOrMethod - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class FieldOrMethod extends AccessFlags implements Cloneable, Node {
	private static final long serialVersionUID = -1833306330869469714L;
	protected int name_index;
	protected int signature_index;
	protected int attributes_count;
	protected Attribute[] attributes;
	protected AnnotationEntry[] annotationEntries;
	protected ConstantPool constant_pool;
	private String signatureAttributeString = null;
	private boolean searchedForSignatureAttribute = false;
	private boolean annotationsOutOfDate = true;

	FieldOrMethod() {
		/* empty */
	}

	protected FieldOrMethod(FieldOrMethod c) {
		this(c.getAccessFlags(), c.getNameIndex(), c.getSignatureIndex(), c.getAttributes(), c.getConstantPool());
	}

	protected FieldOrMethod(DataInputStream file, ConstantPool constant_pool) throws IOException, ClassFormatException {
		this(file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort(), null, constant_pool);
		attributes_count = file.readUnsignedShort();
		attributes = new Attribute[attributes_count];
		for (int i = 0; i < attributes_count; i++)
			attributes[i] = Attribute.readAttribute(file, constant_pool);
	}

	protected FieldOrMethod(int access_flags, int name_index, int signature_index, Attribute[] attributes,
			ConstantPool constant_pool) {
		this.access_flags = access_flags;
		this.name_index = name_index;
		this.signature_index = signature_index;
		this.constant_pool = constant_pool;
		setAttributes(attributes);
	}

	public final void dump(DataOutputStream file) throws IOException {
		file.writeShort(access_flags);
		file.writeShort(name_index);
		file.writeShort(signature_index);
		file.writeShort(attributes_count);
		for (int i = 0; i < attributes_count; i++)
			attributes[i].dump(file);
	}

	public final Attribute[] getAttributes() {
		return attributes;
	}

	public final void setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
		attributes_count = attributes == null ? 0 : attributes.length;
	}

	public final ConstantPool getConstantPool() {
		return constant_pool;
	}

	public final void setConstantPool(ConstantPool constant_pool) {
		this.constant_pool = constant_pool;
	}

	public final int getNameIndex() {
		return name_index;
	}

	public final void setNameIndex(int name_index) {
		this.name_index = name_index;
	}

	public final int getSignatureIndex() {
		return signature_index;
	}

	public final void setSignatureIndex(int signature_index) {
		this.signature_index = signature_index;
	}

	public final String getName() {
		final ConstantUtf8 c = (ConstantUtf8) constant_pool.getConstant(name_index, (byte) 1);
		return c.getBytes();
	}

	public final String getSignature() {
		final ConstantUtf8 c = ((ConstantUtf8) constant_pool.getConstant(signature_index, (byte) 1));
		return c.getBytes();
	}

	protected FieldOrMethod copy_(ConstantPool _constant_pool) {
		FieldOrMethod c = null;
		try {
			c = (FieldOrMethod) this.clone();
		} catch (final CloneNotSupportedException clonenotsupportedexception) {
			/* empty */
		}
		c.constant_pool = constant_pool;
		c.attributes = new Attribute[attributes_count];
		for (int i = 0; i < attributes_count; i++)
			c.attributes[i] = attributes[i].copy(constant_pool);
		return c;
	}

	private void ensureAnnotationsUpToDate() {
		if (annotationsOutOfDate) {
			final Attribute[] attrs = getAttributes();
			final java.util.List accumulatedAnnotations = new ArrayList();
			for (int i = 0; i < attrs.length; i++) {
				final Attribute attribute = attrs[i];
				if (attribute instanceof Annotations) {
					final Annotations annotations = (Annotations) attribute;
					for (int j = 0; j < annotations.getAnnotationEntries().length; j++)
						accumulatedAnnotations.add(annotations.getAnnotationEntries()[j]);
				}
			}
			annotationEntries = ((AnnotationEntry[]) accumulatedAnnotations
					.toArray(new AnnotationEntry[accumulatedAnnotations.size()]));
			annotationsOutOfDate = false;
		}
	}

	public AnnotationEntry[] getAnnotationEntries() {
		ensureAnnotationsUpToDate();
		return annotationEntries;
	}

	public void addAnnotationEntry(AnnotationEntry a) {
		ensureAnnotationsUpToDate();
		final int len = annotationEntries.length;
		final AnnotationEntry[] newAnnotations = new AnnotationEntry[len + 1];
		System.arraycopy(annotationEntries, 0, newAnnotations, 0, len);
		newAnnotations[len] = a;
		annotationEntries = newAnnotations;
	}

	public final String getGenericSignature() {
		if (!searchedForSignatureAttribute) {
			boolean found = false;
			for (int i = 0; !found && i < attributes_count; i++) {
				if (attributes[i] instanceof Signature) {
					signatureAttributeString = ((Signature) attributes[i]).getSignature();
					found = true;
				}
			}
			searchedForSignatureAttribute = true;
		}
		return signatureAttributeString;
	}
}
