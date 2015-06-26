package com.danmaku.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.danmaku.common.Lockable;
import com.danmaku.state.StateManager;
import com.danmaku.state.StateManager.OnStateChangedListener;
import com.danmaku.state.StateManager.State;

public class UserSet extends Lockable implements OnStateChangedListener {

	private Map<String, UserModel> userMap = new HashMap<String, UserModel>();
	private StateManager stateMnager;

	public UserSet(StateManager stateMnager) {
		this.stateMnager = stateMnager;
		this.stateMnager.addOnStateChangedListener(this);
	}

	public boolean add(String userid, String username) {
		if (!contains(userid)) {
			userMap.put(userid, new UserModel(userid, username));
			return true;
		} else {
			return false;
		}
	}

	public boolean remove(String userid) {
		if (contains(userid)) {
			userMap.remove(userid);
			return true;
		} else {
			return false;
		}
	}

	public void removeAll() {
		userMap.clear();
	}

	public UserModel getUserModel(String userid) {
		return userMap.get(userid);
	}

	public Map<String, UserModel> getUserMap() {
		return userMap;
	}

	public boolean contains(UserModel user) {
		return userMap.containsKey(user.userid);
	}

	public boolean contains(String userid) {
		return userMap.containsKey(userid);
	}

	public void freeAllUsers() {
		Set<String> keySet = userMap.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			userMap.get(it.next()).isBanned = false;
		}
	}

	public boolean increaseSpeechnum(String userid) {
		if (contains(userid)) {
			userMap.get(userid).increaseSpeechnum();
			return true;
		} else {
			return false;
		}
	}

	public boolean free(String userid) {
		if (contains(userid)) {
			userMap.get(userid).isBanned = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isbanned(String userid) {
		if (contains(userid)) {
			return userMap.get(userid).isBanned;
		} else {
			return false;
		}
	}

	public boolean ban(String userid) {
		if (contains(userid)) {
			userMap.get(userid).isBanned = true;
			return true;
		} else {
			return false;
		}
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
}
