package cn.milai.ib.container.listener;

import cn.milai.ib.container.lifecycle.LifecycleContainer;

/**
 * {@link LifecycleContainer} 生命周期事件监听器
 * @author milai
 */
public interface LifecycleListener extends ContainerListener {

	/**
	 * 在容器纪元改变时调用
	 * @param container
	 */
	default void onEpochChanged(LifecycleContainer container) {}

	/**
	 * 容器完成一次刷新时调用
	 * @param container
	 */
	default void onRefresh(LifecycleContainer container) {}

	/**
	 * 容器被关闭时调用
	 * @param container
	 */
	default void onClosed(LifecycleContainer container) {}

}
