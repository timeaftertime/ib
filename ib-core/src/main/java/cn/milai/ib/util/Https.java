package cn.milai.ib.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import cn.milai.common.ex.unchecked.Uncheckeds;
import cn.milai.common.io.InputStreams;

/**
 * HTTP 工具类
 * @author milai
 * @date 2021.01.29
 */
public class Https {

	/**
	 * http client 实例
	 */
	private static final HttpClient CLIENT = HttpClientBuilder.create().build();

	/**
	 * 通过 GET 请求从 url 获取文件字节数组
	 * @param url
	 * @return
	 */
	public static byte[] getFile(String url) {
		return Uncheckeds.rethrow(() -> {
			HttpGet request = new HttpGet(url);
			HttpResponse response = CLIENT.execute(request);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new IOException(response.toString());
			}
			return InputStreams.toBytes(response.getEntity().getContent());
		}, url, "获取远程文件失败：url = %s", url);
	}

}
