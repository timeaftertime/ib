package cn.milai.ib.character.bullet;

import cn.milai.ib.character.plane.Plane;
import cn.milai.ib.constant.Camp;

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
