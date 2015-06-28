package com.danmaku.state;


public interface OnStateChangedListener {
	/*
	 * 所以尽量不要在这个函数中调用StateManager::lock()，避免造成死锁
	 */
	public void OnStateChanged(State oldState, State newState);
}