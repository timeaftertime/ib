package cn.milai.ib.role;

/**
 * 阵营
 * @author milai
 */
public interface Camp {

	/**
	 * 无阵营（保留），与任何阵营都交互
	 */
	public static final int NO_CAMP = 0;

	/**
	 * 玩家阵营
	 */
	public static final int PLAYER = 1;

	/**
	 * 玩家敌军阵营
	 */
	public static final int ENEMY = 2;

	/**
	 * 玩家援助道具阵营
	 */
	public static final int HELPER = 3;

	/**
	 * 判断两个阵营是否处于不会冲突的阵营
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static boolean sameCamp(int c1, int c2) {
		return c1 != NO_CAMP && c2 != NO_CAMP && c1 == c2;
	}

}