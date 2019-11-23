package cn.milai.ib;

public final class AudioConf {

	private AudioConf() {
	}

	public static AudioPlayer BOMB = new AudioPlayer(AudioConf.class.getResourceAsStream("/audio/bomb.mp3"));
	public static AudioPlayer SHOOT = new AudioPlayer(AudioConf.class.getResourceAsStream("/audio/shoot.mp3"));
	public static AudioPlayer ENDLESS_BG = new AudioPlayer(AudioConf.class.getResourceAsStream("/audio/bg.mp3"));

}
