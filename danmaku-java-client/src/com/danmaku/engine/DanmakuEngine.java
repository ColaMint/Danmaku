package com.danmaku.engine;

import java.awt.Component;

import com.danmaku.state.StateManager;

public class DanmakuEngine {

	private DanmakuPaintThread paintThread;
	private DanmakuFetchThread fetchThread;
	private DanmakuSet danmakuSet;
	private UserSet userSet;

	private boolean hasStart = false;

	public DanmakuEngine(StateManager stateManager, Component paintBoard) {
		paintThread = new DanmakuPaintThread(stateManager, paintBoard);
		userSet = new UserSet(stateManager);
		danmakuSet = new DanmakuSet(stateManager, paintBoard.getWidth(), paintBoard.getHeight());
		fetchThread = new DanmakuFetchThread(stateManager, danmakuSet, userSet);

	}

	public void run() {
		if (!hasStart) {
			paintThread.start();
			fetchThread.start();
		}
	}

	public DanmakuSet getDanmakuSet() {
		return danmakuSet;
	}
}
