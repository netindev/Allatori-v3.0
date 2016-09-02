package com.allatori;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.allatori.obfuscate.opt.AntiJD;

public class ConfigFileHandler extends DefaultHandler {

	private ClassConstraint aClassConstraint_953;
	private String aString954;
	private Locator locator;
	private final ConfigFile configFile;
	private int anInt959;
	private String aString960;

	private MethodConstraint method1825(String var1, String var2) throws SAXException_Sub1 {
		return this.createMethodFilter(var1, var2, "template");
	}

	private void parseAttributes(Attributes attributes) throws SAXException_Sub1 {
		final String name = this.getAttributeByName(attributes, "name", true, null, true);
		String value = this.getAttributeByName(attributes, "value", true, null, true);
		final String apply2class = this.getAttributeByName(attributes, "apply2class", false, "private+ class *", true);
		final String apply2method = this.getAttributeByName(attributes, "apply2method", false, "private+ *(**)", true);
		int stringEncryptionLevel;
		ClassConstraint classConstraint;
		if ("string-encryption".equals(name)) {
			stringEncryptionLevel = Tuning.STRING_ENCRYPTION_DEFAULT;
			if ("enable".equals(value)) {
				stringEncryptionLevel = Tuning.STRING_ENCRYPTION_DEFAULT;
			} else if ("disable".equals(value)) {
				stringEncryptionLevel = Tuning.STRING_ENCRYPTION_DISABLE;
			} else if ("maximum".equals(value)) {
				stringEncryptionLevel = Tuning.STRING_ENCRYPTION_MAXIMUM;
			} else {
				this.throw4AttributeException("value", "enable", "disable", "maximum");
			}

			if ("private+ class *".equals(apply2class) && "private+ *(**)".equals(apply2method)) {
				Tuning.setStringEncryptionLevel(stringEncryptionLevel);
			}

			(classConstraint = this.createClassFilter(apply2class, null, null, null, "apply2class"))
					.addMethodConstraint(this.createMethodFilter(apply2method, null, "apply2method"));
			Tuning.setStringObfuscationConstraint(
					new ObfuscationTypeConstraint(stringEncryptionLevel, classConstraint));
		} else if ("member-reorder".equals(name)) {
			if ("enable".equals(value)) {
				Tuning.enableMemberReorder(true);
			} else if ("disable".equals(value)) {
				Tuning.enableMemberReorder(false);
			} else {
				this.throw3AttributeException("value", "enable", "disable");
			}
		} else if ("control-flow-obfuscation".equals(name)) {
			if ("enable".equals(value)) {
				Tuning.enableControlFlowObfuscation(true);
			} else if ("disable".equals(value)) {
				Tuning.enableControlFlowObfuscation(false);
			} else {
				this.throw3AttributeException("value", "enable", "disable");
			}
		} else {
			byte localVarNamingType;
			if ("local-variables-naming".equals(name)) {
				localVarNamingType = 1;
				if ("single-name".equals(value)) {
					localVarNamingType = 1;
				} else if ("abc".equals(value)) {
					localVarNamingType = 2;
				} else if ("keep".equals(value)) {
					localVarNamingType = 3;
				} else if ("remove".equals(value)) {
					localVarNamingType = 4;
				} else if ("keep-parameters".equals(value)) {
					localVarNamingType = 5;
				} else {
					this.throws6AttributeException("value", "single-name", "abc", "keep", "remove", "keep-parameters");
				}

				if ("private+ class *".equals(apply2class) && "private+ *(**)".equals(apply2method)) {
					LocalVariables.setLocalVariableNamingType(localVarNamingType);
				}

				(classConstraint = this.createClassFilter(apply2class, null, null, null, "apply2class"))
						.addMethodConstraint(this.createMethodFilter(apply2method, null, "apply2method"));
				LocalVariables.addObfuscationTypeConstraint(
						new ObfuscationTypeConstraint(localVarNamingType, classConstraint));
			} else if ("fields-naming".equals(name)) {
				if ("abc".equals(value)) {
					Unknown.setFieldNamingType(1);
				} else if ("compact".equals(value)) {
					Unknown.setFieldNamingType(2);
				} else if ("keywords".equals(value)) {
					Unknown.setFieldNamingType(3);
				} else {
					this.throw4AttributeException("value", "abc", "compact", "keywords");
				}
			} else if ("methods-naming".equals(name)) {
				if ("abc".equals(value)) {
					Unknown.setMethodNamingType(1);
				} else if ("compact".equals(value)) {
					Unknown.setMethodNamingType(2);
				} else if ("keywords".equals(value)) {
					Unknown.setMethodNamingType(3);
				} else {
					this.throw4AttributeException("value", "abc", "compact", "keywords");
				}
			} else if ("default-package".equals(name)) {
				for (String var10000 = value; var10000
						.endsWith("."); var10000 = value = value.substring(0, value.length() - 1)) {
				}

				Packaging.setDefaultPackage(value);
			} else if ("force-default-package".equals(name)) {
				if ("enable".equals(value)) {
					Packaging.forceDefaultPackage(true);
				} else if ("disable".equals(value)) {
					Packaging.forceDefaultPackage(false);
				} else {
					this.throw3AttributeException("value", "enable", "disable");
				}
			} else if ("line-numbers".equals(name)) {
				if ("keep".equals(value)) {
					LineNumbers.setLineNumberType(2);
				} else if ("remove".equals(value)) {
					LineNumbers.setLineNumberType(3);
				} else if ("obfuscate".equals(value)) {
					LineNumbers.setLineNumberType(1);
				} else {
					this.throw4AttributeException("value", "keep", "remove", "obfuscate");
				}
			} else {
				String absoluteParent;
				String canonicalParent;
				if ("log-file".equals(name)) {
					if (value.length() == 0) {
						this.throw1AttributeException("value");
					}

					if (!(new File(value)).isAbsolute()) {
						try {
							if ((canonicalParent = (new File(ConfigFile.getFileName(this.configFile)))
									.getCanonicalFile().getParent()) != null) {
								value = (new File(canonicalParent + File.separator + value)).getCanonicalPath();
							}
						} catch (final Exception var9) {
							if ((absoluteParent = (new File(ConfigFile.getFileName(this.configFile))).getAbsoluteFile()
									.getParent()) != null) {
								value = (new File(absoluteParent + File.separator + value)).getAbsolutePath();
							}
						}
					}

					Class57.setLogFile(value);
				} else if ("random-seed".equals(name)) {
					Tuning.setRandomSeed(value);
				} else if ("incremental-obfuscation".equals(name)) {
					if (value.length() == 0) {
						this.throw1AttributeException("value");
					}

					if (!(new File(value)).isAbsolute()) {
						try {
							if ((canonicalParent = (new File(ConfigFile.getFileName(this.configFile)))
									.getCanonicalFile().getParent()) != null) {
								value = (new File(canonicalParent + File.separator + value)).getCanonicalPath();
							}
						} catch (final Exception var8) {
							if ((absoluteParent = (new File(ConfigFile.getFileName(this.configFile))).getAbsoluteFile()
									.getParent()) != null) {
								value = (new File(absoluteParent + File.separator + value)).getAbsolutePath();
							}
						}
					}

					if (!(new File(value)).exists()) {
						throw new SAXException_Sub1("Cannot find file \'" + value + "\'. " + this.method1839());
					}

					Packaging.method577(value);
				} else if ("string-encryption-type".equals(name)) {
					stringEncryptionLevel = Tuning.STRING_ENCRYPTION_DEFAULT_TYPE;
					if ("fast".equals(value)) {
						stringEncryptionLevel = Tuning.STRING_ENCRYPTION_DEFAULT_TYPE;
					} else if ("strong".equals(value)) {
						stringEncryptionLevel = Tuning.STRING_ENCRYPTION_STRONG_TYPE;
					} else {
						this.throw3AttributeException("value", "fast", "strong");
					}

					if ("private+ class *".equals(apply2class) && "private+ *(**)".equals(apply2method)) {
						Tuning.setStringEncryptionType(stringEncryptionLevel);
					}

					(classConstraint = this.createClassFilter(apply2class, null, null, null, "apply2class"))
							.addMethodConstraint(this.createMethodFilter(apply2method, null, "apply2method"));
					Tuning.setStringEncryptionTypeConstraint(
							new ObfuscationTypeConstraint(stringEncryptionLevel, classConstraint));
				} else if ("finalize".equals(name)) {
					if ("enable".equals(value)) {
						Tuning.enableFinalizing(true);
					} else if ("disable".equals(value)) {
						Tuning.enableFinalizing(false);
					} else {
						this.throw3AttributeException("value", "enable", "disable");
					}
				} else {
					if (!"anti-jd".equals(name)) {
						throw new SAXException_Sub1("Unknown property \'" + name + "\'. " + this.method1839());
					}

					localVarNamingType = 0;
					if ("disable".equals(value)) {
						localVarNamingType = 0;
					} else if ("normal".equals(value)) {
						localVarNamingType = 1;
					} else if ("maximum".equals(value)) {
						localVarNamingType = 2;
					} else {
						this.throw4AttributeException("value", "disable", "normal", "maximum");
					}

					if ("private+ class *".equals(apply2class) && "private+ *(**)".equals(apply2method)) {
						AntiJD.setType(localVarNamingType);
					}

					(classConstraint = this.createClassFilter(apply2class, null, null, null, "apply2class"))
							.addMethodConstraint(this.createMethodFilter(apply2method, null, "apply2method"));
					AntiJD.addObfuscationTypeConstraint(
							new ObfuscationTypeConstraint(localVarNamingType, classConstraint));
				}
			}
		}

	}

	private void throw1AttributeException(String var1) throws SAXException_Sub1 {
		throw new SAXException_Sub1("Invalid \'" + var1 + "\' attribute. " + this.method1839());
	}

	@Override
	public void startElement(String var1, String var2, String var3, Attributes var4) throws SAXException {
		if (!"config".equals(var3) && !"keep-names".equals(var3)) {
			String var5;
			if ("jars".equals(var3)) {
				this.anInt959 = 1;
				this.aString954 = this.getAttributeByName(var4, "basedir", false, "", true);
				this.aString954 = this.method1845(this.aString954);
				if (!"".equals(this.aString954) && !this.method1830(this.aString954)) {
					throw new SAXException_Sub1(
							"Invalid base directory \'" + this.aString954 + "\'. " + this.method1839());
				}

				if ((var5 = this.getAttributeByName(var4, "single-jar", false, null, true)) != null
						&& !(new File(var5)).isAbsolute()) {
					try {
						var5 = (new File(this.aString954 + File.separator + var5)).getCanonicalPath();
					} catch (final Exception var15) {
						var5 = (new File(this.aString954 + File.separator + var5)).getAbsolutePath();
					}
				}

				Class45.method658(var5);
			} else {
				String var6;
				if ("jar".equals(var3) && this.anInt959 == 1) {
					var5 = this.getAttributeByName(var4, "in", true, "", true);
					var6 = this.getAttributeByName(var4, "out", !Class167.method1659(), "", true);
					this.method1836(var5, var6);
				} else if ("dir".equals(var3) && this.anInt959 == 1) {
					var5 = this.getAttributeByName(var4, "in", true, "", true);
					var6 = this.getAttributeByName(var4, "out", !Class167.method1659(), "", true);
					this.method1829(var5, var6);
				} else if ("jar".equals(var3) && this.anInt959 == 2) {
					var5 = this.getAttributeByName(var4, "name", true, "", true);
					this.method1835(var5);
				} else if ("classpath".equals(var3)) {
					this.anInt959 = 2;
					this.aString960 = this.getAttributeByName(var4, "basedir", false, "", true);
					this.aString960 = this.method1845(this.aString960);
					if (!"".equals(this.aString960) && !this.method1830(this.aString960)) {
						throw new SAXException_Sub1(
								"Invalid base directory \'" + this.aString960 + "\'. " + this.method1839());
					}
				} else {
					String var7;
					String var8;
					if ("class".equals(var3)) {
						this.anInt959 = 3;
						var5 = this.getAttributeByName(var4, "template", false, null, true);
						var6 = this.getAttributeByName(var4, "access", false, null, true);
						var7 = this.getAttributeByName(var4, "ignore", false, null, true);
						var8 = this.getAttributeByName(var4, "stop", false, null, true);
						this.aClassConstraint_953 = this.createClassFilter(var5, var6, var7, var8, "template");
						Class28.method502(this.aClassConstraint_953);
					} else if ("field".equals(var3)) {
						var5 = this.getAttributeByName(var4, "template", false, null, true);
						var6 = this.getAttributeByName(var4, "access", false, null, true);
						final FieldConstraint var16 = this.method1842(var5, var6);
						if (this.anInt959 == 3) {
							if (this.aClassConstraint_953 == null) {
								throw new SAXException_Sub1("Error in configuration. " + this.method1839());
							}

							this.aClassConstraint_953.addFieldConstraint(var16);
						} else {
							Class28.method501(var16);
						}
					} else {
						ClassConstraint var9;
						if ("method".equals(var3)) {
							var5 = this.getAttributeByName(var4, "template", false, null, true);
							var6 = this.getAttributeByName(var4, "access", false, null, true);
							var7 = this.getAttributeByName(var4, "parameters", false, null, true);
							final MethodConstraint var19 = this.method1825(var5, var6);
							if (this.anInt959 == 3) {
								if (this.aClassConstraint_953 == null) {
									throw new SAXException_Sub1("Error in configuration. " + this.method1839());
								}

								this.aClassConstraint_953.addMethodConstraint(var19);
								if ("keep".equals(var7)) {
									(var9 = this.aClassConstraint_953.clone()).addMethodConstraint(var19);
									LocalVariables.addObfuscationTypeConstraint(new ObfuscationTypeConstraint(5, var9));
								}
							} else {
								Class28.method508(var19);
								if ("keep".equals(var7)) {
									try {
										(var9 = new ClassConstraint("class *", false, false))
												.addMethodConstraint(var19);
										LocalVariables
												.addObfuscationTypeConstraint(new ObfuscationTypeConstraint(5, var9));
									} catch (final TemplateException var14) {
									}
								}
							}
						} else if ("watermark".equals(var3)) {
							var5 = this.getAttributeByName(var4, "key", true, null, false);
							if ("".equals(var5)) {
								this.method1831("key");
							}

							Class167.method1657(var5);
							if (!Class167.method1659()) {
								var6 = this.getAttributeByName(var4, "value", true, null, false);
								if ("".equals(var6)) {
									this.method1831("value");
								}

								Class167.method1658(var6);
							}
						} else if ("property".equals(var3)) {
							this.parseAttributes(var4);
						} else if ("trial".equals(var3)) {
							var5 = this.getAttributeByName(var4, "class", true, null, true);
							var6 = this.getAttributeByName(var4, "method", true, null, true);
							Class180.method1774(var5);
							Class180.method1773(var6);
							var7 = this.getAttributeByName(var4, "add2class", false, "private+ class *", true);
							ClassConstraint var18;
							Class180.method1779(var18 = this.createClassFilter(var7, null, null, null, "add2class"));
							final String var23 = this.getAttributeByName(var4, "add2method", false,
									"private+ <init>(**)", true);
							final MethodConstraint var10 = this.method1825(var23, null);
							var18.addMethodConstraint(var10);
							final String var11 = this.getAttributeByName(var4, "passthis", false, "no", true);
							if (!"yes".equals(var11) && !"true".equals(var11)) {
								Class180.method1775(false);
							} else {
								Class180.method1775(true);
							}
						} else {
							if (!"expiry".equals(var3)) {
								throw new SAXException_Sub1("Unknown tag \'" + var3 + "\'. " + this.method1839());
							}

							var5 = this.getAttributeByName(var4, "date", true, null, true);
							Matcher var17;
							if (!(var17 = Pattern.compile("(\\d{4}).(\\d{2}).(\\d{2})").matcher(var5)).matches()) {
								throw new SAXException_Sub1("Date format is yyyy-mm-dd. " + this.method1839());
							}

							try {
								Calendar var20;
								(var20 = Calendar.getInstance()).set(Integer.parseInt(var17.group(1)),
										Integer.parseInt(var17.group(2)) - 1, Integer.parseInt(var17.group(3)), 0, 0,
										0);
								Date var21;
								Class64.method749(var21 = var20.getTime());
								final SimpleDateFormat var22 = new SimpleDateFormat("MMMMM dd, yyyy");
								Logger.printInfo("Expiry date set to " + var22.format(var21));
							} catch (final Exception var13) {
								throw new SAXException_Sub1("Date format is yyyy-mm-dd. " + this.method1839());
							}

							Class64.method744(this.getAttributeByName(var4, "string", true, null, true));
							var8 = this.getAttributeByName(var4, "add2class", false, "private+ class *", true);
							Class64.method745(var9 = this.createClassFilter(var8, null, null, null, "add2class"));
							final String var25 = this.getAttributeByName(var4, "add2method", false, "no input value",
									true);
							MethodConstraint var24;
							if ("no input value".equals(var25)) {
								var24 = this.method1825("private+ <init>(**)", null);
								var9.addMethodConstraint(var24);
								final MethodConstraint var12 = this.method1825("public static void main(**)", null);
								var9.addMethodConstraint(var12);
							} else {
								var24 = this.createMethodFilter(var25, null, "add2method");
								var9.addMethodConstraint(var24);
							}
						}
					}
				}
			}
		}

	}

	@Override
	public void setDocumentLocator(Locator var1) {
		if (var1 != null) {
			this.locator = var1;
		}

	}

	private void method1828(String var1, String var2) throws SAXException_Sub1 {
		throw new SAXException_Sub1("Either \'" + "template" + "\' or \'" + "access"
				+ "\' attribute should be defined. " + this.method1839());
	}

	// $FF: synthetic method
	public ConfigFileHandler(ConfigFile var1, LocatorImpl var2) {
		this(var1);
	}

	private void method1829(String var1, String var2) throws SAXException_Sub1 {
		if (this.aString954.length() > 0) {
			try {
				if (var1 != null && !(new File(var1)).isAbsolute()) {
					var1 = (new File(this.aString954 + File.separator + var1)).getCanonicalPath();
				}

				if (var2 != null && !(new File(var2)).isAbsolute()) {
					var2 = (new File(this.aString954 + File.separator + var2)).getCanonicalPath();
				}
			} catch (final Exception var4) {
				if (var1 != null && !(new File(var1)).isAbsolute()) {
					var1 = (new File(this.aString954 + File.separator + var1)).getAbsolutePath();
				}

				if (var2 != null && !(new File(var2)).isAbsolute()) {
					var2 = (new File(this.aString954 + File.separator + var2)).getAbsolutePath();
				}
			}
		}

		if (!(new File(var1)).exists()) {
			throw new SAXException_Sub1("Cannot find directory \'" + var1 + "\'. " + this.method1839());
		} else if (!(new File(var1)).isDirectory()) {
			throw new SAXException_Sub1("Cannot find directory \'" + var1 + "\'. " + this.method1839());
		} else {
			Class45.method659(new Class157(var1, var2));
		}
	}

	private boolean method1830(String var1) {
		return var1.length() > 0 && (new File(var1)).isDirectory();
	}

	private void method1831(String var1) throws SAXException_Sub1 {
		throw new SAXException_Sub1("Attribute \'" + var1 + "\' cannot have zero length. " + this.method1839());
	}

	private void method1833(String var1, String var2) throws SAXException_Sub1 {
		if (var1 == null && var2 == null) {
			this.method1828("template", "access");
		}

	}

	private void throws6AttributeException(String var1, String var2, String var3, String var4, String var5, String var6)
			throws SAXException_Sub1 {
		throw new SAXException_Sub1("Attribute \'" + "value" + "\' should have one of the following values: \'"
				+ "single-name" + "\', \'" + "abc" + "\', \'" + "keep" + "\', \'" + "remove" + "\' or \'"
				+ "keep-parameters" + "\'. " + this.method1839());
	}

	private void method1835(String var1) throws SAXException_Sub1 {
		if (this.aString960.length() > 0 && !(new File(var1)).isAbsolute()) {
			try {
				var1 = (new File(this.aString960 + File.separator + var1)).getCanonicalPath();
			} catch (final Exception var6) {
				var1 = (new File(this.aString960 + File.separator + var1)).getAbsolutePath();
			}
		}

		while (var1.endsWith(File.separator)) {
			var1 = var1.substring(0, var1.length() - File.separator.length());
		}

		if (var1.indexOf(42) == -1) {
			Class72.method833(var1);
		} else {
			String var2 = ".";
			String var3 = var1;
			if (var1.contains(File.separator) || var1.indexOf(47) >= 0) {
				int var4 = var1.lastIndexOf(File.separator);
				if (var1.lastIndexOf(47) > var4) {
					var4 = var1.lastIndexOf(47);
				}

				var2 = var1.substring(0, var4);
				var3 = var1.substring(var4 + 1);
			}

			boolean var7 = false;
			if (var2.endsWith("**")) {
				var2 = var2.substring(0, var2.length() - 2);
				var7 = true;
			}

			File var5;
			if (!(var5 = new File(var2)).isDirectory()) {
				throw new SAXException_Sub1("Cannot find directory \'" + var2 + "\'. " + this.method1839());
			} else {
				var3 = var3.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
				this.method1843(var5, var3, var7);
			}
		}
	}

	private void method1836(String var1, String var2) throws SAXException_Sub1 {
		if (this.aString954.length() > 0) {
			try {
				if (var1 != null && !(new File(var1)).isAbsolute()) {
					var1 = (new File(this.aString954 + File.separator + var1)).getCanonicalPath();
				}

				if (var2 != null && !(new File(var2)).isAbsolute()) {
					var2 = (new File(this.aString954 + File.separator + var2)).getCanonicalPath();
				}
			} catch (final Exception var14) {
				if (var1 != null && !(new File(var1)).isAbsolute()) {
					var1 = (new File(this.aString954 + File.separator + var1)).getAbsolutePath();
				}

				if (var2 != null && !(new File(var2)).isAbsolute()) {
					var2 = (new File(this.aString954 + File.separator + var2)).getAbsolutePath();
				}
			}
		}

		if (var1 != null && var1.contains("*")) {
			String var3 = ".";
			String var4 = var1;
			if (var1.contains(File.separator) || var1.indexOf(47) >= 0) {
				int var5 = var1.lastIndexOf(File.separator);
				if (var1.lastIndexOf(47) > var5) {
					var5 = var1.lastIndexOf(47);
				}

				var3 = var1.substring(0, var5);
				var4 = var1.substring(var5 + 1);
			}

			File var15;
			if (!(var15 = new File(var3)).isDirectory()) {
				throw new SAXException_Sub1("Cannot find directory \'" + var3 + "\'. " + this.method1839());
			} else {
				final Pattern var6 = Pattern.compile(var4.replaceAll("\\.", "\\\\.").replaceAll("\\*", "(.*)"));

				File[] var7;
				int var8;
				for (int var10000 = var8 = (var7 = var15.listFiles()).length - 1; var10000 >= 0; var10000 = var8) {
					File var9;
					Matcher var10;
					if (!(var9 = var7[var8]).isDirectory() && (var10 = var6.matcher(var9.getName())).matches()) {
						final String var11 = var10.group(1);
						final String var12 = var9.getAbsolutePath();
						String var13 = null;
						if (var2 != null) {
							var13 = var2.replaceFirst("\\*", var11);
						}

						Class45.method656(new Class149(var12, var13));
					}

					--var8;
				}

			}
		} else if (!(new File(var1)).exists()) {
			throw new SAXException_Sub1("Cannot find file \'" + var1 + "\'. " + this.method1839());
		} else {
			Class45.method656(new Class149(var1, var2));
		}
	}

	private String method1837(String var1, String var2, String var3) throws SAXException_Sub1 {
		String var4 = var1;
		if (var1 == null) {
			if (!Class115.method1390(var2)) {
				this.throw1AttributeException("access");
			}

			var4 = var2 + var3;
		}

		return var4;
	}

	private void throw4AttributeException(String var1, String var2, String var3, String var4) throws SAXException_Sub1 {
		throw new SAXException_Sub1("Attribute \'" + "value" + "\' should have one of the following values: \'" + var2
				+ "\', \'" + var3 + "\' or \'" + var4 + "\'. " + this.method1839());
	}

	private ConfigFileHandler(ConfigFile var1) {
		this.configFile = var1;
		this.anInt959 = -1;
		this.aString954 = "";
		this.aString960 = "";
		this.locator = new LocatorImpl(this);
	}

	private String method1839() {
		return "[Line: " + this.locator.getLineNumber() + "]";
	}

	private ClassConstraint createClassFilter(String apply2Class, String var2, String var3, String var4,
			String apply2ClassString) throws SAXException_Sub1 {
		this.method1833(apply2Class, var2);
		boolean var6 = false;
		if ("yes".equals(var3) || "true".equals(var3)) {
			var6 = true;
		}

		boolean var7 = false;
		if ("yes".equals(var4) || "true".equals(var4)) {
			var7 = true;
		}

		try {
			return new ClassConstraint(this.method1837(apply2Class, var2, " class *"), var6, var7);
		} catch (final TemplateException var9) {
			if (apply2Class != null) {
				this.throw1AttributeException(apply2ClassString);
			} else {
				this.throw1AttributeException("access");
			}

			return null;
		}
	}

	private void throw3AttributeException(String var1, String var2, String var3) throws SAXException_Sub1 {
		throw new SAXException_Sub1("Attribute \'" + "value" + "\' should have one of the following values: \'" + var2
				+ "\' or \'" + var3 + "\'. " + this.method1839());
	}

	@Override
	public void endElement(String var1, String var2, String var3) throws SAXException {
		if ("jars".equals(var3) || "classpath".equals(var3) || "class".equals(var3)) {
			this.anInt959 = -1;
		}

	}

	private FieldConstraint method1842(String var1, String var2) throws SAXException_Sub1 {
		this.method1833(var1, var2);

		try {
			return new FieldConstraint(this.method1837(var1, var2, " *"));
		} catch (final TemplateException var4Template) {
			if (var1 != null) {
				this.throw1AttributeException("template");
			} else {
				this.throw1AttributeException("access");
			}

			return null;
		}
	}

	private void method1843(File var1, String var2, boolean var3) {
		File[] var4;
		int var5;
		for (int var10000 = var5 = (var4 = var1.listFiles()).length - 1; var10000 >= 0; var10000 = var5) {
			File var6;
			if (!(var6 = var4[var5]).isDirectory() && var6.getName().matches(var2)) {
				Class72.method833(var6.getAbsolutePath());
			}

			if (var3 && var6.isDirectory()) {
				this.method1843(var6, var2, var3);
			}

			--var5;
		}

	}

	private String getAttributeByName(Attributes var1, String var2, boolean var3, String var4, boolean var5)
			throws SAXException_Sub1 {
		String var6;
		if ((var6 = var1.getValue(var2)) == null && var3) {
			throw new SAXException_Sub1("Missing \'" + var2 + "\' attribute. " + this.method1839());
		} else {
			return var6 == null ? var4 : (var5 ? var6.trim() : var6);
		}
	}

	private String method1845(String var1) {
		if ("".equals(var1) || !(new File(var1)).isAbsolute()) {
			try {
				String var2;
				if ((var2 = (new File(ConfigFile.getFileName(this.configFile))).getCanonicalFile()
						.getParent()) != null) {
					return (new File(var2 + File.separator + var1)).getCanonicalPath();
				}
			} catch (final Exception var4) {
				String var3;
				if ((var3 = (new File(ConfigFile.getFileName(this.configFile))).getAbsoluteFile()
						.getParent()) != null) {
					return (new File(var3 + File.separator + var1)).getAbsolutePath();
				}
			}
		}

		return var1;
	}

	private MethodConstraint createMethodFilter(String var1, String var2, String var3) throws SAXException_Sub1 {
		this.method1833(var1, var2);

		try {
			return new MethodConstraint(this.method1837(var1, var2, " *(**)"));
		} catch (final TemplateException var5) {
			if (var1 != null) {
				this.throw1AttributeException(var3);
			} else {
				this.throw1AttributeException("access");
			}

			return null;
		}
	}
}
