package cn.milai.ib.container.ui;

/**
 * 音频
 * 2020.01.25
 * @author milai
 */
public interface Audio {

	String BGM_CODE = "BGM";

	/**
	 * 音频的唯一标识，音频数据相同的所有实例将返回同一 code
	 * @return
	 */
	String getCode();

	/**
	 * 播放下一帧，方法将立即返回，返回是否已经将下一帧输出
	 * 若已经播放完毕将直接返回 true
	 * @return
	 */
	boolean play();

	/**
	 * 是否播放完毕
	 * @return
	 */
	boolean isComplete();

}
