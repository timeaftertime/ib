package cn.milai.ib.role.property;

/**
 * 需要进行碰撞检测
 * @author milai
 * @date 2021.03.29
 */
public interface Collider extends Property {

	/**
	 * 与另一个 {@link Collider} 碰撞(开始接触)时调用
	 * @param c 碰撞的角色
	 */
	default void onCollided(Collider c) {}

	/**
	 * 每次移动后与另一个 {@link Collider} 仍然接触时调用
	 * @param c
	 */
	default void onTouching(Collider c) {}

	/**
	 * 与另一个 {@link Collider} 结束接触时调用
	 * @param c
	 */
	default void onLeave(Collider c) {}

}
