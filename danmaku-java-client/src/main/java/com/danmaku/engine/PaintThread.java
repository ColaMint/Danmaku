package com.danmaku.engine;

import java.awt.Component;

import com.danmaku.state.StateManager;

public class PaintThread extends BaseThread {

	private Component paintBoard;
	private static final int PAINT_INTERVAL = 30;

	public PaintThread(StateManager stateManager, Component paintBoard) {
		super(stateManager);
		this.paintBoard = paintBoard;
	}

	protected void loop() {
		try {
			paintBoard.repaint();
			sleep(PAINT_INTERVAL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
