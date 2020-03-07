package cn.milai.ib.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import cn.milai.ib.conf.PathConf;
import cn.milai.ib.ex.IBIOException;
import cn.milai.ib.util.FileUtil;
import cn.milai.ib.util.IOUtil;
import cn.milai.ib.util.PropertiesUtil;

/**
 * 游戏对象配置信息加载器
 * 2019.11.24
 * @author milai
 */
public abstract class ConfigLoader {

	private static final Logger log = LoggerFactory.getLogger(ConfigLoader.class);

	/**
	 * 已经加载的配置
	 * class.getName() -> Map
	 */
	public static final Map<String, Map<String, String>> PROPS = Maps.newConcurrentMap();

	/**
	 * 获取指定 Class 的配置信息
	 * 每个 class 对应的配置信息只会加载一次
	 * @param clazz
	 * @return
	 */
	public static final Map<String, String> load(Class<?> clazz) {
		return PROPS.computeIfAbsent(clazz.getName(), c -> PropertiesUtil.load(getConfigStream(clazz)));
	}

	private static final InputStream getConfigStream(Class<?> clazz) {
		String path = PathConf.confPath(clazz);
		File file = new File(path);
		if (!file.exists()) {
			log.info("配置文件 {} 不存在，尝试从 classpath 复制……", path);
			FileUtil.save(path, IOUtil.toBytes(PathConf.confStream(clazz)));
		}
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new IBIOException("读取配置文件未知错误", e);
		}
	}

}
