package cn.milai.ib.plugin;

import cn.milai.ib.plugin.audio.Audio;
import cn.milai.ib.plugin.audio.AudioCrew;

/**
 * {@link Audio} 播放器
 * @author milai
 * @date 2022.05.15
 */
public interface AudioPluginable extends Pluginable<Crew> {

	/**
	 * 播放音频
	 * @param audio
	 */
	default void playAudio(Audio audio) {
		fire(AudioCrew.class, player -> player.playAudio(audio));
	}

	/**
	 * 停止音频的播放
	 * @param code
	 */
	default void stopAudio(String code) {
		fire(AudioCrew.class, player -> player.stopAudio(code));
	}
}
