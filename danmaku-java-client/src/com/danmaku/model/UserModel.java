package com.danmaku.model;

public class UserModel {
	public String userid;
	public String username;
	public int speechnum;
	public boolean isBanned;

	public UserModel(String userid, String username) {
		this.userid = userid;
		this.username = username;
		this.speechnum = 0;
		this.isBanned = false;
	}

	public void increaseSpeechnum() {
		speechnum += 1;
	}

}
