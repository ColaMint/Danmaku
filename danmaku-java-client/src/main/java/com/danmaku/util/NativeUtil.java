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

	/* 把窗口背景中颜色为 Color（R, G, B）的像素点设置为透明、置顶 */
	public static native void SetWindowBackgroundTransparent(int R, int G,
			int B, String windowTitle);

}
