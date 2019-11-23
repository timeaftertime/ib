package cn.milai.ib.util;

import java.util.Random;

public final class RandomUtil {

	private static final ThreadLocal<Random> rands = new ThreadLocal<Random>();

	public static Random getRandom() {
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

	public static boolean goalAtPossible(int expected, int sum) {
		if (expected < 0 || sum < 0) {
			throw new IllegalArgumentException("参数必须为正数：possibility=" + expected + " sum=" + sum);
		}
		if (expected > sum) {
			throw new IllegalArgumentException("expected 必须小于等于 sum： expected=" + expected + " sum=" + sum);
		}
		return getRandom().nextInt(sum) < expected;
	}

	public static final int nextInt(int limit) {
		return getRandom().nextInt(limit);
	}
}
