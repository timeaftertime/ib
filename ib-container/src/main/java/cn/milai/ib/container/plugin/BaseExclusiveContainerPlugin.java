package cn.milai.ib.container.plugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * {@link ExclusiveContainerPlugin} 默认实现
 * @author milai
 * @date 2022.03.19
 */
public class BaseExclusiveContainerPlugin extends AbstractContainerPlugin implements ExclusiveContainerPlugin {

	private AtomicReference<PluginableContainer> container = new AtomicReference<>();

	@Override
	public final boolean plug(PluginableContainer c) throws PluginDestroyedExpcetion {
		if (container.compareAndSet(c, c)) {
			return true;
		}
		if (container.compareAndSet(null, c)) {
			onPlug(c);
			return true;
		}
		return false;
	}

	/**
	 * 绑定插件时调用
	 * @param c
	 */
	protected void onPlug(PluginableContainer c) {
	}

	@Override
	public final boolean unplug(PluginableContainer c) throws PluginDestroyedExpcetion {
		if (container.compareAndSet(c, null)) {
			onUnplug(c);
		}
		return true;
	}

	/**
	 * 取消绑定插件时调用
	 * @param c
	 */
	protected void onUnplug(PluginableContainer c) {
	}

	@Override
	public Collection<PluginableContainer> containers() {
		PluginableContainer c = container.get();
		return c == null ? Collections.emptyList() : Arrays.asList(c);
	}

	@Override
	public PluginableContainer container() {
		return container.get();
	}

}
