package com.danmaku.engine;

import java.awt.Component;

import com.danmaku.model.DanmakuSet;
import com.danmaku.model.UserSet;
import com.danmaku.state.StateManager;

public class Engine {

	private PaintThread paintThread;
	private FetchThread fetchThread;
	private DanmakuSet danmakuSet;
	private UserSet userSet;

	private boolean hasStart = false;

	public Engine(StateManager stateManager, Component paintBoard) {
		paintThread = new PaintThread(stateManager, paintBoard);
		userSet = new UserSet(stateManager);
		danmakuSet = new DanmakuSet(stateManager, paintBoard.getWidth(), paintBoard.getHeight());
		fetchThread = new FetchThread(stateManager, danmakuSet, userSet);

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
