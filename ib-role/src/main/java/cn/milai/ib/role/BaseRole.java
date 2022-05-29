package cn.milai.ib.role;

import cn.milai.ib.actor.BaseActor;
import cn.milai.ib.actor.nature.Painter;
import cn.milai.ib.role.nature.Health;
import cn.milai.ib.role.nature.base.BaseHealth;
import cn.milai.ib.role.nature.base.BaseRolePainter;

/**
 * {@link Role} 默认实现
 * @author milai
 */
public class BaseRole extends BaseActor implements Role {

	private double direction;
	private boolean fixedBox = true;

	public BaseRole() {
		putNature(createPainter());
		setHealth(createHealth());
	}

	protected Painter createPainter() {
		return new BaseRolePainter(this);
	}

	protected Health createHealth() {
		return new BaseHealth(this);
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
	public boolean isFixedBox() { return fixedBox; }

	@Override
	public void setFixedBox(boolean fixedBox) { this.fixedBox = fixedBox; }

}
