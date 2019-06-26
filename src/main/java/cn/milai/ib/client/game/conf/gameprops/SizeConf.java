package cn.milai.ib.client.game.conf.gameprops;

import cn.milai.ib.client.game.conf.SystemConf;

public final class SizeConf {

	private SizeConf() {
	}

	// 游戏对象大小
	public static final int PLAYER_WIDTH = SystemConf.sizeProrate(30);
	public static final int PLAYER_HEIGHT = SystemConf.sizeProrate(45);
	public static final int NORMAL_BULLET_WIDTH = SystemConf.sizeProrate(8);
	public static final int NORMAL_BULLET_HEIGHT = SystemConf.sizeProrate(25);
	public static final int ENEMY_BULLET_WIDTH = SystemConf.sizeProrate(8);
	public static final int ENEMY_BULLET_HEIGHT = SystemConf.sizeProrate(25);
	public static final int ENEMY_WIDTH = SystemConf.sizeProrate(40);
	public static final int ENEMY_HEIGHT = SystemConf.sizeProrate(40);
	public static final int BOMB_WIDTH = SystemConf.sizeProrate(40);
	public static final int BOMB_HEIGHT = SystemConf.sizeProrate(40);
	public static final int WELCOME_PLAYER_WIDTH = SystemConf.sizeProrate(60);
	public static final int WELCOME_PLAYER_HEIGHT = SystemConf.sizeProrate(40);
	public static final int GAME_OVER_LABEL_WIDTH = SystemConf.sizeProrate(560);
	public static final int GAME_OVER_LABEL_HEIGHT = SystemConf.sizeProrate(100);
	public static final int RESTART_BUTTON_WIDTH = SystemConf.sizeProrate(120);
	public static final int RESTART_BUTTON_HEIGHT = SystemConf.sizeProrate(30);
	public static final int ONE_LIFE_HELPER_WIDTH = SystemConf.sizeProrate(30);
	public static final int ONE_LIFE_HELPER_HEIGHT = SystemConf.sizeProrate(30);
	public static final int ACCELERATE_HELPER_WIDTH = SystemConf.sizeProrate(30);
	public static final int ACCELERATE_HELPER_HEIGHT = SystemConf.sizeProrate(45);
	public static final int MISSILE_BOSS_WIDTH = SystemConf.sizeProrate(100);
	public static final int MISSILE_BOSS_HEIGHT = SystemConf.sizeProrate(100);
	public static final int MISSILE_WIDTH = SystemConf.sizeProrate(12);
	public static final int MISSILE_HEIGHT = SystemConf.sizeProrate(80);

}
