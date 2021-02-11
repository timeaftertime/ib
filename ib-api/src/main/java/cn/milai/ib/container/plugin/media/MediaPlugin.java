package cn.milai.ib.container.plugin.media;

import cn.milai.ib.container.ContainerClosedException;
import cn.milai.ib.container.plugin.ContainerPlugin;

/**
 * 用于播放媒体的 {@link ContainerPlugin}
 * @author milai
 * @date 2021.02.09
 */
public interface MediaPlugin extends ContainerPlugin {
	
	/**
	 * 播放音频。若参数为 null 将忽略
	 * @param audio
	 * @throws ContainerClosedException
	 */
	void playAudio(Audio audio) throws ContainerClosedException;

	/**
	 * 停止一个音频的播放
	 * @param code
	 * @throws ContainerClosedException
	 */
	void stopAudio(String code) throws ContainerClosedException;

}