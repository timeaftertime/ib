package cn.milai.ib.obj.bullet;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.obj.plane.Plane;
import cn.milai.ib.property.CanCrashed;

/**
 * 碰撞目标后爆炸的导弹
 *
 * @author milai
 */
public class Missile extends Bullet {

	private static final int WIDTH = SystemConf.prorate(14);
	private static final int HEIGHT = SystemConf.prorate(96);
	private static final int SPEED = SystemConf.prorate(50);

	private static final int POWER = 3;
	private static final int INIT_LIFE = Integer.MAX_VALUE;

	public Missile(int x, int y, Plane owner) {
		super(x, y, WIDTH, HEIGHT, 0, SPEED, POWER, INIT_LIFE, owner);
	}

	@Override
	public Camp getCamp() {
		return Camp.ENEMY_BULLET;
	}

	@Override
	public void onCrash(CanCrashed crashed) {
		super.onCrash(crashed);
		toDead();
	}

}
