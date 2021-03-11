package cn.milai.ib.container.listener;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.pluginable.PluginListener;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * {@link ContainerListener} 工具类
 * @author milai
 * @date 2021.03.09
 */
public class Listeners {

	/**
	 * 根据 {@code listener} 所实现的接口类型，分别调用对应的 add 方法添加到 {@code container} 中
	 * @param container
	 * @param listener
	 */
	public static void add(Container container, ContainerListener listener) {
		if (listener instanceof ObjectListener) {
			container.addObjectListener((ObjectListener) listener);
		}
		if ((container instanceof LifecycleContainer) && (listener instanceof LifecycleListener)) {
			((LifecycleContainer) container).addLifecycleListener((LifecycleListener) listener);
		}
		if ((container instanceof PluginableContainer) && (listener instanceof PluginListener)) {
			((PluginableContainer) container).addPluginListener((PluginListener) listener);
		}
	}

	/**
	 * 根据 {@code listener} 所实现的接口类型，分别调用对应的 remove 方法从 {@code container} 中移除
	 * @param container
	 * @param listener
	 */
	public static void remove(Container container, ContainerListener listener) {
		if (listener instanceof ObjectListener) {
			container.removeObjectListener((ObjectListener) listener);
		}
		if ((container instanceof LifecycleContainer) && (listener instanceof LifecycleListener)) {
			((LifecycleContainer) container).removeLifecycleListener((LifecycleListener) listener);
		}
		if ((container instanceof PluginableContainer) && (listener instanceof PluginListener)) {
			((PluginableContainer) container).removePluginListener((PluginListener) listener);
		}
	}

}
