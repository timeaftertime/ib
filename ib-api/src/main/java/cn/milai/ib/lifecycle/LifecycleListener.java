package cn.milai.ib.lifecycle;

/**
 * {@link Lifecycle} 的监听器
 * @author milai
 * @date 2022.05.19
 */
public interface LifecycleListener {

	/**
	 * 启动时调用
	 * @param lifecycle
	 */
	default void onStarted(Lifecycle lifecycle) {
	}

	/**
	 * 刷新时调用
	 * @param lifecycle
	 * @param frame
	 */
	default void onRefreshed(Lifecycle lifecycle, long frame) {
	}

	/**
	 * 重置时调用
	 * @param lifecycle
	 * @param epoch
	 */
	default void onReseted(Lifecycle lifecycle, int epoch) {
	}

	/**
	 * 关闭时调用
	 * @param lifecycle
	 */
	default void onClosed(Lifecycle lifecycle) {
	}

}
