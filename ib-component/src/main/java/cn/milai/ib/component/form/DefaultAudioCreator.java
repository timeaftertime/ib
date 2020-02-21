package cn.milai.ib.component.form;

import java.io.InputStream;

import cn.milai.ib.container.Audio;
import cn.milai.ib.container.AudioCreator;
import javazoom.jl.decoder.JavaLayerException;

/**
 * AudioCreator 默认实现
 * 2020.01.25
 * @author milai
 */
public class DefaultAudioCreator implements AudioCreator {

	public Audio newAudio(String code, InputStream in) {
		try {
			return new DefaultAudio(code, in);
		} catch (JavaLayerException e) {
			e.printStackTrace();
			return null;
		}
	}
}
