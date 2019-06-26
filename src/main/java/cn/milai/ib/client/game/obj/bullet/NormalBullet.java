package cn.milai.ib.client.game.obj.bullet;

import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.conf.gameprops.SpeedConf;
import cn.milai.ib.client.game.obj.plane.Plane;

public class NormalBullet extends Bullet {

	private static final int INIT_LIFE = 1;

	public NormalBullet(int x, int y, Plane owner) {
		super(x, y, SizeConf.NORMAL_BULLET_WIDTH, SizeConf.NORMAL_BULLET_HEIGHT, 0, -SpeedConf.NORMAL_BULLET_SPEED,
				INIT_LIFE, BattleConf.NORMAL_BULLET_POWER, ImageConf.NORMAL_BULLET, owner);
	}

	@Override
	public Camp getCamp() {
		return Camp.PLAYER_BULLET;
	}

}
