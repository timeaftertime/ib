package cn.milai.ib.container.plugin.media;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * 音频默认实现
 * 2020.01.25
 * @author milai
 */
public class BaseAudio implements Audio {

	private final String code;
	private static final int FRAME_PER_PLAY = 8;
	private Player player;

	public BaseAudio(String code, InputStream in) throws JavaLayerException {
		this.code = code;
		player = new Player(in);
	}

	@Override
	public String getCode() { return code; }

	@Override
	public boolean play() {
		try {
			return player.play(FRAME_PER_PLAY);
		} catch (JavaLayerException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isComplete() { return player.isComplete(); }

}
