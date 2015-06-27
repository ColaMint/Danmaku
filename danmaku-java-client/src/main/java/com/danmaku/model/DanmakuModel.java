package com.danmaku.model;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.zstacks.znet.Message;

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

	public static DanmakuModel fromMessage(Message msg) throws JSONException, NumberFormatException,
			UnsupportedEncodingException {
		String bodyString = msg.getBodyString();
		if (bodyString != null && bodyString.length() > 0) {
			JSONObject jo = new JSONObject(bodyString);
			return DanmakuModel.fromJSON(jo);
		} else {
			DanmakuModel danmaku = new DanmakuModel();
			danmaku.userid = msg.getHeadOrParam("userid");
			danmaku.username = msg.getHeadOrParam("username");
			danmaku.content = msg.getHeadOrParam("content");
			danmaku.fontSize = Integer.parseInt(msg.getHeadOrParam("font_size", "40"));
			danmaku.colorRed = Integer.parseInt(msg.getHeadOrParam("color_r", "0"));
			danmaku.colorGreen = Integer.parseInt(msg.getHeadOrParam("color_g", "0"));
			danmaku.colorBlue = Integer.parseInt(msg.getHeadOrParam("color_b", "0"));
			danmaku.speed = Integer.parseInt(msg.getHeadOrParam("speed", "5"));
			return danmaku;
		}
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
