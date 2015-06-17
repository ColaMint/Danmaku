package com.danmaku.model;

import org.json.JSONException;
import org.json.JSONObject;

public class DanmakuModel extends Object {
	public int id;
	public int channel_id;
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
		danmaku.id = jsonObject.getInt("id");
		danmaku.channel_id = jsonObject.getInt("channel_id");
		danmaku.userid = jsonObject.getString("userid");
		danmaku.username = jsonObject.getString("username");
		danmaku.content = jsonObject.getString("content");
		danmaku.fontSize = jsonObject.getInt("font_size");
		danmaku.colorRed = jsonObject.getInt("color_r");
		danmaku.colorGreen = jsonObject.getInt("color_g");
		danmaku.colorBlue = jsonObject.getInt("color_b");
		danmaku.speed = jsonObject.getInt("speed");
		return danmaku;
	}
}
