package com.danmaku.state;


public interface StateManagerInterface {

	public State getState();

	public boolean checkState(State targetState);

	public void changeState(State newState);

	public void automicChangeState(State newState);

	public void addOnStateChangedListener(OnStateChangedListener l);

	public void removeOnStateChangedListener(OnStateChangedListener l);

}
