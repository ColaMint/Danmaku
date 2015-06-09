package com.danmaku.util;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class ScreenUtil {
	private static int screenWidth;
	private static int screenHeight;
	private static int screenHeightWitoutTaskbar;
	private static int taskbarHeight;

	static {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle maximumWindowBounds = graphicsEnvironment
				.getMaximumWindowBounds();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		screenWidth = maximumWindowBounds.width;
		screenHeight = screenSize.height;
		screenHeightWitoutTaskbar = maximumWindowBounds.height;
		taskbarHeight = screenHeight - screenHeightWitoutTaskbar;

		LogUtil.printVar("ScreenWidth", screenWidth);
		LogUtil.printVar("screenHeight", screenHeight);
		LogUtil.printVar("screenHeightWitoutTaskbar", screenHeightWitoutTaskbar);
		LogUtil.printVar("TaskbarHeight", taskbarHeight);
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static int getscreenHeightWitoutTaskbar() {
		return screenHeightWitoutTaskbar;
	}

	public static int getTaskbarHeight() {
		return taskbarHeight;
	}
}
