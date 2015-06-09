package com.danmaku.util;

public class LogUtil {

	public static void printMessage(String mesg) {
		System.out.println(mesg);
	}

	public static void printVar(String valName, Object val) {
		System.out.println(valName + ":" + val.toString());
	}
}
