package com.danmaku.engine;

import java.util.List;

import com.danmaku.api.ApiConstant;
import com.danmaku.api.DanmakuApi;
import com.danmaku.model.DanmakuModel;
import com.danmaku.state.StateManager;
import com.danmaku.state.StateManager.OnStateChangedListener;

public class DanmakuFetchThread extends BaseThread {

	private DanmakuSet danmakuSet;
	private UserSet userSet;
	private static final int FETCH_INTERVAL = 1000;
	private static final int FETCH_MAX_NUM = 50;
	private int smallest_danmaku_id = ApiConstant.INVALID_DANMAKU_ID;
	private boolean isApiHostAvailable = false;

	public DanmakuFetchThread(StateManager stateManager, DanmakuSet danmakuSet, UserSet userSet) {
		super(stateManager);
		// TODO Auto-generated constructor stub
		this.danmakuSet = danmakuSet;
		this.userSet = userSet;

		stateManager.addOnStateChangedListener(new OnStateChangedListener() {

			@Override
			public void OnStateChanged(com.danmaku.state.StateManager.State oldState,
					com.danmaku.state.StateManager.State newState) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void run() {
		while (true) {
			try {
				this.blockIfNotRunning();
				if (!isApiHostAvailable) {
					isApiHostAvailable = DanmakuApi.testServer();
				}
				if (!isApiHostAvailable) {
					stateManager.lock();
					stateManager.setState(StateManager.State.STATE_STOP);
					stateManager.unLock();
					continue;
				}
				if (smallest_danmaku_id == ApiConstant.INVALID_DANMAKU_ID) {
					smallest_danmaku_id = DanmakuApi.getLastestDanmakuID();
				} else {
					smallest_danmaku_id = 1;
					danmakuSet.lock();
					userSet.lock();

					List<DanmakuModel> danmakuList =
							DanmakuApi.fetchDanmaku(smallest_danmaku_id,
									FETCH_MAX_NUM);
					for (DanmakuModel danmaku : danmakuList) {

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

						if (danmaku.id > smallest_danmaku_id) {
							smallest_danmaku_id = danmaku.id;
						}
					}

					userSet.unLock();
					danmakuSet.unLock();
				}
				sleep(FETCH_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
