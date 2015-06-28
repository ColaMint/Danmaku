package com.danmaku.engine;

import com.danmaku.common.Lockable;
import com.danmaku.state.OnStateChangedListener;
import com.danmaku.state.StateManager;

public abstract class StateBlockedThread extends Thread implements
		OnStateChangedListener {

	/*
	 * 信号量sem会根据stateManager的状态来改变计数
	 * 从而服务于BaseThread::blockIfNotRunning()
	 */
	private Lockable loopLock = new Lockable();
	protected StateManager stateManager;

	public StateBlockedThread(StateManager stateManager) {
		this.stateManager = stateManager;
		addStateListenerAndInitLock();
	}

	private void addStateListenerAndInitLock() {
		stateManager.lock();
		stateManager.addOnStateChangedListener(this);
		if (!stateManager
				.checkState(com.danmaku.state.State.STATE_RUNNING))
			loopLock.lock();
		stateManager.unLock();
	}

	/*
	 * 在Thread::run()中循环的开始调用此函数
	 * 能够使线程在stateManager的状态不是运行状态时阻塞下来
	 */
	protected void blockIfNotRunning() {
		loopLock.lock();
		loopLock.unLock();
	}

	@Override
	public void OnStateChanged(com.danmaku.state.State oldState,
			com.danmaku.state.State newState) {
		// TODO Auto-generated method stub
		switch (newState) {
		case STATE_RUNNING: {
			loopLock.unLock();
		}
			break;
		case STATE_PAUSE: {
			loopLock.lock();
		}
			break;
		case STATE_STOP: {
			if (oldState == com.danmaku.state.State.STATE_RUNNING) {
				loopLock.lock();
			}
		}
			break;
		}
	}

	@Override
	public void run() {
		while (true) {
			this.blockIfNotRunning();
			this.loop();
		}
	}

	protected abstract void loop();
}
