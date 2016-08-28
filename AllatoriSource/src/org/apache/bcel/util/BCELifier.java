package org.apache.bcel.util;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.EmptyVisitor;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Type;

public class BCELifier extends EmptyVisitor {
	private final JavaClass _clazz;
	private final PrintWriter _out;
	private final ConstantPoolGen _cp;

	public BCELifier(JavaClass clazz, OutputStream out) {
		_clazz = clazz;
		_out = new PrintWriter(out);
		_cp = new ConstantPoolGen(_clazz.getConstantPool());
	}

	public void start() {
		visitJavaClass(_clazz);
		_out.flush();
	}

	@Override
	public void visitJavaClass(JavaClass clazz) {
		String class_name = clazz.getClassName();
		final String super_name = clazz.getSuperclassName();
		final String package_name = clazz.getPackageName();
		final String inter = Utility.printArray(clazz.getInterfaceNames(), false, true);
		if (!"".equals(package_name)) {
			class_name = class_name.substring(package_name.length() + 1);
			_out.println(new StringBuilder().append("package ").append(package_name).append(";").toString());
			_out.println();
		}
		_out.println("import org.apache.bcel.generic.*;");
		_out.println("import org.apache.bcel.classfile.*;");
		_out.println("import org.apache.bcel.*;");
		_out.println("import java.io.*;");
		_out.println();
		_out.println(new StringBuilder().append("public class ").append(class_name)
				.append("Creator implements Constants {").toString());
		_out.println("  private InstructionFactory _factory;");
		_out.println("  private ConstantPoolGen    _cp;");
		_out.println("  private ClassGen           _cg;");
		_out.println();
		_out.println(new StringBuilder().append("  public ").append(class_name).append("Creator() {").toString());
		_out.println(new StringBuilder().append("    _cg = new ClassGen(\"")
				.append("".equals(package_name) ? class_name
						: new StringBuilder().append(package_name).append(".").append(class_name).toString())
				.append("\", \"").append(super_name).append("\", ").append("\"").append(clazz.getSourceFileName())
				.append("\", ").append(printFlags(clazz.getAccessFlags(), 0)).append(", ").append("new String[] { ")
				.append(inter).append(" });").toString());
		_out.println();
		_out.println("    _cp = _cg.getConstantPool();");
		_out.println("    _factory = new InstructionFactory(_cg, _cp);");
		_out.println("  }");
		_out.println();
		printCreate();
		final Field[] fields = clazz.getFields();
		if (fields.length > 0) {
			_out.println("  private void createFields() {");
			_out.println("    FieldGen field;");
			for (int i = 0; i < fields.length; i++)
				fields[i].accept(this);
			_out.println("  }");
			_out.println();
		}
		final Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			_out.println(
					new StringBuilder().append("  private void createMethod_").append(i).append("() {").toString());
			methods[i].accept(this);
			_out.println("  }");
			_out.println();
		}
		printMain();
		_out.println("}");
	}

	private void printCreate() {
		_out.println("  public void create(OutputStream out) throws IOException {");
		final Field[] fields = _clazz.getFields();
		if (fields.length > 0)
			_out.println("    createFields();");
		final Method[] methods = _clazz.getMethods();
		for (int i = 0; i < methods.length; i++)
			_out.println(new StringBuilder().append("    createMethod_").append(i).append("();").toString());
		_out.println("    _cg.getJavaClass().dump(out);");
		_out.println("  }");
		_out.println();
	}

	private void printMain() {
		final String class_name = _clazz.getClassName();
		_out.println("  public static void main(String[] args) throws Exception {");
		_out.println(new StringBuilder().append("    ").append(class_name).append("Creator creator = new ")
				.append(class_name).append("Creator();").toString());
		_out.println(new StringBuilder().append("    creator.create(new FileOutputStream(\"").append(class_name)
				.append(".class\"));").toString());
		_out.println("  }");
	}

	@Override
	public void visitField(Field field) {
		_out.println();
		_out.println(new StringBuilder().append("    field = new FieldGen(").append(printFlags(field.getAccessFlags()))
				.append(", ").append(printType(field.getSignature())).append(", \"").append(field.getName())
				.append("\", _cp);").toString());
		final ConstantValue cv = field.getConstantValue();
		if (cv != null) {
			final String value = cv.toString();
			_out.println(new StringBuilder().append("    field.setInitValue(").append(value).append(")").toString());
		}
		_out.println("    _cg.addField(field.getField());");
	}

	@Override
	public void visitMethod(Method method) {
		final MethodGen mg = new MethodGen(method, _clazz.getClassName(), _cp);
		final Type result_type = mg.getReturnType();
		final Type[] arg_types = mg.getArgumentTypes();
		_out.println("    InstructionList il = new InstructionList();");
		_out.println(new StringBuilder().append("    MethodGen method = new MethodGen(")
				.append(printFlags(method.getAccessFlags(), 1)).append(", ").append(printType(result_type)).append(", ")
				.append(printArgumentTypes(arg_types)).append(", ").append("new String[] { ")
				.append(Utility.printArray(mg.getArgumentNames(), false, true)).append(" }, \"")
				.append(method.getName()).append("\", \"").append(_clazz.getClassName()).append("\", il, _cp);")
				.toString());
		_out.println();
		final BCELFactory factory = new BCELFactory(mg, _out);
		factory.start();
		_out.println("    method.setMaxStack();");
		_out.println("    method.setMaxLocals();");
		_out.println("    _cg.addMethod(method.getMethod());");
		_out.println("    il.dispose();");
	}

	static String printFlags(int flags) {
		return printFlags(flags, -1);
	}

	static String printFlags(int flags, int reason) {
		if (flags == 0)
			return "0";
		final StringBuilder buf = new StringBuilder();
		int i = 0;
		int pow = 1;
		while (pow <= 16384) {
			if ((flags & pow) != 0) {
				if (pow == 32 && reason == 0)
					buf.append("ACC_SUPER | ");
				else if (pow == 64 && reason == 1)
					buf.append("ACC_BRIDGE | ");
				else if (pow == 128 && reason == 1)
					buf.append("ACC_VARARGS | ");
				else
					buf.append("ACC_").append(Constants.ACCESS_NAMES[i].toUpperCase(Locale.ENGLISH)).append(" | ");
			}
			pow <<= 1;
			i++;
		}
		final String str = buf.toString();
		return str.substring(0, str.length() - 3);
	}

	static String printArgumentTypes(Type[] arg_types) {
		if (arg_types.length == 0)
			return "Type.NO_ARGS";
		final StringBuilder args = new StringBuilder();
		for (int i = 0; i < arg_types.length; i++) {
			args.append(printType(arg_types[i]));
			if (i < arg_types.length - 1)
				args.append(", ");
		}
		return new StringBuilder().append("new Type[] { ").append(args.toString()).append(" }").toString();
	}

	static String printType(Type type) {
		return printType(type.getSignature());
	}

	static String printType(String signature) {
		final Type type = Type.getType(signature);
		final byte t = type.getType();
		if (t <= 12)
			return new StringBuilder().append("Type.").append(Constants.TYPE_NAMES[t].toUpperCase(Locale.ENGLISH))
					.toString();
		if (type.toString().equals("java.lang.String"))
			return "Type.STRING";
		if (type.toString().equals("java.lang.Object"))
			return "Type.OBJECT";
		if (type.toString().equals("java.lang.StringBuffer"))
			return "Type.STRINGBUFFER";
		if (type instanceof ArrayType) {
			final ArrayType at = (ArrayType) type;
			return new StringBuilder().append("new ArrayType(").append(printType(at.getBasicType())).append(", ")
					.append(at.getDimensions()).append(")").toString();
		}
		return new StringBuilder().append("new ObjectType(\"").append(Utility.signatureToString(signature, false))
				.append("\")").toString();
	}

	public static void main(String[] argv) throws Exception {
		final String name = argv[0];
		JavaClass java_class;
		if ((java_class = org.apache.bcel.Repository.lookupClass(name)) == null)
			java_class = new ClassParser(name).parse();
		final BCELifier bcelifier = new BCELifier(java_class, System.out);
		bcelifier.start();
	}
}
