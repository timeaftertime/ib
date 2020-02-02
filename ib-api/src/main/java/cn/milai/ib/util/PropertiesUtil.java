package cn.milai.ib.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

import cn.milai.ib.ex.IBIOException;

/**
 * Properties 相关工具类
 * 2020.01.12
 * @author milai
 */
public class PropertiesUtil {

	private static final Charset CHARSET = Charsets.UTF_8;

	/**
	 * 以 UTF-8 编码加载并返回 in 指向的配置文件
	 * @param in
	 * @return
	 */
	public static HashMap<String, String> load(InputStream in) {
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(in, CHARSET));
		} catch (IOException e) {
			e.printStackTrace();
			//异常时返回空 Map
		}
		HashMap<String, String> map = Maps.newHashMap();
		for (String key : prop.stringPropertyNames()) {
			map.put(key, prop.getProperty(key));
		}
		return map;
	}

	/**
	 * 以 UTF-8 编码加载并返回 url 指向的配置文件
	 * 若加载失败，返回空 Map
	 * @param url
	 * @return
	 * @throws IBIOException
	 */
	public static HashMap<String, String> load(URL url) {
		try {
			return load(url.openStream());
		} catch (IOException e) {
			throw new IBIOException(e);
		}
	}

}
