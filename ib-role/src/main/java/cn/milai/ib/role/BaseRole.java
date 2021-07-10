package cn.milai.ib.role;

import cn.milai.ib.container.lifecycle.LifecycleContainer;
import cn.milai.ib.item.BaseItem;
import cn.milai.ib.item.BasePainter;
import cn.milai.ib.item.property.Painter;
import cn.milai.ib.role.property.Health;
import cn.milai.ib.role.property.base.BaseHealth;

/**
 * {@link Role} 默认实现
 * @author milai
 */
public class BaseRole extends BaseItem implements Role {

	private double direction;

	public BaseRole() {
		setPainter(createPainter());
		setHealth(createHealth());
	}

	protected Painter createPainter() {
		return new BasePainter();
	}

	protected Health createHealth() {
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
