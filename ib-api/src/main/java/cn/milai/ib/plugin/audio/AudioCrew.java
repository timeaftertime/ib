package cn.milai.ib.plugin.audio;

import cn.milai.ib.plugin.Crew;
import cn.milai.ib.plugin.ExclusiveCrew;

/**
 * 用于播放媒体的 {@link Crew}
 * @author milai
 * @date 2021.02.09
 */
public interface AudioCrew extends ExclusiveCrew {

	/**
	 * 播放音频。若参数为 null 将忽略
	 * @param audio
	 */
	void playAudio(Audio audio);

	/**
	 * 停止一个音频的播放
	 * @param code
	 */
	void stopAudio(String code);

	/**
	 * 清空待播放列表
	 */
	void clearAudio();

}
