package cn.milai.ib.util;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.ex.IBIOException;

/**
 * Http 相关工具类
 * @author milai
 * @date 2020.02.01
 */
public class HttpUtil {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * http client 实例
	 */
	private static final HttpClient CLIENT = HttpClientBuilder.create().build();

	/**
	 * 获取文件字节数组
	 * @param url
	 * @return
	 */
	public static byte[] getFile(String url) {
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = CLIENT.execute(request);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new IOException(response.toString());
			}
			return IOUtil.toBytes(response.getEntity().getContent());
		} catch (IOException e) {
			LOG.error("获取远程文件失败：url = {}, error = {}", url, ExceptionUtils.getStackTrace(e));
			throw new IBIOException(e);
		}
	}
}
