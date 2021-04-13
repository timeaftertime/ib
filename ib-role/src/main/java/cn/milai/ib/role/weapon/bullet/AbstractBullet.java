package cn.milai.ib.role.weapon.bullet;

import cn.milai.ib.Paintable;
import cn.milai.ib.role.Camp;
import cn.milai.ib.role.MovableRole;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.Collider;
import cn.milai.ib.role.property.Movable;
import cn.milai.ib.role.property.base.BaseCollider;
import cn.milai.ib.role.property.base.BaseDamage;
import cn.milai.ib.role.property.base.BaseRotatable;

/**
 * 子弹类游戏对象的抽象基类
 * @author milai
 */
public abstract class AbstractBullet extends MovableRole implements Bullet {

	private Role owner;

	protected AbstractBullet(double centerX, double centerY, Role owner) {
		super(centerX, centerY, owner.getContainer());
		this.owner = owner;
		setRotatable(new BaseRotatable(this));
		Movable m = movable();
		setDirection(owner.getDirection());
		m.setSpeedX(m.getRatedSpeedX() * Math.sin(owner.getDirection()));
		m.setSpeedY(-m.getRatedSpeedY() * Math.cos(owner.getDirection()));
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setDamage(new BaseDamage(this, intConf(P_POWER)));
		setCollider(new BaseCollider(this) {
			@Override
			public void onCollided(Collider crashed) {
				if (!canCrashWith(crashed)) {
					return;
				}
				crashed.getRole().loseLife(AbstractBullet.this, damage().getValue());
			}
		});
	}

	/**
	 * 是否与(不同 {@link Camp} 的) 指定 {@link Collider} 进行碰撞检测
	 * @param crashed
	 * @return
	 */
	protected boolean canCrashWith(Collider crashed) {
		return !(crashed.getRole() instanceof Bullet);
	}

	@Override
	protected void afterMove(Movable m) {
		if (outOfContainer()) {
			toDead();
		}
	}

	@Override
	public Role getOwner() { return owner; }

	private boolean outOfContainer() {
		if (this.getX() > getContainer().getW()) {
			return true;
		}
		if (this.getY() > getContainer().getH()) {
			return true;
		}
		if (this.getX() + getW() < 0) {
			return true;
		}
		if (this.getY() + this.getH() < 0) {
			return true;
		}
		return false;
	}

	@Override
	public int getCamp() { return owner.getCamp(); }

	@Override
	public int getZ() { return Paintable.DEFAULT_Z - 1; }
}
