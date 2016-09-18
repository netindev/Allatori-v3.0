package com.allatori;

import java.util.Date;

public class DateUtils {
	
	/* OK */

	private static String string;
	private static Date date;
	private static ClassConstraint classConstraint;

	public static void setString(String newString) {
		string = newString;
	}

	public static void setClassConstraint(ClassConstraint newClassConstraint) {
		classConstraint = newClassConstraint;
	}

	public static Date getDate() {
		return date;
	}

	public static ClassConstraint getClassConstraint() {
		return classConstraint;
	}

	public static String getString() {
		return string;
	}

	public static void setDate(Date newDate) {
		date = newDate;
	}
}
