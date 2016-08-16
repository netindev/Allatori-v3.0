package org.apache.bcel.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;

final class ConstantHTML implements Constants {
	private String class_name;
	private String class_package;
	private ConstantPool constant_pool;
	private PrintWriter file;
	private String[] constant_ref;
	private Constant[] constants;
	private Method[] methods;

	ConstantHTML(String dir, String class_name, String class_package, Method[] methods, ConstantPool constant_pool)
			throws IOException {
		this.class_name = class_name;
		this.class_package = class_package;
		this.constant_pool = constant_pool;
		this.methods = methods;
		constants = constant_pool.getConstantPool();
		file = new PrintWriter(
				new FileOutputStream(new StringBuilder().append(dir).append(class_name).append("_cp.html").toString()));
		constant_ref = new String[constants.length];
		constant_ref[0] = "&lt;unknown&gt;";
		file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
		for (int i = 1; i < constants.length; i++) {
			if (i % 2 == 0)
				file.print("<TR BGCOLOR=\"#C0C0C0\"><TD>");
			else
				file.print("<TR BGCOLOR=\"#A0A0A0\"><TD>");
			if (constants[i] != null)
				writeConstant(i);
			file.print("</TD></TR>\n");
		}
		file.println("</TABLE></BODY></HTML>");
		file.close();
	}

	String referenceConstant(int index) {
		return constant_ref[index];
	}

	private void writeConstant(int index) {
		byte tag = constants[index].getTag();
		file.println(new StringBuilder().append("<H4> <A NAME=cp").append(index).append(">").append(index)
				.append("</A> ").append(CONSTANT_NAMES[tag]).append("</H4>").toString());
		switch (tag) {
		case 10:
		case 11: {
			int class_index;
			int name_index;
			if (tag == 10) {
				ConstantMethodref c = ((ConstantMethodref) constant_pool.getConstant(index, (byte) 10));
				class_index = c.getClassIndex();
				name_index = c.getNameAndTypeIndex();
			} else {
				ConstantInterfaceMethodref c1 = ((ConstantInterfaceMethodref) constant_pool.getConstant(index,
						(byte) 11));
				class_index = c1.getClassIndex();
				name_index = c1.getNameAndTypeIndex();
			}
			String method_name = constant_pool.constantToString(name_index, (byte) 12);
			String html_method_name = Class2HTML.toHTML(method_name);
			String method_class = constant_pool.constantToString(class_index, (byte) 7);
			String short_method_class = Utility.compactClassName(method_class);
			short_method_class = Utility.compactClassName(short_method_class,
					new StringBuilder().append(class_package).append(".").toString(), true);
			ConstantNameAndType c2 = ((ConstantNameAndType) constant_pool.getConstant(name_index, (byte) 12));
			String signature = constant_pool.constantToString(c2.getSignatureIndex(), (byte) 1);
			String[] args = Utility.methodSignatureArgumentTypes(signature, false);
			String type = Utility.methodSignatureReturnType(signature, false);
			String ret_type = Class2HTML.referenceType(type);
			StringBuilder buf = new StringBuilder("(");
			for (int i = 0; i < args.length; i++) {
				buf.append(Class2HTML.referenceType(args[i]));
				if (i < args.length - 1)
					buf.append(",&nbsp;");
			}
			buf.append(")");
			String arg_types = buf.toString();
			String ref;
			if (method_class.equals(class_name))
				ref = new StringBuilder().append("<A HREF=\"").append(class_name).append("_code.html#method")
						.append(getMethodNumber(new StringBuilder().append(method_name).append(signature).toString()))
						.append("\" TARGET=Code>").append(html_method_name).append("</A>").toString();
			else
				ref = new StringBuilder().append("<A HREF=\"").append(method_class).append(".html")
						.append("\" TARGET=_top>").append(short_method_class).append("</A>.").append(html_method_name)
						.toString();
			constant_ref[index] = new StringBuilder().append(ret_type).append("&nbsp;<A HREF=\"").append(class_name)
					.append("_cp.html#cp").append(class_index).append("\" TARGET=Constants>").append(short_method_class)
					.append("</A>.<A HREF=\"").append(class_name).append("_cp.html#cp").append(index)
					.append("\" TARGET=ConstantPool>").append(html_method_name).append("</A>&nbsp;").append(arg_types)
					.toString();
			file.println(new StringBuilder().append("<P><TT>").append(ret_type).append("&nbsp;").append(ref)
					.append(arg_types).append("&nbsp;</TT>\n<UL>").append("<LI><A HREF=\"#cp").append(class_index)
					.append("\">Class index(").append(class_index).append(")</A>\n").append("<LI><A HREF=\"#cp")
					.append(name_index).append("\">NameAndType index(").append(name_index).append(")</A></UL>")
					.toString());
			break;
		}
		case 9: {
			ConstantFieldref c3 = ((ConstantFieldref) constant_pool.getConstant(index, (byte) 9));
			int class_index = c3.getClassIndex();
			int name_index = c3.getNameAndTypeIndex();
			String field_class = constant_pool.constantToString(class_index, (byte) 7);
			String short_field_class = Utility.compactClassName(field_class);
			short_field_class = Utility.compactClassName(short_field_class,
					new StringBuilder().append(class_package).append(".").toString(), true);
			String field_name = constant_pool.constantToString(name_index, (byte) 12);
			String ref;
			if (field_class.equals(class_name))
				ref = new StringBuilder().append("<A HREF=\"").append(field_class).append("_methods.html#field")
						.append(field_name).append("\" TARGET=Methods>").append(field_name).append("</A>").toString();
			else
				ref = new StringBuilder().append("<A HREF=\"").append(field_class).append(".html\" TARGET=_top>")
						.append(short_field_class).append("</A>.").append(field_name).append("\n").toString();
			constant_ref[index] = new StringBuilder().append("<A HREF=\"").append(class_name).append("_cp.html#cp")
					.append(class_index).append("\" TARGET=Constants>").append(short_field_class)
					.append("</A>.<A HREF=\"").append(class_name).append("_cp.html#cp").append(index)
					.append("\" TARGET=ConstantPool>").append(field_name).append("</A>").toString();
			file.println(new StringBuilder().append("<P><TT>").append(ref).append("</TT><BR>\n").append("<UL>")
					.append("<LI><A HREF=\"#cp").append(class_index).append("\">Class(").append(class_index)
					.append(")</A><BR>\n").append("<LI><A HREF=\"#cp").append(name_index).append("\">NameAndType(")
					.append(name_index).append(")</A></UL>").toString());
			break;
		}
		case 7: {
			ConstantClass c4 = (ConstantClass) constant_pool.getConstant(index, (byte) 7);
			int name_index = c4.getNameIndex();
			String class_name2 = constant_pool.constantToString(index, tag);
			String short_class_name = Utility.compactClassName(class_name2);
			short_class_name = Utility.compactClassName(short_class_name,
					new StringBuilder().append(class_package).append(".").toString(), true);
			String ref = new StringBuilder().append("<A HREF=\"").append(class_name2).append(".html\" TARGET=_top>")
					.append(short_class_name).append("</A>").toString();
			constant_ref[index] = new StringBuilder().append("<A HREF=\"").append(class_name).append("_cp.html#cp")
					.append(index).append("\" TARGET=ConstantPool>").append(short_class_name).append("</A>").toString();
			file.println(new StringBuilder().append("<P><TT>").append(ref).append("</TT><UL>")
					.append("<LI><A HREF=\"#cp").append(name_index).append("\">Name index(").append(name_index)
					.append(")</A></UL>\n").toString());
			break;
		}
		case 8: {
			ConstantString c5 = (ConstantString) constant_pool.getConstant(index, (byte) 8);
			int name_index = c5.getStringIndex();
			String str = Class2HTML.toHTML(constant_pool.constantToString(index, tag));
			file.println(new StringBuilder().append("<P><TT>").append(str).append("</TT><UL>")
					.append("<LI><A HREF=\"#cp").append(name_index).append("\">Name index(").append(name_index)
					.append(")</A></UL>\n").toString());
			break;
		}
		case 12: {
			ConstantNameAndType c6 = ((ConstantNameAndType) constant_pool.getConstant(index, (byte) 12));
			int name_index = c6.getNameIndex();
			int signature_index = c6.getSignatureIndex();
			file.println(new StringBuilder().append("<P><TT>")
					.append(Class2HTML.toHTML(constant_pool.constantToString(index, tag))).append("</TT><UL>")
					.append("<LI><A HREF=\"#cp").append(name_index).append("\">Name index(").append(name_index)
					.append(")</A>\n").append("<LI><A HREF=\"#cp").append(signature_index).append("\">Signature index(")
					.append(signature_index).append(")</A></UL>\n").toString());
			break;
		}
		default:
			file.println(new StringBuilder().append("<P><TT>")
					.append(Class2HTML.toHTML(constant_pool.constantToString(index, tag))).append("</TT>\n")
					.toString());
		}
	}

	private final int getMethodNumber(String str) {
		for (int i = 0; i < methods.length; i++) {
			String cmp = new StringBuilder().append(methods[i].getName()).append(methods[i].getSignature()).toString();
			if (cmp.equals(str))
				return i;
		}
		return -1;
	}
}
