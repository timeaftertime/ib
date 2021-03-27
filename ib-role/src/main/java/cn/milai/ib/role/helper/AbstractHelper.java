package cn.milai.ib.role.helper;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.role.MovableRole;
import cn.milai.ib.role.Role;

/**
 * Helper 的抽象基类
 * @author milai
 */
public abstract class AbstractHelper extends MovableRole implements Helper {

	public AbstractHelper(double x, double y, LifecycleContainer container) {
		super(x, y, container);
		setSpeedX(getRatedSpeedX());
		setSpeedY(getRatedSpeedY());
	}

	@Override
	protected void afterMove() {
		if (getIntY() > getContainer().getH()) {
			getContainer().removeObject(this);
		}
	}

	@Override
	public synchronized void loseLife(Role character, int life) throws IllegalArgumentException {
		return;
	}

}
