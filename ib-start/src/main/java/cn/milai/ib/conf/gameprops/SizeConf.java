package cn.milai.ib.conf.gameprops;

import cn.milai.ib.conf.SystemConf;

public final class SizeConf {

	private SizeConf() {
	}

	// 游戏对象大小
	public static final int PLAYER_WIDTH = SystemConf.prorate(36);
	public static final int PLAYER_HEIGHT = SystemConf.prorate(54);
	public static final int NORMAL_BULLET_WIDTH = SystemConf.prorate(10);
	public static final int NORMAL_BULLET_HEIGHT = SystemConf.prorate(30);
	public static final int BOMB_WIDTH = SystemConf.prorate(48);
	public static final int BOMB_HEIGHT = SystemConf.prorate(48);
	public static final int WELCOME_PLAYER_WIDTH = SystemConf.prorate(72);
	public static final int WELCOME_PLAYER_HEIGHT = SystemConf.prorate(48);
	public static final int GAME_OVER_LABEL_WIDTH = SystemConf.prorate(672);
	public static final int GAME_OVER_LABEL_HEIGHT = SystemConf.prorate(120);
	public static final int RESTART_BUTTON_WIDTH = SystemConf.prorate(144);
	public static final int RESTART_BUTTON_HEIGHT = SystemConf.prorate(36);
	public static final int ONE_LIFE_HELPER_WIDTH = SystemConf.prorate(36);
	public static final int ONE_LIFE_HELPER_HEIGHT = SystemConf.prorate(36);
	public static final int ACCELERATE_HELPER_WIDTH = SystemConf.prorate(36);
	public static final int ACCELERATE_HELPER_HEIGHT = SystemConf.prorate(54);

}
