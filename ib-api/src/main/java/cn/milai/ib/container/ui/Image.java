package cn.milai.ib.container.ui;

import java.awt.image.BufferedImage;

/**
 * 图片，必须有一个以 BufferedImage[] 为参数的构造方法
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
