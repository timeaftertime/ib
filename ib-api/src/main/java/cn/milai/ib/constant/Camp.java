package cn.milai.ib.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 阵营类型
 * @author milai
 */
public enum Camp {
	PLAYER,
	PLAYER_BULLET,
	ENEMY,
	ENEMY_BULLET,
	HELPER,;

	private static class Pair {
		Camp left;
		Camp right;

		public Pair(Camp left, Camp right) {
			this.left = left;
			this.right = right;
		}
	}

	private static List<Pair> sameCamps = Arrays.asList(
		// 不和自己的子弹交互
		new Pair(PLAYER, PLAYER_BULLET),
		new Pair(ENEMY, ENEMY_BULLET),
		// 子弹之间不交互
		new Pair(PLAYER_BULLET, ENEMY_BULLET),
		// 协助道具只和玩家交互
		new Pair(HELPER, ENEMY),
		new Pair(HELPER, ENEMY_BULLET),
		new Pair(HELPER, PLAYER_BULLET));

	/**
	 * 判断两个阵营是否处于不会冲突的阵营
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static boolean sameCamp(Camp c1, Camp c2) {
		if (c1 == null || c2 == null || c1 == c2) {
			return true;
		}
		for (Pair pair : sameCamps) {
			if (c1 == pair.left && c2 == pair.right || c1 == pair.right && c2 == pair.left) {
				return true;
			}
		}
		return false;
	}

}