package cn.milai.ib.role.helper;

import cn.milai.ib.role.BaseRole;
import cn.milai.ib.role.PlayerRole;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Collider;
import cn.milai.ib.role.property.Health;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.base.BaseCollider;

/**
 * {@link Helper} 抽象实现
 * @author milai
 */
public abstract class AbstractHelper extends BaseRole implements Helper {

	public AbstractHelper() {
		setMovable(new HelperMovable());
		setCollider(new BaseCollider() {
			@Override
			public void onCollided(Collider crashed) {
				Role r = crashed.owner();
				if (!(r instanceof PlayerRole)) {
					return;
				}
				getHealth().changeHP(AbstractHelper.this, -getHealth().getHP());
				makeFunction((PlayerRole) r);
				System.out.println();
			}
		});
	}

	@Override
	protected void initItem() {
		Movable m = getMovable();
		m.setSpeedX(m.getRatedSpeedX());
		m.setSpeedY(m.getRatedSpeedY());
	}

	@Override
	protected Health createHealth() {
		return new HelperHealth();
	}

}
