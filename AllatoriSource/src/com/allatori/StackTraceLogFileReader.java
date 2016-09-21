package com.allatori;

import java.io.Reader;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class StackTraceLogFileReader {
	
	/* OK */

	public static StackTraceLog readLogFile(Reader reader) throws Exception {
		final XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		final StackTraceLog stackTraceLog = new StackTraceLog();
		xmlReader.setContentHandler(new PredefinedNamingHandler(stackTraceLog));
		xmlReader.parse(new InputSource(reader));
		return stackTraceLog;
	}
}
