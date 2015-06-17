package com.danmaku.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.danmaku.model.DanmakuModel;
import com.danmaku.util.HTTPUtil;
import com.danmaku.util.HTTPUtil.HTTPResponse;

public class DanmakuApi {

	public static boolean queryChannel(int channel_id) {

		String url = ApiConstant.getUrl(ApiConstant.API_QUERY_CHANNEL);

		Map<String, String> params = new HashMap<String, String>();
		params.put("channel_id", channel_id + "");
		try {
			HTTPResponse response = HTTPUtil.sendGet(url, params);
			JSONObject json = response.getBodyJSON();
			if (DanmakuApi.isSuccessful(json)) {
				return json.getInt("success") == 1;
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static int getLastestDanmakuID(int channel_id) {

		String url = ApiConstant.getUrl(ApiConstant.API_GET_LATEST_DANMAKU_ID);

		Map<String, String> params = new HashMap<String, String>();
		params.put("channel_id", channel_id + "");
		try {
			HTTPResponse response = HTTPUtil.sendGet(url, params);
			JSONObject json = response.getBodyJSON();
			if (DanmakuApi.isSuccessful(json)) {
				return json.getInt("data");
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ApiConstant.INVALID_DANMAKU_ID;

	}

	public static List<DanmakuModel> fetchDanmaku(int channel_id, int smallest_danmaku_id, int max_num) {

		String url = ApiConstant.getUrl(ApiConstant.API_FETCH_DANMAKU);

		List<DanmakuModel> danmakuList = new ArrayList<DanmakuModel>();

		Map<String, String> params = new HashMap<String, String>();
		params.put("channel_id", channel_id + "");
		params.put("latest_id", smallest_danmaku_id + "");
		params.put("max_num", max_num + "");
		try {
			HTTPResponse response = HTTPUtil.sendGet(url, params);
			JSONObject json = response.getBodyJSON();
			if (DanmakuApi.isSuccessful(json)) {
				JSONArray danmakus = json.getJSONArray("data");
				for (int i = 0; i < danmakus.length(); ++i) {
					danmakuList.add(DanmakuModel.fromJSON(danmakus.getJSONObject(i)));
				}
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return danmakuList;
	}

	private static boolean isSuccessful(JSONObject json) {
		try {
			return json.getInt("success") == 1;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
