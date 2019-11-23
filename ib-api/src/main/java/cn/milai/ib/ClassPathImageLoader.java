package cn.milai.ib;

import java.awt.Image;

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
		return loadImage(clazz.getResource(imgPath.toString()));
	}

	private String classToPath(Class<?> clazz) {
		String[] splits = clazz.getName().split("\\.");
		for (int i = 0; i < splits.length; i++) {
			splits[i] = StringUtil.toSnake(splits[i]);
		}
		return String.join("/", splits);
	}

}
