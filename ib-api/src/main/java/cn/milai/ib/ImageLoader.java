package cn.milai.ib;

import java.awt.Image;
import java.net.URL;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.milai.ib.util.ImageUtil;

/**
 * 图片加载器
 * 
 * 同一 URL 的图片只会被加载一次
 *
 * 2019.11.16
 *
 * @author milai
 */
public abstract class ImageLoader {

	private static final Map<String, Image> LOADED_IMAGES = Maps.newConcurrentMap();

	private static final ThreadLocal<ImageLoader> contextImageLoaders = new ThreadLocal<>();

	/**
	 * 获取线程上线文 ImageLoader
	 * @return
	 */
	public static ImageLoader getContextImageLoader() {
		if (contextImageLoaders.get() != null) {
			return contextImageLoaders.get();
		}
		ImageLoader imageLoader = null;
		synchronized (contextImageLoaders) {
			if (contextImageLoaders.get() != null) {
				imageLoader = contextImageLoaders.get();
			}
			imageLoader = new ClassPathImageLoader();
			contextImageLoaders.set(imageLoader);
		}
		return imageLoader;
	}

	protected Image loadImage(URL url) {
		if (LOADED_IMAGES.containsKey(url.toString())) {
			return LOADED_IMAGES.get(url.toString());
		}

		Image image = null;
		synchronized (LOADED_IMAGES) {
			image = LOADED_IMAGES.get(url.toString());
			if (image == null) {
				image = ImageUtil.loadImage(url);
				if (image != null) {
					LOADED_IMAGES.put(url.toString(), image);
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
	public Image loadImage(Class<?> clazz) {
		return loadImage(clazz, null);
	}

	/**
	 * 加载指定游戏对象类型 status 状态下的图片
	 * 
	 * @param clazz
	 * @param status
	 * @return
	 */
	public abstract Image loadImage(Class<?> clazz, String status);

}
