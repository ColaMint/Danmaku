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
	 * API 返回数据格式 : {"success" : 1|0, "data" : ... }.
	 */

	/**
	 * @description : 询问指定频道是否存在.
	 * @method GET/POST
	 * @param int channel_id
	 */
	public static final String API_QUERY_CHANNEL = "api/query_channel.php";

	/**
	 * @description : 获得指定频道最大的弹幕ID
	 * @method GET
	 * @param int channel_id
	 */
	public static final String API_GET_LATEST_DANMAKU_ID = "api/get_latest_id.php";

	/**
	 * @description : 获取弹幕
	 * @method GET
	 * @param int channel_id
	 * @param int latest_id : 比这个ID小的弹幕将被忽略
	 * @param int max_num : 返回的弹幕的最大数目
	 */
	public static final String API_FETCH_DANMAKU = "api/fetch.php";
}
