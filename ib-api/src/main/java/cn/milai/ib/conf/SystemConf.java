package cn.milai.ib.conf;

public class SystemConf {

	// 整个游戏的尺寸比例，所有对象的大小、速度应乘以该常量
	private static final double SIZE_RATIO = getDouble("ib.size", 1.0);
	// 整个游戏的速度比例，帧间隔时间应乘以该常量
	private static final double SPEED_RATIO = getDouble("ib.speed", 1.0);

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
	private static double getDouble(String key, double defaultValue) {
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
}
