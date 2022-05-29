package cn.milai.ib.plugin.audio;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;

/**
 * 基于 Jlayer 的 {@link AudioCreator} 实现
 * 2020.01.25
 * @author milai
 */
public class JLayerAudioCreator implements AudioCreator {

	@Override
	public Audio newAudio(String code, InputStream in) {
		try {
			return new JLayerAudio(code, in);
		} catch (JavaLayerException e) {
			e.printStackTrace();
			return null;
		}
	}
}
