package cn.milai.ib.container.plugin;

import java.util.Collections;
import java.util.List;

import cn.milai.ib.container.Container;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.ContainerListeners;

/**
 * {@link ListenersPlugin} 默认实现
 * @author milai
 * @date 2021.02.25
 */
public class ListenersPlugin extends BaseContainerPlugin {

	private List<ContainerListener> listeners;

	@Override
	protected final void onStart() {
		listeners = newListeners();
		for (ContainerListener listener : listeners) {
			ContainerListeners.add(getContainer(), listener);
		}
		afterAddListeners();
	}

	/**
	 * 将 {@link #newListeners()} 返回的 {@link ContainerListener} 添加到 {@link Container} 后调用
	 */
	protected void afterAddListeners() {}

	@Override
	protected final void onStop() {
		for (ContainerListener listener : listeners) {
			ContainerListeners.remove(getContainer(), listener);
		}
		afterRemoveListeners();
	}

	/**
	 * 移除 {@link #newListeners()} 返回的 {@link ContainerListener} 后调用
	 */
	protected void afterRemoveListeners() {}

	/**
	 * 创建一个监听器列表
	 * 这些监听器将在 {@link #start(cn.milai.ib.container.plugin.PluginableContainer)} 时被添加到容器中，在 {@link #stop()} 时被移除
	 * @return
	 */
	protected List<ContainerListener> newListeners() {
		return Collections.emptyList();
	}
}
