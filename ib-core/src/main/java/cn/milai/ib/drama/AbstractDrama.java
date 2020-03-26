package cn.milai.ib.drama;

import java.awt.Image;

import cn.milai.ib.container.Audio;
import cn.milai.ib.loader.AudioLoader;
import cn.milai.ib.loader.DramaStringLoader;
import cn.milai.ib.loader.ImageLoader;

/**
 * 剧本抽象基类
 * @author milai
 * @date 2020.03.06
 */
public abstract class AbstractDrama implements Drama {

	/**
	 * 读取当前剧本的指定资源并转换为图片
	 * @param resource
	 * @return
	 */
	protected Image image(String resource) {
		return ImageLoader.load(getCode(), resource);
	}

	/**
	 * 读取当前剧本的指定资源并转换为指定 code 的音频
	 * @param audioCode
	 * @param resource
	 * @return
	 */
	protected Audio audio(String audioCode, String resource) {
		return AudioLoader.load(audioCode, getCode(), resource);
	}

	/**
	 * 读取当前剧本的指定字符串
	 * @param strCode
	 * @return
	 */
	protected String str(String strCode) {
		return DramaStringLoader.get(getCode(), strCode);
	}

}
