package cn.milai.ib;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;

import cn.milai.ib.util.StringUtil;

/**
 * 游戏对象配置信息加载器
 *
 * 2019.11.24
 *
 * @author milai
 */
public abstract class ConfigLoader {

	private static final String PREFIX = "/conf/";
	private static final String SUFFIX = ".conf";

	public static final Map<Class<?>, Properties> props = Maps.newHashMap();

	/**
	 * 获取指定 Class 的配置信息
	 * 
	 * @param clazz
	 * @return
	 */
	public static final Properties getProp(Class<?> clazz) {
		if (props.containsKey(clazz)) {
			return props.get(clazz);
		}
		synchronized (props) {
			Properties prop = props.get(clazz);
			if (prop == null) {
				prop = new Properties();
				try {
					prop.load(getConfigURL(clazz).openStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				props.put(clazz, prop);
			}
			return prop;
		}
	}

	private static final URL getConfigURL(Class<?> clazz) {
		String path = PREFIX + classToPath(clazz) + SUFFIX;
		URL url = clazz.getResource(path);
		if (url == null) {
			throw new NullPointerException(String.format("资源 classpath:%s 不存在", path));
		}
		return url;
	}

	private static String classToPath(Class<?> clazz) {
		String[] splits = clazz.getName().split("\\.");
		for (int i = 0; i < splits.length; i++) {
			splits[i] = StringUtil.toSnake(splits[i]);
		}
		return String.join("/", splits);
	}

}
