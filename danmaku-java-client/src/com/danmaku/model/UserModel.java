package com.danmaku.model;

public class UserModel {
	private String userid;
	private String username;
	private int speechnum;
	private boolean isBanned;
	
	public UserModel(String userid,String username){
		this.userid = userid;
		this.username = username;
		this.speechnum = 0;
		this.isBanned = false;
	}
	
	public String getUserid(){
		return userid;
	}
	
	public String getUsername(){
		return username;
	}
	
	public int getSpeechnum(){
		return speechnum;
	}
	
	public boolean isBanned(){
		return isBanned;
	}
	
	public void increaseSpeechnum(){
		speechnum += 1;
	}
}	
