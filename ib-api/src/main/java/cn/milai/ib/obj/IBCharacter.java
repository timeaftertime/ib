package cn.milai.ib.obj;

import cn.milai.ib.constant.Camp;

/**
 * 参与到游戏中的游戏角色
 * @author milai
 * @date 2020.02.20
 */
public interface IBCharacter extends IBObject {

	/**
	 * 属性 life 的 key
	 */
	String P_LIFE = "life";

	/**
	 * 获取角色当前 life
	 * @return
	 */
	int getLife();

	/**
	 * 获取当前角色所属阵营
	 * 返回 null 表示不属于任何阵营，不与任何角色交互
	 * @return
	 */
	default Camp getCamp() {
		return null;
	}

	/**
	 * 是否存活
	 */
	boolean isAlive();

	/**
	 * 设置为死亡状态
	 */
	void toDead();

	/**
	 *  进入死亡状态时应调用此方法
	 */
	default void onDead() {
	}

	/**
	 * 获取最后使当前角色生命值减少的游戏角色
	 * 若不曾受到伤害或通过 toDead() 死亡，返回 null
	 * @return
	 */
	IBCharacter getLastAttacker();

	/**
	 * 恢复指定生命
	 * @param character 引起该次恢复的游戏角色
	 * @param life
	 * @throws IllegalArgumentException 若 life < 0
	 */
	void gainLife(IBCharacter character, int life) throws IllegalArgumentException;

	/**
	 * 失去指定生命
	 * @param character 引起该次失去的游戏角色
	 * @param life
	 * @throws IllegalArgumentException 若 life < 0
	 */
	void loseLife(IBCharacter character, int life) throws IllegalArgumentException;

}
