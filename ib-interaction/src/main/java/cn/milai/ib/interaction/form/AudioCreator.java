package cn.milai.ib.interaction.form;

import java.io.InputStream;

import cn.milai.ib.util.ByteUtil;
import javazoom.jl.decoder.JavaLayerException;

/**
 * 音频实例构造器
 * 2020.01.25
 * @author milai
 */
public class AudioCreator {

	private String code;
	private byte[] data;

	public AudioCreator(String code, InputStream in) {
		this.code = code;
		this.data = ByteUtil.toBytes(in);
	}

	/**
	 * 获取一个音频实例，若构造失败，返回 null
	 * @return
	 */
	public Audio newAudio() {
		try {
			return new DefaultAudio(code, ByteUtil.toInputStream(data));
		} catch (JavaLayerException e) {
			e.printStackTrace();
			return null;
		}
	}
}
