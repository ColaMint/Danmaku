package com.danmaku.engine;

import com.danmaku.model.DanmakuSet;
import com.danmaku.model.UserSet;
import com.danmaku.state.StateManager;

public class Engine extends StateManager implements PaintProcessManager {

	private Painter painter;
	private Collector collector;
	private DanmakuSet danmakuSet;
	private UserSet userSet;

	private boolean hasStart = false;

	public Engine() {
		userSet = new UserSet(this);
		danmakuSet = new DanmakuSet(this);
		painter = new Painter(this);
		collector = new Collector(this, danmakuSet, userSet);
	}

	public void run() {
		if (!hasStart) {
			painter.start();
			collector.start();
			hasStart = true;
		}
	}

	public DanmakuSet getDanmakuSet() {
		return danmakuSet;
	}

	public UserSet getUserSet() {
		return userSet;
	}

	@Override
	public void addPaintProcess(PaintProcess p) {
		// TODO Auto-generated method stub
		painter.addPaintProcess(p);
	}

	@Override
	public void removePaintProcess(PaintProcess p) {
		// TODO Auto-generated method stub
		painter.removePaintProcess(p);
	}

}
