package cn.milai.ib.character.bullet;

import cn.milai.ib.character.property.Shootable;
import cn.milai.ib.obj.Camp;

/**
 * 竖直向下的子弹
 * @author milai
 */
public class DownBullet extends AbstractBullet {

	public DownBullet(int x, int y, Shootable owner) {
		super(x, y, owner);
	}

	@Override
	public Camp getCamp() {
		return Camp.ENEMY_BULLET;
	}

}
