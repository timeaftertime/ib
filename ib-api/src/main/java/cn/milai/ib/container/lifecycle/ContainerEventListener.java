package cn.milai.ib.container.lifecycle;

import cn.milai.ib.IBObject;

/**
 * 容器事件监听器
 * @author milai
 */
public interface ContainerEventListener {

	/**
	 * 是否跨越纪元，即 {@link LifecycleContainer#reset()} 时是否不移除当前监听器
	 * @return
	 */
	default boolean acrossEpoch() {
		return false;
	}

	/**
	 * 在容器纪元改变时调用
	 * @param container
	 */
	default void afterEpochChanged(LifecycleContainer container) {}

	/**
	 * 容器完成一次刷新时调用
	 * @param container
	 */
	default void afterRefresh(LifecycleContainer container) {}

	/**
	 * 游戏对象被加入后调用
	 * @param obj
	 */
	default void onObjectAdded(IBObject obj) {}

	/**
	 * 游戏对象被移除后被调用
	 * @param obj 被移除的对象
	 */
	default void onObjectRemoved(IBObject obj) {}

	/**
	 * 容器被关闭时调用
	 */
	default void onContainerClosed() {}

}
