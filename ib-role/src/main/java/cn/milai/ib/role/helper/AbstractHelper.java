package cn.milai.ib.role.helper;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.role.MovableRole;
import cn.milai.ib.role.PlayerRole;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Collider;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.base.BaseCollider;

/**
 * Helper 的抽象基类
 * @author milai
 */
public abstract class AbstractHelper extends MovableRole implements Helper {

	public AbstractHelper(double x, double y, LifecycleContainer container) {
		super(x, y, container);
		setCollider(new BaseCollider(this) {
			@Override
			public void onCrash(Collider crashed) {
				Role r = crashed.getRole();
				if (!(r instanceof PlayerRole)) {
					return;
				}
				toDead();
				makeFunction((PlayerRole) r);
			}
		});
		Movable m = movable();
		m.setSpeedX(m.getRatedSpeedX());
		m.setSpeedY(m.getRatedSpeedY());
	}

	@Override
	protected void afterMove(Movable m) {
		if (getIntY() > getContainer().getH()) {
			getContainer().removeObject(this);
		}
	}

	@Override
	public synchronized void loseLife(Role character, int life) throws IllegalArgumentException {
		return;
	}

}
