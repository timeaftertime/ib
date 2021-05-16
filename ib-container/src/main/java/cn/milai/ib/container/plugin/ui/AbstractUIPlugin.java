package cn.milai.ib.container.plugin.ui;

import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.container.listener.ContainerListeners;
import cn.milai.ib.container.listener.LifecycleListener;
import cn.milai.ib.container.plugin.BaseContainerPlugin;
import cn.milai.ib.container.plugin.printer.PrinterPlugin;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.graphics.Images;

/**
 * {@link UIPlugin} 抽象实现
 * @author milai
 * @date 2021.05.06
 */
public abstract class AbstractUIPlugin extends BaseContainerPlugin implements UIPlugin {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractUIPlugin.class);

	/**
	 * 当前的 ui
	 */
	private BufferedImage ui = Images.newImage(1, 1);

	/**
	 * 上次重新生成 UI 的帧计数
	 */
	private long lastFrame;

	private LifecycleListener containerListener;

	/**
	 * 视图
	 */
	private Camera camera;

	@Override
	protected final void onStart() {
		setCamera(new BaseCamera());
		lastFrame = getContainer().getFrame() - 1;
		getContainer().addLifecycleListener(containerListener = ContainerListeners.refreshListener(c -> refreshUI()));
		startUIPlugin();
	}

	/**
	 * {@link #onStart()} 后调用
	 */
	protected void startUIPlugin() {}

	@Override
	protected final void onStop() {
		getContainer().removeLifecycleListener(containerListener);
		stopUIPlugin();
	}

	/**
	 * {@link #onStop()} 后调用
	 */
	protected void stopUIPlugin() {}

	@Override
	public void setCamera(Camera camera) { this.camera = camera; }

	@Override
	public Camera getCamera() { return camera; }

	@Override
	public BufferedImage getUI() {
		refreshUI();
		return ui == null ? ui : camera.reflect(ui);
	}

	private void refreshUI() {
		if (!shouldRefresh()) {
			return;
		}
		synchronized (this) {
			if (shouldRefresh()) {
				PluginableContainer container = getContainer();
				lastFrame = container.getFrame();
				PrinterPlugin printer = container.getPlugin(PrinterPlugin.class);
				if (printer == null) {
					LOG.debug("容器没有 {} 插件，返回空 UI", PrinterPlugin.class.getName());
					return;
				}
				ui = printer.print();
				refreshUIPlugin();
			}
		}
	}

	/**
	 * {@link #getUI()} 返回 UI 变化时调用
	 */
	protected abstract void refreshUIPlugin();

	private boolean shouldRefresh() {
		return lastFrame < getContainer().getFrame();
	}

}
