package cn.milai.ib.conf;

import java.util.Map;

import com.google.common.collect.Maps;

import cn.milai.ib.ex.PropNotFoundException;
import cn.milai.ib.loader.ConfigLoader;
import cn.milai.ib.util.PropertiesUtil;
import cn.milai.ib.util.StringUtil;

/**
 * 系统级配置
 * @author milai
 */
public class SystemConf {

	/**
	 * 可以通过命令行参数或配置文件指定的系统级参数
	 */
	private static final Map<String, String> SYS_PROPS = Maps.newHashMap();

	static {
		initSysProps();
		loadConfFile();
		parseJVMParams();
	}

	private static void initSysProps() {
		SYS_PROPS.put(SysProps.SIZE_RATIO, "1.0");
		SYS_PROPS.put(SysProps.SPEED_RATIO, "1.0");
		SYS_PROPS.put(SysProps.REPO_ADDRESS, "http://localhost:80");
		SYS_PROPS.put(SysProps.RESOURCE_PATH, SystemConf.class.getResource("/").getPath());
	}

	/**
	 * 加载 classpath 下的系统配置文件
	 */
	private static void loadConfFile() {
		Map<String, String> props = PropertiesUtil.load(SystemConf.class.getResourceAsStream(SysProps.CONF_FILE));
		for (String key : props.keySet()) {
			// 只替换预定义的配置
			if (SYS_PROPS.containsKey(key)) {
				SYS_PROPS.put(key, props.get(key));
			}
		}
	}

	/**
	 * 解析以 -D 指定的 JVM 参数
	 */
	private static void parseJVMParams() {
		// 只替换预定义的配置
		for (String key : SYS_PROPS.keySet()) {
			String value = System.getProperty(key);
			if (value != null) {
				SYS_PROPS.put(key, value);
			}
		}
	}

	/**
	 * 按照比例缩放大小或速度大小
	 * @param value
	 * @return
	 */
	public static int prorate(double value) {
		return (int) (value * getDouble(SysProps.SIZE_RATIO));
	}

	/**
	 * 按照比例缩放帧间隔时间
	 * @param interval
	 * @return
	 */
	public static int frameProrate(long interval) {
		return (int) (interval / getDouble(SysProps.SPEED_RATIO));
	}

	/**
	 * 获取 repo 的 url
	 * @return
	 */
	public static String repoURL() {
		return getStr(SysProps.REPO_ADDRESS);
	}

	/**
	 * 获取系统配置 key 的 String 类型值
	 * 将去掉字符串前后的空字符
	 * @param key
	 * @return
	 */
	public static final String getStr(String key) {
		return SYS_PROPS.get(key).trim();
	}

	/**
	 * 获取系统配置 key 的 double 类型值
	 * @param key
	 * @return
	 */
	public static final double getDouble(String key) {
		return Double.parseDouble(getStr(key));
	}

	/**
	 * 获取指定游戏对象 String 类型配置
	 * 将去掉字符串前后的空字符
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static final String getStr(Class<?> clazz, String key) {
		String value = ConfigLoader.load(clazz).get(key);
		if (value == null) {
			throw new PropNotFoundException(clazz, key);
		}
		return value.trim();
	}

	/**
	 * 获取指定游戏对象 int 类型配置
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static final int getInt(Class<?> clazz, String key) {
		return StringUtil.parseInt(getStr(clazz, key));
	}

	/**
	 * 获取指定游戏对象 long 类型配置
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static final long getLong(Class<?> clazz, String key) {
		return Long.parseLong(getStr(clazz, key));
	}

	/**
	 * 获取指定游戏对象 double 类型配置
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static final double getDouble(Class<?> clazz, String key) {
		return Double.parseDouble(getStr(clazz, key));
	}

}
