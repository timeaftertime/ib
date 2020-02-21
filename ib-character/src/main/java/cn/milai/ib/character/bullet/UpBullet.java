package cn.milai.ib.character.bullet;

import cn.milai.ib.character.property.Shootable;
import cn.milai.ib.constant.Camp;

/**
 * 简单向上的子弹
 * 2019.11.21
 * @author milai
 */
public class UpBullet extends AbstractBullet {

	public UpBullet(int x, int y, Shootable owner) {
		super(x, y, owner);
	}

	@Override
	public Camp getCamp() {
		return Camp.PLAYER_BULLET;
	}

}
