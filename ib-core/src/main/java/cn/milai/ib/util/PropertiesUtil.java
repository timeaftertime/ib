package cn.milai.ib.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.milai.common.ex.unchecked.Uncheckeds;

/**
 * Properties 相关工具类
 * 2020.01.12
 * @author milai
 */
public class PropertiesUtil {

	/**
	 * 以 UTF-8 编码加载并返回 in 指向的配置文件
	 * 若读取输入流失败，返回空 Map
	 * @param in
	 * @return
	 */
	public static Map<String, String> load(InputStream in) {
		Properties prop = new Properties();
		Uncheckeds.log(() -> prop.load(new InputStreamReader(in, StandardCharsets.UTF_8)));
		return toMap(prop);
	}

	/**
	 * 将 {@link Properties} 转换为 Map<String, String>
	 * @param prop
	 * @return
	 */
	public static Map<String, String> toMap(Properties prop) {
		Map<String, String> map = new HashMap<>();
		for (String key : prop.stringPropertyNames()) {
			map.put(key, prop.getProperty(key));
		}
		return map;
	}

}
