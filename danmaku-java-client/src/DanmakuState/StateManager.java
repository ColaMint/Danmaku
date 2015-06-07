package DanmakuState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class StateManager {

	private List<OnStateChangedListener> listeners;

	private Semaphore stateSem;

	public static enum State {
		STATE_STOP, STATE_RUNNING, STATE_PAUSE
	}

	private State state;

	public StateManager() {
		listeners = new ArrayList<OnStateChangedListener>();
		this.state = State.STATE_STOP;
		stateSem = new Semaphore(1);
	}

	public State getState() {
		return this.state;
	}

	public boolean checkState(State targetState) {
		return this.state == targetState;
	}

	public void setState(State newState) {
		State oldState = this.state;
		this.state = newState;
		notifyListeners(oldState, newState);
	}

	public void lockState() throws InterruptedException {
		stateSem.acquire();
	}

	public void unlockState() {
		stateSem.release();
	}

	public void addOnStateChangedListener(OnStateChangedListener l) {
		listeners.add(l);
	}

	public void removeOnStateChangedListener(OnStateChangedListener l) {
		listeners.remove(l);
	}

	public void notifyListeners(State oldState, State newState) {
		for (OnStateChangedListener l : listeners) {
			l.OnStateChanged(oldState, newState);
		}
	}

	public static interface OnStateChangedListener {
		public void OnStateChanged(State oldState, State newState);
	}
}
