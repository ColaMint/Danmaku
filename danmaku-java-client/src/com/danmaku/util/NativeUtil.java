package com.danmaku.util;

public class NativeUtil {

	static {
		if ("64".equals(System.getProperty("sun.arch.data.model"))) {
			System.loadLibrary("DanmakuNative_x64");
		} else {
			System.loadLibrary("DanmakuNative_x86");
		}
	}

	/* Color£¨R, G, B£© define the specific background pixel to be transparent */
	public static native void SetWindowBackgroundTransparent(int R, int G,
			int B, String windowTitle);

}
