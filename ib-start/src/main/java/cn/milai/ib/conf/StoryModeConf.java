package cn.milai.ib.conf;

import cn.milai.ib.conf.gameprops.SizeConf;

public final class StoryModeConf {

	private StoryModeConf() {
	}

	// 位置
	public static final int INIT_PLAYER_POS_X = FormSizeConf.BATTLE_WIDTH / 2 + SizeConf.PLAYER_WIDTH / 2;
	public static final int INIT_PLAYER_POS_Y = FormSizeConf.BATTLE_HEIGHT - SizeConf.PLAYER_HEIGHT;

}
