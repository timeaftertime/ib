package cn.milai.ib.container.plugin;

import java.util.concurrent.atomic.AtomicBoolean;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * {@link ContainerPlugin} 默认实现
 * @author milai
 * @date 2021.02.09
 */
public abstract class AbstractContainerPlugin implements ContainerPlugin {

	private AtomicBoolean initailized = new AtomicBoolean();
	private AtomicBoolean destroyed = new AtomicBoolean();;

	@Override
	public final void init() {
		if (initailized.compareAndSet(false, true)) {
			onInit();
		}
	}

	/**
	 * 初始化
	 */
	protected void onInit() {
	}

	@Override
	public final void destroy() {
		if (!destroyed.compareAndSet(false, true)) {
			return;
		}
		for (PluginableContainer c : containers()) {
			unplug(c);
		}
		onDestroy();
	}

	@Override
	public void onStart(LifecycleContainer container) {
		init();
	}

	/**
	 * {@link #destroy()} 前调用
	 */
	protected void onDestroy() {
	}

	/**
	 * 当前插件是否已经初始化，即调用过 {@link #init(PluginableContainer)} 方法
	 * @return
	 */
	protected boolean isInitialized() { return initailized.get(); }

	/**
	 * 当前是否已经销毁，即调用过 {@link #destroy()} 方法
	 * @return
	 */
	protected boolean isDestroyed() { return destroyed.get(); }

}
