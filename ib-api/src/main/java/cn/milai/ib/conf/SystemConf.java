package cn.milai.ib.conf;

import cn.milai.ib.ConfigLoader;
import cn.milai.ib.ex.PropNotFoundException;

/**
 * 系统级配置
 *
 * @author milai
 */
public class SystemConf {

	// 整个游戏的尺寸比例，所有对象的大小、速度应乘以该常量
	private static final double SIZE_RATIO = getDoubleOption("ib.size", 1.0);
	// 整个游戏的速度比例，帧间隔时间应乘以该常量
	private static final double SPEED_RATIO = getDoubleOption("ib.speed", 1.0);

	/**
	 * 配置中表示无限大
	 */
	private static final String INF = "INF";

	/**
	 * 按照比例缩放大小或速度大小
	 * 
	 * @param value
	 * @return
	 */
	public static int prorate(double value) {
		return (int) (value * SIZE_RATIO);
	}

	/**
	 * 按照比例缩放帧间隔时间
	 * @param interval
	 * @return
	 */
	public static int frameProrate(long interval) {
		return (int) (interval / SPEED_RATIO);
	}

	/**
	 * 获取 -D 指定的 double 类型运行时参数
	 * 
	 * @param key 参数的 key
	 * @param defaultValue 未指定或指定错误时的默认值
	 * @return
	 */
	private static double getDoubleOption(String key, double defaultValue) {
		double value = defaultValue;
		String property = System.getProperty(key);
		if (property != null) {
			try {
				value = Double.parseDouble(property);
			} catch (NumberFormatException e) {
				//TODO 警告日志
			}
		}
		return value;
	}

	private static final String getValue(Class<?> clazz, String key) {
		String value = ConfigLoader.getProp(clazz).getProperty(key);
		if (value == null) {
			throw new PropNotFoundException(clazz, key);
		}
		return value;
	}

	/**
	 * 获取指定游戏对象 int 类型配置
	 * 
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static final int getInt(Class<?> clazz, String key) {
		String value = getValue(clazz, key);
		if (INF.equalsIgnoreCase(value.trim())) {
			return Integer.MAX_VALUE;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 获取指定游戏对象 long 类型配置
	 * 
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static final long getLong(Class<?> clazz, String key) {
		String value = getValue(clazz, key);
		if (INF.equalsIgnoreCase(value.trim())) {
			return Long.MAX_VALUE;
		}
		return Long.parseLong(value);
	}

	/**
	 * 获取指定游戏对象 double 类型配置
	 * 
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static final double getDouble(Class<?> clazz, String key) {
		return Double.parseDouble(getValue(clazz, key));
	}

}
