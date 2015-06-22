package com.danmaku.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPUtil {
	private final static Logger logger = LoggerFactory.getLogger(HTTPUtil.class);

	public static HTTPResponse sendGet(String url, Map<String, String> params)
			throws IOException {
		return send(url, "GET", params);
	}

	public static HTTPResponse sendPost(String url, Map<String, String> params)
			throws IOException {
		return send(url, "POST", params);
	}

	private static HTTPResponse send(String url, String method,
			Map<String, String> params) throws IOException {

		String paramString = buildParamString(params);

		if ("GET".equalsIgnoreCase(method) && paramString != null) {
			url += url.endsWith("?") ? paramString : "?" + paramString;
		}

		URL requestURL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) requestURL
				.openConnection();
		connection.setRequestMethod(method);
		connection.setDoOutput(true);
		connection.setDoInput(true);

		if ("POST".equalsIgnoreCase(method) && paramString != null) {
			OutputStream out = connection.getOutputStream();
			out.write(paramString.getBytes());
			out.flush();
			out.close();
		}

		InputStream in = connection.getInputStream();
		byte[] body = new byte[in.available()];
		in.read(body);
		in.close();

		HTTPResponse response = new HTTPResponse(connection.getResponseCode(), body);

		logger.info("HTTPResponse : [Mothod : {}, URL : {}, ParamString : {}, ResponseCode : {}, ResponseBody : {}]",
				method, url, paramString, response.getCode(), response.getBodyString());

		return response;
	}

	private static String buildParamString(Map<String, String> params) {
		if (params != null && params.size() > 0) {
			StringBuilder paramString = new StringBuilder();
			Set<String> keySet = params.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = params.get(key);
				paramString.append(key + "=" + value + "&");
			}
			return paramString.substring(0, paramString.length() - 1);
		} else {
			return "";
		}

	}

	public static class HTTPResponse {

		private int code;
		private String strBody;
		private JSONObject joBody;

		public HTTPResponse(int code, byte[] body) {
			this.code = code;
			this.strBody = new String(body);
			try {
				joBody = new JSONObject(strBody);
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}

		public boolean isSuccessful() {
			return code == 200 && null != joBody;
		}

		public int getCode() {
			return code;
		}

		public String getBodyString() {
			return strBody;
		}

		public JSONObject getBodyJSON() {
			return joBody;
		}
	}
}
