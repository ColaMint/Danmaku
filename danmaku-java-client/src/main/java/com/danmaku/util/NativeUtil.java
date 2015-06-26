package com.danmaku.util;

import java.io.File;

public class NativeUtil {

	static {
		if ("64".equals(System.getProperty("sun.arch.data.model"))) {
			System.load(new File("DanmakuNative_x64.dll").getAbsolutePath());
		} else {
			System.load(new File("DanmakuNative_x86.dll").getAbsolutePath());
		}
	}

	/* Color（R, G, B） define the specific background pixel to be transparent */
	public static native void SetWindowBackgroundTransparent(int R, int G,
			int B, String windowTitle);

}
