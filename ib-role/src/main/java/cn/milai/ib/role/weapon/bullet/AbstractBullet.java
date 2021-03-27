package cn.milai.ib.role.weapon.bullet;

import cn.milai.ib.Paintable;
import cn.milai.ib.role.MovableRole;
import cn.milai.ib.role.Role;
import cn.milai.ib.role.property.CanCrash;

/**
 * 子弹类游戏对象的抽象基类
 * @author milai
 */
public abstract class AbstractBullet extends MovableRole implements Bullet {

	public static final String P_SPEED = "speed";

	private Role owner;

	protected AbstractBullet(double centerX, double centerY, Role owner) {
		super(centerX, centerY, owner.getContainer());
		this.owner = owner;
		setDirection(owner.getDirection());
		setSpeedX(getSpeed() * Math.sin(owner.getDirection()));
		setSpeedY(-getSpeed() * Math.cos(owner.getDirection()));
	}

	@Override
	public Role getOwner() { return owner; }

	@Override
	protected void afterMove() {
		if (outOfContainer()) {
			toDead();
		}
	}

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
	protected double initRatedSpeedX() { return getSpeed(); }

	@Override
	protected double initRatedSpeedY() { return getSpeed(); }

	/**
	 * 获取子弹前进速度
	 * @return
	 */
	protected double getSpeed() { return doubleProp(P_SPEED); }

	@Override
	public int getCamp() { return owner.getCamp(); }

	@Override
	public void onCrash(CanCrash crashed) {
		if (crashed instanceof Bullet) {
			return;
		}
		crashed.loseLife(this, getDamage());
	}

	@Override
	public int getDamage() { return intProp(P_POWER); }

	@Override
	public int getZ() { return Paintable.DEFAULT_Z - 1; }
}
