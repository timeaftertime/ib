package cn.milai.ib.container.pluginable.media;

import cn.milai.ib.container.plugin.media.Audio;
import cn.milai.ib.container.plugin.media.MediaPlugin;
import cn.milai.ib.container.pluginable.PluginableContainer;

/**
 * 可能持有 {@link MediaPlugin} 的 {@link PluginableContainer}
 * @author milai
 * @date 2021.02.11
 */
public interface MediaContainer extends PluginableContainer {

	/**
	 * 播放音频
	 * @param audio
	 */
	default void playAudio(Audio audio) {
		fire(MediaPlugin.class, media -> media.playAudio(audio));
	}

	/**
	 * 停止音频的播放
	 * @param code
	 */
	default void stopAudio(String code) {
		fire(MediaPlugin.class, media -> media.stopAudio(code));
	}

}
