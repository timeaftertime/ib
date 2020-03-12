package cn.milai.ib.character.bullet;

import cn.milai.ib.character.property.CanCrash;
import cn.milai.ib.character.property.Shootable;
import cn.milai.ib.obj.Camp;

/**
 * 导弹
 * @author milai
 */
public class Missile extends AbstractBullet {

	public Missile(int x, int y, Shootable owner) {
		super(x, y, owner);
	}

	@Override
	public Camp getCamp() {
		return Camp.ENEMY_BULLET;
	}

	@Override
	public void onCrash(CanCrash crashed) {
		super.onCrash(crashed);
		toDead();
	}

}
