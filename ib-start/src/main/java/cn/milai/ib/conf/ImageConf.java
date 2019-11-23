package cn.milai.ib.conf;

import java.awt.Image;

import cn.milai.ib.InfinityBattle;
import cn.milai.ib.util.ImageUtil;

public final class ImageConf {

	private ImageConf() {
	}

	public static final Image BATTLE_BG;
	public static final Image START_BG;
	public static final Image GAME_OVER;
	public static final Image RESTART;

	static {
		BATTLE_BG = ImageUtil.loadImage(InfinityBattle.class.getResource("/img/battle_bg.jpg"));
		START_BG = ImageUtil.loadImage(InfinityBattle.class.getResource("/img/start_bg.gif"));
		GAME_OVER = ImageUtil.loadImage(InfinityBattle.class.getResource("/img/game_over.gif"));
		RESTART = ImageUtil.loadImage(InfinityBattle.class.getResource("/img/restart_button.gif"));
	}

}
