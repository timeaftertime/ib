package cn.milai.ib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.milai.ib.conf.PathConf;
import cn.milai.ib.ex.ConfigNotFoundException;

/**
 * 游戏对象配置信息加载器
 * 2019.11.24
 * @author milai
 */
public abstract class ConfigLoader {

	/**
	 * class.getName() -> Map
	 */
	public static final Map<String, Map<String, String>> props = Maps.newHashMap();

	/**
	 * 获取指定 Class 的配置信息
	 * 
	 * @param clazz
	 * @return
	 */
	public static final Map<String, String> getProp(Class<?> clazz) {
		String key = clazz.getName();
		if (props.containsKey(key)) {
			return props.get(key);
		}
		synchronized (props) {
			Map<String, String> prop = props.get(key);
			if (prop == null) {
				prop = PropertiesUtil.load(getConfigURL(clazz));
				props.put(key, prop);
			}
			return prop;
		}
	}

	private static final InputStream getConfigURL(Class<?> clazz) {
		String path = PathConf.conf(clazz);
		File file = new File(path);
		if (!file.exists()) {
			FileUtil.save(path, HttpUtil.getFile(PathConf.confRepo(clazz)));
		}
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ConfigNotFoundException(clazz.getName(), e);
		}
	}

}
