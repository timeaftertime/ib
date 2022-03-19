package cn.milai.ib.container.plugin;

import java.util.Collections;
import java.util.List;

import cn.milai.ib.container.listener.TypeMonitor;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.item.Item;

/**
 * 监听指定类型的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.03.31
 */
public class TypeMonitorPlugin<T extends Item> extends MonitorListenerPlugin<TypeMonitor<T>> {

	private Class<T> clazz;

	public TypeMonitorPlugin(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	protected TypeMonitor<T> newMonitor(PluginableContainer container) {
		return TypeMonitor.monitor(container, clazz);
	}

	protected List<T> getAll() { return getMonitor() == null ? Collections.emptyList() : getMonitor().getAll(); }
}
