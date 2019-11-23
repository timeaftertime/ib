package cn.milai.ib.obj.bullet;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.obj.plane.Plane;

/**
 * 竖直向下的子弹
 *
 * @author milai
 */
public class DownBullet extends Bullet {

	private static final int WIDTH = SystemConf.prorate(10);
	private static final int HEIGHT = SystemConf.prorate(30);
	private static final int SPEED = SystemConf.prorate(25);
	
	private static final int POWER = 1;
	private static final int INIT_LIFE = 1;

	public DownBullet(int x, int y, Plane owner) {
		super(x, y, WIDTH, HEIGHT, 0, SPEED, POWER, INIT_LIFE, owner);
	}

	@Override
	public Camp getCamp() {
		return Camp.ENEMY_BULLET;
	}

}
