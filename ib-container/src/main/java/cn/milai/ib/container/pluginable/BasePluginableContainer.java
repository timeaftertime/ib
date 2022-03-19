package cn.milai.ib.container.pluginable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.milai.beginning.collection.Filter;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.lifecycle.BaseLifecycleContainer;
import cn.milai.ib.container.listener.ContainerListeners;
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
		addLifecycleListener(ContainerListeners.closeListener(c -> plugins.forEach(this::doRemovePlugin)));
	}

	@Override
	public final boolean addPlugin(ContainerPlugin plugin) throws ContainerClosedException {
		checkClosed();
		return doAddPlugin(plugin);
	}

	private boolean doAddPlugin(ContainerPlugin plugin) {
		if (!plugin.plug(this)) {
			return false;
		}
		plugins.add(plugin);
		addLifecycleListener(plugin);
		addItemListener(plugin);
		notifyPluginAdded(plugin);
		return true;
	}

	@Override
	public final boolean removePlugin(ContainerPlugin plugin) throws ContainerClosedException {
		checkClosed();
		return doRemovePlugin(plugin);
	}

	private boolean doRemovePlugin(ContainerPlugin plugin) {
		if (!plugin.unplug(this)) {
			return false;
		}
		plugins.remove(plugin);
		removeLifecycleListener(plugin);
		removeItemListener(plugin);
		notifyPluginRemoved(plugin);
		return true;
	}

	@Override
	public final void addPluginListener(PluginListener listener) {
		listeners.add(listener);
	}

	@Override
	public final void removePluginListener(PluginListener listener) {
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

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ContainerPlugin> List<T> getPlugins(Class<T> pluginClass) {
		return (List<T>) Filter.list(new ArrayList<>(plugins), p -> pluginClass.isInstance(p));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ContainerPlugin> T getPlugin(Class<T> pluginClass) {
		for (ContainerPlugin plugin : plugins) {
			if (pluginClass.isInstance(plugin)) {
				return (T) plugin;
			}
		}
		return null;
	}

}
