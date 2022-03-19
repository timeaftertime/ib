package cn.milai.ib.role.weapon.bullet;

import cn.milai.ib.container.Stage;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Health;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.base.BaseMovable;

/**
 * {@link Bullet} 的 {@link Movable}
 * @author milai
 * @date 2021.06.25
 */
public class BulletMovable extends BaseMovable {

	@Override
	public void afterMove() {
		if (outOfContainer()) {
			Health h = owner().getHealth();
			h.changeHP(null, -h.getHP());
		}
	}

	private boolean outOfContainer() {
		Role r = owner();
		Stage c = r.container();
		if (r.getX() > c.getW()) {
			return true;
		}
		if (r.getY() > c.getH()) {
			return true;
		}
		if (r.getX() + r.getW() < 0) {
			return true;
		}
		if (r.getY() + r.getH() < 0) {
			return true;
		}
		return false;
	}
}
