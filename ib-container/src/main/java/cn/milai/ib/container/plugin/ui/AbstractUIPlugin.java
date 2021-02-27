package cn.milai.ib.container.plugin.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.lifecycle.LifecycleListener;
import cn.milai.ib.container.plugin.BaseContainerPlugin;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.util.ImageUtil;

/**
 * {@link UIPlugin} 抽象实现
 * @author milai
 * @date 2021.02.04
 */
public abstract class AbstractUIPlugin extends BaseContainerPlugin implements UIPlugin {

	/**
	 * 视图
	 */
	private Camera camera;

	/**
	 * 当前的 ui
	 */
	private BufferedImage nowImage;

	/**
	 * 背景图片
	 */
	private Image bgImage;

	/**
	 * 上次重新生成 UI 的帧计数
	 */
	private long lastFrame;

	private LifecycleListener listener;

	@Override
	public void setCamera(Camera camera) { this.camera = camera; }

	@Override
	public Camera getCamera() { return camera; }

	@Override
	public BufferedImage getNowImage() {
		if (shouldRefresh()) {
			synchronized (this) {
				if (shouldRefresh()) {
					refreshUI();
				}
			}
		}
		return nowImage;
	}

	private boolean shouldRefresh() {
		return lastFrame < getContainer().getFrame();
	}

	private void refreshUI() {
		long start = System.currentTimeMillis();
		
		PluginableContainer container = getContainer();
		int w = container.getW();
		int h = container.getH();
		if (w == 0 || h == 0) {
			return;
		}
		lastFrame = container.getFrame();
		BufferedImage image = ImageUtil.newImage(w, h);
		Graphics buffer = image.getGraphics();
		if (bgImage != null) {
			buffer.drawImage(bgImage.next(), 0, 0, w, h, null);
		}
		List<IBObject> objs = container.getAll(IBObject.class);
		Collections.sort(objs, Comparator.comparingInt(IBObject::getZ));
		for (IBObject o : objs) {
			o.paintWith(buffer);
		}
		buffer.dispose();
		nowImage = image;
		afterRefresh();
		
		metric(KEY_REFRESH_UI, System.currentTimeMillis() - start);
	}

	/**
	 * UI 被刷新后调用
	 */
	protected abstract void afterRefresh();

	@Override
	public void setBackgroud(Image img) { this.bgImage = img; }

	@Override
	public int getW() { return getContainer().getW(); }

	@Override
	public int getH() { return getContainer().getH(); }

	@Override
	protected final void onStart() {
		camera = new BaseCamera();
		lastFrame = getContainer().getFrame();
		listener = new LifecycleListener() {
			@Override
			public void afterRefresh(LifecycleContainer container) {
				refreshUI();
			}
		};
		getContainer().addLifecycleListener(listener);
		initUI();
	}

	/**
	 * {@link #onStart()} 调用后被调用
	 */
	protected abstract void initUI();

	@Override
	protected final void onStop() {
		getContainer().removeLifecycleListener(listener);
		destroyUI();
	}

	/**
	 * {@link #onStop()} 调用后调用
	 */
	protected abstract void destroyUI();
}
