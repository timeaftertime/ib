package cn.milai.ib.obj.character.bullet;

import cn.milai.ib.constant.Camp;
import cn.milai.ib.obj.character.plane.Plane;

/**
 * 简单向上的子弹
 *
 * 2019.11.21
 *
 * @author milai
 */
public class UpBullet extends Bullet {

	public UpBullet(int x, int y, Plane owner) {
		super(x, y, owner);
	}

	@Override
	public Camp getCamp() {
		return Camp.PLAYER_BULLET;
	}

}
