package org.apache.bcel.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.ExceptionTable;
import org.apache.bcel.classfile.InnerClass;
import org.apache.bcel.classfile.InnerClasses;
import org.apache.bcel.classfile.LineNumber;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.SourceFile;
import org.apache.bcel.classfile.Utility;

final class AttributeHTML implements Constants {
	private String class_name;
	private PrintWriter file;
	private int attr_count = 0;
	private ConstantHTML constant_html;
	private ConstantPool constant_pool;

	AttributeHTML(String dir, String class_name, ConstantPool constant_pool, ConstantHTML constant_html)
			throws IOException {
		this.class_name = class_name;
		this.constant_pool = constant_pool;
		this.constant_html = constant_html;
		file = new PrintWriter(new FileOutputStream(
				new StringBuilder().append(dir).append(class_name).append("_attributes.html").toString()));
		file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
	}

	private final String codeLink(int link, int method_number) {
		return new StringBuilder().append("<A HREF=\"").append(class_name).append("_code.html#code")
				.append(method_number).append("@").append(link).append("\" TARGET=Code>").append(link).append("</A>")
				.toString();
	}

	final void close() {
		file.println("</TABLE></BODY></HTML>");
		file.close();
	}

	final void writeAttribute(Attribute attribute, String anchor) throws IOException {
		writeAttribute(attribute, anchor, 0);
	}

	final void writeAttribute(Attribute attribute, String anchor, int method_number) throws IOException {
		byte tag = attribute.getTag();
		if (tag != -1) {
			attr_count++;
			if (attr_count % 2 == 0)
				file.print("<TR BGCOLOR=\"#C0C0C0\"><TD>");
			else
				file.print("<TR BGCOLOR=\"#A0A0A0\"><TD>");
			file.println(new StringBuilder().append("<H4><A NAME=\"").append(anchor).append("\">").append(attr_count)
					.append(" ").append(ATTRIBUTE_NAMES[tag]).append("</A></H4>").toString());
			switch (tag) {
			case 2: {
				Code c = (Code) attribute;
				file.print(new StringBuilder().append("<UL><LI>Maximum stack size = ").append(c.getMaxStack())
						.append("</LI>\n<LI>Number of local variables = ").append(c.getMaxLocals())
						.append("</LI>\n<LI><A HREF=\"").append(class_name).append("_code.html#method")
						.append(method_number).append("\" TARGET=Code>Byte code</A></LI></UL>\n").toString());
				CodeException[] ce = c.getExceptionTable();
				int len = ce.length;
				if (len > 0) {
					file.print("<P><B>Exceptions handled</B><UL>");
					for (int i = 0; i < len; i++) {
						int catch_type = ce[i].getCatchType();
						file.print("<LI>");
						if (catch_type != 0)
							file.print(constant_html.referenceConstant(catch_type));
						else
							file.print("Any Exception");
						file.print(new StringBuilder().append("<BR>(Ranging from lines ")
								.append(codeLink(ce[i].getStartPC(), method_number)).append(" to ")
								.append(codeLink(ce[i].getEndPC(), method_number)).append(", handled at line ")
								.append(codeLink(ce[i].getHandlerPC(), method_number)).append(")</LI>").toString());
					}
					file.print("</UL>");
				}
				break;
			}
			case 1: {
				int index = ((ConstantValue) attribute).getConstantValueIndex();
				file.print(new StringBuilder().append("<UL><LI><A HREF=\"").append(class_name).append("_cp.html#cp")
						.append(index).append("\" TARGET=\"ConstantPool\">Constant value index(").append(index)
						.append(")</A></UL>\n").toString());
				break;
			}
			case 0: {
				int index = ((SourceFile) attribute).getSourceFileIndex();
				file.print(new StringBuilder().append("<UL><LI><A HREF=\"").append(class_name).append("_cp.html#cp")
						.append(index).append("\" TARGET=\"ConstantPool\">Source file index(").append(index)
						.append(")</A></UL>\n").toString());
				break;
			}
			case 3: {
				int[] indices = ((ExceptionTable) attribute).getExceptionIndexTable();
				file.print("<UL>");
				for (int i = 0; i < indices.length; i++)
					file.print(new StringBuilder().append("<LI><A HREF=\"").append(class_name).append("_cp.html#cp")
							.append(indices[i]).append("\" TARGET=\"ConstantPool\">Exception class index(")
							.append(indices[i]).append(")</A>\n").toString());
				file.print("</UL>\n");
				break;
			}
			case 4: {
				LineNumber[] line_numbers = ((LineNumberTable) attribute).getLineNumberTable();
				file.print("<P>");
				for (int i = 0; i < line_numbers.length; i++) {
					file.print(new StringBuilder().append("(").append(line_numbers[i].getStartPC()).append(",&nbsp;")
							.append(line_numbers[i].getLineNumber()).append(")").toString());
					if (i < line_numbers.length - 1)
						file.print(", ");
				}
				break;
			}
			case 5: {
				LocalVariable[] vars = ((LocalVariableTable) attribute).getLocalVariableTable();
				file.print("<UL>");
				for (int i = 0; i < vars.length; i++) {
					int index = vars[i].getSignatureIndex();
					String signature = ((ConstantUtf8) constant_pool.getConstant(index, (byte) 1)).getBytes();
					signature = Utility.signatureToString(signature, false);
					int start = vars[i].getStartPC();
					int end = start + vars[i].getLength();
					file.println(new StringBuilder().append("<LI>").append(Class2HTML.referenceType(signature))
							.append("&nbsp;<B>").append(vars[i].getName()).append("</B> in slot %")
							.append(vars[i].getIndex()).append("<BR>Valid from lines ").append("<A HREF=\"")
							.append(class_name).append("_code.html#code").append(method_number).append("@")
							.append(start).append("\" TARGET=Code>").append(start).append("</A> to ")
							.append("<A HREF=\"").append(class_name).append("_code.html#code").append(method_number)
							.append("@").append(end).append("\" TARGET=Code>").append(end).append("</A></LI>")
							.toString());
				}
				file.print("</UL>\n");
				break;
			}
			case 6: {
				InnerClass[] classes = ((InnerClasses) attribute).getInnerClasses();
				file.print("<UL>");
				for (int i = 0; i < classes.length; i++) {
					int index = classes[i].getInnerNameIndex();
					String name;
					if (index > 0)
						name = ((ConstantUtf8) constant_pool.getConstant(index, (byte) 1)).getBytes();
					else
						name = "&lt;anonymous&gt;";
					String access = Utility.accessToString(classes[i].getInnerAccessFlags());
					file.print(new StringBuilder().append("<LI><FONT COLOR=\"#FF0000\">").append(access)
							.append("</FONT> ").append(constant_html.referenceConstant(classes[i].getInnerClassIndex()))
							.append(" in&nbsp;class ")
							.append(constant_html.referenceConstant(classes[i].getOuterClassIndex())).append(" named ")
							.append(name).append("</LI>\n").toString());
				}
				file.print("</UL>\n");
				break;
			}
			default:
				file.print(new StringBuilder().append("<P>").append(attribute.toString()).toString());
			}
			file.println("</TD></TR>");
			file.flush();
		}
	}
}
