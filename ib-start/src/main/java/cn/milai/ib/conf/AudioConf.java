package cn.milai.ib.conf;

import cn.milai.ib.component.form.DefaultAudioCreator;
import cn.milai.ib.container.Audio;
import cn.milai.ib.container.AudioCreator;
import cn.milai.ib.loader.DramaResourceLoader;

public final class AudioConf {

	private AudioConf() {
	}

	private static final AudioCreator audioCreator = new DefaultAudioCreator();

	private static final String DRAMA_CODE = "cn.milai.ib.Welcome";

	public static final String BGM_CODE = "BGM";
	public static final String BOMB_CODE = "BOMB";

	public static Audio newBombAudio() {
		return audioCreator.newAudio(BOMB_CODE, DramaResourceLoader.load(DRAMA_CODE, "/audio/bomb.mp3"));
	}

	public static Audio newBGMAudio() {
		return audioCreator.newAudio(BGM_CODE, DramaResourceLoader.load(BGM_CODE, "/audio/bg.mp3"));
	}
}
