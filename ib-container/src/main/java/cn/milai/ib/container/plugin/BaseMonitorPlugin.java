package cn.milai.ib.container.plugin;

import java.util.Collections;
import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.ContainerMonitor;
import cn.milai.ib.container.lifecycle.LifecycleListener;
import cn.milai.ib.container.listener.ContainerListener;
import cn.milai.ib.container.listener.ObjectListener;

/**
 * {@link MonitorPlugin} 基础实现
 * @author milai
 * @date 2021.02.11
 */
public class BaseMonitorPlugin<T extends IBObject> extends BaseContainerPlugin implements MonitorPlugin<T> {

	private Class<T> clazz;
	private ContainerMonitor<T> monitor;
	private List<ContainerListener> listeners;

	/**
	 * 构造针对指定类型的 {@link BaseMonitorPlugin}
	 * @param clazz
	 */
	public BaseMonitorPlugin(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	protected final void onStart() {
		monitor = ContainerMonitor.monitor(getContainer(), clazz);
		listeners = newListeners();
		for (ContainerListener listener : listeners) {
			if (listener instanceof ObjectListener) {
				getContainer().addObjectListener((ObjectListener) listener);
			}
			if (listener instanceof LifecycleListener) {
				getContainer().addLifecycleListener((LifecycleListener) listener);
			}
		}
	}

	@Override
	protected final void onStop() {
		for (ContainerListener listener : listeners) {
			if (listener instanceof ObjectListener) {
				getContainer().removeObjectListener((ObjectListener) listener);
			}
			if (listener instanceof LifecycleListener) {
				getContainer().removeLifecycleListener((LifecycleListener) listener);
			}
		}
	}

	/**
	 * 创建一个监听器列表
	 * 这些监听器将在 {@link #start(cn.milai.ib.container.plugin.PluginableContainer)} 时被添加到容器中，在 {@link #stop()} 时被移除
	 * @return
	 */
	protected List<ContainerListener> newListeners() {
		return Collections.emptyList();
	}

	/**
	 * 获取监听到的所有角色列表
	 * @return
	 */
	protected List<T> getAll() { return monitor.getAll(); }
}
