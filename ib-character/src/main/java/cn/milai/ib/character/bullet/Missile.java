package cn.milai.ib.character.bullet;

import cn.milai.ib.character.plane.Plane;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.property.CanCrashed;

/**
 * 碰撞目标后爆炸的导弹
 *
 * @author milai
 */
public class Missile extends Bullet {

	public Missile(int x, int y, Plane owner) {
		super(x, y, owner);
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
