package com.danmaku.engine;

import java.util.concurrent.Semaphore;

import com.danmaku.state.StateManager;
import com.danmaku.state.StateManager.OnStateChangedListener;

public abstract class BaseThread extends Thread implements
		OnStateChangedListener {

	/*
	 * �ź���sem�����stateManager��״̬���ı����
	 * �Ӷ�������BaseThread::blockIfNotRunning()
	 */
	private Semaphore sem;
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
			if (!stateManager
					.checkState(com.danmaku.state.StateManager.State.STATE_RUNNING))
				sem.acquire();
			stateManager.unLock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * ��Thread::run()��ѭ���Ŀ�ʼ���ô˺���
	 * �ܹ�ʹ�߳���stateManager��״̬��������״̬ʱ��������
	 */
	protected void blockIfNotRunning() throws InterruptedException {
		sem.acquire();
		sem.release();
	}

	@Override
	public void OnStateChanged(com.danmaku.state.StateManager.State oldState,
			com.danmaku.state.StateManager.State newState) {
		// TODO Auto-generated method stub
		try {
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
