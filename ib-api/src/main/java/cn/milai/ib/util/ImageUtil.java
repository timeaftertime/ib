package cn.milai.ib.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * 图片相关工具类
 *
 * 2019.11.21
 *
 * @author milai
 */
public abstract class ImageUtil {

	private ImageUtil() {
	}

	private static final Toolkit TK = Toolkit.getDefaultToolkit();

	/**
	 * 加载 url 指定的图片
	 * 
	 * @param url
	 * @return
	 */
	public static final Image loadImage(URL url) {
		return TK.createImage(url);
	}

	/**
	 * 返回 image 的复制
	 * @param image
	 * @return
	 */
	public static BufferedImage copy(BufferedImage image) {
		BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		img.setData(image.getData());
		return img;
	}
}
