package com.danmaku.engine;

import java.util.List;

import com.danmaku.api.ApiConstant;
import com.danmaku.api.DanmakuApi;
import com.danmaku.model.DanmakuModel;
import com.danmaku.state.StateManager;

public class DanmakuFetchThread extends BaseThread {

	private DanmakuSet danmakuSet;
	private static final int FETCH_INTERVAL = 1000;
	private static final int FETCH_MAX_NUM = 50;
	private int smallest_danmaku_id = ApiConstant.INVALID_DANMAKU_ID;

	public DanmakuFetchThread(StateManager stateManager, DanmakuSet danmakuSet) {
		super(stateManager);
		// TODO Auto-generated constructor stub
		this.danmakuSet = danmakuSet;
	}

	public void run() {
		while (true) {
			try {
				if (smallest_danmaku_id == ApiConstant.INVALID_DANMAKU_ID) {
					smallest_danmaku_id = DanmakuApi.getLastestDanmakuID();
				} else {
					List<DanmakuModel> danmakuList = DanmakuApi.fetchDanmaku(smallest_danmaku_id, FETCH_MAX_NUM);
					danmakuSet.lock();
					danmakuSet.add(danmakuList);
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
