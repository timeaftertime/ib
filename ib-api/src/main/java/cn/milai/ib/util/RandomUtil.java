package cn.milai.ib.util;

import java.util.Random;

public final class RandomUtil {

	private static final ThreadLocal<Random> rands = new ThreadLocal<Random>();

	private static Random getRandom() {
		if (rands.get() != null) {
			return rands.get();
		}
		synchronized (rands) {
			Random random = rands.get();
			if (random == null) {
				random = new Random();
			}
			return random;
		}
	}

	/**
	 * 返回下一个随机浮点数 [0~1) 是否小于 limit
	 * 
	 * @param limit
	 * @return
	 */
	public static boolean nextLess(double limit) {
		return getRandom().nextDouble() < limit;
	}

	/**
	 * 返回下一个 [0~limit) 的随机整数
	 * @return
	 */
	public static int nextInt(int limit) {
		return getRandom().nextInt(limit);
	}

}
