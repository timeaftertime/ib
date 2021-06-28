package cn.milai.ib.container.plugin;

import java.util.List;

import cn.milai.ib.container.listener.PropertyMonitor;
import cn.milai.ib.obj.property.Property;

/**
 * 监听容器类型的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.11
 */
public class PropertyMonitorPlugin<T extends Property> extends ListenersPlugin {

	private Class<T> clazz;
	private PropertyMonitor<T> monitor;

	/**
	 * 构造针对指定类型的 {@link PropertyMonitorPlugin}
	 * @param clazz
	 */
	public PropertyMonitorPlugin(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	protected final void afterAddListeners() {
		monitor = PropertyMonitor.monitor(getContainer(), clazz);
	}

	/**
	 * 获取监听到的所有 {@link Property} 列表
	 * @return
	 */
	protected List<T> getAll() { return monitor.getProps(); }
}
