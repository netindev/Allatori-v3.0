package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.MethodGen;

public class DemoWaterMark implements ObfuscationType {

	@Override
	public void terminate() {
	}

	@Override
	public void execute(ClassGen var1) {
		try {
			if (var1.getClassName().startsWith("com.allatori.")) {
				return;
			}

			Method var2;
			if ((var2 = var1.containsMethod("main", "([Ljava/lang/String;)V")) != null) {
				final MethodGen var3 = InitUtils.createMethodGen(var2, var1.getClassName(), var1.getConstantPool(),
						var1.getConstantPool().getConstantPool());
				final InstructionFactory var4 = new InstructionFactory(var1);
				var3.getInstructionList().append(var4.createPrintln(this.getWaterMark()));
				var3.setMaxStack();
				var1.replaceMethod(var2, var3.getMethod());
			}
		} catch (final Exception var5) {
			var5.printStackTrace();
		}

	}

	private StringBuffer method1213(StringBuffer var1, char var2, int var3) {
		int var4;
		for (int var10000 = var4 = 0; var10000 < var3; var10000 = var4) {
			var1.append(var2);
			++var4;
		}

		return var1;
	}

	private String getWaterMark() {
		final String var1 = " ## #   #    ## ### ### ##  ###";
		final String var2 = "# # #   #   # #  #  # # # #  # ";
		final String var3 = "### #   #   ###  #  # # ##   # ";
		final String var4 = "# # ### ### # #  #  ### # # ###";
		final String var5 = "Obfuscation by " + Info.name() + " " + Info.version();
		final String var6 = Info.website();
		int var7 = Math.max(var1.length(), Math.max(var5.length(), var6.length()));
		var7 += 4;
		final StringBuffer var8 = new StringBuffer("\n");
		this.method1213(var8, '#', var7).append("\n#");
		this.method1213(var8, ' ', var7 - 2).append("#\n#");
		int var9 = (var7 - var1.length() - 2) / 2;
		int var10 = var7 - var9 - var1.length() - 2;
		this.method1213(var8, ' ', var9).append(var1);
		this.method1213(var8, ' ', var10).append("#\n#");
		this.method1213(var8, ' ', var9).append(var2);
		this.method1213(var8, ' ', var10).append("#\n#");
		this.method1213(var8, ' ', var9).append(var3);
		this.method1213(var8, ' ', var10).append("#\n#");
		this.method1213(var8, ' ', var9).append(var4);
		this.method1213(var8, ' ', var10).append("#\n#");
		this.method1213(var8, ' ', var7 - 2).append("#\n#");
		var9 = (var7 - var5.length() - 2) / 2;
		var10 = var7 - var9 - var5.length() - 2;
		this.method1213(var8, ' ', var9).append(var5);
		this.method1213(var8, ' ', var10).append("#\n#");
		this.method1213(var8, ' ', var7 - 2).append("#\n#");
		var9 = (var7 - var6.length() - 2) / 2;
		var10 = var7 - var9 - var6.length() - 2;
		this.method1213(var8, ' ', var9).append(var6);
		this.method1213(var8, ' ', var10).append("#\n#");
		this.method1213(var8, ' ', var7 - 2).append("#\n");
		this.method1213(var8, '#', var7).append("\n");
		return var8.toString();
	}
}
