package cn.milai.ib.util;

import java.net.URL;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.milai.ib.ex.LoadConfigException;

/**
 * 游戏对象配置信息加载器
 * 2019.11.24
 * @author milai
 */
public abstract class ConfigLoader {

	private static final String PREFIX = "/conf/";
	private static final String SUFFIX = ".conf";

	public static final Map<Class<?>, Map<String, String>> props = Maps.newHashMap();

	/**
	 * 获取指定 Class 的配置信息
	 * 
	 * @param clazz
	 * @return
	 */
	public static final Map<String, String> getProp(Class<?> clazz) {
		if (props.containsKey(clazz)) {
			return props.get(clazz);
		}
		synchronized (props) {
			Map<String, String> prop = props.get(clazz);
			if (prop == null) {
				prop = PropertiesUtil.load(getConfigURL(clazz));
				props.put(clazz, prop);
			}
			return prop;
		}
	}

	private static final URL getConfigURL(Class<?> clazz) {
		String path = PREFIX + classToPath(clazz) + SUFFIX;
		URL url = clazz.getResource(path);
		if (url == null) {
			throw new LoadConfigException(path);
		}
		return url;
	}

	private static String classToPath(Class<?> clazz) {
		return clazz.getName().replace('.', '/');
	}

}
