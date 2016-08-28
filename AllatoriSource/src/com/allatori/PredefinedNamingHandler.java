package com.allatori;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PredefinedNamingHandler extends DefaultHandler {

	private String aString966;
	private final Class166 aClass166_967;
	private String aString968;

	public PredefinedNamingHandler(Class166 var1) {
		this.aClass166_967 = var1;
		var1.aRenamingMap_852 = new RenamingMap();
		var1.aRenamingMap_854 = new RenamingMap();
		var1.aVector853 = new Vector();
	}

	@Override
	public void startElement(String var1, String var2, String var3, Attributes var4) throws SAXException {
		if ("class".equals(var3)) {
			this.aString966 = var4.getValue("old") + ".";
			this.aString968 = var4.getValue("new") + ".";
			this.aClass166_967.aRenamingMap_852.put(this.aString968 + "<init>", this.aString966 + "<init>");
			this.aClass166_967.aRenamingMap_852.put(this.aString968 + "<clinit>", this.aString966 + "<clinit>");
		} else {
			String var5;
			String var6;
			if ("method".equals(var3)) {
				var5 = var4.getValue("old");
				var6 = var4.getValue("new");
				final String var7 = var5.substring(0, var5.indexOf(40));
				this.aClass166_967.aRenamingMap_852.put(this.aString968 + var6, this.aString966 + var7);
			} else if ("source".equals(var3)) {
				var5 = var4.getValue("old");
				var6 = var4.getValue("new");
				this.aClass166_967.aRenamingMap_854.put(var6, var5);
			} else if ("line".equals(var3)) {
				final Integer var8 = Integer.valueOf(var4.getValue("l"));
				this.aClass166_967.aVector853.add(var8);
			}
		}

	}
}
