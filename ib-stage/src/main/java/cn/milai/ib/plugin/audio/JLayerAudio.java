package cn.milai.ib.plugin.audio;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * 基于 JLayer 的 {@link Audio} 实现
 * 2020.01.25
 * @author milai
 */
public class JLayerAudio implements Audio {

	private final String code;
	private static final int FRAME_PER_PLAY = 8;
	private Player player;

	public JLayerAudio(String code, InputStream in) throws JavaLayerException {
		this.code = code;
		player = createPlayer(in);
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

	/**
	 * 使用指定 {@link InputStream} 构造一个 {@link Player}
	 * @param in
	 * @return
	 * @throws JavaLayerException
	 */
	protected Player createPlayer(InputStream in) throws JavaLayerException {
		return new Player(in);
	}

}
