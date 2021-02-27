package cn.milai.ib.container.plugin;

import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.ContainerMonitor;

/**
 * {@link MonitorPlugin} 基础实现
 * @author milai
 * @date 2021.02.11
 */
public class BaseMonitorPlugin<T extends IBObject> extends ListenersPlugin implements MonitorPlugin<T> {

	private Class<T> clazz;
	private ContainerMonitor<T> monitor;

	/**
	 * 构造针对指定类型的 {@link BaseMonitorPlugin}
	 * @param clazz
	 */
	public BaseMonitorPlugin(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	protected final void onStart() {
		super.onStart();
		monitor = ContainerMonitor.monitor(getContainer(), clazz);
	}

	/**
	 * 获取监听到的所有角色列表
	 * @return
	 */
	protected List<T> getAll() { return monitor.getAll(); }
}
