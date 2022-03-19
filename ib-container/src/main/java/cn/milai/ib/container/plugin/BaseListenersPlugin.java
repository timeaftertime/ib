package cn.milai.ib.container.plugin;

import java.util.HashSet;
import java.util.Set;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.ContainerListeners;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 关联容器时添加监听器并在取消关联时移除的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.25
 */
public class BaseListenersPlugin extends BaseExclusiveContainerPlugin {

	private Set<ContainerListener> listeners = new HashSet<>();

	/**
	 * 添加一个被管理的 {@link ContainerListener}。
	 * 重复添加的 listener 将被忽略。
	 * 这些 listener 将只在 {@link #afterPlugin(PluginableContainer)} 被添加到关联的 {@link Container} ，
	 * 并在 {@link #beforeUnplug(PluginableContainer)} 从关联 {@link Container} 移除。
	 * @param listener
	 * @return 是否实际添加了 {@link ContainerListener}
	 */
	protected boolean addListener(ContainerListener listener) {
		return listeners.add(listener);
	}

	/**
	 * 添加一个被管理的刷新事件监听器
	 * @see #addListener(ContainerListener)
	 * @param listener
	 * @return
	 */
	protected boolean addRefreshListener(PluginableContainerCallback listener) {
		return listeners.add(ContainerListeners.refreshListener(c -> listener.callback((PluginableContainer) c)));
	}

	public static interface PluginableContainerCallback {
		void callback(PluginableContainer container);
	}

	@Override
	protected void onPlug(PluginableContainer container) {
		beforeAddListeners(container);
		for (ContainerListener listener : listeners) {
			ContainerListeners.add(container, listener);
		}
		afterAddListeners(container);
	}

	@Override
	protected void onUnplug(PluginableContainer container) {
		beforeRemoveListeners(container);
		for (ContainerListener listener : listeners) {
			ContainerListeners.remove(container, listener);
		}
		afterRemoveListeners(container);
	}

	/**
	 * 将管理的 {@link ContainerListener} 添加到关联的 {@link PluginableContainer} 前调用
	 * @param container
	 */
	protected void beforeAddListeners(PluginableContainer container) {
	}

	/**
	 * 将管理的 {@link ContainerListener} 添加到关联的 {@link PluginableContainer} 后调用
	 * @param container
	 */
	protected void afterAddListeners(PluginableContainer container) {
	}

	/**
	 * 移除 {@link #newListeners()} 返回的 {@link ContainerListener} 后调用
	 * @param container
	 */
	protected void beforeRemoveListeners(PluginableContainer container) {
	}

	/**
	 * 移除 {@link #newListeners()} 返回的 {@link ContainerListener} 后调用
	 * @param container
	 */
	protected void afterRemoveListeners(PluginableContainer container) {
	}

}
