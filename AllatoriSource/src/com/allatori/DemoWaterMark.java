package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.MethodGen;

public class DemoWaterMark implements ObfuscationType {

	/* OK */

	@Override
	public void terminate() {
		/* empty */
	}

	@Override
	public void execute(ClassGen classGen) {
		try {
			if (classGen.getClassName().startsWith("com.allatori.")) {
				return;
			}
			final Method method = classGen.containsMethod("main", "([Ljava/lang/String;)V");
			if (method != null) {
				final MethodGen methodGen = InitUtils.createMethodGen(method, classGen.getClassName(),
						classGen.getConstantPool(), classGen.getConstantPool().getConstantPool());
				final InstructionFactory instructionFactory = new InstructionFactory(classGen);
				methodGen.getInstructionList().append(instructionFactory.createPrintln(this.getWaterMark()));
				methodGen.setMaxStack();
				classGen.replaceMethod(method, methodGen.getMethod());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private StringBuffer append(StringBuffer stringBuffer, char toAppend, int max) {
		for (int i = 0; i < max; i++) {
			stringBuffer.append(toAppend);
		}
		return stringBuffer;
	}

	private String getWaterMark() {
		final String fLine = " ## #   #    ## ### ### ##  ###";
		final String sLine = "# # #   #   # #  #  # # # #  # ";
		final String tLine = "### #   #   ###  #  # # ##   # ";
		final String vLine = "# # ### ### # #  #  ### # # ###";
		final String credits = "Obfuscation by " + Info.name() + " " + Info.version();
		final String website = Info.website();
		int max = Math.max(fLine.length(), Math.max(credits.length(), website.length()));
		max += 4;
		final StringBuffer stringBuffer = new StringBuffer("\n");
		this.append(stringBuffer, '#', max).append("\n#");
		this.append(stringBuffer, ' ', max - 2).append("#\n#");
		int lM2 = (max - fLine.length() - 2) / 2;
		int lM4 = max - lM2 - fLine.length() - 2;
		this.append(stringBuffer, ' ', lM2).append(fLine);
		this.append(stringBuffer, ' ', lM4).append("#\n#");
		this.append(stringBuffer, ' ', lM2).append(sLine);
		this.append(stringBuffer, ' ', lM4).append("#\n#");
		this.append(stringBuffer, ' ', lM2).append(tLine);
		this.append(stringBuffer, ' ', lM4).append("#\n#");
		this.append(stringBuffer, ' ', lM2).append(vLine);
		this.append(stringBuffer, ' ', lM4).append("#\n#");
		this.append(stringBuffer, ' ', max - 2).append("#\n#");
		lM2 = (max - credits.length() - 2) / 2;
		lM4 = max - lM2 - credits.length() - 2;
		this.append(stringBuffer, ' ', lM2).append(credits);
		this.append(stringBuffer, ' ', lM4).append("#\n#");
		this.append(stringBuffer, ' ', max - 2).append("#\n#");
		lM2 = (max - website.length() - 2) / 2;
		lM4 = max - lM2 - website.length() - 2;
		this.append(stringBuffer, ' ', lM2).append(website);
		this.append(stringBuffer, ' ', lM4).append("#\n#");
		this.append(stringBuffer, ' ', max - 2).append("#\n");
		this.append(stringBuffer, '#', max).append("\n");
		return stringBuffer.toString();
	}
}
