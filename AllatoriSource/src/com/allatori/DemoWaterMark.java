package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.MethodGen;

public class DemoWaterMark implements ObfuscationType {
	
	/* OK */

	@Override
	public void terminate() {}

	@Override
	public void execute(ClassGen classGen) {
		try {
			if (classGen.getClassName().startsWith("com.allatori.")) {
				return;
			}
			Method method = classGen.containsMethod("main", "([Ljava/lang/String;)V");
			if (method != null) {
				final MethodGen methodGen = InitUtils.createMethodGen(method, classGen.getClassName(), classGen.getConstantPool(),
						classGen.getConstantPool().getConstantPool());
				final InstructionFactory instructionFactory = new InstructionFactory(classGen);
				methodGen.getInstructionList().append(instructionFactory.createPrintln(this.getWaterMark()));
				methodGen.setMaxStack();
				classGen.replaceMethod(method, methodGen.getMethod());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private StringBuffer app(StringBuffer stringBuffer, char c, int length) {
		for (int i = 0; i < length; i++) {
			stringBuffer.append(c);
		}
		return stringBuffer;
	}

	private String getWaterMark() {
		final String firstLine = " ## #   #    ## ### ### ##  ###";
		final String secondLine = "# # #   #   # #  #  # # # #  # ";
		final String thirdLine = "### #   #   ###  #  # # ##   # ";
		final String fourthLine = "# # ### ### # #  #  ### # # ###";
		final String credits = "Obfuscation by " + Info.name() + " " + Info.version();
		final String webSite = Info.website();
		int i = Math.max(firstLine.length(), Math.max(credits.length(), webSite.length()));
		i += 4;
		final StringBuffer stringBuffer = new StringBuffer("\n");
		this.app(stringBuffer, '#', i).append("\n#");
		this.app(stringBuffer, ' ', i - 2).append("#\n#");
		int firstLineD2 = (i - firstLine.length() - 2) / 2;
		int mif = i - firstLineD2 - firstLine.length() - 2;
		this.app(stringBuffer, ' ', firstLineD2).append(firstLine);
		this.app(stringBuffer, ' ', mif).append("#\n#");
		this.app(stringBuffer, ' ', firstLineD2).append(secondLine);
		this.app(stringBuffer, ' ', mif).append("#\n#");
		this.app(stringBuffer, ' ', firstLineD2).append(thirdLine);
		this.app(stringBuffer, ' ', mif).append("#\n#");
		this.app(stringBuffer, ' ', firstLineD2).append(fourthLine);
		this.app(stringBuffer, ' ', mif).append("#\n#");
		this.app(stringBuffer, ' ', i - 2).append("#\n#");
		firstLineD2 = (i - credits.length() - 2) / 2;
		mif = i - firstLineD2 - credits.length() - 2;
		this.app(stringBuffer, ' ', firstLineD2).append(credits);
		this.app(stringBuffer, ' ', mif).append("#\n#");
		this.app(stringBuffer, ' ', i - 2).append("#\n#");
		firstLineD2 = (i - webSite.length() - 2) / 2;
		mif = i - firstLineD2 - webSite.length() - 2;
		this.app(stringBuffer, ' ', firstLineD2).append(webSite);
		this.app(stringBuffer, ' ', mif).append("#\n#");
		this.app(stringBuffer, ' ', i - 2).append("#\n");
		this.app(stringBuffer, '#', i).append("\n");
		return stringBuffer.toString();
	}
}
