package com.danmaku.model;

import org.json.JSONException;
import org.json.JSONObject;

public class DanmakuModel extends Object {
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
		danmaku.fontSize = jsonObject.getInt("font_size");
		danmaku.colorRed = jsonObject.getInt("color_r");
		danmaku.colorGreen = jsonObject.getInt("color_g");
		danmaku.colorBlue = jsonObject.getInt("color_b");
		danmaku.speed = jsonObject.getInt("speed");
		return danmaku;
	}

	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();

		jo.put("userid", userid);
		jo.put("username", username);
		jo.put("content", content);
		jo.put("font_size", fontSize);
		jo.put("color_r", colorRed);
		jo.put("color_g", colorGreen);
		jo.put("color_b", colorBlue);
		jo.put("speed", speed);

		return jo;
	}
}
