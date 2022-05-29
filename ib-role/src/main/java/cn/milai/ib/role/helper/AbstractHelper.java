package cn.milai.ib.role.helper;

import cn.milai.ib.role.BaseRole;
import cn.milai.ib.role.PlayerRole;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Collider;
import cn.milai.ib.role.nature.Health;
import cn.milai.ib.role.nature.Movable;
import cn.milai.ib.role.nature.base.BaseCollider;

/**
 * {@link Helper} 抽象实现
 * @author milai
 */
public abstract class AbstractHelper extends BaseRole implements Helper {

	public AbstractHelper(double maxY) {
		setMovable(new HelperMovable(this, maxY));
		setCollider(new BaseCollider(this) {
			@Override
			public void onCollided(Collider crashed) {
				Role r = crashed.owner();
				if (!(r instanceof PlayerRole)) {
					return;
				}
				getHealth().changeHP(AbstractHelper.this, -getHealth().getHP());
				makeFunction((PlayerRole) r);
			}
		});
		onMakeUp(e -> {
			Movable m = getMovable();
			m.setSpeedX(m.getRatedSpeedX());
			m.setSpeedY(m.getRatedSpeedY());
		});
	}

	@Override
	protected Health createHealth() {
		return new HelperHealth(this);
	}

}
