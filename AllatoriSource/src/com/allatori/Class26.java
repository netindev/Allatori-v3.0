package com.allatori;

import java.util.Vector;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.SourceFile;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.LineNumberGen;
import org.apache.bcel.generic.MethodGen;

public class Class26 {

	private final RenamingMap aRenamingMap_566 = new RenamingMap();
	private final IObfuscationStyle anInterface22_567 = ObfuscationStyleUtils.method334();
	private final Vector<Object> aVector568 = new Vector<Object>();
	private final Vector<LogRepo> aVector569 = new Vector<LogRepo>();

	public void method494(MethodGen var1) {
		if (LineNumbers.getLineNumberType() != 2) {
			if (LineNumbers.getLineNumberType() == 3) {
				var1.removeLineNumbers();
			} else {
				int var3;
				if (LineNumbers.getLineNumberType() == 1) {
					LineNumberGen[] var2;
					for (int var10000 = var3 = (var2 = var1.getLineNumbers()).length
							- 1; var10000 >= 0; var10000 = var3) {
						LineNumberGen var4;
						int var5;
						if ((var5 = (var4 = var2[var3]).getSourceLine()) > this.aVector568.size() - 1) {
							final int var6 = this.aVector568.size();
							final int var7 = var5 - var6 + 200;
							final Vector<Object> var8 = new Vector<Object>(var7);

							int var9;
							for (var10000 = var9 = 0; var10000 < var7; var10000 = var9) {
								var8.add(var6 + var9);
								++var9;
							}

							Class34.arrangeOrder(var8);
							this.aVector568.addAll(var8);
						}

						var4.setSourceLine((Integer) this.aVector568.get(var5));
						--var3;
					}
				}
			}

		}
	}

	public void method495(String var1, String var2) {
		this.aVector569.add(new LogRepo(var1, var2));
		this.aRenamingMap_566.put(var1, var2);
	}

	public Vector<LogRepo> method496() {
		return this.aVector569;
	}

	public Vector<Object> method497() {
		return this.aVector568;
	}

	public void method498(ClassGen var1) {
		if (LineNumbers.getLineNumberType() != 2) {
			Attribute[] var2;
			int var3;
			Attribute var4;
			int var10000;
			if (LineNumbers.getLineNumberType() == 3) {
				for (var10000 = var3 = (var2 = var1.getAttributes()).length - 1; var10000 >= 0; var10000 = var3) {
					if ((var4 = var2[var3]) instanceof SourceFile) {
						var1.removeAttribute(var4);
						break;
					}

					--var3;
				}
			} else if (LineNumbers.getLineNumberType() == 1) {
				for (var10000 = var3 = (var2 = var1.getAttributes()).length - 1; var10000 >= 0; var10000 = var3) {
					if ((var4 = var2[var3]) instanceof SourceFile) {
						SourceFile var5;
						final String var6 = (var5 = (SourceFile) var4).getSourceFileName();
						String var7;
						if ((var7 = this.aRenamingMap_566.get(var6)) == null) {
							var7 = this.anInterface22_567.next();
							this.aVector569.add(new LogRepo(var6, var7));
							this.aRenamingMap_566.put(var6, var7);
						}

						var5.setSourceFileIndex(var1.getConstantPool().addUtf8(var7));
						break;
					}

					--var3;
				}
			}

		}
	}
}
