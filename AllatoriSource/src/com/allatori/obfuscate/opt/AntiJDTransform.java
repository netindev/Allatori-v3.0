package com.allatori.obfuscate.opt;

import com.allatori.clazz.ClassStorage;
import com.allatori.obfuscate.ObfuscationType;
import com.allatori.util.InitUtils;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

public class AntiJDTransform implements ObfuscationType {

    private ClassStorage classes;


    public void execute(ClassGen _cg) {
        int type = AntiJD.getType(this.classes, _cg);
        if (type != 0) {
            try {
                for(Method method : _cg.getMethods()){
                    if (method.getCode() != null) {
                        MethodGen _mg = InitUtils.createMethodGen(method, _cg.getClassName(), _cg.getConstantPool(), _cg.getConstantPool().getConstantPool());
                        InstructionList _il = _mg.getInstructionList();
                        InstructionHandle currentHandle = _il.getStart();
                        while (currentHandle != null) {
                            if (currentHandle.getInstruction() instanceof NEWARRAY || currentHandle.getInstruction() instanceof ANEWARRAY) {
                                InstructionHandle h = _il.append(currentHandle, new PUSH(_cg.getConstantPool(), 1));
                                h = _il.append(h, new DUP());
                                _il.append(h, new POP2());
                                currentHandle = currentHandle.getNext().getNext().getNext().getNext();
                            } else if (type == 2 && (currentHandle.getInstruction() instanceof BIPUSH || currentHandle.getInstruction() instanceof SIPUSH || currentHandle.getInstruction() instanceof ICONST)) {
                                InstructionHandle h = _il.append(currentHandle, new PUSH(_cg.getConstantPool(), 1));
                                h = _il.append(h, new DUP());
                                _il.append(h, new POP2());
                                currentHandle = currentHandle.getNext().getNext().getNext().getNext();
                            } else {
                                currentHandle = currentHandle.getNext();
                            }
                        }
                        _mg.setMaxStack();
                        _mg.setMaxLocals();
                        _cg.replaceMethod(method, _mg.getMethod());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public AntiJDTransform(ClassStorage var1) {
        this.classes = var1;
    }

    public void terminate() {
    }
}
