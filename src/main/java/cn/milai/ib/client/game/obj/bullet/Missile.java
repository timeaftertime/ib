package cn.milai.ib.client.game.obj.bullet;

import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.conf.gameprops.SpeedConf;
import cn.milai.ib.client.game.obj.plane.Plane;
import cn.milai.ib.client.game.obj.property.CanCrashed;

public class Missile extends Bullet {

	private static final int INIT_LIFE = Integer.MAX_VALUE;

	public Missile(int x, int y, Plane owner) {
		super(x, y, SizeConf.MISSILE_WIDTH, SizeConf.MISSILE_HEIGHT, 0, SpeedConf.MISSILE_SPEED,
				BattleConf.MISSILE_POWER, INIT_LIFE, ImageConf.MISSILE, owner);
	}

	@Override
	public void onCrash(CanCrashed crashed) {
		super.onCrash(crashed);
		toDead();
	}

	@Override
	public Camp getCamp() {
		return Camp.ENEMY_BULLET;
	}

}
