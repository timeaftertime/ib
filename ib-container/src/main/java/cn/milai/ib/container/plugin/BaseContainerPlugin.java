package cn.milai.ib.container.plugin;

import java.util.concurrent.atomic.AtomicBoolean;

import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * {@link ContainerPlugin} 默认实现
 * @author milai
 * @date 2021.02.09
 */
public class BaseContainerPlugin implements ContainerPlugin {

	private PluginableContainer container;
	private AtomicBoolean started = new AtomicBoolean();

	public final PluginableContainer container() { return container; }

	@Override
	public final boolean start(PluginableContainer container) {
		if (!started.compareAndSet(false, true)) {
			return false;
		}
		this.container = container;
		onStart();
		return true;
	}

	@Override
	public final boolean stop() {
		if (!started.compareAndSet(true, false)) {
			return false;
		}
		onStop();
		return true;
	}

	@Override
	public final void reset() {
		if (!isRunning()) {
			return;
		}
		doReset();
	}

	@Override
	public final boolean isRunning() { return started.get(); }

	/**
	 * 启动插件时调用
	 */
	protected void onStart() {}

	/**
	 * 停止插件时调用
	 */
	protected void onStop() {}

	/**
	 * 重置插件时调用
	 */
	protected void doReset() {
		PluginableContainer c = container();
		if (stop()) {
			start(c);
		}
	}

}
