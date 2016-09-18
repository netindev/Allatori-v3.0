package org.apache.bcel.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.ExceptionTable;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;

final class MethodHTML implements Constants {
	private final String class_name;
	private final PrintWriter file;
	private final ConstantHTML constant_html;
	private final AttributeHTML attribute_html;

	MethodHTML(String dir, String class_name, Method[] methods, Field[] fields, ConstantHTML constant_html,
			AttributeHTML attribute_html) throws IOException {
		this.class_name = class_name;
		this.attribute_html = attribute_html;
		this.constant_html = constant_html;
		file = new PrintWriter(new FileOutputStream(
				new StringBuilder().append(dir).append(class_name).append("_methods.html").toString()));
		file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
		file.println(
				"<TR><TH ALIGN=LEFT>Access&nbsp;flags</TH><TH ALIGN=LEFT>Type</TH><TH ALIGN=LEFT>Field&nbsp;name</TH></TR>");
		for (int i = 0; i < fields.length; i++)
			writeField(fields[i]);
		file.println("</TABLE>");
		file.println(
				"<TABLE BORDER=0><TR><TH ALIGN=LEFT>Access&nbsp;flags</TH><TH ALIGN=LEFT>Return&nbsp;type</TH><TH ALIGN=LEFT>Method&nbsp;name</TH><TH ALIGN=LEFT>Arguments</TH></TR>");
		for (int i = 0; i < methods.length; i++)
			writeMethod(methods[i], i);
		file.println("</TABLE></BODY></HTML>");
		file.close();
	}

	private void writeField(Field field) throws IOException {
		final String type = Utility.signatureToString(field.getSignature());
		final String name = field.getName();
		String access = Utility.accessToString(field.getAccessFlags());
		access = Utility.replace(access, " ", "&nbsp;");
		file.print(new StringBuilder().append("<TR><TD><FONT COLOR=\"#FF0000\">").append(access)
				.append("</FONT></TD>\n<TD>").append(Class2HTML.referenceType(type)).append("</TD><TD><A NAME=\"field")
				.append(name).append("\">").append(name).append("</A></TD>").toString());
		final Attribute[] attributes = field.getAttributes();
		for (int i = 0; i < attributes.length; i++)
			attribute_html.writeAttribute(attributes[i],
					new StringBuilder().append(name).append("@").append(i).toString());
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].getTag() == 1) {
				final String str = ((ConstantValue) attributes[i]).toString();
				file.print(new StringBuilder().append("<TD>= <A HREF=\"").append(class_name).append("_attributes.html#")
						.append(name).append("@").append(i).append("\" TARGET=\"Attributes\">").append(str)
						.append("</TD>\n").toString());
				break;
			}
		}
		file.println("</TR>");
	}

	private final void writeMethod(Method method, int method_number) throws IOException {
		final String signature = method.getSignature();
		final String[] args = Utility.methodSignatureArgumentTypes(signature, false);
		final String type = Utility.methodSignatureReturnType(signature, false);
		final String name = method.getName();
		String access = Utility.accessToString(method.getAccessFlags());
		final Attribute[] attributes = method.getAttributes();
		access = Utility.replace(access, " ", "&nbsp;");
		final String html_name = Class2HTML.toHTML(name);
		file.print(new StringBuilder().append("<TR VALIGN=TOP><TD><FONT COLOR=\"#FF0000\"><A NAME=method")
				.append(method_number).append(">").append(access).append("</A></FONT></TD>").toString());
		file.print(new StringBuilder().append("<TD>").append(Class2HTML.referenceType(type)).append("</TD><TD>")
				.append("<A HREF=").append(class_name).append("_code.html#method").append(method_number)
				.append(" TARGET=Code>").append(html_name).append("</A></TD>\n<TD>(").toString());
		for (int i = 0; i < args.length; i++) {
			file.print(Class2HTML.referenceType(args[i]));
			if (i < args.length - 1)
				file.print(", ");
		}
		file.print(")</TD></TR>");
		for (int i = 0; i < attributes.length; i++) {
			attribute_html.writeAttribute(attributes[i],
					new StringBuilder().append("method").append(method_number).append("@").append(i).toString(),
					method_number);
			final byte tag = attributes[i].getTag();
			if (tag == 3) {
				file.print("<TR VALIGN=TOP><TD COLSPAN=2></TD><TH ALIGN=LEFT>throws</TH><TD>");
				final int[] exceptions = ((ExceptionTable) attributes[i]).getExceptionIndexTable();
				for (int j = 0; j < exceptions.length; j++) {
					file.print(constant_html.referenceConstant(exceptions[j]));
					if (j < exceptions.length - 1)
						file.print(", ");
				}
				file.println("</TD></TR>");
			} else if (tag == 2) {
				final Attribute[] c_a = ((Code) attributes[i]).getAttributes();
				for (int j = 0; j < c_a.length; j++)
					attribute_html.writeAttribute(c_a[j], new StringBuilder().append("method").append(method_number)
							.append("@").append(i).append("@").append(j).toString(), method_number);
			}
		}
	}
}
