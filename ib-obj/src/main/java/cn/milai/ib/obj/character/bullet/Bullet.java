package cn.milai.ib.obj.character.bullet;

import cn.milai.ib.constant.Camp;
import cn.milai.ib.obj.character.MovableGameEntity;
import cn.milai.ib.obj.character.plane.Plane;
import cn.milai.ib.property.Alive;
import cn.milai.ib.property.CanCrash;
import cn.milai.ib.property.CanCrashed;
import cn.milai.ib.property.HasDamage;

/**
 * 子弹的抽象基类
 *
 * @author milai
 */
public abstract class Bullet extends MovableGameEntity implements CanCrash, CanCrashed, HasDamage {

	private static final String P_POWER = "power";

	private Plane owner;
	private int power;

	protected Bullet(int centerX, int centerY, Plane owner) {
		super(centerX, centerY, owner.getContainer());
		// 实例化之后修改 X 坐标以使 centerX 位于对象中心
		setX(getX() - getWidth() / 2);
		this.power = intProp(P_POWER);
		this.owner = owner;
	}

	@Override
	protected void afterMove() {
		if (outOfContainer()) {
			this.toDead();
		}
	}

	private boolean outOfContainer() {
		if (this.getX() > getContainer().getWidth()) {
			return true;
		}
		if (this.getY() > getContainer().getHeight()) {
			return true;
		}
		if (this.getX() + getWidth() < 0) {
			return true;
		}
		if (this.getY() + this.getHeight() < 0) {
			return true;
		}
		return false;
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		if (!isAlive()) {
			return;
		}
		synchronized (this) {
			loseLife(attacker.getDamage());
		}
	}

	@Override
	public Camp getCamp() {
		return owner.getCamp();
	}

	@Override
	public void onKill(Alive alive) {
		owner.onKill(alive);
	}

	@Override
	public int getScore() {
		return 0;
	}

	@Override
	public void onCrash(CanCrashed crashed) {
		crashed.damagedBy(this);
	}

	@Override
	public int getDamage() {
		return power;
	}
}
