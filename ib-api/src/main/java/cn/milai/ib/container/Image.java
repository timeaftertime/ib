package cn.milai.ib.container;

import java.awt.image.BufferedImage;

/**
 * 图片
 * @author milai
 * @date 2020.04.03
 */
public interface Image {

	/**
	 * 获取第一张图片
	 * @return
	 */
	BufferedImage first();

	/**
	 * 获取下一张图片
	 * @return
	 */
	BufferedImage next();
}
