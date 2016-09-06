/* CodeHTML - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.BitSet;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;

final class CodeHTML implements Constants {
	private final String class_name;
	private final PrintWriter file;
	private BitSet goto_set;
	private final ConstantPool constant_pool;
	private final ConstantHTML constant_html;
	private static boolean wide = false;

	CodeHTML(String dir, String class_name, Method[] methods, ConstantPool constant_pool, ConstantHTML constant_html)
			throws IOException {
		this.class_name = class_name;
		this.constant_pool = constant_pool;
		this.constant_html = constant_html;
		file = new PrintWriter(new FileOutputStream(
				new StringBuilder().append(dir).append(class_name).append("_code.html").toString()));
		file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\">");
		for (int i = 0; i < methods.length; i++)
			writeMethod(methods[i], i);
		file.println("</BODY></HTML>");
		file.close();
	}

	private final String codeToHTML(ByteSequence bytes, int method_number) throws IOException {
		final short opcode = (short) bytes.readUnsignedByte();
		int default_offset = 0;
		int no_pad_bytes = 0;
		final StringBuilder buf = new StringBuilder(256);
		buf.append("<TT>").append(OPCODE_NAMES[opcode]).append("</TT></TD><TD>");
		if (opcode == 170 || opcode == 171) {
			final int remainder = bytes.getIndex() % 4;
			no_pad_bytes = remainder == 0 ? 0 : 4 - remainder;
			for (int i = 0; i < no_pad_bytes; i++)
				bytes.readByte();
			default_offset = bytes.readInt();
		}
		switch (opcode) {
		case 170: {
			final int low = bytes.readInt();
			final int high = bytes.readInt();
			final int offset = bytes.getIndex() - 12 - no_pad_bytes - 1;
			default_offset += offset;
			buf.append("<TABLE BORDER=1><TR>");
			final int[] jump_table = new int[high - low + 1];
			for (int i = 0; i < jump_table.length; i++) {
				jump_table[i] = offset + bytes.readInt();
				buf.append("<TH>").append(low + i).append("</TH>");
			}
			buf.append("<TH>default</TH></TR>\n<TR>");
			for (int i = 0; i < jump_table.length; i++)
				buf.append("<TD><A HREF=\"#code").append(method_number).append("@").append(jump_table[i]).append("\">")
						.append(jump_table[i]).append("</A></TD>");
			buf.append("<TD><A HREF=\"#code").append(method_number).append("@").append(default_offset).append("\">")
					.append(default_offset).append("</A></TD></TR>\n</TABLE>\n");
			break;
		}
		case 171: {
			final int npairs = bytes.readInt();
			final int offset = bytes.getIndex() - 8 - no_pad_bytes - 1;
			final int[] jump_table = new int[npairs];
			default_offset += offset;
			buf.append("<TABLE BORDER=1><TR>");
			for (int i = 0; i < npairs; i++) {
				final int match = bytes.readInt();
				jump_table[i] = offset + bytes.readInt();
				buf.append("<TH>").append(match).append("</TH>");
			}
			buf.append("<TH>default</TH></TR>\n<TR>");
			for (int i = 0; i < npairs; i++)
				buf.append("<TD><A HREF=\"#code").append(method_number).append("@").append(jump_table[i]).append("\">")
						.append(jump_table[i]).append("</A></TD>");
			buf.append("<TD><A HREF=\"#code").append(method_number).append("@").append(default_offset).append("\">")
					.append(default_offset).append("</A></TD></TR>\n</TABLE>\n");
			break;
		}
		case 153:
		case 154:
		case 155:
		case 156:
		case 157:
		case 158:
		case 159:
		case 160:
		case 161:
		case 162:
		case 163:
		case 164:
		case 165:
		case 166:
		case 167:
		case 168:
		case 198:
		case 199: {
			final int index = bytes.getIndex() + bytes.readShort() - 1;
			buf.append("<A HREF=\"#code").append(method_number).append("@").append(index).append("\">").append(index)
					.append("</A>");
			break;
		}
		case 200:
		case 201: {
			final int windex = bytes.getIndex() + bytes.readInt() - 1;
			buf.append("<A HREF=\"#code").append(method_number).append("@").append(windex).append("\">").append(windex)
					.append("</A>");
			break;
		}
		case 21:
		case 22:
		case 23:
		case 24:
		case 25:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 169: {
			int vindex;
			if (wide) {
				vindex = bytes.readShort();
				wide = false;
			} else
				vindex = bytes.readUnsignedByte();
			buf.append("%").append(vindex);
			break;
		}
		case 196:
			wide = true;
			buf.append("(wide)");
			break;
		case 188:
			buf.append("<FONT COLOR=\"#00FF00\">").append(TYPE_NAMES[bytes.readByte()]).append("</FONT>");
			break;
		case 178:
		case 179:
		case 180:
		case 181: {
			int index = bytes.readShort();
			final ConstantFieldref c1 = ((ConstantFieldref) constant_pool.getConstant(index, (byte) 9));
			final int class_index = c1.getClassIndex();
			String name = constant_pool.getConstantString(class_index, (byte) 7);
			name = Utility.compactClassName(name, false);
			index = c1.getNameAndTypeIndex();
			final String field_name = constant_pool.constantToString(index, (byte) 12);
			if (name.equals(class_name))
				buf.append("<A HREF=\"").append(class_name).append("_methods.html#field").append(field_name)
						.append("\" TARGET=Methods>").append(field_name).append("</A>\n");
			else
				buf.append(constant_html.referenceConstant(class_index)).append(".").append(field_name);
			break;
		}
		case 187:
		case 192:
		case 193: {
			final int index = bytes.readShort();
			buf.append(constant_html.referenceConstant(index));
			break;
		}
		case 182:
		case 183:
		case 184:
		case 185: {
			final int m_index = bytes.readShort();
			int class_index;
			int index;
			if (opcode == 185) {
				bytes.readUnsignedByte();
				bytes.readUnsignedByte();
				final ConstantInterfaceMethodref c = ((ConstantInterfaceMethodref) constant_pool.getConstant(m_index,
						(byte) 11));
				class_index = c.getClassIndex();
				constant_pool.constantToString(c);
				index = c.getNameAndTypeIndex();
			} else {
				final ConstantMethodref c = ((ConstantMethodref) constant_pool.getConstant(m_index, (byte) 10));
				class_index = c.getClassIndex();
				constant_pool.constantToString(c);
				index = c.getNameAndTypeIndex();
			}
			final String name = Class2HTML.referenceClass(class_index);
			final String str = Class2HTML
					.toHTML(constant_pool.constantToString(constant_pool.getConstant(index, (byte) 12)));
			final ConstantNameAndType c2 = ((ConstantNameAndType) constant_pool.getConstant(index, (byte) 12));
			final String signature = constant_pool.constantToString(c2.getSignatureIndex(), (byte) 1);
			final String[] args = Utility.methodSignatureArgumentTypes(signature, false);
			final String type = Utility.methodSignatureReturnType(signature, false);
			buf.append(name).append(".<A HREF=\"").append(class_name).append("_cp.html#cp").append(m_index)
					.append("\" TARGET=ConstantPool>").append(str).append("</A>").append("(");
			for (int i = 0; i < args.length; i++) {
				buf.append(Class2HTML.referenceType(args[i]));
				if (i < args.length - 1)
					buf.append(", ");
			}
			buf.append("):").append(Class2HTML.referenceType(type));
			break;
		}
		case 19:
		case 20: {
			final int index = bytes.readShort();
			buf.append("<A HREF=\"").append(class_name).append("_cp.html#cp").append(index)
					.append("\" TARGET=\"ConstantPool\">")
					.append(Class2HTML
							.toHTML(constant_pool.constantToString(index, constant_pool.getConstant(index).getTag())))
					.append("</a>");
			break;
		}
		case 18: {
			final int index = bytes.readUnsignedByte();
			buf.append("<A HREF=\"").append(class_name).append("_cp.html#cp").append(index)
					.append("\" TARGET=\"ConstantPool\">")
					.append(Class2HTML
							.toHTML(constant_pool.constantToString(index, constant_pool.getConstant(index).getTag())))
					.append("</a>");
			break;
		}
		case 189: {
			final int index = bytes.readShort();
			buf.append(constant_html.referenceConstant(index));
			break;
		}
		case 197: {
			final int index = bytes.readShort();
			final int dimensions = bytes.readByte();
			buf.append(constant_html.referenceConstant(index)).append(":").append(dimensions).append("-dimensional");
			break;
		}
		case 132: {
			int vindex;
			int constant;
			if (wide) {
				vindex = bytes.readShort();
				constant = bytes.readShort();
				wide = false;
			} else {
				vindex = bytes.readUnsignedByte();
				constant = bytes.readByte();
			}
			buf.append("%").append(vindex).append(" ").append(constant);
			break;
		}
		default:
			if (NO_OF_OPERANDS[opcode] > 0) {
				for (int i = 0; i < TYPE_OF_OPERANDS[opcode].length; i++) {
					switch (TYPE_OF_OPERANDS[opcode][i]) {
					case 8:
						buf.append(bytes.readUnsignedByte());
						break;
					case 9:
						buf.append(bytes.readShort());
						break;
					case 10:
						buf.append(bytes.readInt());
						break;
					default:
						System.err.println("Unreachable default case reached!");
						System.exit(-1);
					}
					buf.append("&nbsp;");
				}
			}
		}
		buf.append("</TD>");
		return buf.toString();
	}

	private final void findGotos(ByteSequence bytes, Method method, Code code) throws IOException {
		goto_set = new BitSet(bytes.available());
		if (code != null) {
			final CodeException[] ce = code.getExceptionTable();
			final int len = ce.length;
			for (int i = 0; i < len; i++) {
				goto_set.set(ce[i].getStartPC());
				goto_set.set(ce[i].getEndPC());
				goto_set.set(ce[i].getHandlerPC());
			}
			final Attribute[] attributes = code.getAttributes();
			for (int i = 0; i < attributes.length; i++) {
				if (attributes[i].getTag() == 5) {
					final LocalVariable[] vars = ((LocalVariableTable) attributes[i]).getLocalVariableTable();
					for (int j = 0; j < vars.length; j++) {
						final int start = vars[j].getStartPC();
						final int end = start + vars[j].getLength();
						goto_set.set(start);
						goto_set.set(end);
					}
					break;
				}
			}
		}
		while (bytes.available() > 0) {
			final int opcode = bytes.readUnsignedByte();
			switch (opcode) {
			case 170:
			case 171: {
				final int remainder = bytes.getIndex() % 4;
				final int no_pad_bytes = remainder == 0 ? 0 : 4 - remainder;
				for (int j = 0; j < no_pad_bytes; j++)
					bytes.readByte();
				int default_offset = bytes.readInt();
				if (opcode == 170) {
					final int low = bytes.readInt();
					final int high = bytes.readInt();
					final int offset = bytes.getIndex() - 12 - no_pad_bytes - 1;
					default_offset += offset;
					goto_set.set(default_offset);
					for (int j = 0; j < high - low + 1; j++) {
						final int index = offset + bytes.readInt();
						goto_set.set(index);
					}
				} else {
					final int npairs = bytes.readInt();
					final int offset = bytes.getIndex() - 8 - no_pad_bytes - 1;
					default_offset += offset;
					goto_set.set(default_offset);
					for (int j = 0; j < npairs; j++) {
						bytes.readInt();
						final int index = offset + bytes.readInt();
						goto_set.set(index);
					}
				}
				break;
			}
			case 153:
			case 154:
			case 155:
			case 156:
			case 157:
			case 158:
			case 159:
			case 160:
			case 161:
			case 162:
			case 163:
			case 164:
			case 165:
			case 166:
			case 167:
			case 168:
			case 198:
			case 199: {
				final int index = bytes.getIndex() + bytes.readShort() - 1;
				goto_set.set(index);
				break;
			}
			case 200:
			case 201: {
				final int index = bytes.getIndex() + bytes.readInt() - 1;
				goto_set.set(index);
				break;
			}
			default:
				bytes.unreadByte();
				codeToHTML(bytes, 0);
			}
		}
	}

	private void writeMethod(Method method, int method_number) throws IOException {
		final String signature = method.getSignature();
		final String[] args = Utility.methodSignatureArgumentTypes(signature, false);
		final String type = Utility.methodSignatureReturnType(signature, false);
		final String name = method.getName();
		final String html_name = Class2HTML.toHTML(name);
		String access = Utility.accessToString(method.getAccessFlags());
		access = Utility.replace(access, " ", "&nbsp;");
		final Attribute[] attributes = method.getAttributes();
		file.print(new StringBuilder().append("<P><B><FONT COLOR=\"#FF0000\">").append(access).append("</FONT>&nbsp;")
				.append("<A NAME=method").append(method_number).append(">").append(Class2HTML.referenceType(type))
				.append("</A>&nbsp<A HREF=\"").append(class_name).append("_methods.html#method").append(method_number)
				.append("\" TARGET=Methods>").append(html_name).append("</A>(").toString());
		for (int i = 0; i < args.length; i++) {
			file.print(Class2HTML.referenceType(args[i]));
			if (i < args.length - 1)
				file.print(",&nbsp;");
		}
		file.println(")</B></P>");
		Code c = null;
		byte[] code = null;
		if (attributes.length > 0) {
			file.print("<H4>Attributes</H4><UL>\n");
			for (int i = 0; i < attributes.length; i++) {
				byte tag = attributes[i].getTag();
				if (tag != -1)
					file.print(new StringBuilder().append("<LI><A HREF=\"").append(class_name)
							.append("_attributes.html#method").append(method_number).append("@").append(i)
							.append("\" TARGET=Attributes>").append(ATTRIBUTE_NAMES[tag]).append("</A></LI>\n")
							.toString());
				else
					file.print(new StringBuilder().append("<LI>").append(attributes[i]).append("</LI>").toString());
				if (tag == 2) {
					c = (Code) attributes[i];
					final Attribute[] attributes2 = c.getAttributes();
					code = c.getCode();
					file.print("<UL>");
					for (int j = 0; j < attributes2.length; j++) {
						tag = attributes2[j].getTag();
						file.print(new StringBuilder().append("<LI><A HREF=\"").append(class_name)
								.append("_attributes.html#").append("method").append(method_number).append("@")
								.append(i).append("@").append(j).append("\" TARGET=Attributes>")
								.append(ATTRIBUTE_NAMES[tag]).append("</A></LI>\n").toString());
					}
					file.print("</UL>");
				}
			}
			file.println("</UL>");
		}
		if (code != null) {
			final ByteSequence stream = new ByteSequence(code);
			stream.mark(stream.available());
			findGotos(stream, method, c);
			stream.reset();
			file.println(
					"<TABLE BORDER=0><TR><TH ALIGN=LEFT>Byte<BR>offset</TH><TH ALIGN=LEFT>Instruction</TH><TH ALIGN=LEFT>Argument</TH>");
			while (stream.available() > 0) {
				final int offset = stream.getIndex();
				final String str = codeToHTML(stream, method_number);
				String anchor = "";
				if (goto_set.get(offset))
					anchor = new StringBuilder().append("<A NAME=code").append(method_number).append("@").append(offset)
							.append("></A>").toString();
				String anchor2;
				if (stream.getIndex() == code.length)
					anchor2 = new StringBuilder().append("<A NAME=code").append(method_number).append("@")
							.append(code.length).append(">").append(offset).append("</A>").toString();
				else
					anchor2 = new StringBuilder().append("").append(offset).toString();
				file.println(new StringBuilder().append("<TR VALIGN=TOP><TD>").append(anchor2).append("</TD><TD>")
						.append(anchor).append(str).append("</TR>").toString());
			}
			file.println("<TR><TD> </A></TD></TR>");
			file.println("</TABLE>");
		}
	}
}
