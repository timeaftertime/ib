package cn.milai.ib.loader;

import java.io.InputStream;
import java.util.List;

import cn.milai.ib.IBCore;
import cn.milai.ib.container.plugin.media.Audio;
import cn.milai.ib.container.plugin.media.AudioCreator;

/**
 * 音频加载器
 * @author milai
 * @date 2020.03.06
 */
public class AudioLoader {

	private AudioLoader() {
	}

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

	private static AudioCreator getAudioCreator() {
		List<AudioCreator> creators = IBCore.getBeans(AudioCreator.class);
		if (creators.isEmpty()) {
			return new AudioCreator() {
				@Override
				public Audio newAudio(String code, InputStream in) {
					return new Audio() {
						@Override
						public boolean play() {
							throw new UnsupportedOperationException();
						}

						@Override
						public boolean isComplete() {
							throw new UnsupportedOperationException();
						}

						@Override
						public String getCode() {
							throw new UnsupportedOperationException();
						}
					};
				}
			};
		}
		return creators.get(0);
	}

}
