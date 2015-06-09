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

	public static int getLastestDanmakuID() {
		String url = ApiConstant.getUrlPrefix() + ApiConstant.GET_LATEST_DANMAKU_ID;
		try {
			HTTPResponse response = HTTPUtil.sendGet(url, null);
			JSONObject json = response.getBodyJSON();
			if (isSuccessful(json)) {
				return json.getJSONObject("data").getInt("lastest_id");
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ApiConstant.INVALID_DANMAKU_ID;

	}

	public static List<DanmakuModel> fetchDanmaku(int smallest_danmaku_id, int max_num) {

		List<DanmakuModel> danmakuList = new ArrayList<DanmakuModel>();

		String url = ApiConstant.getUrlPrefix() + ApiConstant.FETCH_DANMAKU;
		Map<String, String> params = new HashMap<String, String>();
		params.put("smallest_danmaku_id", smallest_danmaku_id + "");
		params.put("max_num", max_num + "");
		try {
			HTTPResponse response = HTTPUtil.sendGet(url, params);
			JSONObject json = response.getBodyJSON();
			if (isSuccessful(json)) {
				JSONArray danmakus = json.getJSONObject("data").getJSONArray("danmakus");
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
