package cn.milai.ib;

import java.awt.Image;

import cn.milai.ib.container.Audio;
import cn.milai.ib.loader.AudioLoader;
import cn.milai.ib.loader.ImageLoader;

/**
 * System 剧本的资源持有类
 * @author milai
 * @date 2020.03.06
 */
public class SystemRes {

	private static final String SYSTEM_DRAMA_CODE = "cn.milai.ib.drama.System";

	/**
	 * 获取 System 剧本的图片资源
	 * @param resource
	 * @return
	 */
	public static Image getImage(String resource) {
		return ImageLoader.load(SYSTEM_DRAMA_CODE, resource);
	}

	/**
	 * 获取 System 剧本的音频资源
	 * @param audioCode
	 * @param resource
	 * @return
	 */
	public static Audio getAudio(String audioCode, String resource) {
		return AudioLoader.load(audioCode, SYSTEM_DRAMA_CODE, resource);
	}
}