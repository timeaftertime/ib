package cn.milai.ib.conf;

import cn.milai.ib.IBCore;
import cn.milai.ib.loader.ConfigLoader;
import cn.milai.ib.loader.ex.PropNotFoundException;
import cn.milai.ib.util.StringUtil;

/**
 * InfinityBattle 系统级配置
 * @author milai
 */
public class IBConf {

	private static CoreConf conf = IBCore.getBean(CoreConf.class);

	private IBConf() {}

	/**
	 * 按照 {@link CoreConf#getSpeed()} 设置的比例缩放指定时间
	 * @param time
	 * @return
	 */
	public static int frameProrate(long time) {
		return (int) (time / conf.getSpeed());
	}

	/**
	 * 获取指定游戏对象 String 类型配置
	 * 将去掉字符串前后的空字符
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static String strConf(Class<?> clazz, String key) {
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
	public static int intConf(Class<?> clazz, String key) {
		return StringUtil.parseInt(strConf(clazz, key));
	}

	/**
	 * 获取指定游戏对象 long 类型配置
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static long longConf(Class<?> clazz, String key) {
		return Long.parseLong(strConf(clazz, key));
	}

	/**
	 * 获取指定游戏对象 double 类型配置
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static double doubleConf(Class<?> clazz, String key) {
		return Double.parseDouble(strConf(clazz, key));
	}

	/**
	 * 获取当前游戏刷新时间间隔（微秒）
	 * @return
	 */
	public static long refreshMillisec() {
		return frameProrate(50L);
	}

	public static int imageUpdateFrame() {
		return conf.getImageUpdateFrame();
	}

}
