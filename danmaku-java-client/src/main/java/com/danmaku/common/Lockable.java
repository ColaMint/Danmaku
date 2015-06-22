package com.danmaku.common;

import java.util.concurrent.Semaphore;

public abstract class Lockable {

	private Semaphore sem = new Semaphore(1);

	public void lock() throws InterruptedException {
		sem.acquire();
	}

	public void unLock() {
		sem.release();
	}
}
