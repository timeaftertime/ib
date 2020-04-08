package cn.milai.ib.drama;

import cn.milai.ib.container.Audio;
import cn.milai.ib.container.Image;
import cn.milai.ib.loader.AudioLoader;
import cn.milai.ib.loader.DramaStringLoader;
import cn.milai.ib.loader.ImageLoader;

/**
 * 剧本抽象基类
 * @author milai
 * @date 2020.03.06
 */
public abstract class AbstractDrama implements Drama {

	@Override
	public Image image(String resource) {
		return ImageLoader.load(getCode(), resource);
	}

	@Override
	public Audio audio(String audioCode, String resource) {
		return AudioLoader.load(audioCode, getCode(), resource);
	}

	@Override
	public String str(String strCode) {
		return DramaStringLoader.get(getCode(), strCode);
	}

}
