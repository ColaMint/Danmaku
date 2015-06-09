package com.danmaku.model;

import org.json.JSONException;
import org.json.JSONObject;

public class DanmakuModel extends Object{
	public String userid;
	public String username;
	public String content;
	public int fontSize;
	public int colorRed;
	public int colorGreen;
	public int colorBlue;
	public int speed;
	public int x;
	public int y;

	public static DanmakuModel fromJSON(JSONObject jsonObject)
			throws JSONException {
		DanmakuModel danmaku = new DanmakuModel();
		danmaku.userid = jsonObject.getString("userid");
		danmaku.username = jsonObject.getString("username");
		danmaku.content = jsonObject.getString("content");
		danmaku.fontSize = jsonObject.getInt("fontSize");
		danmaku.colorRed = jsonObject.getInt("colorRed" );
		danmaku.colorGreen = jsonObject.getInt("colorGreen");
		danmaku.colorBlue = jsonObject.getInt("colorBlue");
		danmaku.speed = jsonObject.getInt("speed");
		return danmaku;
	}
	
	public DanmakuModel copy(){
		DanmakuModel danmaku = new DanmakuModel();
		danmaku.userid = this.userid;
		danmaku.username = this.username;
		danmaku.content = this.content;
		danmaku.fontSize =this.fontSize;
		danmaku.colorRed = this.colorRed;
		danmaku.colorGreen =this.colorGreen;
		danmaku.colorBlue = this.colorBlue;
		danmaku.speed = this.speed;
		return danmaku;
	}

}
