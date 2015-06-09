package com.danmaku.component;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.danmaku.engine.DanmakuEngine;
import com.danmaku.state.StateManager;
import com.danmaku.state.StateManager.State;
import com.danmaku.util.NativeUtil;
import com.danmaku.util.ScreenUtil;

public class DanmakuBoard extends JDialog {

	private static final long serialVersionUID = -4142280682395283885L;
	private static final String TITLE = "Danmaku-Board";

	private JPanel danmakuPanel = new DanmakuPanel();
	private DanmakuPainter danmakuPainter = new DanmakuPainter();
	private StateManager stateManager;
	private DanmakuEngine engine;

	/*
	 * Prevent ceaselessly bringing the dialog to front when native method
	 * hasn't been done.
	 */
	private boolean hasSetTransparent = false;

	public DanmakuBoard(StateManager stateManager) {
		this.stateManager = stateManager;
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle(TITLE);
		this.setBounds(0, 0, ScreenUtil.getScreenWidth(),
				ScreenUtil.getscreenHeightWitoutTaskbar());
		this.getContentPane().setBackground(new Color(1, 1, 1));
		this.validate();
		this.setContentPane(danmakuPanel);

		/*
		 * Color(1, 1, 1) is a special color the same with the color passed to
		 * NativeUtil.SetWindowBackgroundTransparent(int R, int G, int B, String
		 * windowTitle), used to make this dialog transparent.
		 */
		danmakuPanel.setBackground(new Color(1, 1, 1));

		engine = new DanmakuEngine(stateManager, this);
		engine.start();

	}

	@SuppressWarnings("deprecation")
	public void show() {
		super.show();
		if (!hasSetTransparent) {
			NativeUtil.SetWindowBackgroundTransparent(1, 1, 1,
					DanmakuBoard.TITLE);
			hasSetTransparent = true;
		}
	}

	/* Write this class only to make the paint process more smoothly */
	public class DanmakuPanel extends JPanel {
		private static final long serialVersionUID = 988932744923127264L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (hasSetTransparent
					&& stateManager.checkState(State.STATE_RUNNING)) {
				DanmakuBoard.this.toFront();
				danmakuPainter.paintDanmaku(g, engine.getDanmakuSet());
			}
		}
	}
}
