package com.danmaku.state;

import java.util.ArrayList;
import java.util.List;

import com.danmaku.common.Lockable;

public class StateManager extends Lockable implements StateManagerInterface {

	private List<OnStateChangedListener> listeners;

	private State state;

	public StateManager() {
		listeners = new ArrayList<OnStateChangedListener>();
		this.state = State.STATE_STOP;
	}

	@Override
	public State getState() {
		return this.state;
	}

	@Override
	public boolean checkState(State targetState) {
		return this.state == targetState;
	}

	@Override
	public void changeState(State newState) {
		// TODO Auto-generated method stub
		State oldState = this.state;
		this.state = newState;
		notifyListeners(oldState, newState);
	}

	@Override
	public void automicChangeState(State newState) {
		// TODO Auto-generated method stub
		this.lock();
		this.changeState(newState);
		this.unLock();
	}

	@Override
	public void addOnStateChangedListener(OnStateChangedListener l) {
		listeners.add(l);
	}

	@Override
	public void removeOnStateChangedListener(OnStateChangedListener l) {
		listeners.remove(l);
	}

	public void notifyListeners(State oldState, State newState) {
		for (OnStateChangedListener l : listeners) {
			l.OnStateChanged(oldState, newState);
		}
	}
}
