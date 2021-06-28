package cn.milai.ib.container.plugin.printer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.milai.ib.container.plugin.PropertyMonitorPlugin;
import cn.milai.ib.container.plugin.ui.Image;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.obj.property.Painter;

/**
 * {@link PrinterPlugin} 默认实现
 * @author milai
 * @date 2021.05.08
 */
public class BasePrinterPlugin extends PropertyMonitorPlugin<Painter> implements PrinterPlugin {

	private Image bgImage;

	public BasePrinterPlugin() {
		super(Painter.class);
	}

	@Override
	public BufferedImage print() {
		long start = System.currentTimeMillis();

		PluginableContainer container = getContainer();
		int w = container.getW();
		int h = container.getH();
		if (w == 0 || h == 0) {
			return Images.newImage(1, 1);
		}
		BufferedImage image = Images.newImage(w, h);
		Graphics buffer = image.getGraphics();
		if (bgImage != null) {
			buffer.drawImage(bgImage.next(), 0, 0, w, h, null);
		}
		List<Painter> painters = getAll();
		Collections.sort(painters, Comparator.comparingInt(p -> p.owner().getZ()));
		for (Painter p : painters) {
			p.paintWith(buffer);
		}
		buffer.dispose();

		metric(PRINT_DELAY, System.currentTimeMillis() - start);
		return image;
	}

	@Override
	public void setBackgroud(Image img) { this.bgImage = img; }

}
