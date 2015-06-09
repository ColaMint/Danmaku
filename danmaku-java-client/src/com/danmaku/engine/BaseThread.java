package com.danmaku.engine;

import java.util.concurrent.Semaphore;

import com.danmaku.state.StateManager;
import com.danmaku.state.StateManager.OnStateChangedListener;

/* extend this class can make sure the thread is blocked when danmaku is not running. */
public abstract class BaseThread extends Thread implements
		OnStateChangedListener {

	protected Semaphore sem;
	protected StateManager stateManager;

	public BaseThread(StateManager stateManager) {
		this.stateManager = stateManager;
		addStateListenerAndInitSem();
	}

	private void addStateListenerAndInitSem() {
		sem = new Semaphore(1);
		try {
			stateManager.lock();
			stateManager.addOnStateChangedListener(this);
			/* init paintSem's state */
			if (!stateManager
					.checkState(com.danmaku.state.StateManager.State.STATE_RUNNING))
				sem.acquire();
			stateManager.unLock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void OnStateChanged(com.danmaku.state.StateManager.State oldState,
			com.danmaku.state.StateManager.State newState) {
		// TODO Auto-generated method stub
		try {
			stateManager.lock();
			switch (newState) {
			case STATE_RUNNING: {
				sem.release();
			}
				break;
			case STATE_PAUSE: {
				sem.acquire();
			}
				break;
			case STATE_STOP: {
				if (oldState == StateManager.State.STATE_RUNNING) {
					sem.acquire();
				}
			}
				break;
			}
			stateManager.unLock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
