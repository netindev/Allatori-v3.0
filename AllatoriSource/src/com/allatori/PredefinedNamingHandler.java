package com.allatori;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PredefinedNamingHandler extends DefaultHandler {
	
	/* OK */

	private String oldString;
	private final StackTraceLog stackTraceLog;
	private String newString;

	public PredefinedNamingHandler(StackTraceLog stackTraceLog) {
		this.stackTraceLog = stackTraceLog;
		stackTraceLog.init = new RenamingMap();
		stackTraceLog.oldNewRep = new RenamingMap();
		stackTraceLog.vector = new Vector<Object>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("class".equals(qName)) {
			this.oldString = attributes.getValue("old") + ".";
			this.newString = attributes.getValue("new") + ".";
			this.stackTraceLog.init.put(this.newString + "<init>", this.oldString + "<init>");
			this.stackTraceLog.init.put(this.newString + "<clinit>", this.oldString + "<clinit>");
		} else {
			String oldS = attributes.getValue("old");
			String newS = attributes.getValue("new");
			if ("method".equals(qName)) {
				final String substring = oldS.substring(0, oldS.indexOf(40));
				this.stackTraceLog.init.put(this.newString + newS, this.oldString + substring);
			} else if ("source".equals(qName)) {
				this.stackTraceLog.oldNewRep.put(newS, oldS);
			} else if ("line".equals(qName)) {
				final Integer integer = Integer.valueOf(attributes.getValue("l"));
				this.stackTraceLog.vector.add(integer);
			}
		}
	}
}
