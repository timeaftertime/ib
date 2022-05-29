package cn.milai.ib.role.weapon.bullet;

import cn.milai.ib.role.BaseRole;
import cn.milai.ib.role.Camp;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.nature.Collider;
import cn.milai.ib.role.nature.Movable;
import cn.milai.ib.role.nature.base.BaseCollider;
import cn.milai.ib.role.nature.base.BaseDamage;
import cn.milai.ib.stage.Stage;

/**
 * {@link Bullet} 抽象实现
 * @author milai
 * @date 2021.06.25
 */
public abstract class AbstractBullet extends BaseRole implements Bullet {

	private Role owner;

	protected AbstractBullet(Role owner) {
		this.owner = owner;
		setMovable(new BulletMovable(this));
		setDamage(new BaseDamage(this));
		setFixedBox(false);
		setCollider(new BaseCollider(this) {
			@Override
			public void onCollided(Collider crashed) {
				if (!canCrashWith(crashed)) {
					return;
				}
				crashed.owner().getHealth().changeHP(AbstractBullet.this, -getDamage().getValue());
			}
		});
	}

	@Override
	protected void onEnterStage(Stage stage) {
		Movable m = getMovable();
		setDirection(owner.getDirection());
		m.setSpeedX(m.getRatedSpeedX() * Math.sin(owner.getDirection()));
		m.setSpeedY(-m.getRatedSpeedY() * Math.cos(owner.getDirection()));
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
