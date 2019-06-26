package cn.milai.ib.client.util;

import java.util.Random;

public final class RandomUtil {

	private static final Random rand = new Random();

	public static boolean goalAtPossible(int expected, int sum) {
		if (expected < 0 || sum < 0) {
			throw new IllegalArgumentException("参数必须为正数：possibility=" + expected + " sum=" + sum);
		}
		if (expected > sum) {
			throw new IllegalArgumentException("expected 必须小于等于 sum： expected=" + expected + " sum=" + sum);
		}
		return rand.nextInt(sum) < expected;
	}

	public static final int nextInt(int limit) {
		return rand.nextInt(limit);
	}
}
