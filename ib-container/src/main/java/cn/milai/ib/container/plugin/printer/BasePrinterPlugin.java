package cn.milai.ib.container.plugin.printer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.milai.ib.IBObject;
import cn.milai.ib.container.plugin.BaseContainerPlugin;
import cn.milai.ib.container.plugin.ui.Image;
import cn.milai.ib.container.pluginable.PluginableContainer;
import cn.milai.ib.graphics.Images;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Rotatable;

/**
 * {@link PrinterPlugin} 默认实现
 * @author milai
 * @date 2021.05.08
 */
public class BasePrinterPlugin extends BaseContainerPlugin implements PrinterPlugin {

	private Image bgImage;

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
		List<IBObject> objs = container.getAll(IBObject.class);
		Collections.sort(objs, Comparator.comparingInt(IBObject::getZ));
		for (IBObject o : objs) {
			if ((o instanceof Role) && ((Role) o).hasProperty(Rotatable.class)) {
				Rotatable.paintWith(buffer, (Role) o);
			} else {
				o.paintWith(buffer);
			}
		}
		buffer.dispose();
		
		metric(PRINT_DELAY, System.currentTimeMillis() - start);
		return image;
	}

	@Override
	public void setBackgroud(Image img) { this.bgImage = img; }

}
