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
	 */

	/**
	 * @description : Test whether ApiConstant.HOST and ApiConstant.PORT are
	 *              correct or not.
	 * @method GET/POST
	 */
	public static final String TEST_SERVER = "/api/test_server.php";

	/**
	 * @description : Get The beggest id of all danmakus.
	 * @method GET
	 */
	public static final String GET_LATEST_DANMAKU_ID = "/api/get_latest_id.php";

	/**
	 * @description : Fetch danmakus.
	 * @method GET
	 * @param int latest_id : Those danmakus whose ID is smaller than
	 *        this will not return
	 * @param int max_num : The maximun danmaku number to be returned
	 */
	public static final String FETCH_DANMAKU = "/api/fetch.php";
}
