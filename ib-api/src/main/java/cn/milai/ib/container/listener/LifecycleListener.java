package cn.milai.ib.container.listener;

import cn.milai.ib.container.lifecycle.LifecycleContainer;

/**
 * {@link LifecycleContainer} 生命周期事件监听器。
 * 所有方法调用为同步操作，不应该做耗时操作
 * @author milai
 */
public interface LifecycleListener extends ContainerListener {

	/**
	 * 启动时调用。调用当前方法时若 {@link LifecycleContainer} 已经启动，将立刻调用
	 * @param container
	 */
	default void onStart(LifecycleContainer container) {
	}

	/**
	 * 在容器纪元改变时调用
	 * @param container
	 */
	default void onEpochChanged(LifecycleContainer container) {
	}

	/**
	 * 容器完成一次刷新时调用
	 * @param container
	 */
	default void onRefresh(LifecycleContainer container) {
	}

	/**
	 * 容器被关闭时调用
	 * @param container
	 */
	default void onClosed(LifecycleContainer container) {
	}

}
