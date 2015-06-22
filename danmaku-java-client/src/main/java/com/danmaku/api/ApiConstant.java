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
	 * API �������ݸ�ʽ : {"success" : 1|0, "data" : ... }.
	 */

	/**
	 * @description : ѯ��ָ��Ƶ���Ƿ����.
	 * @method GET/POST
	 * @param int channel_id
	 */
	public static final String API_QUERY_CHANNEL = "api/query_channel.php";

	/**
	 * @description : ���ָ��Ƶ�����ĵ�ĻID
	 * @method GET
	 * @param int channel_id
	 */
	public static final String API_GET_LATEST_DANMAKU_ID = "api/get_latest_id.php";

	/**
	 * @description : ��ȡ��Ļ
	 * @method GET
	 * @param int channel_id
	 * @param int latest_id : �����IDС�ĵ�Ļ��������
	 * @param int max_num : ���صĵ�Ļ�������Ŀ
	 */
	public static final String API_FETCH_DANMAKU = "api/fetch.php";
}
