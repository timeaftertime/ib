package cn.milai.ib.util;

import java.awt.Image;
import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.milai.ib.conf.PathConf;

/**
 * 图片加载器，同一 URL 的图片只会被加载一次
 * @author milai
 * @date 2019.11.16
 */
public abstract class ImageLoader {

	private static final Map<String, Image> LOADED_IMAGES = Maps.newConcurrentMap();

	protected static Image loadImageFile(File file) {
		if (LOADED_IMAGES.containsKey(file.toString())) {
			return LOADED_IMAGES.get(file.toString());
		}

		Image image = null;
		synchronized (LOADED_IMAGES) {
			image = LOADED_IMAGES.get(file.toString());
			if (image == null) {
				image = ImageUtil.loadImage(file);
				if (image != null) {
					LOADED_IMAGES.put(file.toString(), image);
				}
			}
		}
		return image;
	}

	/**
	 * 加载指定游戏对象类型默认状态的图片
	 * 
	 * @param image
	 * @return
	 */
	public static Image loadImage(Class<?> clazz) {
		return loadImage(clazz, null);
	}

	/**
	 * 从 classpath 加载指定游戏对象类型在指定状态的图片
	 * @param clazz
	 * @param status
	 * @return
	 */
	public static Image loadImage(Class<?> clazz, String status) {
		String path = PathConf.img(clazz, status);
		File file = new File(path);
		if (!file.exists()) {
			FileUtil.save(path, HttpUtil.getFile(PathConf.imgRepo(clazz, status)));
		}
		return loadImageFile(file);
	}

}
