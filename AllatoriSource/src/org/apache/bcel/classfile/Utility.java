package org.apache.bcel.classfile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterReader;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.AnnotationEntryGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.util.ByteSequence;

public abstract class Utility {
	private static ThreadLocal<Object> consumed_chars = new ThreadLocal<Object>() {

		@Override
		protected Object initialValue() {
			return new Integer(0);
		}
	};
	private static boolean wide = false;
	static int[] CHAR_MAP = new int[48];
	static int[] MAP_CHAR = new int[256];

	private static class JavaWriter extends FilterWriter {
		public JavaWriter(Writer out) {
			super(out);
		}

		@Override
		public void write(int b) throws IOException {
			if (isJavaIdentifierPart((char) b) && b != 36)
				out.write(b);
			else {
				out.write('$');
				if (b >= 0 && b < 48)
					out.write(Utility.CHAR_MAP[b]);
				else {
					final char[] tmp = Integer.toHexString(b).toCharArray();
					if (tmp.length == 1) {
						out.write('0');
						out.write(tmp[0]);
					} else {
						out.write(tmp[0]);
						out.write(tmp[1]);
					}
				}
			}
		}

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			for (int i = 0; i < len; i++)
				write(cbuf[off + i]);
		}

		@Override
		public void write(String str, int off, int len) throws IOException {
			write(str.toCharArray(), off, len);
		}
	}

	private static class JavaReader extends FilterReader {
		public JavaReader(Reader in) {
			super(in);
		}

		@Override
		public int read() throws IOException {
			final int b = in.read();
			if (b != 36)
				return b;
			final int i = in.read();
			if (i < 0)
				return -1;
			if (i >= 48 && i <= 57 || i >= 97 && i <= 102) {
				final int j = in.read();
				if (j < 0)
					return -1;
				final char[] tmp = { (char) i, (char) j };
				final int s = Integer.parseInt(new String(tmp), 16);
				return s;
			}
			return Utility.MAP_CHAR[i];
		}

		@Override
		public int read(char[] cbuf, int off, int len) throws IOException {
			for (int i = 0; i < len; i++)
				cbuf[off + i] = (char) read();
			return len;
		}
	}

	private static int unwrap(ThreadLocal<Object> consumed_chars2) {
		return ((Integer) consumed_chars2.get()).intValue();
	}

	private static void wrap(ThreadLocal<Object> consumed_chars2, int value) {
		consumed_chars2.set(Integer.valueOf(value));
	}

	public static final String accessToString(int access_flags) {
		return accessToString(access_flags, false);
	}

	public static final String accessToString(int access_flags, boolean for_class) {
		final StringBuilder buf = new StringBuilder();
		int p = 0;
		int i = 0;
		while (p < 16384) {
			p = pow2(i);
			if ((access_flags & p) != 0 && (!for_class || p != 32 && p != 512))
				buf.append(Constants.ACCESS_NAMES[i]).append(" ");
			i++;
		}
		return buf.toString().trim();
	}

	public static final String classOrInterface(int access_flags) {
		return (access_flags & 0x200) != 0 ? "interface" : "class";
	}

	public static final String codeToString(byte[] code, ConstantPool constant_pool, int index, int length,
			boolean verbose) {
		final StringBuilder buf = new StringBuilder(code.length * 20);
		final ByteSequence stream = new ByteSequence(code);
		try {
			for (int i = 0; i < index; i++)
				codeToString(stream, constant_pool, verbose);
			int i = 0;
			while (stream.available() > 0) {
				if (length < 0 || i < length) {
					final String indices = fillup(new StringBuilder().append(stream.getIndex()).append(":").toString(),
							6, true, ' ');
					buf.append(indices).append(codeToString(stream, constant_pool, verbose)).append('\n');
				}
				i++;
			}
		} catch (final IOException e) {
			System.out.println(buf.toString());
			e.printStackTrace();
			throw new ClassFormatException(new StringBuilder().append("Byte code error: ").append(e).toString(), e);
		}
		return buf.toString();
	}

	public static final String codeToString(byte[] code, ConstantPool constant_pool, int index, int length) {
		return codeToString(code, constant_pool, index, length, true);
	}

	public static final String codeToString(ByteSequence bytes, ConstantPool constant_pool, boolean verbose)
			throws IOException {
		final short opcode = (short) bytes.readUnsignedByte();
		int default_offset = 0;
		int no_pad_bytes = 0;
		final StringBuilder buf = new StringBuilder(Constants.OPCODE_NAMES[opcode]);
		if (opcode == 170 || opcode == 171) {
			final int remainder = bytes.getIndex() % 4;
			no_pad_bytes = remainder == 0 ? 0 : 4 - remainder;
			for (int i = 0; i < no_pad_bytes; i++) {
				byte b;
				if ((b = bytes.readByte()) != 0)
					System.err.println(new StringBuilder().append("Warning: Padding byte != 0 in ")
							.append(Constants.OPCODE_NAMES[opcode]).append(":").append(b).toString());
			}
			default_offset = bytes.readInt();
		}
		switch (opcode) {
		case 170: {
			final int low = bytes.readInt();
			final int high = bytes.readInt();
			final int offset = bytes.getIndex() - 12 - no_pad_bytes - 1;
			default_offset += offset;
			buf.append("\tdefault = ").append(default_offset).append(", low = ").append(low).append(", high = ")
					.append(high).append("(");
			final int[] jump_table = new int[high - low + 1];
			for (int i = 0; i < jump_table.length; i++) {
				jump_table[i] = offset + bytes.readInt();
				buf.append(jump_table[i]);
				if (i < jump_table.length - 1)
					buf.append(", ");
			}
			buf.append(")");
			break;
		}
		case 171: {
			final int npairs = bytes.readInt();
			final int offset = bytes.getIndex() - 8 - no_pad_bytes - 1;
			final int[] match = new int[npairs];
			final int[] jump_table = new int[npairs];
			default_offset += offset;
			buf.append("\tdefault = ").append(default_offset).append(", npairs = ").append(npairs).append(" (");
			for (int i = 0; i < npairs; i++) {
				match[i] = bytes.readInt();
				jump_table[i] = offset + bytes.readInt();
				buf.append("(").append(match[i]).append(", ").append(jump_table[i]).append(")");
				if (i < npairs - 1)
					buf.append(", ");
			}
			buf.append(")");
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
		case 199:
			buf.append("\t\t#").append(bytes.getIndex() - 1 + bytes.readShort());
			break;
		case 200:
		case 201:
			buf.append("\t\t#").append(bytes.getIndex() - 1 + bytes.readInt());
			break;
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
				vindex = bytes.readUnsignedShort();
				wide = false;
			} else
				vindex = bytes.readUnsignedByte();
			buf.append("\t\t%").append(vindex);
			break;
		}
		case 196:
			wide = true;
			buf.append("\t(wide)");
			break;
		case 188:
			buf.append("\t\t<").append(Constants.TYPE_NAMES[bytes.readByte()]).append(">");
			break;
		case 178:
		case 179:
		case 180:
		case 181: {
			final int index = bytes.readUnsignedShort();
			buf.append("\t\t").append(constant_pool.constantToString(index, (byte) 9))
					.append(verbose ? new StringBuilder().append(" (").append(index).append(")").toString() : "");
			break;
		}
		case 187:
		case 192:
			buf.append("\t");
		case 193: {
			final int index = bytes.readUnsignedShort();
			buf.append("\t<").append(constant_pool.constantToString(index, (byte) 7)).append(">")
					.append(verbose ? new StringBuilder().append(" (").append(index).append(")").toString() : "");
			break;
		}
		case 182:
		case 183:
		case 184: {
			final int index = bytes.readUnsignedShort();
			buf.append("\t").append(constant_pool.constantToString(index, (byte) 10))
					.append(verbose ? new StringBuilder().append(" (").append(index).append(")").toString() : "");
			break;
		}
		case 185: {
			final int index = bytes.readUnsignedShort();
			final int nargs = bytes.readUnsignedByte();
			buf.append("\t").append(constant_pool.constantToString(index, (byte) 11))
					.append(verbose ? new StringBuilder().append(" (").append(index).append(")\t").toString() : "")
					.append(nargs).append("\t").append(bytes.readUnsignedByte());
			break;
		}
		case 19:
		case 20: {
			final int index = bytes.readUnsignedShort();
			buf.append("\t\t").append(constant_pool.constantToString(index, constant_pool.getConstant(index).getTag()))
					.append(verbose ? new StringBuilder().append(" (").append(index).append(")").toString() : "");
			break;
		}
		case 18: {
			final int index = bytes.readUnsignedByte();
			buf.append("\t\t").append(constant_pool.constantToString(index, constant_pool.getConstant(index).getTag()))
					.append(verbose ? new StringBuilder().append(" (").append(index).append(")").toString() : "");
			break;
		}
		case 189: {
			final int index = bytes.readUnsignedShort();
			buf.append("\t\t<").append(compactClassName(constant_pool.getConstantString(index, (byte) 7), false))
					.append(">")
					.append(verbose ? new StringBuilder().append(" (").append(index).append(")").toString() : "");
			break;
		}
		case 197: {
			final int index = bytes.readUnsignedShort();
			final int dimensions = bytes.readUnsignedByte();
			buf.append("\t<").append(compactClassName(constant_pool.getConstantString(index, (byte) 7), false))
					.append(">\t").append(dimensions)
					.append(verbose ? new StringBuilder().append(" (").append(index).append(")").toString() : "");
			break;
		}
		case 132: {
			int vindex;
			int constant;
			if (wide) {
				vindex = bytes.readUnsignedShort();
				constant = bytes.readShort();
				wide = false;
			} else {
				vindex = bytes.readUnsignedByte();
				constant = bytes.readByte();
			}
			buf.append("\t\t%").append(vindex).append("\t").append(constant);
			break;
		}
		default:
			if (Constants.NO_OF_OPERANDS[opcode] > 0) {
				for (int i = 0; i < Constants.TYPE_OF_OPERANDS[opcode].length; i++) {
					buf.append("\t\t");
					switch (Constants.TYPE_OF_OPERANDS[opcode][i]) {
					case 8:
						buf.append(bytes.readByte());
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
				}
			}
		}
		return buf.toString();
	}

	public static final String codeToString(ByteSequence bytes, ConstantPool constant_pool) throws IOException {
		return codeToString(bytes, constant_pool, true);
	}

	public static final String compactClassName(String str) {
		return compactClassName(str, true);
	}

	public static final String compactClassName(String str, String prefix, boolean chopit) {
		final int len = prefix.length();
		str = str.replace('/', '.');
		if (chopit && str.startsWith(prefix) && str.substring(len).indexOf('.') == -1)
			str = str.substring(len);
		return str;
	}

	public static final String compactClassName(String str, boolean chopit) {
		return compactClassName(str, "java.lang.", chopit);
	}

	public static final int setBit(int flag, int i) {
		return flag | pow2(i);
	}

	public static final int clearBit(int flag, int i) {
		final int bit = pow2(i);
		return (flag & bit) == 0 ? flag : flag ^ bit;
	}

	public static final boolean isSet(int flag, int i) {
		return (flag & pow2(i)) != 0;
	}

	public static final String methodTypeToSignature(String ret, String[] argv) throws ClassFormatException {
		final StringBuilder buf = new StringBuilder("(");
		if (argv != null) {
			for (int i = 0; i < argv.length; i++) {
				final String str = getSignature(argv[i]);
				if (str.endsWith("V"))
					throw new ClassFormatException(
							new StringBuilder().append("Invalid type: ").append(argv[i]).toString());
				buf.append(str);
			}
		}
		final String str = getSignature(ret);
		buf.append(")").append(str);
		return buf.toString();
	}

	public static final String[] methodSignatureArgumentTypes(String signature) throws ClassFormatException {
		return methodSignatureArgumentTypes(signature, true);
	}

	public static final String[] methodSignatureArgumentTypes(String signature, boolean chopit)
			throws ClassFormatException {
		final List<String> vec = new ArrayList<String>();
		try {
			if (signature.charAt(0) != '(')
				throw new ClassFormatException(
						new StringBuilder().append("Invalid method signature: ").append(signature).toString());
			for (int index = 1; signature.charAt(index) != ')'; index += unwrap(consumed_chars))
				vec.add(signatureToString(signature.substring(index), chopit));
		} catch (final StringIndexOutOfBoundsException e) {
			throw new ClassFormatException(
					new StringBuilder().append("Invalid method signature: ").append(signature).toString(), e);
		}
		return (String[]) vec.toArray(new String[vec.size()]);
	}

	public static final String methodSignatureReturnType(String signature) throws ClassFormatException {
		return methodSignatureReturnType(signature, true);
	}

	public static final String methodSignatureReturnType(String signature, boolean chopit) throws ClassFormatException {
		String type;
		try {
			final int index = signature.lastIndexOf(')') + 1;
			type = signatureToString(signature.substring(index), chopit);
		} catch (final StringIndexOutOfBoundsException e) {
			throw new ClassFormatException(
					new StringBuilder().append("Invalid method signature: ").append(signature).toString(), e);
		}
		return type;
	}

	public static final String methodSignatureToString(String signature, String name, String access) {
		return methodSignatureToString(signature, name, access, true);
	}

	public static final String methodSignatureToString(String signature, String name, String access, boolean chopit) {
		return methodSignatureToString(signature, name, access, chopit, null);
	}

	public static final String methodSignatureToString(String signature, String name, String access, boolean chopit,
			LocalVariableTable vars) throws ClassFormatException {
		final StringBuilder buf = new StringBuilder("(");
		int var_index = access.indexOf("static") >= 0 ? 0 : 1;
		String type;
		try {
			if (signature.charAt(0) != '(')
				throw new ClassFormatException(
						new StringBuilder().append("Invalid method signature: ").append(signature).toString());
			int index;
			for (index = 1; signature.charAt(index) != ')'; index += unwrap(consumed_chars)) {
				final String param_type = signatureToString(signature.substring(index), chopit);
				buf.append(param_type);
				if (vars != null) {
					@SuppressWarnings("deprecation")
					final LocalVariable l = vars.getLocalVariable(var_index);
					if (l != null)
						buf.append(" ").append(l.getName());
				} else
					buf.append(" arg").append(var_index);
				if ("double".equals(param_type) || "long".equals(param_type))
					var_index += 2;
				else
					var_index++;
				buf.append(", ");
			}
			index++;
			type = signatureToString(signature.substring(index), chopit);
		} catch (final StringIndexOutOfBoundsException e) {
			throw new ClassFormatException(
					new StringBuilder().append("Invalid method signature: ").append(signature).toString(), e);
		}
		if (buf.length() > 1)
			buf.setLength(buf.length() - 2);
		buf.append(")");
		return new StringBuilder().append(access).append(access.length() > 0 ? " " : "").append(type).append(" ")
				.append(name).append(buf.toString()).toString();
	}

	private static final int pow2(int n) {
		return 1 << n;
	}

	public static final String replace(String str, String old, String new_) {
		try {
			if (str.indexOf(old) != -1) {
				final StringBuffer buf = new StringBuffer();
				int index;
				int old_index;
				for (old_index = 0; (index = str.indexOf(old, old_index)) != -1; old_index = index + old.length()) {
					buf.append(str.substring(old_index, index));
					buf.append(new_);
				}
				buf.append(str.substring(old_index));
				str = buf.toString();
			}
		} catch (final StringIndexOutOfBoundsException e) {
			System.err.println(e);
		}
		return str;
	}

	public static final String signatureToString(String signature) {
		return signatureToString(signature, true);
	}

	public static final String signatureToString(String signature, boolean chopit) {
		wrap(consumed_chars, 1);
		try {
			switch (signature.charAt(0)) {
			case 'B':
				return "byte";
			case 'C':
				return "char";
			case 'D':
				return "double";
			case 'F':
				return "float";
			case 'I':
				return "int";
			case 'J':
				return "long";
			case 'L': {
				final int index = signature.indexOf(';');
				if (index < 0) {
					throw new ClassFormatException("Invalid signature: " + signature);
				}
				wrap(consumed_chars, index + 1);
				return compactClassName(signature.substring(1, index), chopit);
			}
			case 'S':
				return "short";
			case 'Z':
				return "boolean";
			case '[': {
				int n;
				StringBuffer brackets;
				String type;
				int consumed_chars;
				brackets = new StringBuffer();
				for (n = 0; signature.charAt(n) == '['; n++) {
					brackets.append("[]");
				}
				consumed_chars = n;
				type = signatureToString(signature.substring(n), chopit);
				final int _temp = unwrap(Utility.consumed_chars) + consumed_chars;
				wrap(Utility.consumed_chars, _temp);
				return type + brackets.toString();
			}
			case 'V':
				return "void";
			default:
				throw new ClassFormatException("Invalid signature: `" + signature + "'");
			}
		} catch (final StringIndexOutOfBoundsException e) {
			throw new ClassFormatException("Invalid signature: " + e + ":" + signature);
		}
	}

	public static String getSignature(String type) {
		final StringBuilder buf = new StringBuilder();
		final char[] chars = type.toCharArray();
		boolean char_found = false;
		boolean delim = false;
		int index = -1;
		while_33_: for (int i = 0; i < chars.length; i++) {
			switch (chars[i]) {
			case '\t':
			case '\n':
			case '\014':
			case '\r':
			case ' ':
				if (char_found)
					delim = true;
				break;
			case '[':
				if (!char_found)
					throw new RuntimeException(new StringBuilder().append("Illegal type: ").append(type).toString());
				index = i;
				break while_33_;
			default:
				char_found = true;
				if (!delim)
					buf.append(chars[i]);
			}
		}
		int brackets = 0;
		if (index > 0)
			brackets = countBrackets(type.substring(index));
		type = buf.toString();
		buf.setLength(0);
		for (int i = 0; i < brackets; i++)
			buf.append('[');
		boolean found = false;
		for (int i = 4; i <= 12 && !found; i++) {
			if (Constants.TYPE_NAMES[i].equals(type)) {
				found = true;
				buf.append(Constants.SHORT_TYPE_NAMES[i]);
			}
		}
		if (!found)
			buf.append('L').append(type.replace('.', '/')).append(';');
		return buf.toString();
	}

	private static int countBrackets(String brackets) {
		final char[] chars = brackets.toCharArray();
		int count = 0;
		boolean open = false;
		for (int i = 0; i < chars.length; i++) {
			switch (chars[i]) {
			case '[':
				if (open)
					throw new RuntimeException(
							new StringBuilder().append("Illegally nested brackets:").append(brackets).toString());
				open = true;
				break;
			case ']':
				if (!open)
					throw new RuntimeException(
							new StringBuilder().append("Illegally nested brackets:").append(brackets).toString());
				open = false;
				count++;
				break;
			}
		}
		if (open)
			throw new RuntimeException(
					new StringBuilder().append("Illegally nested brackets:").append(brackets).toString());
		return count;
	}

	public static final byte typeOfMethodSignature(String signature) throws ClassFormatException {
		byte i;
		try {
			if (signature.charAt(0) != '(')
				throw new ClassFormatException(
						new StringBuilder().append("Invalid method signature: ").append(signature).toString());
			final int index = signature.lastIndexOf(')') + 1;
			i = typeOfSignature(signature.substring(index));
		} catch (final StringIndexOutOfBoundsException e) {
			throw new ClassFormatException(
					new StringBuilder().append("Invalid method signature: ").append(signature).toString(), e);
		}
		return i;
	}

	public static final byte typeOfSignature(String signature) throws ClassFormatException {
		try {
			switch (signature.charAt(0)) {
			case 'B':
				return Constants.T_BYTE;
			case 'C':
				return Constants.T_CHAR;
			case 'D':
				return Constants.T_DOUBLE;
			case 'F':
				return Constants.T_FLOAT;
			case 'I':
				return Constants.T_INT;
			case 'J':
				return Constants.T_LONG;
			case 'L':
				return Constants.T_REFERENCE;
			case '[':
				return Constants.T_ARRAY;
			case 'V':
				return Constants.T_VOID;
			case 'Z':
				return Constants.T_BOOLEAN;
			case 'S':
				return Constants.T_SHORT;
			default:
				throw new ClassFormatException("Invalid method signature: " + signature);
			}
		} catch (final StringIndexOutOfBoundsException e) {
			throw new ClassFormatException("Invalid method signature: " + signature);
		}
	}

	public static short searchOpcode(String name) {
		name = name.toLowerCase(Locale.ENGLISH);
		for (short i = 0; i < Constants.OPCODE_NAMES.length; i++) {
			if (Constants.OPCODE_NAMES[i].equals(name))
				return i;
		}
		return (short) -1;
	}

	private static final short byteToShort(byte b) {
		return b < 0 ? (short) (256 + b) : (short) b;
	}

	public static final String toHexString(byte[] bytes) {
		final StringBuilder buf = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			final short b = byteToShort(bytes[i]);
			final String hex = Integer.toString(b, 16);
			if (b < 16)
				buf.append('0');
			buf.append(hex);
			if (i < bytes.length - 1)
				buf.append(' ');
		}
		return buf.toString();
	}

	public static final String format(int i, int length, boolean left_justify, char fill) {
		return fillup(Integer.toString(i), length, left_justify, fill);
	}

	public static final String fillup(String str, int length, boolean left_justify, char fill) {
		final int len = length - str.length();
		final char[] buf = new char[len < 0 ? 0 : len];
		for (int j = 0; j < buf.length; j++)
			buf[j] = fill;
		if (left_justify)
			return new StringBuilder().append(str).append(new String(buf)).toString();
		return new StringBuilder().append(new String(buf)).append(str).toString();
	}

	static final boolean equals(byte[] a, byte[] b) {
		int size;
		if ((size = a.length) != b.length)
			return false;
		for (int i = 0; i < size; i++) {
			if (a[i] != b[i])
				return false;
		}
		return true;
	}

	public static final void printArray(PrintStream out, Object[] obj) {
		out.println(printArray(obj, true));
	}

	public static final void printArray(PrintWriter out, Object[] obj) {
		out.println(printArray(obj, true));
	}

	public static final String printArray(Object[] obj) {
		return printArray(obj, true);
	}

	public static final String printArray(Object[] obj, boolean braces) {
		return printArray(obj, braces, false);
	}

	public static final String printArray(Object[] obj, boolean braces, boolean quote) {
		if (obj == null)
			return null;
		final StringBuilder buf = new StringBuilder();
		if (braces)
			buf.append('{');
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null)
				buf.append(quote ? "\"" : "").append(obj[i].toString()).append(quote ? "\"" : "");
			else
				buf.append("null");
			if (i < obj.length - 1)
				buf.append(", ");
		}
		if (braces)
			buf.append('}');
		return buf.toString();
	}

	public static boolean isJavaIdentifierPart(char ch) {
		return (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9' || ch == '_');
	}

	public static String encode(byte[] bytes, boolean compress) throws IOException {
		if (compress) {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final GZIPOutputStream gos = new GZIPOutputStream(baos);
			gos.write(bytes, 0, bytes.length);
			gos.close();
			baos.close();
			bytes = baos.toByteArray();
		}
		final CharArrayWriter caw = new CharArrayWriter();
		final JavaWriter jw = new JavaWriter(caw);
		for (int i = 0; i < bytes.length; i++) {
			final int in = bytes[i] & 0xff;
			jw.write(in);
		}
		jw.close();
		return caw.toString();
	}

	public static byte[] decode(String s, boolean uncompress) throws IOException {
		final char[] chars = s.toCharArray();
		final CharArrayReader car = new CharArrayReader(chars);
		final JavaReader jr = new JavaReader(car);
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int ch;
		while ((ch = jr.read()) >= 0)
			bos.write(ch);
		bos.close();
		car.close();
		jr.close();
		byte[] bytes = bos.toByteArray();
		if (uncompress) {
			final GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
			final byte[] tmp = new byte[bytes.length * 3];
			int count = 0;
			int b;
			while ((b = gis.read()) >= 0)
				tmp[count++] = (byte) b;
			bytes = new byte[count];
			System.arraycopy(tmp, 0, bytes, 0, count);
		}
		return bytes;
	}

	public static final String convertString(String label) {
		final char[] ch = label.toCharArray();
		final StringBuilder buf = new StringBuilder();
		for (int i = 0; i < ch.length; i++) {
			switch (ch[i]) {
			case '\n':
				buf.append("\\n");
				break;
			case '\r':
				buf.append("\\r");
				break;
			case '\"':
				buf.append("\\\"");
				break;
			case '\'':
				buf.append("\\'");
				break;
			case '\\':
				buf.append("\\\\");
				break;
			default:
				buf.append(ch[i]);
			}
		}
		return buf.toString();
	}

	public static Attribute[] getAnnotationAttributes(ConstantPoolGen cp, List<?> vec) {
		if (vec.isEmpty())
			return new Attribute[0];
		Attribute[] attributes;
		try {
			int countVisible = 0;
			int countInvisible = 0;
			final Iterator<?> i$ = vec.iterator();
			while (i$.hasNext()) {
				final AnnotationEntryGen a = (AnnotationEntryGen) i$.next();
				if (a.isRuntimeVisible())
					countVisible++;
				else
					countInvisible++;
			}
			final ByteArrayOutputStream rvaBytes = new ByteArrayOutputStream();
			final ByteArrayOutputStream riaBytes = new ByteArrayOutputStream();
			final DataOutputStream rvaDos = new DataOutputStream(rvaBytes);
			final DataOutputStream riaDos = new DataOutputStream(riaBytes);
			rvaDos.writeShort(countVisible);
			riaDos.writeShort(countInvisible);
			final Iterator<?> i$_24_ = vec.iterator();
			while (i$_24_.hasNext()) {
				final AnnotationEntryGen a = (AnnotationEntryGen) i$_24_.next();
				if (a.isRuntimeVisible())
					a.dump(rvaDos);
				else
					a.dump(riaDos);
			}
			rvaDos.close();
			riaDos.close();
			final byte[] rvaData = rvaBytes.toByteArray();
			final byte[] riaData = riaBytes.toByteArray();
			int rvaIndex = -1;
			int riaIndex = -1;
			if (rvaData.length > 2)
				rvaIndex = cp.addUtf8("RuntimeVisibleAnnotations");
			if (riaData.length > 2)
				riaIndex = cp.addUtf8("RuntimeInvisibleAnnotations");
			final List<Annotations> newAttributes = new ArrayList<Annotations>();
			if (rvaData.length > 2)
				newAttributes.add(new RuntimeVisibleAnnotations(rvaIndex, rvaData.length,
						new DataInputStream(new ByteArrayInputStream(rvaData)), cp.getConstantPool()));
			if (riaData.length > 2)
				newAttributes.add(new RuntimeInvisibleAnnotations(riaIndex, riaData.length,
						new DataInputStream(new ByteArrayInputStream(riaData)), cp.getConstantPool()));
			attributes = ((Attribute[]) newAttributes.toArray(new Attribute[newAttributes.size()]));
		} catch (final IOException e) {
			System.err.println("IOException whilst processing annotations");
			e.printStackTrace();
			return null;
		}
		return attributes;
	}

	public static Attribute[] getParameterAnnotationAttributes(ConstantPoolGen cp, List<?>[] vec) {
		final int[] visCount = new int[vec.length];
		int totalVisCount = 0;
		final int[] invisCount = new int[vec.length];
		int totalInvisCount = 0;
		Attribute[] attributes;
		try {
			for (int i = 0; i < vec.length; i++) {
				if (vec[i] != null) {
					final Iterator<?> i$ = vec[i].iterator();
					while (i$.hasNext()) {
						final AnnotationEntryGen element = (AnnotationEntryGen) i$.next();
						if (element.isRuntimeVisible()) {
							visCount[i]++;
							totalVisCount++;
						} else {
							invisCount[i]++;
							totalInvisCount++;
						}
					}
				}
			}
			final ByteArrayOutputStream rvaBytes = new ByteArrayOutputStream();
			final DataOutputStream rvaDos = new DataOutputStream(rvaBytes);
			rvaDos.writeByte(vec.length);
			for (int i = 0; i < vec.length; i++) {
				rvaDos.writeShort(visCount[i]);
				if (visCount[i] > 0) {
					final Iterator<?> i$ = vec[i].iterator();
					while (i$.hasNext()) {
						final AnnotationEntryGen element = (AnnotationEntryGen) i$.next();
						if (element.isRuntimeVisible())
							element.dump(rvaDos);
					}
				}
			}
			rvaDos.close();
			final ByteArrayOutputStream riaBytes = new ByteArrayOutputStream();
			final DataOutputStream riaDos = new DataOutputStream(riaBytes);
			riaDos.writeByte(vec.length);
			for (int i = 0; i < vec.length; i++) {
				riaDos.writeShort(invisCount[i]);
				if (invisCount[i] > 0) {
					final Iterator<?> i$ = vec[i].iterator();
					while (i$.hasNext()) {
						final AnnotationEntryGen element = (AnnotationEntryGen) i$.next();
						if (!element.isRuntimeVisible())
							element.dump(riaDos);
					}
				}
			}
			riaDos.close();
			final byte[] rvaData = rvaBytes.toByteArray();
			final byte[] riaData = riaBytes.toByteArray();
			int rvaIndex = -1;
			int riaIndex = -1;
			if (totalVisCount > 0)
				rvaIndex = cp.addUtf8("RuntimeVisibleParameterAnnotations");
			if (totalInvisCount > 0)
				riaIndex = cp.addUtf8("RuntimeInvisibleParameterAnnotations");
			final List<ParameterAnnotations> newAttributes = new ArrayList<ParameterAnnotations>();
			if (totalVisCount > 0)
				newAttributes.add(new RuntimeVisibleParameterAnnotations(rvaIndex, rvaData.length,
						new DataInputStream(new ByteArrayInputStream(rvaData)), cp.getConstantPool()));
			if (totalInvisCount > 0)
				newAttributes.add(new RuntimeInvisibleParameterAnnotations(riaIndex, riaData.length,
						new DataInputStream(new ByteArrayInputStream(riaData)), cp.getConstantPool()));
			attributes = ((Attribute[]) newAttributes.toArray(new Attribute[newAttributes.size()]));
		} catch (final IOException e) {
			System.err.println("IOException whilst processing parameter annotations");
			e.printStackTrace();
			return null;
		}
		return attributes;
	}

	static {
		int j = 0;
		for (int i = 65; i <= 90; i++) {
			CHAR_MAP[j] = i;
			MAP_CHAR[i] = j;
			j++;
		}
		for (int i = 103; i <= 122; i++) {
			CHAR_MAP[j] = i;
			MAP_CHAR[i] = j;
			j++;
		}
		CHAR_MAP[j] = 36;
		MAP_CHAR[36] = j;
		j++;
		CHAR_MAP[j] = 95;
		MAP_CHAR[95] = j;
	}
}
