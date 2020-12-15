package cn.milai.ib.container.ui;

import java.awt.image.BufferedImage;

import cn.milai.ib.conf.SysProps;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.ui.Image;

/**
 * Image 默认实现
 * @author milai
 * @date 2020.04.03
 */
public class BaseImage implements Image {

	private BufferedImage[] images;
	private int nextCnt = 1;
	private int imageIndex = 0;

	public BaseImage(BufferedImage... bufferedImages) {
		this.images = bufferedImages;
	}

	@Override
	public BufferedImage first() {
		return images[0];
	}

	@Override
	public synchronized BufferedImage next() {
		nextCnt = (nextCnt + 1) % SystemConf.getInt(SysProps.IMAGE_UPDATE_FRAME);
		if (nextCnt == 0) {
			imageIndex = (imageIndex + 1) % images.length;
		}
		return images[imageIndex];
	}

}
