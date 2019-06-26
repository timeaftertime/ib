package cn.milai.ib.client.game.obj.bullet;

import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.conf.gameprops.SpeedConf;
import cn.milai.ib.client.game.obj.plane.Plane;

public class EnemyBullet extends Bullet {

	private static final int INIT_LIFE = 1;

	public EnemyBullet(int x, int y, Plane owner) {
		super(x, y, SizeConf.ENEMY_BULLET_WIDTH, SizeConf.ENEMY_BULLET_HEIGHT, 0, SpeedConf.NORMAL_BULLET_SPEED,
				BattleConf.ENEMY_BULLET_POWER, INIT_LIFE, ImageConf.ENEMY_BULLET, owner);
	}

	@Override
	public Camp getCamp() {
		return Camp.ENEMY_BULLET;
	}

}
