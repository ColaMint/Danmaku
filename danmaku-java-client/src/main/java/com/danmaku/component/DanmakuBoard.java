package com.danmaku.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.danmaku.engine.Engine;
import com.danmaku.model.DanmakuModel;
import com.danmaku.model.DanmakuSet;
import com.danmaku.state.StateManager;
import com.danmaku.state.StateManager.State;
import com.danmaku.util.NativeUtil;
import com.danmaku.util.ScreenUtil;

public class DanmakuBoard extends JDialog {

	private static final long serialVersionUID = -4142280682395283885L;
	private static final String TITLE = "Danmaku-Board";

	private JPanel danmakuPanel = new DanmakuPanel();
	private StateManager stateManager;
	private Engine engine;
	private DanmakuSet danmakuSet;

	/*
	 * 标记是否调用过JNI函数使窗口透明，防止重复调用
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
		 * Color(1, 1, 1) 是自定义的一个特殊的颜色
		 * 当调用JNI函数时，与此颜色相同的像素将变成透明
		 */
		danmakuPanel.setBackground(new Color(1, 1, 1));

		engine = new Engine(stateManager, this);
		danmakuSet = engine.getDanmakuSet();

		engine.run();

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

	/* 将绘制过程在DanmakuPanel中实现只是为了使绘制过程比较平滑 */
	public class DanmakuPanel extends JPanel {
		private static final long serialVersionUID = 988932744923127264L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (hasSetTransparent
					&& stateManager.checkState(State.STATE_RUNNING)) {

				// DanmakuBoard.this.toFront();

				Graphics2D g2 = (Graphics2D) g;

				try {
					danmakuSet.lock();
					Iterator<DanmakuModel> it = danmakuSet.getDanmakus().iterator();
					while (it.hasNext()) {
						DanmakuModel danmaku = it.next();

						/* 屏幕上显示的弹幕文字. */
						String danmakuStr = danmaku.username + ":" + danmaku.content;

						/* 设置画笔并绘制. */
						g2.setColor(getColor(danmaku.colorRed, danmaku.colorGreen,
								danmaku.colorBlue));
						g2.setFont(new Font("微软雅黑", Font.BOLD, danmaku.fontSize));
						g2.drawString(danmakuStr, danmaku.x, danmaku.y);

						/* 向左移动弹幕坐标. */
						danmaku.x -= danmaku.speed;

						/* 计算弹幕的长度. */
						FontRenderContext frc = g2.getFontRenderContext();
						Rectangle2D rect = g2.getFont()
								.getStringBounds(danmakuStr, frc);
						int lengthInPix = (int) rect.getWidth();

						/* 若弹幕已经超出屏幕，则删除 */
						if (danmaku.x < -lengthInPix) {
							it.remove();
						}
					}
					danmakuSet.unLock();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private Color getColor(int R, int G, int B) {
			if (R >= 0 && R <= 255 && G >= 0 && G <= 255 && B >= 0 && B <= 255) {
				return new Color(R, G, B);
			} else {
				return new Color(0, 0, 0);
			}
		}
	}
}
