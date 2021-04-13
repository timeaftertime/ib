package cn.milai.ib.role.property;

/**
 * 需要进行碰撞检测
 * @author milai
 * @date 2021.03.29
 */
public interface Collider extends Property {

	/**
	 * 与另一个游戏角色碰撞时调用
	 * @param collider
	 */
	void onCollided(Collider collider);

}
