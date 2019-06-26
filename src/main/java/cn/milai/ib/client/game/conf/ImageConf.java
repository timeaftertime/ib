package cn.milai.ib.client.game.conf;

import java.awt.Image;
import java.awt.Toolkit;

import cn.milai.ib.client.game.InfinityBattle;

public final class ImageConf {

	private ImageConf() {
	}

	private static final Toolkit tk = Toolkit.getDefaultToolkit();

	public static final Image BATTLE_BG;
	public static final Image PLAYER;
	public static final Image PLAYER_ACCELERATE;
	public static final Image NORMAL_BULLET;
	public static final Image ENEMY_BULLET;
	public static final Image[] ENEMY;
	public static final Image BOMB;
	public static final Image WELCOME_PLAYER;
	public static final Image START_BG;
	public static final Image GAME_OVER;
	public static final Image RESTART_BUTTON;
	public static final Image One_LIFE_HELPER;
	public static final Image ACCELERATE_HELPER;
	public static final Image MISSILE_BOSS;
	public static final Image MISSILE_BOSS_DANGER;
	public static final Image MISSILE;

	static {
		BATTLE_BG = tk.createImage(InfinityBattle.class.getResource("/img/battle_bg.jpg"));
		PLAYER = tk.createImage(InfinityBattle.class.getResource("/img/plane/player.gif"));
		PLAYER_ACCELERATE = tk.createImage(InfinityBattle.class.getResource("/img/plane/player_accelerate.gif"));
		NORMAL_BULLET = tk.createImage(InfinityBattle.class.getResource("/img/bullet/normal_bullet.gif"));
		ENEMY_BULLET = tk.createImage(InfinityBattle.class.getResource("/img/bullet/enemy_bullet.gif"));
		ENEMY = new Image[] { tk.createImage(InfinityBattle.class.getResource("/img/plane/enemy_1.gif")),
				tk.createImage(InfinityBattle.class.getResource("/img/plane/enemy_2.gif")) };
		BOMB = tk.createImage(InfinityBattle.class.getResource("/img/bomb.gif"));
		WELCOME_PLAYER = tk.createImage(InfinityBattle.class.getResource("/img/plane/welcome_player.gif"));
		START_BG = tk.createImage(InfinityBattle.class.getResource("/img/start_bg.gif"));
		GAME_OVER = tk.createImage(InfinityBattle.class.getResource("/img/game_over.gif"));
		RESTART_BUTTON = tk.createImage(InfinityBattle.class.getResource("/img/restart_button.gif"));
		One_LIFE_HELPER = tk.createImage(InfinityBattle.class.getResource("/img/helper/one_life_helper.gif"));
		ACCELERATE_HELPER = tk.createImage(InfinityBattle.class.getResource("/img/helper/accelerate_helper.gif"));
		MISSILE_BOSS = tk.createImage(InfinityBattle.class.getResource("/img/plane/missile_boss.gif"));
		MISSILE_BOSS_DANGER = tk.createImage(InfinityBattle.class.getResource("/img/plane/missile_boss_danger.gif"));
		MISSILE = tk.createImage(InfinityBattle.class.getResource("/img/bullet/missile.gif"));
	}

}
