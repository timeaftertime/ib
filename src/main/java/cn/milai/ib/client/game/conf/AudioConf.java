package cn.milai.ib.client.game.conf;

import cn.milai.ib.client.game.AudioPlayer;
import cn.milai.ib.client.game.InfinityBattle;

public final class AudioConf {

	private AudioConf() {
	}
	
	public static AudioPlayer BOMB = new AudioPlayer(InfinityBattle.class.getResourceAsStream("/audio/bomb.mp3"));
	public static AudioPlayer SHOOT = new AudioPlayer(InfinityBattle.class.getResourceAsStream("/audio/shoot.mp3"));
	public static AudioPlayer ENDLESS_BG = new AudioPlayer(InfinityBattle.class.getResourceAsStream("/audio/bg.mp3"));

}
