package cn.milai.ib.container.lifecycle;

/**
 * 对容器生命周期相关事件的监听器
 * @author milai
 * @date 2020.03.06
 */
public interface ContainerLifecycleListener {

	/**
	 * 容器被关闭时调用
	 */
	void onContainerClosed();
}
