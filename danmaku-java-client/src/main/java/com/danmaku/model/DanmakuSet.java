package com.danmaku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.danmaku.common.Lockable;
import com.danmaku.state.OnStateChangedListener;
import com.danmaku.state.State;
import com.danmaku.state.StateManager;
import com.danmaku.util.ScreenUtil;

public class DanmakuSet extends Lockable implements OnStateChangedListener {

	private StateManager stateManager;
	private List<DanmakuModel> danmakuList = new ArrayList<DanmakuModel>();
	private Random random = new Random();

	private int boardWidth, boardHeight;

	public DanmakuSet(StateManager stateManager) {
		this.stateManager = stateManager;
		this.boardWidth = ScreenUtil.getScreenWidth();
		this.boardHeight = ScreenUtil.getscreenHeightWitoutTaskbar();
		this.stateManager.addOnStateChangedListener(this);
	}

	public void add(DanmakuModel danmaku) {
		/* 初始化弹幕的位置 */
		danmaku.x = boardWidth + random.nextInt(300);
		danmaku.y = random.nextInt(boardHeight);
		if (boardHeight - danmaku.y < danmaku.fontSize) {
			danmaku.y = boardHeight - danmaku.fontSize;
		}
		if (danmaku.y < 15) {
			danmaku.y = 15;
		}
		danmakuList.add(danmaku);
	}

	public void remove(DanmakuModel danmaku) {
		danmakuList.remove(danmaku);
	}

	public void removeAll() {
		danmakuList.clear();
	}

	public List<DanmakuModel> getDanmakus() {
		return danmakuList;
	}

	@Override
	public void OnStateChanged(State oldState, State newState) {
		// TODO Auto-generated method stub
		if (stateManager.checkState(State.STATE_STOP)) {
			this.lock();
			this.removeAll();
			this.unLock();
		}
	}

}
