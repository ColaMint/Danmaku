package com.danmaku.engine;

import java.util.ArrayList;
import java.util.List;

import com.danmaku.api.ApiConstant;
import com.danmaku.api.DanmakuApi;
import com.danmaku.model.DanmakuModel;
import com.danmaku.state.StateManager;
import com.danmaku.util.LogUtil;

public class DanmakuFetchThread extends BaseThread {

	private DanmakuSet danmakuSet;
	private UserSet userSet;
	private static final int FETCH_INTERVAL = 1000;
	private static final int FETCH_MAX_NUM = 50;
	private int smallest_danmaku_id = ApiConstant.INVALID_DANMAKU_ID;

	public DanmakuFetchThread(StateManager stateManager, DanmakuSet danmakuSet, UserSet userSet) {
		super(stateManager);
		// TODO Auto-generated constructor stub
		this.danmakuSet = danmakuSet;
		this.userSet = userSet;
	}

	public void run() {
		while (true) {
			try {
				sem.acquire();
				sem.release();
				smallest_danmaku_id = 1;
				if (smallest_danmaku_id == ApiConstant.INVALID_DANMAKU_ID) {
					smallest_danmaku_id = DanmakuApi.getLastestDanmakuID();
				} else {
					// List<DanmakuModel> danmakuList =
					// DanmakuApi.fetchDanmaku(smallest_danmaku_id,
					// FETCH_MAX_NUM);
					List<DanmakuModel> danmakuList = new ArrayList<DanmakuModel>();
					danmakuList.add(DanmakuModel.createTestData());
					danmakuSet.lock();
					userSet.lock();
					for (DanmakuModel danmaku : danmakuList) {
						LogUtil.printMessage("== Danmaku Comes ==");
						LogUtil.printVar("userid", danmaku.userid);
						LogUtil.printVar("username", danmaku.username);
						LogUtil.printVar("content", danmaku.content);
						LogUtil.printVar("fontSize", danmaku.fontSize);
						LogUtil.printVar("speed", danmaku.speed);
						LogUtil.printVar("colorRed", danmaku.colorRed);
						LogUtil.printVar("colorGreen", danmaku.colorGreen);
						LogUtil.printVar("colorBlue", danmaku.colorBlue);
						if (userSet.contains(danmaku.userid)) {
							if (!userSet.isbanned(danmaku.userid)) {
								danmakuSet.add(danmaku);
								userSet.increaseSpeechnum(danmaku.userid);
							} else {
								LogUtil.printVar("accessedToDisplay", false);
							}
						} else {
							danmakuSet.add(danmaku);
							userSet.add(danmaku.userid, danmaku.username);
							userSet.increaseSpeechnum(danmaku.userid);
						}

						LogUtil.printVar("accessedToDisplay", true);
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
