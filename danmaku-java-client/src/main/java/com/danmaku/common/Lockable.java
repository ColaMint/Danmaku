package com.danmaku.common;

import java.util.concurrent.Semaphore;

public class Lockable {

	private Semaphore sem = new Semaphore(1);

	public void lock() {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void unLock() {
		sem.release();
	}
}
