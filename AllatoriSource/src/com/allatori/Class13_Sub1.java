package com.allatori;

import java.util.Date;

public abstract class Class13_Sub1 extends Class13 {

	public static long getTime(Date date) {
		return date.getTime();
	}

	public static long newDate() {
		return getTime(new Date());
	}
}
