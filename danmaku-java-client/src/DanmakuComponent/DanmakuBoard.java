package DanmakuComponent;

import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JWindow;

import DanmakuState.StateManager;

public class DanmakuBoard extends JWindow {

	private static final long serialVersionUID = -4142280682395283885L;

	private StateManager stateManager;

	public DanmakuBoard(StateManager stateManager) {
		this.stateManager = stateManager;
		init();
	}

	private void init() {
		this.setAlwaysOnTop(true);
		this.setOpacity(0.5f);
		this.setLayout(null);
		this.setFocusable(false);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocation(0, 0);
		this.validate();

	}

	public void paint(Graphics g) {
		switch (stateManager.getState()) {
		case STATE_RUNNING: {

		}
			break;
		case STATE_PAUSE:
		case STATE_STOP: {

		}
			break;
		}
	}
}
