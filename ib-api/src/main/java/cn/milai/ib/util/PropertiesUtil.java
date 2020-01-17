package cn.milai.ib.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

/**
 * Properties 相关工具类
 * 2020.01.12
 * @author milai
 */
public class PropertiesUtil {

	private static final Charset CHARSET = Charsets.UTF_8;

	/**
	 * 以 UTF-8 编码加载并返回 url 指向的配置文件
	 * 若加载失败，返回空 Map
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static HashMap<String,String> load(URL url) {
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(url.openStream(), CHARSET));
		} catch (IOException e) {
			// 加载失败则返回空 Map
		}
		HashMap<String, String> map = Maps.newHashMap();
		for (String key : prop.stringPropertyNames()) {
			map.put(key, prop.getProperty(key));
		}
		return map;
	}

	/**
	 * 以 UTF-8 编码加载并返回 url 指向的配置文件，并返回指定 key 对应 value 值
	 * 若加载失败或对应的 key 不存在，返回 null
	 * @param url
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String get(URL url, String key) {
		return load(url).get(key);
	}

}
