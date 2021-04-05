package cn.milai.ib.container.plugin;

import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.listener.TypeMonitor;

/**
 * 监听指定类型的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.03.31
 */
public class TypeMonitorPlugin<T extends IBObject> extends ListenersPlugin {

	private Class<T> clazz;
	private TypeMonitor<T> monitor;

	public TypeMonitorPlugin(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	protected void afterAddListeners() {
		monitor = TypeMonitor.monitor(getContainer(), clazz);
	}

	protected List<T> getAll() { return monitor.getAll(); }
}
