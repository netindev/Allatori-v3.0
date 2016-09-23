package org.apache.bcel.generic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ElementValuePair;

public class AnnotationEntryGen {
	public void setTypeIndex(int typeIndex) {
		this.typeIndex = typeIndex;
	}

	private int typeIndex;
	private List<ElementValuePairGen> evs;
	private final ConstantPoolGen cpool;
	private boolean isRuntimeVisible = false;

	public AnnotationEntryGen(AnnotationEntry a, ConstantPoolGen cpool, boolean copyPoolEntries) {
		this.cpool = cpool;
		if (copyPoolEntries)
			typeIndex = cpool.addUtf8(a.getAnnotationType());
		else
			typeIndex = a.getAnnotationTypeIndex();
		isRuntimeVisible = a.isRuntimeVisible();
		evs = copyValues(a.getElementValuePairs(), cpool, copyPoolEntries);
	}

	private List<ElementValuePairGen> copyValues(ElementValuePair[] in, ConstantPoolGen cpool, boolean copyPoolEntries) {
		final List<ElementValuePairGen> out = new ArrayList<ElementValuePairGen>();
		final int l = in.length;
		for (int i = 0; i < l; i++) {
			final ElementValuePair nvp = in[i];
			out.add(new ElementValuePairGen(nvp, cpool, copyPoolEntries));
		}
		return out;
	}

	private AnnotationEntryGen(ConstantPoolGen cpool) {
		this.cpool = cpool;
	}

	public AnnotationEntry getAnnotation() {
		final AnnotationEntry a = new AnnotationEntry(typeIndex, cpool.getConstantPool(), isRuntimeVisible);
		final Iterator<ElementValuePairGen> i$ = evs.iterator();
		while (i$.hasNext()) {
			final ElementValuePairGen element = (ElementValuePairGen) i$.next();
			a.addElementNameValuePair(element.getElementNameValuePair());
		}
		return a;
	}

	public AnnotationEntryGen(ObjectType type, List<ElementValuePairGen> elements, boolean vis, ConstantPoolGen cpool) {
		this.cpool = cpool;
		typeIndex = cpool.addUtf8(type.getSignature());
		evs = elements;
		isRuntimeVisible = vis;
	}

	public static AnnotationEntryGen read(DataInputStream dis, ConstantPoolGen cpool, boolean b) throws IOException {
		final AnnotationEntryGen a = new AnnotationEntryGen(cpool);
		a.typeIndex = dis.readUnsignedShort();
		final int elemValuePairCount = dis.readUnsignedShort();
		for (int i = 0; i < elemValuePairCount; i++) {
			final int nidx = dis.readUnsignedShort();
			a.addElementNameValuePair(
					new ElementValuePairGen(nidx, ElementValueGen.readElementValue(dis, cpool), cpool));
		}
		a.isRuntimeVisible(b);
		return a;
	}

	public void dump(DataOutputStream dos) throws IOException {
		dos.writeShort(typeIndex);
		dos.writeShort(evs.size());
		for (int i = 0; i < evs.size(); i++) {
			final ElementValuePairGen envp = (ElementValuePairGen) evs.get(i);
			envp.dump(dos);
		}
	}

	public void addElementNameValuePair(ElementValuePairGen evp) {
		if (evs == null)
			evs = new ArrayList<ElementValuePairGen>();
		evs.add(evp);
	}

	public int getTypeIndex() {
		return typeIndex;
	}

	public final String getTypeSignature() {
		final ConstantUtf8 utf8 = (ConstantUtf8) cpool.getConstant(typeIndex);
		return utf8.getBytes();
	}

	public final String getTypeName() {
		return getTypeSignature();
	}

	public List<ElementValuePairGen> getValues() {
		return evs;
	}

	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder(32);
		s.append(new StringBuilder().append("AnnotationGen:[").append(getTypeName()).append(" #").append(evs.size())
				.append(" {").toString());
		for (int i = 0; i < evs.size(); i++) {
			s.append(evs.get(i));
			if (i + 1 < evs.size())
				s.append(",");
		}
		s.append("}]");
		return s.toString();
	}

	public String toShortString() {
		final StringBuilder s = new StringBuilder();
		s.append(new StringBuilder().append("@").append(getTypeName()).append("(").toString());
		for (int i = 0; i < evs.size(); i++) {
			s.append(evs.get(i));
			if (i + 1 < evs.size())
				s.append(",");
		}
		s.append(")");
		return s.toString();
	}

	private void isRuntimeVisible(boolean b) {
		isRuntimeVisible = b;
	}

	public boolean isRuntimeVisible() {
		return isRuntimeVisible;
	}
}
