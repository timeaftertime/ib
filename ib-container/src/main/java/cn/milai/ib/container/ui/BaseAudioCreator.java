package cn.milai.ib.container.ui;

import java.io.InputStream;

import cn.milai.ib.container.ui.Audio;
import cn.milai.ib.container.ui.AudioCreator;
import javazoom.jl.decoder.JavaLayerException;

/**
 * AudioCreator 默认实现
 * 2020.01.25
 * @author milai
 */
public class BaseAudioCreator implements AudioCreator {

	public Audio newAudio(String code, InputStream in) {
		try {
			return new BaseAudio(code, in);
		} catch (JavaLayerException e) {
			e.printStackTrace();
			return null;
		}
	}
}
