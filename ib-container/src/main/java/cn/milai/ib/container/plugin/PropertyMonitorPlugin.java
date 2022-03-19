package cn.milai.ib.container.plugin;

import java.util.Collections;
import java.util.List;

import cn.milai.ib.container.listener.PropertyMonitor;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.item.property.Property;

/**
 * 监听容器类型的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.11
 */
public class PropertyMonitorPlugin<T extends Property> extends MonitorListenerPlugin<PropertyMonitor<T>> {

	private Class<T> clazz;

	/**
	 * 构造针对指定类型的 {@link PropertyMonitorPlugin}
	 * @param clazz
	 */
	public PropertyMonitorPlugin(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	protected PropertyMonitor<T> newMonitor(PluginableContainer container) {
		return PropertyMonitor.monitor(container, clazz);
	}

	/**
	 * 获取监听到的所有 {@link Property} 列表
	 * @return
	 */
	protected List<T> getAll() { return getMonitor() == null ? Collections.emptyList() : getMonitor().getProps(); }
}
