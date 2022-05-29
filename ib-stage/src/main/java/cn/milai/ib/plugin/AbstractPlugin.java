package cn.milai.ib.plugin;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@link Plugin} 默认实现
 * @author milai
 * @date 2021.02.09
 */
public abstract class AbstractPlugin implements Plugin {

	private AtomicBoolean initailized = new AtomicBoolean();
	private AtomicBoolean destroyed = new AtomicBoolean();

	@Override
	public final void init() {
		if (initailized.compareAndSet(false, true)) {
			onInit();
		}
	}

	@Override
	public final boolean plug(Pluginable<?> p) {
		init();
		return doPlug(p);
	}

	protected abstract boolean doPlug(Pluginable<?> p);

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
		for (Pluginable<?> c : pluginables()) {
			unplug(c);
		}
		onDestroy();
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
