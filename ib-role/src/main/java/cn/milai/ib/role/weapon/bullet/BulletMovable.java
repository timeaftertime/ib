package cn.milai.ib.role.weapon.bullet;

import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Health;
import cn.milai.ib.role.nature.Movable;
import cn.milai.ib.role.nature.base.BaseMovable;
import cn.milai.ib.stage.Stage;

/**
 * {@link Bullet} 的 {@link Movable}
 * @author milai
 * @date 2021.06.25
 */
public class BulletMovable extends BaseMovable {

	public BulletMovable(Role owner) {
		super(owner);
	}

	@Override
	public void afterMove() {
		if (outOfContainer()) {
			Health h = owner().getHealth();
			h.changeHP(null, -h.getHP());
		}
	}

	private boolean outOfContainer() {
		Role r = owner();
		Stage c = r.stage();
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
