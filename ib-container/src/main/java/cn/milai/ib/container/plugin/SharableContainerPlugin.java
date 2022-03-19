package cn.milai.ib.container.plugin;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 可关联多个 {@link PluginableContainer} 的 {@link ContainerPlugin}
 * @author milai
 * @date 2022.03.18
 */
public class SharableContainerPlugin extends AbstractContainerPlugin {

	private Set<PluginableContainer> containers = Collections.newSetFromMap(new ConcurrentHashMap<>());

	@Override
	public boolean plug(PluginableContainer c) throws PluginDestroyedExpcetion {
		return containers.add(c);
	}

	@Override
	public boolean unplug(PluginableContainer c) throws PluginDestroyedExpcetion {
		containers.remove(c);
		return true;
	}

	@Override
	public Collection<PluginableContainer> containers() {
		return Collections.unmodifiableSet(containers);
	}

}
