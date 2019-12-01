package cn.milai.ib.conf;

import java.awt.Image;

import cn.milai.ib.InfinityBattle;
import cn.milai.ib.util.ImageUtil;

public final class ImageConf {

	private ImageConf() {
	}

	public static final Image BATTLE_BG;
	public static final Image START_BG;

	static {
		BATTLE_BG = ImageUtil.loadImage(InfinityBattle.class.getResource("/img/battle_bg.jpg"));
		START_BG = ImageUtil.loadImage(InfinityBattle.class.getResource("/img/start_bg.gif"));
	}

}
