package cn.milai.ib.container.pluginable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.milai.common.base.Collects;
import cn.milai.common.ex.unchecked.Uncheckeds;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.lifecycle.BaseLifecycleContainer;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.container.plugin.ContainerPlugin;

/**
 * {@link PluginableContainer} 默认实现
 * @author milai
 * @date 2021.02.09
 */
public class BasePluginableContainer extends BaseLifecycleContainer implements PluginableContainer {

	private Set<ContainerPlugin> plugins = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private List<PluginListener> listeners = new CopyOnWriteArrayList<>();

	public BasePluginableContainer() {
		addLifecycleListener(new LifecycleListener() {
			@Override
			public void onClosed(LifecycleContainer container) {
				Uncheckeds.log(BasePluginableContainer.this::stopAllPlugins);
			}

			@Override
			public void onEpochChanged(LifecycleContainer container) {
				resetAllPlugins();
			}
		});
	}

	@Override
	public void addPlugin(ContainerPlugin plugin) throws IllegalStateException, ContainerClosedException {
		checkClosed();
		if (plugin.isRunning()) {
			throw new IllegalStateException("不能添加运行中的插件");
		}
		if (plugins.add(plugin)) {
			notifyPluginAdded(plugin);
		}
	}

	@Override
	public void removePlugin(ContainerPlugin plugin) throws IllegalStateException, ContainerClosedException {
		checkClosed();
		if (plugin.isRunning()) {
			if (plugin.container() != this) {
				throw new IllegalStateException("不能移除不属于当前容器的插件");
			}
			plugin.stop();
		}
		if (plugins.remove(plugin)) {
			notifyPluginRemoved(plugin);
		}
	}

	@Override
	public void addPluginListener(PluginListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removePluginListener(PluginListener listener) {
		listeners.remove(listener);
	}

	private void notifyPluginAdded(ContainerPlugin plugin) {
		for (PluginListener listener : listeners) {
			listener.onPluginAdded(this, plugin);
		}
	}

	private void notifyPluginRemoved(ContainerPlugin plugin) {
		for (PluginListener listener : listeners) {
			listener.onPluginRemoved(this, plugin);
		}
	}

	private void stopAllPlugins() {
		for (ContainerPlugin plugin : plugins) {
			plugin.stop();
		}
	}

	private void resetAllPlugins() {
		for (ContainerPlugin plugin : plugins) {
			plugin.reset();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ContainerPlugin> List<T> getPlugins(Class<T> pluginClass) {
		return (List<T>) Collects.filterList(new ArrayList<>(plugins), p -> pluginClass.isInstance(p));
	}

}
