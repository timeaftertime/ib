package cn.milai.ib.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.common.ex.unchecked.Uncheckeds;
import cn.milai.common.io.Files;
import cn.milai.common.io.InputStreams;
import cn.milai.ib.conf.PathConf;
import cn.milai.ib.util.PropertiesUtil;

/**
 * 游戏对象配置信息加载器
 * 2019.11.24
 * @author milai
 */
public class ConfigLoader {

	private ConfigLoader() {}

	private static final Logger LOG = LoggerFactory.getLogger(ConfigLoader.class);

	/**
	 * 已经加载的配置
	 * class.getName() -> Map
	 */
	public static final Map<String, Map<String, String>> PROPS = new ConcurrentHashMap<>();

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
			LOG.info("配置文件 {} 不存在，尝试从 classpath 复制……", path);
			Files.saveRethrow(path, InputStreams.toBytes(PathConf.confStream(clazz)));
		}
		return Uncheckeds.rethrow(() -> new FileInputStream(file));
	}

}
