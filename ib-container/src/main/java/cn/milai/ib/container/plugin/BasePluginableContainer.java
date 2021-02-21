package cn.milai.ib.container.plugin;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.milai.common.base.Collects;
import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.lifecycle.BaseLifecycleContainer;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.lifecycle.LifecycleListener;

/**
 * {@link PluginableContainer} 默认实现
 * @author milai
 * @date 2021.02.09
 */
public class BasePluginableContainer extends BaseLifecycleContainer implements PluginableContainer {

	private Set<ContainerPlugin> plugins;

	public BasePluginableContainer() {
		plugins = Sets.newConcurrentHashSet();
		addLifecycleListener(new LifecycleListener() {
			@Override
			public void onContainerClosed() {
				stopAllPlugins();
			}

			@Override
			public void afterEpochChanged(LifecycleContainer container) {
				stopAllPlugins();
				startAllPlugins();
			}
		});
	}

	@Override
	public void addPlugin(ContainerPlugin plugin) throws IllegalStateException, ContainerClosedException {
		checkClosed();
		if (plugin.isRunning()) {
			throw new IllegalStateException("不能添加运行中的插件");
		}
		plugins.add(plugin);
	}

	@Override
	public void removePlugin(ContainerPlugin plugin) throws IllegalStateException, ContainerClosedException {
		checkClosed();
		if (plugin.isRunning()) {
			if (plugin.getContainer() != this) {
				throw new IllegalStateException("不能移除不属于当前容器的插件");
			}
			plugin.stop();
		}
		plugins.remove(plugin);
	}

	private void stopAllPlugins() {
		for (ContainerPlugin plugin : plugins) {
			plugin.stop();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ContainerPlugin> List<T> getPlugins(Class<T> pluginClass) {
		return (List<T>) Collects.filter(Lists.newArrayList(plugins), p -> pluginClass.isInstance(p));
	}

}
