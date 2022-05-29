package cn.milai.ib.role.nature.holder;

import cn.milai.ib.role.nature.Movable;

/**
 * 意识到 {@link Movable} 的 {@link MovableHolder}
 * @author milai
 * @date 2021.08.01
 */
public interface AwareMovableHolder extends MovableHolder {

	/**
	 * 刷新速度前调用
	 * @param 持有的 {@link Movable}
	 */
	default void beforeRefreshSpeeds(Movable m) {}

	/**
	 * 刷新速度后调用
	 * @param 持有的 {@link Movable}
	 */
	default void afterRefreshSpeeds(Movable m) {}

	/**
	 * 移动结束后调用
	 * @param m 持有的 {@link Movable}
	 */
	default void afterMove(Movable m) {}
}
