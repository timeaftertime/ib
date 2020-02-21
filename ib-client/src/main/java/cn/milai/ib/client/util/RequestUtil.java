package cn.milai.ib.client.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class RequestUtil {

	private RequestUtil() {
	}

	public static class Entry {
		private String name;
		private String value;

		public Entry(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

	}

	public static JSONObject post(String url, Entry... params) throws IOException {
		return post(url, 15, params);
	}

	public static JSONObject post(String url, long timeoutSecond, Entry... params) throws IOException {
		OkHttpClient client = newClient(timeoutSecond);
		Builder builder = new FormBody.Builder();
		for (Entry param : params) {
			builder.add(param.getName(), param.getValue());
		}
		FormBody form = builder.build();
		Request request = new Request.Builder().post(form).url(url).build();
		Response response = null;
		response = client.newCall(request).execute();
		return JSON.parseObject(response.body().string());
	}

	private static OkHttpClient newClient(long timeoutSecond) {
		return new OkHttpClient.Builder().callTimeout(timeoutSecond, TimeUnit.SECONDS).build();
	}

	public static String param(String name, String value) {
		JSONObject json = new JSONObject();
		json.put(name, value);
		return JSONUtil.toConfiguredJSONString(json);
	}
}
