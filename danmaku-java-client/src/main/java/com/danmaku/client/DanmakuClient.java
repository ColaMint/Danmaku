package com.danmaku.client;

import com.danmaku.component.MainFrame;
import com.danmaku.engine.Engine;

public class DanmakuClient {

	private Engine engine;

	private MainFrame ui;

	public DanmakuClient() {
		this.engine = new Engine();
		this.ui = new MainFrame(engine);
	}

	@SuppressWarnings("deprecation")
	public void run() {
		this.engine.run();
		this.ui.show();
	}

	public static void main(String[] args) {
		new DanmakuClient().run();
	}
}
