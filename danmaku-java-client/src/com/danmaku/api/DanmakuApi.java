package com.danmaku.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danmaku.model.DanmakuModel;
import com.danmaku.util.HTTPUtil;
import com.danmaku.util.HTTPUtil.HTTPResponse;

public class DanmakuApi {
	private final static Logger logger = LoggerFactory.getLogger(DanmakuApi.class);

	public static boolean queryChannel(int channel_id) {

		String url = ApiConstant.getUrl(ApiConstant.API_QUERY_CHANNEL);

		Map<String, String> params = new HashMap<String, String>();
		params.put("channel_id", channel_id + "");
		try {
			HTTPResponse response = HTTPUtil.sendGet(url, params);
			if (response.isSuccessful()) {
				JSONObject json = response.getBodyJSON();
				return json.getInt("success") == 1;
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
		}

		return false;
	}

	public static int getLastestDanmakuID(int channel_id) {

		String url = ApiConstant.getUrl(ApiConstant.API_GET_LATEST_DANMAKU_ID);

		Map<String, String> params = new HashMap<String, String>();
		params.put("channel_id", channel_id + "");
		try {
			HTTPResponse response = HTTPUtil.sendGet(url, params);
			if (response.isSuccessful()) {
				JSONObject json = response.getBodyJSON();
				return json.getInt("data");
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
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
			if (response.isSuccessful()) {
				JSONObject json = response.getBodyJSON();
				JSONArray danmakus = json.getJSONArray("data");
				for (int i = 0; i < danmakus.length(); ++i) {
					danmakuList.add(DanmakuModel.fromJSON(danmakus.getJSONObject(i)));
				}
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
		}

		return danmakuList;
	}

}
