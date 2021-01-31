package cn.milai.ib.loader;

import cn.milai.ib.IBCore;
import cn.milai.ib.container.ui.Audio;
import cn.milai.ib.container.ui.AudioCreator;

/**
 * 音频加载器
 * @author milai
 * @date 2020.03.06
 */
public class AudioLoader {

	/**
	 * 加载指定剧本的指定 code 的音频
	 * @param audioCode
	 * @param dramaCode
	 * @param resource
	 * @return
	 */
	public static Audio load(String audioCode, String dramaCode, String resource) {
		return getAudioCreator().newAudio(audioCode, DramaResLoader.load(dramaCode, resource));
	}

	private static AudioCreator getAudioCreator() { return IBCore.getBean(AudioCreator.class); }
}
