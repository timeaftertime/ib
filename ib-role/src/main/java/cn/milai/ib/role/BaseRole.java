package cn.milai.ib.role;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.obj.BaseObject;
import cn.milai.ib.obj.BasePainter;
import cn.milai.ib.obj.property.Painter;
import cn.milai.ib.role.property.Health;
import cn.milai.ib.role.property.base.BaseHealth;

/**
 * {@link Role} 默认实现
 * @author milai
 */
public class BaseRole extends BaseObject implements Role {

	private double direction;

	public BaseRole() {
		setPainter(initPainter());
		setHealth(initHealth());
	}

	protected Painter initPainter() {
		return new BasePainter();
	}

	protected Health initHealth() {
		return new BaseHealth();
	}

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
	public LifecycleContainer container() {
		return (LifecycleContainer) super.container();
	}

}
