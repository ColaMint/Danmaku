package com.danmaku.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.danmaku.common.Lockable;
import com.danmaku.model.DanmakuModel;
import com.danmaku.state.StateManager;
import com.danmaku.state.StateManager.OnStateChangedListener;
import com.danmaku.state.StateManager.State;

public class DanmakuSet extends Lockable implements OnStateChangedListener {

	private StateManager stateMnager;
	private List<DanmakuModel> danmakuList = new ArrayList<DanmakuModel>();
	private Random random = new Random();

	private int boardWidth, boardHeight;

	public DanmakuSet(StateManager stateMnager, int boardWidth, int boardHeight) {
		this.stateMnager = stateMnager;
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.stateMnager.addOnStateChangedListener(this);
		addTestData();
	}

	public void add(List<DanmakuModel> newDanmakuList) {
		for (DanmakuModel danmaku : newDanmakuList) {
			addHelp(danmaku);
		}
	}

	public void add(DanmakuModel danmaku) {
		addHelp(danmaku);
	}

	private void addHelp(DanmakuModel danmaku) {
		/* init danmaku's position */
		danmaku.x = boardWidth;
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
		if (stateMnager.checkState(State.STATE_STOP)) {
			try {
				this.lock();
				this.removeAll();
				this.unLock();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void addTestData() {
		try {
			this.lock();
			DanmakuModel ma = new DanmakuModel();
			ma.username = "Li";
			ma.content = "½¨˜IÊÇÉµ±Æ£¡";
			ma.fontSize = 40;
			ma.speed = 5;
			this.add(ma);
			this.add(ma.copy());
			this.add(ma.copy());
			this.add(ma.copy());
			this.unLock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
