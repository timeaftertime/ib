package cn.milai.ib.obj.character.bullet;

import cn.milai.ib.constant.Camp;
import cn.milai.ib.obj.character.plane.Plane;

/**
 * 竖直向下的子弹
 *
 * @author milai
 */
public class DownBullet extends Bullet {

	public DownBullet(int x, int y, Plane owner) {
		super(x, y, owner);
	}

	@Override
	public Camp getCamp() {
		return Camp.ENEMY_BULLET;
	}

}
