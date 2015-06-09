package com.danmaku.engine;

import java.awt.Component;

import com.danmaku.state.StateManager;

public class DanmakuPaintThread extends BaseThread {

	private Component paintBoard;
	private static final int PAINT_INTERVAL = 25;

	public DanmakuPaintThread(StateManager stateManager, Component paintBoard) {
		super(stateManager);
		this.paintBoard = paintBoard;
	}

	public void run() {
		while (true) {
			try {
				sem.acquire();
				sem.release();
				paintBoard.repaint();
				sleep(PAINT_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
