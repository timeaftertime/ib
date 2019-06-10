package cn.milai.ib.client.game.conf;

import java.awt.Image;
import java.awt.Toolkit;

import cn.milai.ib.client.game.InfinityBattle;

public final class ImageConf {

	private ImageConf() {}
	
	private static final Toolkit tk = Toolkit.getDefaultToolkit();
	
	public static final Image BATTLE_BG;
	public static final Image PLAYER;
	public static final Image NORMAL_BULLET;
	public static final Image ENEMY_BULLET;
	public static final Image[] ENEMY;
	public static final Image BOMB;
	public static final Image WELCOME_PLAYER;
	public static final Image START_BG;
	
	static {
		BATTLE_BG =tk.createImage(InfinityBattle.class.getResource("/img/battle_bg.jpg"));
		PLAYER = tk.createImage(InfinityBattle.class.getResource("/img/player.gif"));
		NORMAL_BULLET = tk.createImage(InfinityBattle.class.getResource("/img/normal_bullet.gif"));
		ENEMY_BULLET = tk.createImage(InfinityBattle.class.getResource("/img/enemy_bullet.gif"));
		ENEMY = new Image[]{
				tk.createImage(InfinityBattle.class.getResource("/img/enemy_1.gif")),
				tk.createImage(InfinityBattle.class.getResource("/img/enemy_2.gif"))
		};
		BOMB = tk.createImage(InfinityBattle.class.getResource("/img/bomb.gif"));
		WELCOME_PLAYER = tk.createImage(InfinityBattle.class.getResource("/img/welcome_player.gif"));
		START_BG = tk.createImage(InfinityBattle.class.getResource("/img/start_bg.gif"));
	}
	
}
