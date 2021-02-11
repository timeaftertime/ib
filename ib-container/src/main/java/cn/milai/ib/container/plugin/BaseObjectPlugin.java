package cn.milai.ib.container.plugin;

import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.ContainerMonitor;
import cn.milai.ib.container.lifecycle.ContainerEventListener;

/**
 * {@link ObjectPlugin} 基础实现
 * @author milai
 * @date 2021.02.11
 */
public class BaseObjectPlugin<T extends IBObject> extends BaseContainerPlugin implements ObjectPlugin<T> {

	private Class<T> clazz;
	private ContainerMonitor<T> monitor;
	private ContainerEventListener listener;

	/**
	 * 构造针对指定类型的 {@link BaseObjectPlugin}
	 * @param clazz
	 */
	public BaseObjectPlugin(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	protected final void onStart() {
		monitor = ContainerMonitor.monitor(getContainer(), clazz);
		listener = newEventListener();
		getContainer().addEventListener(listener);
	}

	@Override
	protected final void onStop() {
		getContainer().removeEventListener(listener);
	}

	/**
	 * 创建一个监听器
	 * 这个监听器将在 {@link #start(cn.milai.ib.container.plugin.PluginableContainer)} 时被添加到容器中
	 * 在 {@link #stop()} 将被移除
	 * @return
	 */
	protected ObjectLifecycleListener newEventListener() {
		return new ObjectLifecycleListener() {};
	}

	/**
	 * 获取监听到的所有角色列表
	 * @return
	 */
	protected List<T> getAll() { return monitor.getAll(); }
}
