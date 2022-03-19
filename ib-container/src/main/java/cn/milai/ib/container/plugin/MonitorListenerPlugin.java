package cn.milai.ib.container.plugin;

import cn.milai.ib.container.listener.ContainerMonitor;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 持有 {@link ContainerMonitor} 的 {@link BaseListenersPlugin}
 * @author milai
 * @date 2022.02.19
 */
public abstract class MonitorListenerPlugin<T extends ContainerMonitor> extends BaseListenersPlugin {

	private T monitor;

	@Override
	protected void afterAddListeners(PluginableContainer container) {
		monitor = newMonitor(container);
	}

	@Override
	protected void afterRemoveListeners(PluginableContainer container) {
		monitor.stop();
		monitor = null;
	}

	/**
	 * 创建被管理的 {@link ContainerMonitor}
	 * @param container
	 * @return
	 */
	protected abstract T newMonitor(PluginableContainer container);

	/**
	 * 获取被管理的 {@link ContainerMonitor}
	 * @return
	 */
	protected T getMonitor() { return monitor; }
}
