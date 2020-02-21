package cn.milai.ib.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.ex.IBIOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Http 相关工具类
 * @author milai
 * @date 2020.02.01
 */
public class HttpUtil {

	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * 获取文件字节数组
	 * @param url
	 * @return
	 */
	public static byte[] getFile(String url) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.url(url)
			.build();
		try {
			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException(response.message());
			}
			return IOUtil.toBytes(response.body().byteStream());
		} catch (IOException e) {
			log.error("获取远程文件失败：url = {}, error = {}", url, e);
			throw new IBIOException(e);
		}
	}
}
