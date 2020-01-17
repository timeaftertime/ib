package cn.milai.ib;

import java.awt.Image;
import java.net.URL;

import cn.milai.ib.util.StringUtil;

/**
 * 从 ClassPath 的 img 目录下根据 className 和 status 加载图片
 *
 * 2019.11.16
 *
 * @author milai
 */
public class ClassPathImageLoader extends ImageLoader {

	private static final String IMAGE_DIR = "/img/";
	private static final String STATUS_SPLIT = "$";
	private static final String IMAGE_EXT = ".gif";

	@Override
	public Image loadImage(Class<?> clazz, String status) {
		StringBuilder imgPath = new StringBuilder(IMAGE_DIR).append(classToPath(clazz));
		if (status != null) {
			imgPath.append(STATUS_SPLIT).append(StringUtil.toSnake(status));
		}
		imgPath.append(IMAGE_EXT);
		URL url = clazz.getResource(imgPath.toString());
		if (url == null) {
			throw new NullPointerException(String.format("资源 classpath:%s 不存在", imgPath.toString()));
		}
		return loadImage(url);
	}

	private String classToPath(Class<?> clazz) {
		return clazz.getName().replace('.', '/');
	}

}
