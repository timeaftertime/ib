package cn.milai.ib.plugin.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.ib.actor.nature.Painter;
import cn.milai.ib.geometry.BaseBounds;
import cn.milai.ib.geometry.Bounds;
import cn.milai.ib.global.IBMetrics;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.plugin.BaseExclusiveCrew;
import cn.milai.ib.stage.Stage;
import cn.milai.ib.stage.event.ResizedEvent;
import cn.milai.ib.stage.event.StageRefreshedEvent;
import io.micrometer.core.instrument.Timer;

/**
 * {@link UICrew} 抽象实现
 * @author milai
 * @date 2021.05.06
 */
public class BaseUICrew extends BaseExclusiveCrew implements UICrew {

	private static final Logger LOG = LoggerFactory.getLogger(BaseUICrew.class);

	private static final Timer REFRESH_DELAY = Timer.builder("crew.ui.delay").register(IBMetrics.registry());

	private String title = "";
	private Bounds bounds = new BaseBounds();
	private Image bgImage;
	private List<Screen> screens = new ArrayList<>();
	/**
	 * 当前的 ui
	 */
	private BufferedImage ui = Images.EMPTY;

	private AtomicBoolean refreshing = new AtomicBoolean();

	@Override
	protected void onInit() {
		notifyScreens(Screen::onInit);
	}

	@Override
	public Consumer<StageRefreshedEvent> createOnRefreshed() {
		return e -> {
			Stage stage = e.stage();
			List<Painter> painters = stage.getNatures(Painter.NAME);
			stage.lifecycle()
				.loop()
				.submitDeputy(
					() -> {
						refreshUI((painters));
						notifyScreens(Screen::onRefreshed);
					}
				);
		};
	}

	@Override
	public BufferedImage getUI() { return ui; }

	@Override
	protected Consumer<ResizedEvent> createOnResized() {
		return e -> {
			Stage stage = e.stage();
			resize(stage.getW(), stage.getH());
		};
	}

	@Override
	protected void onDestroy() {
		notifyScreens(Screen::onDestroyed);
	}

	private void refreshUI(List<Painter> painters) {
		try {
			REFRESH_DELAY.record(() -> {
				int w = bounds.getIntW();
				int h = bounds.getIntH();
				if (w <= 0 || h <= 0) {
					refreshing.set(false);
					return;
				}
				BufferedImage image = Images.newImage(w, h);
				Graphics buffer = image.getGraphics();
				if (bgImage != null) {
					buffer.drawImage(bgImage.next(), 0, 0, w, h, null);
				}
				Collections.sort(painters, Comparator.comparingInt(p -> p.owner().getZ()));
				for (Painter p : painters) {
					p.paintWith(buffer);
				}
				buffer.dispose();
				ui = image;
				refreshing.set(false);
			});
		} catch (Exception e) {
			LOG.warn("刷新 UI 异常", e);
		}
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
		UICrew.super.setX(x);
		notifyScreens(Screen::onRelocationed);
	}

	@Override
	public void setY(double y) {
		UICrew.super.setY(y);
		notifyScreens(Screen::onRelocationed);
	}

	@Override
	public void setW(double w) {
		UICrew.super.setW(w);
		notifyScreens(Screen::onResized);
	}

	@Override
	public void setH(double h) {
		UICrew.super.setH(h);
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
