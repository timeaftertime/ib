package cn.milai.ib.role;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.milai.ib.AbstractIBObject;
import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.role.property.Property;

/**
 * IBCharacter 的抽象实现
 * @author milai
 */
public abstract class AbstractRole extends AbstractIBObject implements Role {

	private double direction;

	private int life;

	private Role lastAttacker;

	private Map<Class<? extends Property>, Property> properties = new ConcurrentHashMap<>();

	public AbstractRole(double x, double y, LifecycleContainer container,
		Class<? extends Role> configClass) {
		super(x, y, container, configClass);
		this.life = getInitLife();
		initProperties();
	}

	/**
	 * 初始化 {@link Property}
	 */
	protected void initProperties() {}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Property> T putProperty(Class<T> c, T p) {
		return (T) properties.put(c, p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Property> T getProperty(Class<T> c) {
		return (T) properties.get(c);
	}

	@Override
	public <T extends Property> boolean hasProperty(Class<T> c) {
		return properties.containsKey(c);
	}

	@Override
	public int getInitLife() { return intProp(P_LIFE); }

	public AbstractRole(double x, double y, LifecycleContainer container) {
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
	public synchronized void gainLife(Role character, int life) {
		if (life < 0) {
			throw new IllegalArgumentException("life 必须大于 0");
		}
		if (!isAlive()) {
			return;
		}
		this.life += life;
	}

	@Override
	public synchronized void loseLife(Role character, int life) throws IllegalArgumentException {
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
	public Role getLastAttacker() { return lastAttacker; }

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
