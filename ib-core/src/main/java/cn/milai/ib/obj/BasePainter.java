package cn.milai.ib.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cn.milai.ib.loader.ImageLoader;
import cn.milai.ib.obj.property.BaseProperty;
import cn.milai.ib.obj.property.Painter;

/**
 * {@link Painter} 基础实现
 * @author milai
 * @date 2021.06.06
 */
public class BasePainter extends BaseProperty implements Painter {

	@Override
	public BufferedImage getNowImage() { return ImageLoader.load(owner().getClass(), owner().getStatus()).next(); }

	@Override
	public void paintWith(Graphics g) {
		BufferedImage nowImage = getNowImage();
		if (nowImage == null) {
			return;
		}
		IBObject o = owner();
		g.drawImage(nowImage, o.getIntX(), o.getIntY(), o.getIntW(), o.getIntH(), null);
	}

}
