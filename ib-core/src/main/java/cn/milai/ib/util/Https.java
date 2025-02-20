package cn.milai.ib.util;

import java.io.IOException;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpStatus;

import cn.milai.common.ex.unchecked.Uncheckeds;
import cn.milai.common.io.InputStreams;

/**
 * HTTP 工具类
 * 
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
	 * 
	 * @param url
	 * @return
	 */
	public static byte[] getFile(String url) {
		return Uncheckeds.rethrow(() -> {
			HttpGet request = new HttpGet(url);
			ClassicHttpResponse response = CLIENT.execute(request, res -> {
				if (res.getCode() != HttpStatus.SC_OK) {
					throw new IOException(res.toString());
				}
				return res;
			});

			return InputStreams.toBytes(response.getEntity().getContent());
		}, url, "获取远程文件失败：url = %s", url);
	}

}
