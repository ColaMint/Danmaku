package com.danmaku.engine;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.json.JSONException;

import com.danmaku.model.DanmakuModel;
import com.danmaku.model.DanmakuSet;
import com.danmaku.model.UserSet;
import com.danmaku.state.OnStateChangedListener;
import com.danmaku.state.StateManager;
import com.danmaku.zbus.DanmakuFetcher;
import com.danmaku.zbus.DanmakuFetcher.OnFetchListener;
import com.danmaku.zbus.DanmakuZbus;

public class Collector extends StateBlockedThread implements OnStateChangedListener, OnFetchListener {

	private DanmakuSet danmakuSet;
	private UserSet userSet;

	private int ADD_INTERVAL = 2000;
	private BlockingQueue<DanmakuModel> danmakuQueue = new ArrayBlockingQueue<DanmakuModel>(500);

	private DanmakuFetcher fetcher;
	private boolean acceptDanmaku = false;

	public Collector(StateManager stateManager, DanmakuSet danmakuSet, UserSet userSet) {
		super(stateManager);
		this.danmakuSet = danmakuSet;
		this.userSet = userSet;

		registerStateListener();
	}

	private void registerStateListener() {
		stateManager.addOnStateChangedListener(new OnStateChangedListener() {

			@Override
			public void OnStateChanged(com.danmaku.state.State oldState,
					com.danmaku.state.State newState) {
				// TODO Auto-generated method stub
				switch (newState) {
				case STATE_RUNNING: {
					acceptDanmaku = true;
					if (fetcher == null) {
						try {
							fetcher = DanmakuZbus.createFetcher();
							fetcher.addOnFetchListener(Collector.this);
							fetcher.startFetch();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (fetcher == null) {
							stateManager.changeState(com.danmaku.state.State.STATE_STOP);
						}
					}
				}
					break;
				case STATE_PAUSE: {
					acceptDanmaku = false;
				}
					break;
				case STATE_STOP: {
					if (fetcher != null) {
						fetcher.close();
						fetcher = null;
					}
					acceptDanmaku = false;
				}
					break;
				}
			}

		});
	}

	protected void loop() {
		try {
			if (!danmakuQueue.isEmpty()) {
				danmakuSet.lock();
				userSet.lock();
				while (!danmakuQueue.isEmpty()) {
					DanmakuModel danmaku = danmakuQueue.poll();
					if (null == danmaku) {
						break;
					} else {
						if (userSet.contains(danmaku.userid)) {
							if (!userSet.isbanned(danmaku.userid)) {
								danmakuSet.add(danmaku);
								userSet.increaseSpeechnum(danmaku.userid);
							}
						} else {
							danmakuSet.add(danmaku);
							userSet.add(danmaku.userid, danmaku.username);
							userSet.increaseSpeechnum(danmaku.userid);
						}
					}

					userSet.unLock();
					danmakuSet.unLock();
				}
			}
			sleep(ADD_INTERVAL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	@Override
	public void onReceive(DanmakuModel danmaku) {
		// TODO Auto-generated method stub
		if (acceptDanmaku) {
			try {
				danmakuQueue.offer(danmaku);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub
		stateManager.automicChangeState(com.danmaku.state.State.STATE_STOP);
	}
}
