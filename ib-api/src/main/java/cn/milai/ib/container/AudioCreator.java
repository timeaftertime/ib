package cn.milai.ib.container;

import java.io.InputStream;

/**
 * 音频实例构造器
 * @author milai
 * @date 2020.02.20
 */
public interface AudioCreator {

	/**
	 * 构造一个新的 Audio 实例
	 * @param code
	 * @param in
	 * @return
	 */
	Audio newAudio(String code, InputStream in);
}
