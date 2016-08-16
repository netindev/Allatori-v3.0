package org.apache.bcel.generic;

public interface Visitor {
	public void visitStackInstruction(StackInstruction stackinstruction);

	public void visitLocalVariableInstruction(LocalVariableInstruction localvariableinstruction);

	public void visitBranchInstruction(BranchInstruction branchinstruction);

	public void visitLoadClass(LoadClass loadclass);

	public void visitFieldInstruction(FieldInstruction fieldinstruction);

	public void visitIfInstruction(IfInstruction ifinstruction);

	public void visitConversionInstruction(ConversionInstruction conversioninstruction);

	public void visitPopInstruction(PopInstruction popinstruction);

	public void visitStoreInstruction(StoreInstruction storeinstruction);

	public void visitTypedInstruction(TypedInstruction typedinstruction);

	public void visitSelect(Select select);

	public void visitJsrInstruction(JsrInstruction jsrinstruction);

	public void visitGotoInstruction(GotoInstruction gotoinstruction);

	public void visitUnconditionalBranch(UnconditionalBranch unconditionalbranch);

	public void visitPushInstruction(PushInstruction pushinstruction);

	public void visitArithmeticInstruction(ArithmeticInstruction arithmeticinstruction);

	public void visitCPInstruction(CPInstruction cpinstruction);

	public void visitInvokeInstruction(InvokeInstruction invokeinstruction);

	public void visitArrayInstruction(ArrayInstruction arrayinstruction);

	public void visitAllocationInstruction(AllocationInstruction allocationinstruction);

	public void visitReturnInstruction(ReturnInstruction returninstruction);

	public void visitFieldOrMethod(FieldOrMethod fieldormethod);

	public void visitConstantPushInstruction(ConstantPushInstruction constantpushinstruction);

	public void visitExceptionThrower(ExceptionThrower exceptionthrower);

	public void visitLoadInstruction(LoadInstruction loadinstruction);

	public void visitVariableLengthInstruction(VariableLengthInstruction variablelengthinstruction);

	public void visitStackProducer(StackProducer stackproducer);

	public void visitStackConsumer(StackConsumer stackconsumer);

	public void visitACONST_NULL(ACONST_NULL aconst_null);

	public void visitGETSTATIC(GETSTATIC getstatic);

	public void visitIF_ICMPLT(IF_ICMPLT if_icmplt);

	public void visitMONITOREXIT(MONITOREXIT monitorexit);

	public void visitIFLT(IFLT iflt);

	public void visitLSTORE(LSTORE lstore);

	public void visitPOP2(POP2 pop2);

	public void visitBASTORE(BASTORE bastore);

	public void visitISTORE(ISTORE istore);

	public void visitCHECKCAST(CHECKCAST checkcast);

	public void visitFCMPG(FCMPG fcmpg);

	public void visitI2F(I2F i2f);

	public void visitATHROW(ATHROW athrow);

	public void visitDCMPL(DCMPL dcmpl);

	public void visitARRAYLENGTH(ARRAYLENGTH arraylength);

	public void visitDUP(DUP dup);

	public void visitINVOKESTATIC(INVOKESTATIC invokestatic);

	public void visitLCONST(LCONST lconst);

	public void visitDREM(DREM drem);

	public void visitIFGE(IFGE ifge);

	public void visitCALOAD(CALOAD caload);

	public void visitLASTORE(LASTORE lastore);

	public void visitI2D(I2D i2d);

	public void visitDADD(DADD dadd);

	public void visitINVOKESPECIAL(INVOKESPECIAL invokespecial);

	public void visitIAND(IAND iand);

	public void visitPUTFIELD(PUTFIELD putfield);

	public void visitILOAD(ILOAD iload);

	public void visitDLOAD(DLOAD dload);

	public void visitDCONST(DCONST dconst);

	public void visitNEW(NEW var_new);

	public void visitIFNULL(IFNULL ifnull);

	public void visitLSUB(LSUB lsub);

	public void visitL2I(L2I l2i);

	public void visitISHR(ISHR ishr);

	public void visitTABLESWITCH(TABLESWITCH tableswitch);

	public void visitIINC(IINC iinc);

	public void visitDRETURN(DRETURN dreturn);

	public void visitFSTORE(FSTORE fstore);

	public void visitDASTORE(DASTORE dastore);

	public void visitIALOAD(IALOAD iaload);

	public void visitDDIV(DDIV ddiv);

	public void visitIF_ICMPGE(IF_ICMPGE if_icmpge);

	public void visitLAND(LAND land);

	public void visitIDIV(IDIV idiv);

	public void visitLOR(LOR lor);

	public void visitCASTORE(CASTORE castore);

	public void visitFREM(FREM frem);

	public void visitLDC(LDC ldc);

	public void visitBIPUSH(BIPUSH bipush);

	public void visitDSTORE(DSTORE dstore);

	public void visitF2L(F2L f2l);

	public void visitFMUL(FMUL fmul);

	public void visitLLOAD(LLOAD lload);

	public void visitJSR(JSR jsr);

	public void visitFSUB(FSUB fsub);

	public void visitSASTORE(SASTORE sastore);

	public void visitALOAD(ALOAD aload);

	public void visitDUP2_X2(DUP2_X2 dup2_x2);

	public void visitRETURN(RETURN var_return);

	public void visitDALOAD(DALOAD daload);

	public void visitSIPUSH(SIPUSH sipush);

	public void visitDSUB(DSUB dsub);

	public void visitL2F(L2F l2f);

	public void visitIF_ICMPGT(IF_ICMPGT if_icmpgt);

	public void visitF2D(F2D f2d);

	public void visitI2L(I2L i2l);

	public void visitIF_ACMPNE(IF_ACMPNE if_acmpne);

	public void visitPOP(POP pop);

	public void visitI2S(I2S i2s);

	public void visitIFEQ(IFEQ ifeq);

	public void visitSWAP(SWAP swap);

	public void visitIOR(IOR ior);

	public void visitIREM(IREM irem);

	public void visitIASTORE(IASTORE iastore);

	public void visitNEWARRAY(NEWARRAY newarray);

	public void visitINVOKEINTERFACE(INVOKEINTERFACE invokeinterface);

	public void visitINEG(INEG ineg);

	public void visitLCMP(LCMP lcmp);

	public void visitJSR_W(JSR_W jsr_w);

	public void visitMULTIANEWARRAY(MULTIANEWARRAY multianewarray);

	public void visitDUP_X2(DUP_X2 dup_x2);

	public void visitSALOAD(SALOAD saload);

	public void visitIFNONNULL(IFNONNULL ifnonnull);

	public void visitDMUL(DMUL dmul);

	public void visitIFNE(IFNE ifne);

	public void visitIF_ICMPLE(IF_ICMPLE if_icmple);

	public void visitLDC2_W(LDC2_W ldc2_w);

	public void visitGETFIELD(GETFIELD getfield);

	public void visitLADD(LADD ladd);

	public void visitNOP(NOP nop);

	public void visitFALOAD(FALOAD faload);

	public void visitINSTANCEOF(INSTANCEOF var_instanceof);

	public void visitIFLE(IFLE ifle);

	public void visitLXOR(LXOR lxor);

	public void visitLRETURN(LRETURN lreturn);

	public void visitFCONST(FCONST fconst);

	public void visitIUSHR(IUSHR iushr);

	public void visitBALOAD(BALOAD baload);

	public void visitDUP2(DUP2 dup2);

	public void visitIF_ACMPEQ(IF_ACMPEQ if_acmpeq);

	public void visitIMPDEP1(IMPDEP1 impdep1);

	public void visitMONITORENTER(MONITORENTER monitorenter);

	public void visitLSHL(LSHL lshl);

	public void visitDCMPG(DCMPG dcmpg);

	public void visitD2L(D2L d2l);

	public void visitIMPDEP2(IMPDEP2 impdep2);

	public void visitL2D(L2D l2d);

	public void visitRET(RET ret);

	public void visitIFGT(IFGT ifgt);

	public void visitIXOR(IXOR ixor);

	public void visitINVOKEVIRTUAL(INVOKEVIRTUAL invokevirtual);

	public void visitFASTORE(FASTORE fastore);

	public void visitIRETURN(IRETURN ireturn);

	public void visitIF_ICMPNE(IF_ICMPNE if_icmpne);

	public void visitFLOAD(FLOAD fload);

	public void visitLDIV(LDIV ldiv);

	public void visitPUTSTATIC(PUTSTATIC putstatic);

	public void visitAALOAD(AALOAD aaload);

	public void visitD2I(D2I d2i);

	public void visitIF_ICMPEQ(IF_ICMPEQ if_icmpeq);

	public void visitAASTORE(AASTORE aastore);

	public void visitARETURN(ARETURN areturn);

	public void visitDUP2_X1(DUP2_X1 dup2_x1);

	public void visitFNEG(FNEG fneg);

	public void visitGOTO_W(GOTO_W goto_w);

	public void visitD2F(D2F d2f);

	public void visitGOTO(GOTO var_goto);

	public void visitISUB(ISUB isub);

	public void visitF2I(F2I f2i);

	public void visitDNEG(DNEG dneg);

	public void visitICONST(ICONST iconst);

	public void visitFDIV(FDIV fdiv);

	public void visitI2B(I2B i2b);

	public void visitLNEG(LNEG lneg);

	public void visitLREM(LREM lrem);

	public void visitIMUL(IMUL imul);

	public void visitIADD(IADD iadd);

	public void visitLSHR(LSHR lshr);

	public void visitLOOKUPSWITCH(LOOKUPSWITCH lookupswitch);

	public void visitDUP_X1(DUP_X1 dup_x1);

	public void visitFCMPL(FCMPL fcmpl);

	public void visitI2C(I2C i2c);

	public void visitLMUL(LMUL lmul);

	public void visitLUSHR(LUSHR lushr);

	public void visitISHL(ISHL ishl);

	public void visitLALOAD(LALOAD laload);

	public void visitASTORE(ASTORE astore);

	public void visitANEWARRAY(ANEWARRAY anewarray);

	public void visitFRETURN(FRETURN freturn);

	public void visitFADD(FADD fadd);

	public void visitBREAKPOINT(BREAKPOINT breakpoint);
}
