package cn.milai.ib.actor.nature;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.actor.Actor;
import cn.milai.ib.loader.ImageLoader;
import cn.milai.ib.plugin.ui.Image;

/**
 * {@link Painter} 基础实现
 * @author milai
 * @date 2021.06.06
 */
public class BasePainter extends AbstractNature implements Painter {
	private Map<String, Image> images = new ConcurrentHashMap<>();

	public BasePainter(Actor owner) {
		super(owner);
	}

	@Override
	public BufferedImage getNowImage() {
		return images.computeIfAbsent(owner().getStatus(), s -> ImageLoader.load(imageClass(), s)).next();
	}

	/**
	 * 获取图片 Class
	 * @return
	 */
	protected Class<?> imageClass() {
		return owner().getClass();
	}

	@Override
	public void paintWith(Graphics g) {
		BufferedImage nowImage = getNowImage();
		if (nowImage == null) {
			return;
		}
		Actor o = owner();
		g.drawImage(nowImage, o.getIntX(), o.getIntY(), o.getIntW(), o.getIntH(), null);
	}

}
