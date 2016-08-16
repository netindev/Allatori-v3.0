package com.allatori.watermark;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.MethodGen;

import com.allatori.obfuscate.ObfuscationType;
import com.allatori.util.Info;
import com.allatori.util.InitUtils;

public class DemoWaterMark implements ObfuscationType {

    public void terminate() {}

    public void execute(ClassGen classGen) {
        try {
            if (classGen.getClassName().startsWith("com.allatori.")) {
                return;
            }
            Method method = classGen.containsMethod("main", "([Ljava/lang/String;)V");
            if (method != null) {
                MethodGen methodGen = InitUtils.createMethodGen(method, classGen.getClassName(), classGen.getConstantPool(), classGen.getConstantPool().getConstantPool());
                InstructionFactory instructionFactory = new InstructionFactory(classGen);
                methodGen.getInstructionList().append(instructionFactory.createPrintln(this.getWaterMark()));
                methodGen.setMaxStack();
                classGen.replaceMethod(method, methodGen.getMethod());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StringBuffer append(StringBuffer stringBuffer, char c0, int i) {
        int k;
        for (int j = k = 0; j < i; j = k) {
        	stringBuffer.append(c0);
            ++k;
        }
        return stringBuffer;
    }

    private String getWaterMark() {
        String line0 = " ## #   #    ## ### ### ##  ###";
        String line1 = "# # #   #   # #  #  # # # #  # ";
        String line2 = "### #   #   ###  #  # # ##   # ";
        String line3 = "# # ### ### # #  #  ### # # ###";
        String line4 = "Obfuscation by " + Info.name() + " " + Info.version();
        String line5 = Info.website();
        int integer0 = Math.max(line0.length(), Math.max(line4.length(), line5.length()));
        integer0 += 4;
        StringBuffer stringBuffer = new StringBuffer("\n");
        this.append(stringBuffer, '#', integer0).append("\n#");
        this.append(stringBuffer, ' ', integer0 - 2).append("#\n#");
        int integer1 = (integer0 - line0.length() - 2) / 2;
        int line00 = integer0 - integer1 - line0.length() - 2;
        this.append(stringBuffer, ' ', integer1).append(line0);
        this.append(stringBuffer, ' ', line00).append("#\n#");
        this.append(stringBuffer, ' ', integer1).append(line1);
        this.append(stringBuffer, ' ', line00).append("#\n#");
        this.append(stringBuffer, ' ', integer1).append(line2);
        this.append(stringBuffer, ' ', line00).append("#\n#");
        this.append(stringBuffer, ' ', integer1).append(line3);
        this.append(stringBuffer, ' ', line00).append("#\n#");
        this.append(stringBuffer, ' ', integer0 - 2).append("#\n#");
        integer1 = (integer0 - line4.length() - 2) / 2;
        line00 = integer0 - integer1 - line4.length() - 2;
        this.append(stringBuffer, ' ', integer1).append(line4);
        this.append(stringBuffer, ' ', line00).append("#\n#");
        this.append(stringBuffer, ' ', integer0 - 2).append("#\n#");
        integer1 = (integer0 - line5.length() - 2) / 2;
        line00 = integer0 - integer1 - line5.length() - 2;
        this.append(stringBuffer, ' ', integer1).append(line5);
        this.append(stringBuffer, ' ', line00).append("#\n#");
        this.append(stringBuffer, ' ', integer0 - 2).append("#\n");
        this.append(stringBuffer, '#', integer0).append("\n");
        return stringBuffer.toString();
    }
}
