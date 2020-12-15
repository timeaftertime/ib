package cn.milai.ib.loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import cn.milai.ib.IBCore;
import cn.milai.ib.conf.PathConf;
import cn.milai.ib.container.ui.Image;
import cn.milai.ib.util.FileUtil;
import cn.milai.ib.util.IOUtil;
import cn.milai.ib.util.ImageUtil;

/**
 * 图片加载器，同一 URL 的图片只会被加载一次
 * @author milai
 * @date 2019.11.16
 */
public abstract class ImageLoader {

	private static final Logger log = LoggerFactory.getLogger(ImageLoader.class);

	/**
	 * 已经加载的图片
	 * fileName -> Image
	 */
	private static final Map<String, BufferedImage[]> IMAGES = Maps.newConcurrentMap();

	private static Image loadImageFile(File file) {
		return buildImage(IMAGES.computeIfAbsent(file.toString(), f -> ImageUtil.loadImage(file)));
	}

	/**
	 * 加载指定游戏对象类型默认状态的图片
	 * 每个 class 每个状态的图片只会加载一次
	 * @param image
	 * @return
	 */
	public static Image load(Class<?> clazz) {
		return load(clazz, null);
	}

	/**
	 * 获取指定游戏对象类型在指定状态的图片
	 * 每个 class 每个状态的图片只会加载一次
	 * @param clazz
	 * @param status
	 * @return
	 */
	public static Image load(Class<?> clazz, String status) {
		String path = PathConf.imgPath(clazz, status);
		File file = new File(path);
		if (!file.exists()) {
			log.info("图片文件 {} 不存在，尝试从 classpath 复制……", path);
			FileUtil.save(path, IOUtil.toBytes(PathConf.imgStream(clazz, status)));
		}
		return loadImageFile(file);
	}

	/**
	 * 加载指定剧本的指定图片
	 * @param dramaCode
	 * @param resource
	 * @return
	 */
	public static Image load(String dramaCode, String resource) {
		return buildImage(ImageUtil.loadImage(DramaResLoader.load(dramaCode, resource)));
	}

	private static Image buildImage(BufferedImage[] images) {
		return IBCore.getBean(Image.class, (Object) (images));
	}

}
