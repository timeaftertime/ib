package cn.milai.ib.conf;

import cn.milai.ib.interaction.form.AudioCreator;

public final class AudioConf {

	private AudioConf() {
	}

	public static final String BGM_CODE = "BGM";
	public static final String BOMB_CODE = "BOMB";

	public static final AudioCreator BOMB = new AudioCreator(BOMB_CODE, AudioConf.class.getResourceAsStream("/audio/bomb.mp3"));
	public static final AudioCreator BGM = new AudioCreator(BGM_CODE, AudioConf.class.getResourceAsStream("/audio/bg.mp3"));

}
