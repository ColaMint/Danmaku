package DanmakuComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;

import javax.swing.JDialog;
import javax.swing.JPanel;

import DanmakuModel.DanmakuModel;
import DanmakuState.StateManager;
import DanmakuState.StateManager.OnStateChangedListener;
import DanmakuState.StateManager.State;
import DanmakuUtil.ScreenUtil;

public class DanmakuBoard extends JDialog {

	private static final long serialVersionUID = -4142280682395283885L;
	private static final String TITLE = "-4142280682395283885";

	private JPanel danmakuPanel;

	private int boardWidth, boardHeight;

	private StateManager stateManager;
	private DanmakuPainter danmakuPainter;
	private Semaphore paintSem;

	static {
		System.loadLibrary("DanmakuWindow");
	}

	public DanmakuBoard(StateManager stateManager) {
		this.stateManager = stateManager;
		this.paintSem = new Semaphore(1);

		boardWidth = ScreenUtil.getScreenWidth();
		boardHeight = ScreenUtil.getscreenHeightWitoutTaskbar();

		this.setUndecorated(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle(TITLE);
		this.setBounds(0, 0, boardWidth, boardHeight);
		this.getContentPane().setBackground(new Color(1, 1, 1));
		this.validate();

		danmakuPanel = new DanmakuPanel();
		danmakuPanel.setBackground(new Color(1, 1, 1));
		this.setContentPane(danmakuPanel);

		danmakuPainter = new DanmakuPainter(boardWidth,boardHeight);
		DanmakuModel danmaku = new DanmakuModel();
		danmaku.username = "Li";
		danmaku.content = "³Ï½¡ÊÇÉµ±Æ~~";
		danmaku.fontSize = 40;
		danmaku.colorRed = 20;
		danmaku.colorGreen = 200;
		danmaku.colorBlue = 77;
		danmaku.speed = 5;
		danmakuPainter.addDanmaku(danmaku);

		addOnStateChangedListener();

		new PaintThread().start();
	}


	private void addOnStateChangedListener() {
		try {
			stateManager.lockState();

			stateManager
					.addOnStateChangedListener(new OnStateChangedListener() {

						@Override
						public void OnStateChanged(State oldState,
								State newState) {
							// TODO Auto-generated method stub
							try {
								stateManager.lockState();
								switch (newState) {
								case STATE_RUNNING: {
									paintSem.release();
								}
									break;
								case STATE_PAUSE:
								case STATE_STOP: {
									try {
										paintSem.acquire();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
									break;
								}
								stateManager.unlockState();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

					});

			if (!stateManager.checkState(State.STATE_RUNNING)) {
				paintSem.acquire();
			}
			stateManager.unlockState();
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	public void show() {
		super.show();
		setWindowsTransparent(DanmakuBoard.TITLE);
	}

	private native void setWindowsTransparent(String windowName);

	private int i = 0;

	protected class PaintThread extends Thread {
		protected static final int PAINT_INTERVAL = 20;

		public void run() {
			while (true) {
				try {
					paintSem.acquire();
					paintSem.release();
					// setWindowsTransparent(DanmakuBoard.TITLE);
					//DanmakuBoard.this.setExtendedState(JFrame.MAXIMIZED_BOTH);
					DanmakuBoard.this.toFront();
					DanmakuBoard.this.repaint();
					sleep(PAINT_INTERVAL);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public class DanmakuPanel extends JPanel {
		private static final long serialVersionUID = 988932744923127264L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			BufferedImage image = new BufferedImage(boardWidth, boardHeight,
					BufferedImage.TYPE_4BYTE_ABGR);
			Graphics g2 = image.getGraphics();
			if (stateManager.checkState(State.STATE_RUNNING)) {
				danmakuPainter.paintAllDanmaku(g2);
			}
			g.drawImage(image, 0, 0, null);
		}
	}
}
