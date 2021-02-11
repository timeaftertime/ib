package cn.milai.ib.character.helper;

import cn.milai.ib.character.IBCharacter;
import cn.milai.ib.character.MovableIBCharacter;
import cn.milai.ib.container.lifecycle.LifecycleContainer;

/**
 * Helper 的抽象基类
 * @author milai
 */
public abstract class AbstractHelper extends MovableIBCharacter implements Helper {

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
	public synchronized void loseLife(IBCharacter character, int life) throws IllegalArgumentException {
		return;
	}

}
