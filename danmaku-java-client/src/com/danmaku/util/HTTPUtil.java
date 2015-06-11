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

public class HTTPUtil {

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

		HTTPResponse response = new HTTPResponse(connection.getResponseCode(),
				connection.getContentType(), body);

		LogUtil.printMessage("== HTTP Request Info Start ==");
		LogUtil.printVar("Mothod", method);
		LogUtil.printVar("URL", url);
		LogUtil.printVar("ParamString", paramString);
		LogUtil.printVar("ResponseCode", response.getCode());
		LogUtil.printVar("ResponseBody", response.getBodyString());
		LogUtil.printMessage("== HTTP Request Info End==");

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
		private String contentType;
		private byte[] body;

		public HTTPResponse(int code, String contentType, byte[] body) {
			this.code = code;
			this.contentType = contentType;
			this.body = body;
		}

		public boolean isSuccessful() {
			return code == 200;
		}

		public int getCode() {
			return code;
		}

		public String getContentType() {
			return contentType;
		}

		public String getBodyString() {
			return new String(body);
		}

		public byte[] getBodyBytes() {
			return body;
		}

		public JSONObject getBodyJSON() {
			try {
				return new JSONObject(new String(body));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}
}
