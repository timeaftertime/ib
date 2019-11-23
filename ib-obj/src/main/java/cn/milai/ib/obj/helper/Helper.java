package cn.milai.ib.obj.helper;

import cn.milai.ib.constant.Camp;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.MovableGameEntity;
import cn.milai.ib.obj.plane.Plane;
import cn.milai.ib.property.CanCrash;
import cn.milai.ib.property.CanCrashed;
import cn.milai.ib.property.HasDamage;

/**
 * 援助道具
 * 
 * @author milai
 *
 */
public abstract class Helper extends MovableGameEntity implements CanCrash {

	public Helper(int x, int y, int width, int height, int speedX, int speedY, int life, Container container) {
		super(x, y, width, height, speedX, speedY, life, container);
	}

	@Override
	public void onCrash(CanCrashed crashed) {
		if (!(crashed instanceof Plane)) {
			return;
		}
		toDead();
		makeFunction((Plane) crashed);
	}

	public abstract void makeFunction(Plane plane);

	@Override
	public int getScore() {
		return 0;
	}

	@Override
	protected void afterMove() {
		removeIfOutOfOwner();
	}

	private void removeIfOutOfOwner() {
		if (getY() > getContainer().getHeight())
			getContainer().removeGameObject(this);
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		throw new IllegalStateException("Helper 不接受攻击");
	}

	@Override
	public Camp getCamp() {
		return Camp.HELPER;
	}

}