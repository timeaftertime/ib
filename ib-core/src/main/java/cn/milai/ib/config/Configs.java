package cn.milai.ib.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.common.ex.unchecked.Uncheckeds;
import cn.milai.ib.conf.IBConf;
import cn.milai.ib.util.PropertiesUtil;

/**
 * {@link Config} 管理器
 * @author milai
 * @date 2021.06.05
 */
public class Configs {

	private Configs() {}

	private static final String CONFIG_PREFIX = ".properties";

	private static final Logger LOG = LoggerFactory.getLogger(Configs.class);

	private static Map<String, Config> configs = new ConcurrentHashMap<>();

	/**
	 * 加载指定 configCode ，若配置文件不存在，将使用默认 {@link Config}。
	 * 同一个 configCode 只会加载一次
	 * @param configCode
	 * @param defaultConfig
	 * @return
	 */
	public static Config load(String configCode, Supplier<Config> defaultConfig) {
		if (configs.containsKey(configCode)) {
			return configs.get(configCode);
		}
		synchronized (configs) {
			if (configs.containsKey(configCode)) {
				return configs.get(configCode);
			}
			Config config = loadFromFile(configCode);
			if (config == null) {
				config = defaultConfig.get();
				if (IBConf.saveConfigFile()) {
					saveConfigFile(configCode, config);
				}
			}
			configs.put(configCode, config);
			return config;
		}
	}

	private static Config loadFromFile(String configCode) {
		String path = configFile(configCode);
		if (!new File(path).exists()) {
			LOG.debug("配置文件不存在: file = {}", path);
			return null;
		}
		return new Config(configCode, Uncheckeds.rethrow(() -> PropertiesUtil.load(new FileInputStream(path))));
	}

	private static void saveConfigFile(String code, Config config) {
		StringBuilder sb = new StringBuilder();
		Map<String, String> m = config.toMap();
		for (String key : m.keySet()) {
			sb.append(key + "=" + m.get(key) + System.lineSeparator());
		}
		Uncheckeds.log(() -> {
			String path = configFile(code);
			File file = new File(path);
			LOG.debug("保存配置到文件: code = {}, file = {}", code, path);
			if (!file.getParentFile().mkdir()) {
				LOG.debug("创建配置文件目录失败: file = {}", file.getPath());
				return;
			}
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
			}
		});
	}

	private static String configFile(String code) {
		return IBConf.configFilePath() + code + CONFIG_PREFIX;
	}

}
