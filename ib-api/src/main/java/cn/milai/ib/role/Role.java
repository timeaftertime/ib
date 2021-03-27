package cn.milai.ib.role;

import java.awt.Graphics;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.lifecycle.LifecycleContainer;

/**
 * 参与到游戏中的游戏角色
 * @author milai
 * @date 2020.02.20
 */
public interface Role extends IBObject {

	/**
	 * 属性 life 的 key
	 */
	String P_LIFE = "life";

	@Override
	default void paintWith(Graphics g) {
		if (!isAlive()) {
			return;
		}
		IBObject.super.paintWith(g);
	}

	/**
	 * 获取初始生命值
	 * @return
	 */
	int getInitLife();

	/**
	 * 获取角色当前 life
	 * @return
	 */
	int getLife();

	/**
	 * 获取当前角色所属阵营
	 * @return
	 */
	default int getCamp() { return 0; }

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
	default void onDead() {}

	/**
	 * 获取最后使当前角色生命值减少的游戏角色
	 * 若不曾受到伤害或通过 toDead() 死亡，返回 null
	 * @return
	 */
	Role getLastAttacker();

	/**
	 * 恢复指定生命
	 * @param character 引起该次恢复的游戏角色
	 * @param life
	 * @throws IllegalArgumentException 若 life < 0
	 */
	void gainLife(Role character, int life) throws IllegalArgumentException;

	/**
	 * 失去指定生命
	 * @param character 引起该次失去的游戏角色
	 * @param life
	 * @throws IllegalArgumentException 若 life < 0
	 */
	void loseLife(Role character, int life) throws IllegalArgumentException;

	/**
	 * 获取当前游戏角色的朝向与 y 轴负方向的夹角弧度
	 * 顺时针为正
	 * @return
	 */
	double getDirection();

	/**
	 * 设置当前游戏角色的朝向
	 * 弧度将转化为 (-PI/2, PI/2] 之间
	 * @param radian 新朝向与 y 轴负方向的夹角弧度
	 * @return
	 */
	void setDirection(double radian);

	/**
	 * 确保当前游戏角色出于容器中，若不在，将其移动到容器之中
	 */
	void ensureInContainer();

	@Override
	LifecycleContainer getContainer();

}
