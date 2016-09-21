package com.allatori;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.Type;

public class Class56 {

	private static final Vector<?> aVector605 = new Vector<Object>();
	private final Hashtable<String, Vector<ClassGen>> aHashtable607 = new Hashtable<String, Vector<ClassGen>>();
	private final ClassStorage aClassStorage_608;
	private Vector<ClassGen> aVector609;
	private final Hashtable<String, String> aHashtable610 = new Hashtable<String, String>();
	private static final Set<?> aSet611 = new TreeSetImpl();
	private final Set<Object> aSet612 = new TreeSet<Object>();

	public Vector<?> method691(String var1) {
		Vector<?> var2;
		return (var2 = this.aHashtable607.get(var1)) != null ? var2 : aVector605;
	}

	private Set<Object> method692(Set<?> var1, Set<?> var2) {
		final TreeSet<Object> var3 = new TreeSet<Object>();
		final Set<?> var4 = var1.size() < var2.size() ? var1 : var2;
		final Set<?> var5 = var1.size() < var2.size() ? var2 : var1;

		Iterator<?> var6;
		for (Iterator<?> var10000 = var6 = var4.iterator(); var10000.hasNext(); var10000 = var6) {
			final Object var7 = var6.next();
			if (var5.contains(var7)) {
				var3.add(var7);
			}
		}

		return var3;
	}

	private Set<?> method693(Set<Object> var1) {
		if (var1.contains("-=404=-")) {
			return aSet611;
		} else {
			final TreeSet<Object> var2 = new TreeSet<Object>();

			Iterator<Object> var3;
			for (Iterator<Object> var10000 = var3 = var1.iterator(); var10000.hasNext(); var10000 = var3) {
				final String var4 = (String) var3.next();
				if (this.aClassStorage_608.getClassGen(var4) == null) {
					int var7;
					try {
						java.lang.reflect.Method[] var6;
						for (int var10 = var7 = (var6 = Class.forName(var4, false, this.aClassStorage_608.getClassLoader())
								.getDeclaredMethods()).length - 1; var10 >= 0; var10 = var7) {
							final java.lang.reflect.Method var8 = var6[var7];
							if (this.method706(var8)) {
								var2.add(var8.getName() + "&" + Type.getSignature(var8));
							}

							--var7;
						}
					} catch (final ClassNotFoundException var9) {
					}
				}
			}

			return var2;
		}
	}

	public boolean method694(String var1, String var2, String var3) {
		return this.aSet612.contains(var1 + "&" + var2 + "&" + var3);
	}

	private void method695(ClassGen var1) {
		this.method701(var1.getSuperclassName(), var1);

		String[] var2;
		int var3;
		for (int var10000 = var3 = (var2 = var1.getInterfaceNames()).length - 1; var10000 >= 0; var10000 = var3) {
			this.method701(var2[var3], var1);
			--var3;
		}

	}

	private Set<?> method696(ClassGen var1) {
		final TreeSet<Object> var2 = new TreeSet<Object>();

		Method[] var3;
		int var4;
		for (int var10000 = var4 = (var3 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var4) {
			final Method var5 = var3[var4];
			if (this.method698(var5)) {
				var2.add(var5.getName() + "&" + var5.getSignature());
			}

			--var4;
		}

		return var2;
	}

	private void method697() {
		final Hashtable<ClassGen, String> var1 = new Hashtable<ClassGen, String>();

		int var2;
		for (int var10000 = var2 = 0; var10000 < this.aVector609.size(); var10000 = var2) {
			final ClassGen var3 = this.aVector609.get(var2);
			var1.put(var3, "");
			final Vector<?> var4 = this.method691(var3.getClassName());

			int var5;
			for (var10000 = var5 = 0; var10000 < var4.size(); var10000 = var5) {
				final ClassGen var6 = (ClassGen) var4.get(var5);
				if (var1.containsKey(var6)) {
					Logger.printError("Incorrect classes order");
				}

				++var5;
			}

			++var2;
		}

	}

	private boolean method698(Method var1) {
		return !var1.isPrivate() && !var1.isStatic();
	}

	public void method699() {
		Iterator<?> var1;
		for (Iterator<?> var10000 = var1 = this.aClassStorage_608.valuesIterator(); var10000.hasNext(); var10000 = var1) {
			final ClassGen var2 = (ClassGen) var1.next();
			this.method695(var2);
			this.method707(var2);
		}

		this.method705();
	}

	private void method700(Set<Object> var1, Set<?> var2) {
		Iterator<Object> var3;
		for (Iterator<Object> var10000 = var3 = var1.iterator(); var10000.hasNext(); var10000 = var3) {
			final String var4 = (String) var3.next();
			ClassGen var5;
			if (!"-=404=-".equals(var4) && (var5 = this.aClassStorage_608.getClassGen(var4)) != null
					&& var5.isInterface()) {
				final Set<?> var6 = this.method696(var5);
				final Iterator<Object> var8 = this.method692(var6, var2).iterator();

				for (var10000 = var8; var10000.hasNext(); var10000 = var8) {
					this.aSet612.add(var5.getClassName() + "&" + var8.next());
				}
			}
		}

	}

	public Class56(ClassStorage var1) {
		this.aClassStorage_608 = var1;
	}

	private void method701(String var1, ClassGen var2) {
		if (this.aClassStorage_608.getClassGen(var1) != null) {
			Vector<ClassGen> var3;
			if ((var3 = this.aHashtable607.get(var1)) == null) {
				var3 = new Vector<ClassGen>();
			}

			var3.add(var2);
			this.aHashtable607.put(var1, var3);
		}

	}

	private Set<Object> method702(ClassGen var1) {
		final TreeSet<Object> var2 = new TreeSet<Object>();
		this.method708(var2, var1.getClassName());
		return var2;
	}

	public Vector<ClassGen> method703() {
		return this.aVector609;
	}

	private void method704(ClassGen var1) {
		this.aVector609.remove(var1);
		this.aVector609.add(0, var1);
		final String var2 = var1.getSuperclassName();
		ClassGen var3;
		if ((var3 = this.aClassStorage_608.getClassGen(var2)) != null) {
			this.method704(var3);
		}

		String[] var4;
		int var5;
		for (int var10000 = var5 = (var4 = var1.getInterfaceNames()).length - 1; var10000 >= 0; var10000 = var5) {
			ClassGen var6;
			if ((var6 = this.aClassStorage_608.getClassGen(var4[var5])) != null) {
				this.method704(var6);
			}

			--var5;
		}

	}

	private void method705() {
		final Vector<Object> var1 = new Vector<Object>();

		Iterator<?> var2;
		Iterator<?> var10000;
		for (var10000 = var2 = this.aClassStorage_608.valuesIterator(); var10000.hasNext(); var10000 = var2) {
			var1.add(var2.next());
		}

		Collections.sort(var1, new ClassGenComparator());
		this.aVector609 = new Vector<ClassGen>();

		Iterator<?> var3;
		for (var10000 = var3 = var1.iterator(); var10000.hasNext(); var10000 = var3) {
			final ClassGen var4 = (ClassGen) var3.next();
			if (this.method691(var4.getClassName()).size() == 0) {
				this.method704(var4);
			}
		}

		Collections.sort(this.aVector609, new InterfaceComparator());
		this.method697();
	}

	private boolean method706(java.lang.reflect.Method var1) {
		return !Modifier.isPrivate(var1.getModifiers()) && !Modifier.isStatic(var1.getModifiers());
	}

	private void method707(ClassGen var1) {
		final Set<?> var2 = this.method696(var1);
		final Set<Object> var3 = this.method702(var1);
		final Set<?> var4 = this.method693(var3);
		final Iterator<Object> var6 = this.method692(var2, var4).iterator();

		for (Iterator<Object> var10000 = var6; var10000.hasNext(); var10000 = var6) {
			this.aSet612.add(var1.getClassName() + "&" + var6.next());
		}

		this.method700(var3, var4);
	}

	private void method708(Set<Object> var1, String var2) {
		var1.add(var2);
		ClassGen var3;
		int var10000;
		if ((var3 = this.aClassStorage_608.getClassGen(var2)) != null) {
			this.method708(var1, var3.getSuperclassName());

			String[] var4;
			int var5;
			for (var10000 = var5 = (var4 = var3.getInterfaceNames()).length - 1; var10000 >= 0; var10000 = var5) {
				this.method708(var1, var4[var5]);
				--var5;
			}
		} else {
			try {
				Class<?> var8;
				if ((var8 = Class.forName(var2, false, this.aClassStorage_608.getClassLoader())).getSuperclass() != null) {
					this.method708(var1, var8.getSuperclass().getName());
				}

				int var6;
				Class<?>[] var9;
				for (var10000 = var6 = (var9 = var8.getInterfaces()).length - 1; var10000 >= 0; var10000 = var6) {
					this.method708(var1, var9[var6].getName());
					--var6;
				}
			} catch (final ClassNotFoundException var7) {
				var1.add("-=404=-");
				if (!this.aHashtable610.containsKey(var2)) {
					this.aHashtable610.put(var2, "");
					Logger.printWarning("Class \'" + var2
							+ "\' cannot be found. It may result in weaker obfuscation. Add needed jars to the \'classpath\' element of the configuration file.");
				}
			}
		}

	}

}
