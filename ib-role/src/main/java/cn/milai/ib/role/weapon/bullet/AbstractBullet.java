package cn.milai.ib.role.weapon.bullet;

import cn.milai.ib.role.BaseRole;
import cn.milai.ib.role.Camp;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Collider;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.base.BaseCollider;
import cn.milai.ib.role.property.base.BaseDamage;
import cn.milai.ib.role.property.base.BaseRotatable;

/**
 * {@link Bullet} 抽象实现
 * @author milai
 * @date 2021.06.25
 */
public abstract class AbstractBullet extends BaseRole implements Bullet {

	private Role owner;

	protected AbstractBullet(Role owner) {
		this.owner = owner;
		Movable m = getMovable();
		setDirection(owner.getDirection());
		m.setSpeedX(m.getRatedSpeedX() * Math.sin(owner.getDirection()));
		m.setSpeedY(-m.getRatedSpeedY() * Math.cos(owner.getDirection()));

		setDamage(new BaseDamage());
		setRotatable(new BaseRotatable());
		setCollider(new BaseCollider() {
			@Override
			public void onCollided(Collider crashed) {
				if (!canCrashWith(crashed)) {
					return;
				}
				crashed.owner().getHealth().changeHP(AbstractBullet.this, -getDamage().getValue());
			}
		});
	}

	/**
	 * 是否与(不同 {@link Camp} 的) 指定 {@link Collider} 进行碰撞检测
	 * @param crashed
	 * @return
	 */
	protected boolean canCrashWith(Collider crashed) {
		return !(crashed.owner() instanceof Bullet);
	}

	@Override
	public Role getOwner() { return owner; }

	@Override
	public int getCamp() { return owner.getCamp(); }

	@Override
	public int getZ() { return super.getZ() - 1; }

}
