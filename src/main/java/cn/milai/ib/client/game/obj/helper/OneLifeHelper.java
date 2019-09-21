package cn.milai.ib.client.game.obj.helper;

import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.conf.gameprops.SpeedConf;
import cn.milai.ib.client.game.form.BattleForm;
import cn.milai.ib.client.game.obj.plane.Plane;

public class OneLifeHelper extends Helper {

	private static final int GAIN_LIFE = 1;
	private static final int INIT_LIFE = 1;

	public OneLifeHelper(int x, int y, BattleForm container) {
		super(x, y, SizeConf.ONE_LIFE_HELPER_WIDTH, SizeConf.ONE_LIFE_HELPER_HEIGHT,
				SpeedConf.ONE_LIFE_HELPER_SPEED_X, SpeedConf.ONE_LIFE_HELPER_SPEED_Y, INIT_LIFE, ImageConf.One_LIFE_HELPER,
				container);
	}

	@Override
	public void makeFunction(Plane plane) {
		plane.gainLife(GAIN_LIFE);
	}

}
