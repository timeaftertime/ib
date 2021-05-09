package cn.milai.ib.loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.common.io.Files;
import cn.milai.common.io.InputStreams;
import cn.milai.ib.IBCore;
import cn.milai.ib.conf.PathConf;
import cn.milai.ib.container.plugin.ui.Image;
import cn.milai.ib.graphics.Images;

/**
 * 图片加载器，同一 URL 的图片只会被加载一次
 * @author milai
 * @date 2019.11.16
 */
public class ImageLoader {

	private ImageLoader() {}

	private static final Logger LOG = LoggerFactory.getLogger(ImageLoader.class);

	private static final String DEFAULT_STATUS = "default";

	/**
	 * 已经加载的图片
	 * fileName -> Image
	 */
	private static final Map<String, BufferedImage[]> IMAGES = new ConcurrentHashMap<>();

	private static Image loadImageFile(File file) {
		return buildImage(IMAGES.computeIfAbsent(file.toString(), f -> Images.loadImage(file)));
	}

	/**
	 * 加载指定游戏对象类型默认状态的图片
	 * 每个 class 每个状态的图片只会加载一次
	 * @param image
	 * @return
	 */
	public static Image load(Class<?> clazz) {
		return load(clazz, DEFAULT_STATUS);
	}

	/**
	 * 获取指定游戏对象类型在指定状态的图片
	 * 每个 class 每个状态的图片只会加载一次
	 * @param clazz
	 * @param status
	 * @return
	 */
	public static Image load(Class<?> clazz, String status) {
		if (status == null) {
			status = DEFAULT_STATUS;
		}
		String path = PathConf.imgFile(clazz, status);
		File file = new File(path);
		if (!file.exists()) {
			LOG.info("图片文件 {} 不存在，尝试从 classpath 复制……", path);
			Files.saveRethrow(path, InputStreams.toBytes(PathConf.imgStream(clazz, status)));
		}
		return loadImageFile(file);
	}

	/**
	 * 加载指定命名空间 code 的指定图片
	 * @param code
	 * @param resource
	 * @return
	 */
	public static Image load(String code, String resource) {
		return buildImage(Images.loadImage(DramaResLoader.load(code, resource)));
	}

	private static Image buildImage(BufferedImage... images) {
		return IBCore.getBean(Image.class, (Object) images);
	}

}
