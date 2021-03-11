package cn.milai.ib.container.plugin;

import java.util.Collections;
import java.util.List;

import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.Listeners;

/**
 * {@link ListenersPlugin} 默认实现
 * @author milai
 * @date 2021.02.25
 */
public class ListenersPlugin extends BaseContainerPlugin {

	private List<ContainerListener> listeners;

	@Override
	protected void onStart() {
		listeners = newListeners();
		for (ContainerListener listener : listeners) {
			Listeners.add(getContainer(), listener);
		}
	}

	@Override
	protected final void onStop() {
		for (ContainerListener listener : listeners) {
			Listeners.remove(getContainer(), listener);
		}
	}

	/**
	 * 创建一个监听器列表
	 * 这些监听器将在 {@link #start(cn.milai.ib.container.plugin.PluginableContainer)} 时被添加到容器中，在 {@link #stop()} 时被移除
	 * @return
	 */
	protected List<ContainerListener> newListeners() {
		return Collections.emptyList();
	}
}
