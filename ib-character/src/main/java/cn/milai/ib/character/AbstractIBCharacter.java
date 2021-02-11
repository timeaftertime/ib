package cn.milai.ib.character;

import cn.milai.ib.AbstractIBObject;
import cn.milai.ib.container.lifecycle.LifecycleContainer;

/**
 * IBCharacter 的抽象实现
 * @author milai
 */
public abstract class AbstractIBCharacter extends AbstractIBObject implements IBCharacter {

	private double direction;

	private int life;

	private IBCharacter lastAttacker;

	public AbstractIBCharacter(double x, double y, LifecycleContainer container,
		Class<? extends IBCharacter> configClass) {
		super(x, y, container, configClass);
		this.life = getInitLife();
	}

	@Override
	public int getInitLife() { return intProp(P_LIFE); }

	public AbstractIBCharacter(double x, double y, LifecycleContainer container) {
		this(x, y, container, null);
	}

	@Override
	public boolean isAlive() { return life > 0; }

	@Override
	public synchronized void toDead() {
		if (!isAlive()) {
			return;
		}
		lastAttacker = null;
		life = 0;
	}

	@Override
	public synchronized void gainLife(IBCharacter character, int life) {
		if (life < 0) {
			throw new IllegalArgumentException("life 必须大于 0");
		}
		if (!isAlive()) {
			return;
		}
		this.life += life;
	}

	@Override
	public synchronized void loseLife(IBCharacter character, int life) throws IllegalArgumentException {
		if (life < 0) {
			throw new IllegalArgumentException("life 必须大于 0");
		}
		if (!isAlive()) {
			return;
		}
		this.life -= life;
		if (this.life < 0) {
			this.life = 0;
		}
		lastAttacker = character;
	}

	@Override
	public IBCharacter getLastAttacker() { return lastAttacker; }

	@Override
	public int getLife() { return life; }

	@Override
	public double getDirection() { return direction; }

	@Override
	public void setDirection(double direction) {
		if (direction > Math.PI * 2) {
			direction -= Math.PI * 2 * (int) (direction / Math.PI / 2);
		} else if (direction < -Math.PI * 2) {
			direction += Math.PI * 2 * (int) (-direction / Math.PI / 2);
		}
		if (direction > Math.PI) {
			direction -= 2 * Math.PI;
		} else if (direction <= -Math.PI) {
			direction += 2 * Math.PI;
		}
		this.direction = direction;
	}

	@Override
	public void ensureInContainer() {
		ensureIn(0, getContainer().getW(), 0, getContainer().getH());
	}

	@Override
	public LifecycleContainer getContainer() { return (LifecycleContainer) super.getContainer(); }

}
