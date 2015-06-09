package com.danmaku.engine;

import java.awt.Component;

import com.danmaku.state.StateManager;

public class DanmakuEngine {
	
	private DanmakuPaintThread paintThread;
	private DanmakuFetchThread fetchThread;
	private DanmakuSet danmakuSet;
	
	private boolean hasStart = false;

	public DanmakuEngine(StateManager stateManager, Component paintBoard) {
		paintThread = new DanmakuPaintThread(stateManager,paintBoard);
		fetchThread = new DanmakuFetchThread(stateManager,danmakuSet);
		danmakuSet = new DanmakuSet(stateManager,paintBoard.getWidth(),paintBoard.getHeight());
	}
	
	public void start(){
		if(!hasStart){
			paintThread.start();
			fetchThread.start();
		}
	}
	
	public DanmakuSet getDanmakuSet(){
		return danmakuSet;
	}
}
