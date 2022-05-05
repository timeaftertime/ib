package cn.milai.ib.container.plugin.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.container.plugin.BaseExclusiveContainerPlugin;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.geometry.BaseBounds;
import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.global.IBMetrics;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.item.property.Painter;
import io.micrometer.core.instrument.Timer;

/**
 * {@link UIPlugin} 抽象实现
 * @author milai
 * @date 2021.05.06
 */
public class BaseUIPlugin extends BaseExclusiveContainerPlugin implements UIPlugin {

	private static final Timer REFRESH_DELAY = Timer.builder("containerplugin.ui.delay").register(IBMetrics.registry());

	private static final BufferedImage DEF_UI = Images.newImage(1, 1);

	private String title = "";
	private Bounds bounds = new BaseBounds();
	private Image bgImage;
	private List<Screen> screens = new ArrayList<>();
	/**
	 * 当前的 ui
	 */
	private BufferedImage ui = DEF_UI;

	private AtomicBoolean refreshing = new AtomicBoolean();

	@Override
	protected void onInit() {
		notifyScreens(Screen::onInit);
	}

	@Override
	public void onRefresh(LifecycleContainer container) {
		container.eventLoop().submit(() -> refreshUI((PluginableContainer) container));
	}

	@Override
	public BufferedImage getUI() { return ui; }

	private void refreshUI(PluginableContainer container) {
		if (!refreshing.compareAndSet(false, true)) {
			return;
		}
		ui = print(container);
		notifyScreens(Screen::onRefreshed);
		refreshing.set(false);
	}

	@Override
	protected void onDestroy() {
		notifyScreens(Screen::onDestroyed);
	}

	private BufferedImage print(PluginableContainer container) {
		return REFRESH_DELAY.record(() -> {
			int w = bounds.getIntW();
			int h = bounds.getIntH();
			if (w <= 0 || h <= 0) {
				return DEF_UI;
			}
			BufferedImage image = Images.newImage(w, h);
			Graphics buffer = image.getGraphics();
			if (bgImage != null) {
				buffer.drawImage(bgImage.next(), 0, 0, w, h, null);
			}
			List<Painter> painters = container.getProps(Painter.class);
			Collections.sort(painters, Comparator.comparingInt(p -> p.owner().getZ()));
			for (Painter p : painters) {
				p.paintWith(buffer);
			}
			buffer.dispose();
			return image;
		});

	}

	@Override
	public void addScreen(Screen screen) {
		screens.add(screen);
		screen.setUI(this);
	}

	@Override
	public void setBackgroud(Image img) { bgImage = img; }

	@Override
	public Bounds bounds() {
		return bounds;
	}

	@Override
	public void setX(double x) {
		UIPlugin.super.setX(x);
		notifyScreens(Screen::onRelocationed);
	}

	@Override
	public void setY(double y) {
		UIPlugin.super.setY(y);
		notifyScreens(Screen::onRelocationed);
	}

	@Override
	public void setW(double w) {
		UIPlugin.super.setW(w);
		notifyScreens(Screen::onResized);
	}

	@Override
	public void setH(double h) {
		UIPlugin.super.setH(h);
		notifyScreens(Screen::onResized);
	}

	private void notifyScreens(Consumer<Screen> consumer) {
		for (Screen s : screens) {
			consumer.accept(s);
		}
	}

	@Override
	public void setTitle(String title) { this.title = title; }

	@Override
	public String getTitle() { return title; }

}
