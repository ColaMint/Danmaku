package com.danmaku.api;

public class ApiConstant {

	public static String HOST = "localhost";
	public static String PORT = "80";
	public static String PROJECT_NAME = "danmaku-php-server";
	public static int CHANNEL_ID = 1000;

	public static final int INVALID_DANMAKU_ID = -1;

	public static void setServer(String host, String port, String project_name, int channel_id) {
		ApiConstant.HOST = host;
		ApiConstant.PORT = port;
		ApiConstant.PROJECT_NAME = project_name;
		ApiConstant.CHANNEL_ID = channel_id;
	}

	public static String getUrl(String api) {
		return "http://" + HOST + ":" + PORT + "/" + PROJECT_NAME + "/" + api;
	}

	/**
	 * API return format :{"success" : 1|0, "data" : ... }.
	 */

	/**
	 * @description : Test whether ApiConstant.HOST, ApiConstant.PORT,
	 *              ApiConstant.PROJECT_NAME are correct or not.
	 * @method GET/POST
	 * @param int channel_id
	 */
	public static final String API_QUERY_CHANNEL = "api/query_channel.php";

	/**
	 * @description : Get The beggest id of all danmakus.
	 * @method GET
	 * @param int channel_id
	 */
	public static final String API_GET_LATEST_DANMAKU_ID = "api/get_latest_id.php";

	/**
	 * @description : Fetch danmakus.
	 * @method GET
	 * @param int channel_id
	 * @param int latest_id : Those danmakus whose ID is smaller than
	 *        this will not return
	 * @param int max_num : The maximun danmaku number to be returned
	 */
	public static final String API_FETCH_DANMAKU = "api/fetch.php";
}
