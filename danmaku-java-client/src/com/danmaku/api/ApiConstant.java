package com.danmaku.api;

public class ApiConstant {

	public static String HOST = "localhost";
	public static String PORT = "80";

	public static final int INVALID_DANMAKU_ID = -1;

	public static void setServer(String host, String port) {
		ApiConstant.HOST = host;
		ApiConstant.PORT = port;
	}

	public static String getUrlPrefix() {
		return "http://" + HOST + ":" + PORT;
	}

	/**
	 * API return format :{"success" : 1|0, "data" : ... }.
	 * Other returned field will be in data.
	 */

	/**
	 * @method GET
	 * @return int lastest_id
	 */
	public static final String GET_LATEST_DANMAKU_ID = "/get_latest_danmaku_id";

	/**
	 * @method GET
	 * @param int latest_id : Those danmakus whose ID is smaller than
	 *        this will not return
	 * @param int max_num : The maximun danmaku number to be returned
	 * @return array
	 */
	public static final String FETCH_DANMAKU = "/fetch_danmaku";
}
