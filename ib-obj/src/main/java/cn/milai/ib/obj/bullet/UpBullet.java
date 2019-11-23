package cn.milai.ib.obj.bullet;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.obj.plane.Plane;

/**
 * 简单向上的子弹
 *
 * 2019.11.21
 *
 * @author milai
 */
public class UpBullet extends Bullet {

	private static final int WIDTH = SystemConf.prorate(10);
	private static final int HEIGHT = SystemConf.prorate(30);
	private static final int SPEED = SystemConf.prorate(25);

	private static final int INIT_LIFE = 1;
	private static final int POWER = 1;

	public UpBullet(int x, int y, Plane owner) {
		super(x, y, WIDTH, HEIGHT, 0, -SPEED, INIT_LIFE, POWER, owner);
	}

	@Override
	public Camp getCamp() {
		return Camp.PLAYER_BULLET;
	}

}
