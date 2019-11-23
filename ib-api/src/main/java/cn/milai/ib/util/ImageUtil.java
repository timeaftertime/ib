package cn.milai.ib.util;

import java.awt.Image;
import java.awt.Toolkit;
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
}
