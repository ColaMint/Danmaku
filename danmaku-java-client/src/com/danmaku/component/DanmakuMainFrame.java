package com.danmaku.component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.danmaku.api.ApiConstant;
import com.danmaku.conf.ConfManager;
import com.danmaku.conf.DanmakuConfManager;
import com.danmaku.state.StateManager;
import com.danmaku.state.StateManager.OnStateChangedListener;
import com.danmaku.state.StateManager.State;

public class DanmakuMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/* Frame Params */
	private final String DANMAKU_FRAME_TITLE = "Danmaku";
	private final int DANMAKU_FRAME_WIDTH = 250;
	private final int DANMAKU_FRAME_HEIGHT = 180;

	/* Frame Component */
	private JLabel labelHost;
	private JLabel labelPort;
	private JTextField textHost;
	private JTextField textPort;
	private JButton btnStart;
	private JButton btnPause;
	private JButton btnStop;

	/* Manager */
	private StateManager stateManager;

	/* DanmakuBoard */
	private DanmakuBoard danmakuBoard;

	public DanmakuMainFrame() {
		initFrameParams();
		initStateManager();
		initDanmakuBoard();
	}

	private void initFrameParams() {
		this.setTitle(DANMAKU_FRAME_TITLE);
		this.setLayout(null);
		this.setSize(DANMAKU_FRAME_WIDTH, DANMAKU_FRAME_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowDeactivated(WindowEvent e) {
				if (!stateManager.checkState(State.STATE_STOP)) {

				}
			}
		});
		initComponent();
		this.validate();
	}

	private void initStateManager() {
		stateManager = new StateManager();
		stateManager.addOnStateChangedListener(new OnStateChangedListener() {

			@Override
			public void OnStateChanged(State oldState, State newState) {
				// TODO Auto-generated method stub
				switch (newState) {
				case STATE_STOP: {
					btnStart.setEnabled(true);
					btnPause.setEnabled(false);
					btnStop.setEnabled(false);
				}
					break;
				case STATE_RUNNING: {
					btnStart.setEnabled(false);
					btnPause.setEnabled(true);
					btnStop.setEnabled(false);
				}
					break;
				case STATE_PAUSE: {
					btnStart.setEnabled(true);
					btnPause.setEnabled(false);
					btnStop.setEnabled(true);
				}
					break;
				}

				if (oldState == State.STATE_RUNNING && newState == State.STATE_STOP) {
					JOptionPane.showMessageDialog(DanmakuMainFrame.this, "Please check the host and the port!",
							"ERROR_MESSAGE",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		stateManager.setState(State.STATE_STOP);
	}

	private void initDanmakuBoard() {
		danmakuBoard = new DanmakuBoard(stateManager);
		danmakuBoard.show();
		danmakuBoard.toFront();
	}

	private void initComponent() {
		ConfManager conf = DanmakuConfManager.getInstance();

		/* init labelHost */
		labelHost = new JLabel();
		labelHost.setText("Host:");
		labelHost.setBounds(40, 20, 75, 25);
		this.add(labelHost);

		/* init labelPort */
		labelPort = new JLabel();
		labelPort.setText("Post:");
		labelPort.setBounds(40, 65, 75, 25);
		this.add(labelPort);

		/* init textHost */
		textHost = new JTextField();
		textHost.setText(conf.getProperty("danmaku.host", "localhost"));
		textHost.setBounds(80, 20, 120, 25);
		textHost.setBorder(BorderFactory.createCompoundBorder(
				new EtchedBorder(), new EmptyBorder(0, 5, 0, 5)));
		this.add(textHost);

		/* init textPort */
		textPort = new JTextField();
		textPort.setText(conf.getProperty("danmaku.port", "80"));
		textPort.setBounds(80, 65, 120, 25);
		textPort.setBorder(BorderFactory.createCompoundBorder(
				new EtchedBorder(), new EmptyBorder(0, 5, 0, 5)));
		this.add(textPort);

		/* init btnStart */
		btnStart = new JButton();
		btnStart.setText("Start");
		btnStart.setBounds(15, 110, 70, 25);
		this.add(btnStart);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				onStartBtnClicked();
			}

		});

		/* init btnPause */
		btnPause = new JButton();
		btnPause.setText("Pause");
		btnPause.setBounds(90, 110, 70, 25);
		this.add(btnPause);
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				onPauseBtnClicked();
			}

		});

		/* init btnStop */
		btnStop = new JButton();
		btnStop.setText("Stop");
		btnStop.setBounds(165, 110, 70, 25);
		this.add(btnStop);
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				onStopBtnClicked();
			}

		});
	}

	private void onStartBtnClicked() {
		ApiConstant.setServer(textHost.getText().trim(), textPort.getText().trim());
		try {
			stateManager.lock();
			stateManager.setState(State.STATE_RUNNING);
			stateManager.unLock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void onPauseBtnClicked() {
		try {
			stateManager.lock();
			stateManager.setState(State.STATE_PAUSE);
			stateManager.unLock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void onStopBtnClicked() {
		try {
			stateManager.lock();
			stateManager.setState(State.STATE_STOP);
			stateManager.unLock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
