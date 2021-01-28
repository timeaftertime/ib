package cn.milai.ib.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
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
	 * 以 UTF-8 编码加载并返回 in 指向的配置文件
	 * 若读取输入流失败，返回空 Map
	 * @param in
	 * @return
	 */
	public static Map<String, String> load(InputStream in) {
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(in, CHARSET));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> map = Maps.newHashMap();
		for (String key : prop.stringPropertyNames()) {
			map.put(key, prop.getProperty(key));
		}
		return map;
	}

}
